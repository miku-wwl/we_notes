在web.csproj 添加
    
    <ItemGroup>
        <PackageReference Include="Microsoft.AspNetCore.OpenApi" Version="9.0.8"/>
        <PackageReference Include="Swashbuckle.AspNetCore" Version="9.0.4"/>
    </ItemGroup>

``` xml
<Project Sdk="Microsoft.NET.Sdk.Web">

    <PropertyGroup>
        <TargetFramework>net9.0</TargetFramework>
        <Nullable>enable</Nullable>
        <ImplicitUsings>enable</ImplicitUsings>
    </PropertyGroup>

    <ItemGroup>
        <PackageReference Include="Microsoft.AspNetCore.OpenApi" Version="9.0.8"/>
        <PackageReference Include="Swashbuckle.AspNetCore" Version="9.0.4"/>
    </ItemGroup>

</Project>
```


在Program.cs 中添加

``` csharp
// 添加Swagger文档支持
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}
```


``` csharp
using web.Service;

var builder = WebApplication.CreateBuilder(args);

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

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

// 启用CORS
app.UseCors("AllowAll");

app.UseHttpsRedirection();
app.MapControllers();

app.Run();
```

访问 http://localhost:5222/swagger/index.html