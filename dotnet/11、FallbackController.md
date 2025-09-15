Program.cs
``` cs
app.UseDefaultFiles();
app.UseStaticFiles();

// 最后配置fallback：所有未匹配的请求都回退到Fallback控制器的Index方法
app.MapFallbackToController("Index", "Fallback");

```


Controller\FallbackController.cs
``` cs
using Microsoft.AspNetCore.Mvc;

namespace web.Controller;

public class FallbackController : ControllerBase
{
    public IActionResult Index()
    {
        return PhysicalFile(Path.Combine(Directory.GetCurrentDirectory(), "wwwroot", "index.html"), "text/html");
    }
}
```

``` cs

wwwroot\index.html
``` html
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My SPA App</title>
    <!-- 引入前端框架（示例用简单JS模拟） -->
</head>
<body>
<h1>My SPA Application</h1>
<div id="route-content">首页内容</div>
<script>
    // 简单模拟前端路由（实际项目中会用React Router/Vue Router等）
    function handleClientRouting() {
        const path = window.location.pathname;
        const contentDiv = document.getElementById('route-content');

        switch(path) {
            case '/':
                contentDiv.textContent = '首页内容';
                break;
            case '/about':
                contentDiv.textContent = '关于我们页面';
                break;
            case '/user/123':
                contentDiv.textContent = '用户ID为123的详情页';
                break;
            default:
                contentDiv.textContent = '404 - 页面不存在（前端处理）';
        }
    }

    // 页面加载时处理路由
    window.onload = handleClientRouting;
</script>
</body>
</html>
```