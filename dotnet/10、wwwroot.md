```
项目根目录/
├── wwwroot/              # 静态文件根目录
│   ├── index.html        # 默认首页
│   ├── about.html        # 关于页面
│   ├── contact.html      # 联系页面
│   ├── css/              # CSS文件夹
│   │   └── styles.css    # 样式表文件
│   ├── js/               # JavaScript文件夹
│   │   └── script.js     # JavaScript文件
│   └── images/           # 图像文件夹
│       └── example.jpg   # 示例图片（你需要自己添加一张图片）
├── Program.cs            # 程序入口
└── ...其他项目文件
```

当你运行ASP.NET Core 应用并正确配置了app.UseDefaultFiles()和app.UseStaticFiles()后，可以通过以下 URL 访问这些文件：

首页：http://localhost:端口号/ （会自动加载 index.html）
关于页面：http://localhost:端口号/about.html
联系页面：http://localhost:端口号/contact.html
CSS 文件：http://localhost:端口号/css/styles.css
JavaScript 文件：http://localhost:端口号/js/script.js
图片：http://localhost:端口号/images/example.jpg

Program.cs
``` cs
// 配置默认文件（如index.html）
app.UseDefaultFiles();
// 启用静态文件服务
app.UseStaticFiles();
```