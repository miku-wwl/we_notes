要零基础学习 **Entity Framework Core (EF Core)** 并结合 SQLite 完成代码中的数据库操作，我们可以分 **6个核心步骤** 讲解，从“是什么”到“实现代码”逐步拆解：  


### 一、EF Core 是什么？（基础概念）  
EF Core 是 **.NET 平台的“对象关系映射（ORM）框架”**，它能让你用 **C# 类（对象）代替 SQL 语句** 操作数据库，核心优势是：  
- **不用写 SQL**：通过代码自动生成数据库表、插入/查询数据；  
- **跨数据库兼容**：同一套代码可切换 SQLite、MySQL、SQL Server 等数据库；  
- **自动管理关系**：支持实体间的关联（如一对多、多对多）。  


### 二、步骤 1：安装 dotnet-ef 和 Microsoft.EntityFrameworkCore.Sqlite
dotnet tool install --global dotnet-ef 

dotnet add package Microsoft.EntityFrameworkCore.Sqlite

### 三、步骤 2：定义“实体类”（对应数据库表的结构）   
```csharp
namespace FirstWebApi.Domain;

public class Activity
{
    public string Id { get; set; } = Guid.NewGuid().ToString();
    public required string Title { get; set; }
    public DateTime Date { get; set; }
    public required string Description { get; set; }
    public required string Category { get; set; }
    public bool IsCancelled { get; set; }

    // location props
    public required string City { get; set; }
    public required string Venue { get; set; }
    public double Latitude { get; set; }
    public double Longitude { get; set; }
}
```  


### 四、步骤 3：创建“数据库上下文（DbContext）”（EF Core 的核心类）  
```csharp
using FirstWebApi.Domain;
using Microsoft.EntityFrameworkCore;

namespace FirstWebApi.Persistency;

public class AppDbContext(DbContextOptions options) : DbContext(options)
{
    public required DbSet<Activity> Activities { get; set; }
}
```  


### 五、步骤 4：用“迁移”创建数据库表（EF Core 的核心功能）  
“迁移”是 EF Core 特有的功能，能**自动将实体类的结构转化为数据库表**，并记录变更历史（类似“数据库版本控制”）。  

#### 执行迁移命令（终端中执行）：  
1. **添加迁移**：生成“创建 Activities 表”的迁移文件  
   ```bash
   dotnet ef migrations add InitialCreate
   ```  

2. **更新数据库**：根据迁移文件创建实际的数据库表  
   ```bash
   dotnet ef database update
   ```  
   - 此命令会在指定路径（如 `activity.db`）创建 SQLite 数据库，并生成 `Activities` 表（结构与 `Activity` 类的属性一致）。  


### 六、步骤 5：编写“种子数据（Seed Data）
```csharp
using FirstWebApi.Domain;

namespace FirstWebApi.Persistency;

public class DbInitializer
{
    public static async Task SeedData(AppDbContext context)
    {
        if (context.Activities.Any()) return;

        var activities = new List<Activity>
        {
            new()
            {
                Title = "Past Activity 1",
                Date = DateTime.Now.AddMonths(-2),
                Description = "Activity 2 months ago",
                Category = "drinks",
                City = "London",
                Venue =
                    "The Lamb and Flag, 33, Rose Street, Seven Dials, Covent Garden, London, Greater London, England, WC2E 9EB, United Kingdom",
                Latitude = 51.51171665,
                Longitude = -0.1256611057818921
            },
            new()
            {
                Title = "Past Activity 2",
                Date = DateTime.Now.AddMonths(-1),
                Description = "Activity 1 month ago",
                Category = "culture",
                City = "Paris",
                Venue =
                    "Louvre Museum, Rue Saint-Honoré, Quartier du Palais Royal, 1st Arrondissement, Paris, Ile-de-France, Metropolitan France, 75001, France",
                Latitude = 48.8611473,
                Longitude = 2.33802768704666
            },
            new()
            {
                Title = "Future Activity 1",
                Date = DateTime.Now.AddMonths(1),
                Description = "Activity 1 month in future",
                Category = "culture",
                City = "London",
                Venue = "Natural History Museum",
                Latitude = 51.496510900000004,
                Longitude = -0.17600190725447445
            },
            new()
            {
                Title = "Future Activity 2",
                Date = DateTime.Now.AddMonths(2),
                Description = "Activity 2 months in future",
                Category = "music",
                City = "London",
                Venue = "The O2",
                Latitude = 51.502936649999995,
                Longitude = 0.0032029278126681844
            },
            new()
            {
                Title = "Future Activity 3",
                Date = DateTime.Now.AddMonths(3),
                Description = "Activity 3 months in future",
                Category = "drinks",
                City = "London",
                Venue = "The Mayflower",
                Latitude = 51.501778,
                Longitude = -0.053577
            },
            new()
            {
                Title = "Future Activity 4",
                Date = DateTime.Now.AddMonths(4),
                Description = "Activity 4 months in future",
                Category = "drinks",
                City = "London",
                Venue = "The Blackfriar",
                Latitude = 51.512146650000005,
                Longitude = -0.10364680647106028
            },
            new()
            {
                Title = "Future Activity 5",
                Date = DateTime.Now.AddMonths(5),
                Description = "Activity 5 months in future",
                Category = "culture",
                City = "London",
                Venue =
                    "Sherlock Holmes Museum, 221b, Baker Street, Marylebone, London, Greater London, England, NW1 6XE, United Kingdom",
                Latitude = 51.5237629,
                Longitude = -0.1584743
            },
            new()
            {
                Title = "Future Activity 6",
                Date = DateTime.Now.AddMonths(6),
                Description = "Activity 6 months in future",
                Category = "music",
                City = "London",
                Venue =
                    "Roundhouse, Chalk Farm Road, Maitland Park, Chalk Farm, London Borough of Camden, London, Greater London, England, NW1 8EH, United Kingdom",
                Latitude = 51.5432505,
                Longitude = -0.15197608174931165
            },
            new()
            {
                Title = "Future Activity 7",
                Date = DateTime.Now.AddMonths(7),
                Description = "Activity 2 months ago",
                Category = "travel",
                City = "London",
                Venue = "River Thames, England, United Kingdom",
                Latitude = 51.5575525,
                Longitude = -0.781404
            },
            new()
            {
                Title = "Future Activity 8",
                Date = DateTime.Now.AddMonths(8),
                Description = "Activity 8 months in future",
                Category = "film",
                City = "London",
                Venue = "River Thames, England, United Kingdom",
                Latitude = 51.5575525,
                Longitude = -0.781404
            }
        };

        context.Activities.AddRange(activities);

        await context.SaveChangesAsync();
    }
}
```  


### 七、步骤 6：在项目中“启用种子数据”（程序启动时调用）  

```csharp
using FirstWebApi.Persistency;
using Microsoft.EntityFrameworkCore;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
// Learn more about configuring OpenAPI at https://aka.ms/aspnet/openapi
builder.Services.AddOpenApi();
builder.Services.AddControllers();

// 注册AppDbContext
builder.Services.AddDbContext<AppDbContext>(opt =>
{
    opt.UseSqlite(builder.Configuration.GetConnectionString("DefaultConnection"));
});


var app = builder.Build();

// Configure the HTTP request pipeline.

if (app.Environment.IsDevelopment()) app.MapOpenApi();

app.UseHttpsRedirection();
app.MapControllers(); // 添加这一行！

var summaries = new[]
{
    "Freezing", "Bracing", "Chilly", "Cool", "Mild", "Warm", "Balmy", "Hot", "Sweltering", "Scorching"
};

app.MapGet("/weatherforecast", () =>
    {
        var forecast = Enumerable.Range(1, 5).Select(index =>
                new WeatherForecast
                (
                    DateOnly.FromDateTime(DateTime.Now.AddDays(index)),
                    Random.Shared.Next(-20, 55),
                    summaries[Random.Shared.Next(summaries.Length)]
                ))
            .ToArray();
        return forecast;
    })
    .WithName("GetWeatherForecast");


// 启用种子数据

using var scope = app.Services.CreateScope();
var services = scope.ServiceProvider;
try
{
    var context = services.GetRequiredService<AppDbContext>();
    await context.Database.MigrateAsync();
    await DbInitializer.SeedData(context);
}
catch (Exception ex)
{
    var logger = services.GetRequiredService<ILogger<Program>>();
    logger.LogError(ex, "An error occurred during migration.");
}


app.Run();

internal record WeatherForecast(DateOnly Date, int TemperatureC, string? Summary)
{
    public int TemperatureF => 32 + (int)(TemperatureC / 0.5556);
}
```  


### 八、最终效果验证  
完成以上步骤后，运行项目：  
1. EF Core 会自动创建 `activity.db` SQLite 数据库文件；  
2. 迁移会生成 `Activities` 表；  
3. `SeedData` 会自动插入 10 条左右的测试数据；  