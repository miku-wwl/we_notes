Program.cs
``` cs
builder.Services.AddSignalR();

// 配置CORS以允许前端调用
builder.Services.AddCors(options =>
{
  
  options.AddPolicy("AllowAll", policy =>
    {
        policy.WithOrigins("http://localhost:5500") 
            // 2. 允许 SignalR 所需的 HTTP 方法（WebSocket/长轮询等）
            .AllowAnyMethod()
            // 3. 允许 SignalR 通信所需的请求头
            .AllowAnyHeader()
            .AllowCredentials();
    });
});

app.MapHub<NotificationHub>("/notificationHub");

// 添加一个简单的API端点用于测试
app.MapPost("/api/notify", async (IHubContext<NotificationHub> hubContext, string user, string message) =>
{
    // 从API控制器发送通知
    await hubContext.Clients.All.SendAsync("ReceiveNotification", "系统", $"{user} 执行了操作: {message}");
    return Results.Ok();
});
```

SignalR\NotificationHub.cs
``` cs
using Microsoft.AspNetCore.SignalR;

namespace web.SignalR
{
    // 继承自Hub类，这是SignalR的核心
    public class NotificationHub : Hub
    {
        // 客户端可以调用这个方法来发送通知
        public async Task SendNotification(string user, string message)
        {
            // 向所有连接的客户端广播消息
            await Clients.All.SendAsync("ReceiveNotification", user, message);
        }
        
        // 向特定用户发送消息
        public async Task SendPrivateNotification(string user, string receiver, string message)
        {
            // 只向指定用户发送消息
            await Clients.User(receiver).SendAsync("ReceivePrivateNotification", user, message);
        }
        
        // 当客户端连接时调用
        public override async Task OnConnectedAsync()
        {
            // 获取连接ID
            var connectionId = Context.ConnectionId;
            Console.WriteLine($"客户端已连接: {connectionId}");
            
            // 可以在这里进行一些初始化操作
            await base.OnConnectedAsync();
        }
        
        // 当客户端断开连接时调用
        public override async Task OnDisconnectedAsync(Exception? exception)
        {
            var connectionId = Context.ConnectionId;
            Console.WriteLine($"客户端已断开连接: {connectionId}");
            
            await base.OnDisconnectedAsync(exception);
        }
    }
}

```

index.html
``` html
<!DOCTYPE html>
<html>
<head>
    <title>SignalR 通知示例</title>
    <style>
        .container { max-width: 800px; margin: 0 auto; padding: 20px; }
        #messages { border: 1px solid #ccc; height: 400px; overflow-y: auto; padding: 10px; margin-bottom: 20px; }
        .message { margin: 5px 0; padding: 8px; border-radius: 4px; }
        .public { background-color: #e3f2fd; }
        .private { background-color: #e8f5e9; }
        .system { background-color: #fff3e0; }
        input, button { margin-right: 10px; padding: 8px; }
    </style>
</head>
<body>
    <div class="container">
        <h1>实时通知系统</h1>
        
        <div>
            <input type="text" id="userInput" placeholder="你的名字" />
            <button onclick="connect()">连接到服务器</button>
            <span id="connectionStatus" style="margin-left: 10px;">未连接</span>
        </div>
        
        <div id="messages"></div>
        
        <div>
            <input type="text" id="messageInput" placeholder="输入消息" />
            <button onclick="sendPublicMessage()">发送公共消息</button>
        </div>
        
        <div>
            <input type="text" id="receiverInput" placeholder="接收者" />
            <input type="text" id="privateMessageInput" placeholder="私人消息" />
            <button onclick="sendPrivateMessage()">发送私人消息</button>
        </div>
    </div>

    <!-- 引入SignalR客户端库 -->
    <script src="https://cdn.jsdelivr.net/npm/@microsoft/signalr@7.0.0/dist/browser/signalr.min.js"></script>
    
    <script>
        let connection = null;
        const messagesList = document.getElementById('messages');
        const connectionStatus = document.getElementById('connectionStatus');
        
        // 连接到SignalR服务器
        function connect() {
            const userName = document.getElementById('userInput').value;
            if (!userName) {
                alert('请输入你的名字');
                return;
            }
            
            // 创建连接
            connection = new signalR.HubConnectionBuilder()
                .withUrl("http://localhost:5222/notificationHub") // 对应服务器端配置的路由
                .configureLogging(signalR.LogLevel.Information)
                .build();
            
            // 处理接收到的公共通知
            connection.on("ReceiveNotification", (user, message) => {
                addMessage(user, message, 'public');
            });
            
            // 处理接收到的私人通知
            connection.on("ReceivePrivateNotification", (user, message) => {
                addMessage(`[私人] ${user}`, message, 'private');
            });
            
            // 启动连接
            connection.start()
                .then(() => {
                    connectionStatus.textContent = "已连接";
                    connectionStatus.style.color = "green";
                    addMessage("系统", "成功连接到服务器", 'system');
                })
                .catch(err => {
                    console.error(err.toString());
                    connectionStatus.textContent = "连接失败";
                    connectionStatus.style.color = "red";
                });
        }
        
        // 发送公共消息
        function sendPublicMessage() {
            const userName = document.getElementById('userInput').value;
            const message = document.getElementById('messageInput').value;
            
            if (!connection) {
                alert('请先连接到服务器');
                return;
            }
            
            // 调用服务器端的SendNotification方法
            connection.invoke("SendNotification", userName, message)
                .catch(err => console.error(err.toString()));
                
            document.getElementById('messageInput').value = '';
        }
        
        // 发送私人消息
        function sendPrivateMessage() {
            const userName = document.getElementById('userInput').value;
            const receiver = document.getElementById('receiverInput').value;
            const message = document.getElementById('privateMessageInput').value;
            
            if (!connection) {
                alert('请先连接到服务器');
                return;
            }
            
            // 调用服务器端的SendPrivateNotification方法
            connection.invoke("SendPrivateNotification", userName, receiver, message)
                .catch(err => console.error(err.toString()));
                
            document.getElementById('privateMessageInput').value = '';
        }
        
        // 添加消息到界面
        function addMessage(user, message, type) {
            const now = new Date();
            const timeString = now.toLocaleTimeString();
            
            const messageElement = document.createElement('div');
            messageElement.className = `message ${type}`;
            messageElement.innerHTML = `<strong>${user}</strong> [${timeString}]: ${message}`;
            
            messagesList.appendChild(messageElement);
            // 滚动到底部
            messagesList.scrollTop = messagesList.scrollHeight;
        }
    </script>
</body>
</html>

```

vsc 安装Live Server
