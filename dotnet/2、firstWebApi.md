要学习 **ASP.NET Core**（.NET 生态中对标 Spring Boot 的 Web 后端框架），可以按照以下步骤从“环境准备”到“第一个 Web 服务”快速入门：  


### 一、环境准备：安装 .NET SDK  
ASP.NET Core 基于 .NET SDK 运行，首先需要安装它：  
1. 打开 [.NET 官方下载页](https://dotnet.microsoft.com/en-us/download)；  
2. 根据操作系统（Windows/macOS/Linux）选择 **.NET 8 SDK**（长期支持版，推荐）下载并安装。  

安装完成后，打开终端/命令提示符，执行 `dotnet --version`，能看到版本号则说明安装成功。  


### 二、创建第一个 ASP.NET Core Web API 项目  
可以用 **Rider** 或 **命令行** 创建项目，以下是两种方式：  


#### 方式 1：用 Rider 创建（可视化操作）  
1. 打开 Rider，点击 `File → New → Solution`；  
2. 左侧选择 `ASP.NET Core → Web API`，右侧配置：  
   - **Target framework**：选 `.NET 8.0`；  
   - **Name**：输入项目名（如 `FirstWebApi`）；  
   - **Location**：选择项目保存路径；  
3. 点击 `Create`，Rider 会自动生成 ASP.NET Core Web API 模板代码。  


#### 方式 2：用命令行创建（更通用）  
打开终端，执行以下命令：  
```bash
dotnet new webapi -n FirstWebApi -f net8.0
cd FirstWebApi
```  
- `dotnet new webapi`：创建 Web API 模板项目；  
- `-n FirstWebApi`：指定项目名；  
- `-f net8.0`：指定目标框架为 .NET 8。  


### 三、理解项目结构（核心文件）  
生成的项目中，核心文件和目录作用：  
- `Program.cs`：**程序入口**，配置依赖注入、中间件管道（类似 Spring Boot 的 `Main` + 配置类）；  
- `Controllers` 目录：存放 API 控制器（类似 Spring Boot 的 `@RestController` 类）；  
- `appsettings.json`：配置文件（类似 Spring Boot 的 `application.yml`）；  
- `WeatherForecastController.cs`：模板自带的示例控制器，可参考它写自己的 API。  


### 四、编写第一个自定义 API 控制器  


在  Program.cs中添加 builder.Services.AddControllers() 和 app.MapControllers();
```csharp
var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
// Learn more about configuring OpenAPI at https://aka.ms/aspnet/openapi
builder.Services.AddOpenApi();
builder.Services.AddControllers();

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

app.Run();

internal record WeatherForecast(DateOnly Date, int TemperatureC, string? Summary)
{
    public int TemperatureF => 32 + (int)(TemperatureC / 0.5556);
}
```


在 `Controllers` 目录下，新建类 `HelloController.cs`，编写代码：  
```csharp
using Microsoft.AspNetCore.Mvc;

namespace FirstWebApi.Controllers;

// 路由：所有接口以 /api/hello 开头
[Route("api/[controller]")]
// 标识这是一个 API 控制器（会自动处理返回值为 JSON/XML 等）
[ApiController]
public class HelloController : ControllerBase
{
    // GET 请求：匹配 /api/hello 或 /api/hello/index
    [HttpGet]
    [HttpGet("index")]
    public IActionResult GetHello()
    {
        return Ok(new { Message = "Hello, ASP.NET Core!", Time = DateTime.Now });
    }

    // GET 请求：匹配 /api/hello/{name}（带路径参数）
    [HttpGet("{name}")]
    public IActionResult GetHelloByName(string name)
    {
        return Ok(new { Message = $"Hello, {name}!", Time = DateTime.Now });
    }
}
```  


### 五、运行项目并测试 API  
#### 方式 1：用 Rider 运行  
点击 Rider 顶部的 **绿色运行按钮**（三角形图标），项目会自动编译并启动。启动后，控制台会显示**接口地址**（默认是 `https://localhost:5001; http://localhost:5000`）。  


#### 方式 2：用命令行运行  
在项目根目录（`FirstWebApi` 文件夹）执行：  
```bash
dotnet run
```  
终端会输出接口地址，类似：  
```
Now listening on: https://localhost:5001
Now listening on: http://localhost:5000
```  


#### 测试 API（用浏览器/Postman）  
1. 访问 `GET https://localhost:5001/api/hello`：  
   会返回 JSON：`{"message":"Hello, ASP.NET Core!","time":"2025-09-03T12:34:56"}`。  

2. 访问 `GET https://localhost:5001/api/hello/John`：  
   会返回 JSON：`{"message":"Hello, John!","time":"2025-09-03T12:34:56"}`。  


### 六、核心概念对照（与 Spring Boot 类比）  
| Spring Boot 概念       | ASP.NET Core 对应       | 说明                                                                 |
|------------------------|-------------------------|----------------------------------------------------------------------|
| `@SpringBootApplication`（主入口） | `Program.cs`            | 配置服务注册、中间件管道（类似 Spring 的 `@Bean` + 启动流程）|
| `@RestController`       | `[ApiController]` + 继承 `ControllerBase` | 标识“返回 HTTP 响应（如 JSON）的控制器”|
| `@GetMapping`           | `[HttpGet]`             | 限定 HTTP 请求方法（还有 `[HttpPost]`/`[HttpPut]`/`[HttpDelete]` 等）|
| `@PathVariable`         | 方法参数 + 路由模板     | 从 URL 路径中获取参数（如 `[HttpGet("{name}")]` + `string name`）|
| `application.yml`       | `appsettings.json`      | 配置文件（支持环境区分，如 `appsettings.Development.json`）|
| 依赖注入（`@Autowired`） | 构造函数注入 / `[FromServices]` | ASP.NET Core 原生支持依赖注入，通过构造函数或特性注入服务           |  


### 七、下一步学习方向  
1. **依赖注入**：学习如何在 ASP.NET Core 中注册和注入服务（类似 Spring 的 `@Service`/`@Component`）；  
2. **数据访问**：集成 Entity Framework Core（.NET 的 ORM 框架，类似 MyBatis/JPA）操作数据库；  
3. **身份认证与授权**：学习 JWT、OAuth 等在 ASP.NET Core 中的实现；  
4. **部署**：将 ASP.NET Core 应用发布到服务器（如 Docker、Linux 守护进程、IIS 等）。  


通过这个入门流程，你已经能搭建并运行一个简单的 Web 后端服务，后续可以围绕“业务需求”逐步扩展功能～