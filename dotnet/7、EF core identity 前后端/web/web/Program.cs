using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Routing;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using web.Data;
using web.Models;
using web.Service;

var builder = WebApplication.CreateBuilder(args);

// 1. 配置数据库连接（示例用 SQL Server，可替换为 SQLite 等）
builder.Services.AddDbContext<AppDbContext>(options =>
    options.UseSqlite(builder.Configuration.GetConnectionString("DefaultConnection")));

// 2. 配置 Identity API 端点
builder.Services.AddIdentityApiEndpoints<AppUser>(options =>
    {
        // 用户配置：邮箱必须唯一
        options.User.RequireUniqueEmail = true;

        // 登录配置：必须验证邮箱才能登录
        options.SignIn.RequireConfirmedEmail = false;

        // 密码复杂度配置（可选）
        options.Password.RequireDigit = false;
        options.Password.RequireLowercase = false;
        options.Password.RequireNonAlphanumeric = false;
        options.Password.RequireUppercase = false;
        
        options.Password.RequiredLength = 3;
    })
    .AddRoles<IdentityRole>() // 启用角色管理
    .AddEntityFrameworkStores<AppDbContext>(); // 指定数据库存储




// Add services to the container.
// Learn more about configuring OpenAPI at https://aka.ms/aspnet/openapi
builder.Services.AddOpenApi();

// 添加服务
builder.Services.AddControllers();
builder.Services.AddScoped<ITodoService, TodoService>();

// 添加Swagger文档支持
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

// 配置CORS以允许前端调用
builder.Services.AddCors(options =>
{
    options.AddPolicy("AllowAll", policy =>
    {
        policy.AllowAnyOrigin()
            .AllowAnyMethod()
            .AllowAnyHeader();
    });
});

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

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

// 启用CORS
app.UseCors("AllowAll");

app.UseHttpsRedirection();

// 启用认证和授权中间件（必须在 MapControllers 之前）
app.UseAuthentication();
app.UseAuthorization();

// 7. 映射 Identity 预设 API 端点（如 /api/register, /api/login 等）
app.MapGroup("/api")
    .MapIdentityApi<AppUser>();

app.MapControllers();

app.Run();