Program.cs
``` cs
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


// 启用CORS
app.UseCors("AllowAll");
```