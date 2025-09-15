下面我将通过一个完整案例讲解 ASP.NET Core Identity 的 `AddIdentityApiEndpoints` 配置及使用，并提供详细的测试方法。


### 案例场景
我们将创建一个简单的用户认证系统，支持：
- 用户注册（需唯一邮箱）
- 邮箱验证后才能登录
- 角色管理（如管理员、普通用户）
- 基于 JWT 的身份认证
- 受保护的 API 接口（需登录/特定角色）


### 步骤 1：项目准备
1. 创建 ASP.NET Core Web API 项目（.NET 8+）
2. 安装 NuGet 包：
   ```bash
   Install-Package Microsoft.AspNetCore.Identity.EntityFrameworkCore
   Install-Package Microsoft.AspNetCore.Identity.UI
   Install-Package Microsoft.EntityFrameworkCore
   Install-Package Microsoft.EntityFrameworkCore.Design
   Install-Package Microsoft.EntityFrameworkCore.Sqlite  # 或其他数据库提供商

   ```


### 步骤 2：核心代码实现

#### 1. 自定义用户模型（`Models/AppUser.cs`）
继承 IdentityUser 扩展用户属性（可选）：
```csharp
using Microsoft.AspNetCore.Identity;

namespace IdentityDemo.Models;

// 扩展 IdentityUser，可添加自定义属性（如昵称、年龄等）
public class AppUser : IdentityUser
{
    public string? NickName { get; set; } // 示例：添加昵称
}
```

#### 2. 数据库上下文（`Data/AppDbContext.cs`）
集成 Identity 数据库上下文：
```csharp
using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;
using IdentityDemo.Models;

namespace IdentityDemo.Data;

public class AppDbContext : IdentityDbContext<AppUser>
{
    public AppDbContext(DbContextOptions<AppDbContext> options) : base(options) { }
}
```

#### 3. 服务配置（`Program.cs`）
```csharp
using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using IdentityDemo.Data;
using IdentityDemo.Models;

var builder = WebApplication.CreateBuilder(args);

// 1. 配置数据库连接（示例用 SQL Server，可替换为 SQLite 等）
builder.Services.AddDbContext<AppDbContext>(options =>
    options.UseSqlServer(builder.Configuration.GetConnectionString("DefaultConnection")));

// 2. 配置 Identity API 端点
builder.Services.AddIdentityApiEndpoints<AppUser>(options =>
    {
        // 用户配置：邮箱必须唯一
        options.User.RequireUniqueEmail = true;

        // 登录配置：必须验证邮箱才能登录
        options.SignIn.RequireConfirmedEmail = true;

        // 密码复杂度配置（可选）
        options.Password.RequireDigit = true;
        options.Password.RequireLowercase = true;
        options.Password.RequiredLength = 6;
    })
    .AddRoles<IdentityRole>() // 启用角色管理
    .AddEntityFrameworkStores<AppDbContext>(); // 指定数据库存储

// 3. 配置控制器和全局授权（可选）
builder.Services.AddControllers();

// 4. 其他服务（如 Swagger 用于测试）
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var app = builder.Build();

// 5. 迁移数据库（开发环境自动迁移，生产环境建议手动执行）
if (app.Environment.IsDevelopment())
{
    using var scope = app.Services.CreateScope();
    var dbContext = scope.ServiceProvider.GetRequiredService<AppDbContext>();
    var userManager = scope.ServiceProvider.GetRequiredService<UserManager<AppUser>>();
    var roleManager = scope.ServiceProvider.GetRequiredService<RoleManager<IdentityRole>>();
    await dbContext.Database.MigrateAsync(); // 应用迁移
    await SeedData.Initialize(userManager, roleManager); // 初始化测试数据（见下文）
}

// 6. 中间件配置
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();

// 启用认证和授权中间件（必须在 MapControllers 之前）
app.UseAuthentication();
app.UseAuthorization();

// 7. 映射 Identity 预设 API 端点（如 /api/register, /api/login 等）
app.MapGroup("/api")
   .MapIdentityApi<AppUser>();

// 8. 映射自定义控制器
app.MapControllers();

app.Run();
```

#### 4. 初始化测试数据（`Data/SeedData.cs`）
创建默认角色和测试用户：
```csharp
using Microsoft.AspNetCore.Identity;
using IdentityDemo.Models;

namespace IdentityDemo.Data;

public static class SeedData
{
    public static async Task Initialize(UserManager<AppUser> userManager, RoleManager<IdentityRole> roleManager)
    {
        // 创建角色
        var roles = new[] { "Admin", "User" };
        foreach (var role in roles)
        {
            if (!await roleManager.RoleExistsAsync(role))
            {
                await roleManager.CreateAsync(new IdentityRole(role));
            }
        }

        // 创建管理员用户（已验证邮箱）
        var adminEmail = "admin@example.com";
        var adminUser = await userManager.FindByEmailAsync(adminEmail);
        if (adminUser == null)
        {
            adminUser = new AppUser
            {
                UserName = adminEmail,
                Email = adminEmail,
                EmailConfirmed = true, // 已验证邮箱（跳过邮箱验证步骤）
                NickName = "超级管理员"
            };
            await userManager.CreateAsync(adminUser, "Admin123!"); // 密码需符合复杂度要求
            await userManager.AddToRoleAsync(adminUser, "Admin");
        }
    }
}
```

#### 5. 自定义受保护 API（`Controllers/ProfileController.cs`）
```csharp
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using IdentityDemo.Models;

namespace IdentityDemo.Controllers;

[ApiController]
[Route("api/[controller]")]
[Authorize] // 需登录才能访问
public class ProfileController : ControllerBase
{
    private readonly UserManager<AppUser> _userManager;

    public ProfileController(UserManager<AppUser> userManager)
    {
        _userManager = userManager;
    }

    // 获取当前用户信息（所有登录用户可访问）
    [HttpGet]
    public async Task<IActionResult> GetProfile()
    {
        var user = await _userManager.GetUserAsync(User);
        if (user == null) return Unauthorized();

        return Ok(new
        {
            user.Id,
            user.UserName,
            user.Email,
            user.NickName,
            Roles = await _userManager.GetRolesAsync(user)
        });
    }

    // 管理员专属接口（仅 Admin 角色可访问）
    [HttpGet("admin")]
    [Authorize(Roles = "Admin")]
    public IActionResult AdminOnly()
    {
        return Ok("只有管理员能看到这条消息");
    }
}
```

#### 6. 配置数据库连接字符串（`appsettings.json`）
```json
{
  "ConnectionStrings": {
    "DefaultConnection": "Data Source=IdentityDemoDb.db;Cache=Shared"
  }
}
```

使用数据库迁移指令:
``` bash
   dotnet ef migrations add InitialCreate
   dotnet ef migrations add InitialCreate --project ../Persistence --startup-project .
   dotnet ef database update
```

### 步骤 3：测试方法（使用 Swagger 或 Postman）

#### 1. 测试前准备
- 启动项目，访问 `https://localhost:端口号/swagger` 打开 Swagger 界面
- 数据库会自动创建（基于代码中的 `MigrateAsync`）


#### 2. 核心接口测试流程

##### （1）用户注册（无需登录）
- **请求**：`POST /api/register`  
- **请求体**：
  ```json
  {
    "email": "user@example.com",
    "password": "User123!", // 需符合密码复杂度
    "userName": "user123"
  }
  ```
- **响应**：200 OK（返回用户 ID）  
- **说明**：此时用户邮箱未验证，无法登录（因配置了 `RequireConfirmedEmail = true`）


##### （2）模拟邮箱验证（开发环境快捷方式）
由于真实邮箱验证需要邮件服务，这里用代码直接标记邮箱为已验证（生产环境需通过邮件链接实现）：
- 在 `SeedData.Initialize` 中添加测试用户时设置 `EmailConfirmed = true`  
- 或通过自定义接口手动验证（略）


##### （3）用户登录（需已验证邮箱）
- **请求**：`POST /api/login`  
- **请求体**：
  ```json
  {
    "email": "admin@example.com", // 用 SeedData 中创建的管理员账号
    "password": "Admin123!"
  }
  ```
- **响应**：返回 JWT 令牌（包含 `access_token`、`refresh_token` 等）：
  ```json
  {
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refreshToken": "CfDJ8...",
    "expiresIn": 3600,
    "tokenType": "Bearer"
  }
  ```


##### （4）访问受保护接口（需携带令牌）
- **请求**：`GET /api/profile`  
- **请求头**：`Authorization: Bearer {accessToken}`（替换为登录返回的令牌）  
- **响应**：返回当前用户信息（包含角色）


##### （5）访问管理员专属接口
- **请求**：`GET /api/profile/admin`  
- **请求头**：同上（使用管理员令牌）  
- **响应**：返回 "只有管理员能看到这条消息"  
- **测试失败场景**：用普通用户令牌访问会返回 403 禁止访问


##### （6）刷新令牌（当 access_token 过期时）
- **请求**：`POST /api/refresh`  
- **请求体**：
  ```json
  {
    "refreshToken": "登录时返回的 refresh_token"
  }
  ```
- **响应**：返回新的 `access_token`