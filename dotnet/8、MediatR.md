web.csproj
``` xml
    <ItemGroup>
        <PackageReference Include="MediatR" Version="13.0.0" />
    </ItemGroup>
```


Program.cs
``` cs
// 注册MediatR，扫描当前程序集中的处理程序
builder.Services.AddMediatR(cfg => cfg.RegisterServicesFromAssembly(typeof(Program).Assembly));

```

Models\User.cs
``` cs
public class User
{
    public int Id { get; set; }
    public string Name { get; set; } = string.Empty;
    public string Email { get; set; } = string.Empty;
    public string Password { get; set; } = string.Empty;
}

public class UserDto
{
    public int Id { get; set; }
    public string Name { get; set; } = string.Empty;
    public string Email { get; set; } = string.Empty;
}

```

Data\UserData.cs
``` cs
using System.Collections.Generic;
using System.Collections.ObjectModel;

// 静态数据存储，替代数据库
public static class UserData
{
    // 静态列表存储用户数据
    public static readonly ICollection<User> Users = new Collection<User>
    {
        // 初始测试数据
        new User { Id = 1, Name = "张三", Email = "zhangsan@example.com", Password = "123456" },
        new User { Id = 2, Name = "李四", Email = "lisi@example.com", Password = "123456" }
    };
}

```


Queries\GetUsersQuery.cs
``` cs
using MediatR;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;

// 查询：获取用户列表
public class GetUsersQuery : IRequest<List<UserDto>>
{
    public int PageIndex { get; set; } = 1;
    public int PageSize { get; set; } = 10;
}

// 查询处理程序
public class GetUsersQueryHandler : IRequestHandler<GetUsersQuery, List<UserDto>>
{
    public async Task<List<UserDto>> Handle(GetUsersQuery request, CancellationToken cancellationToken)
    {
        // 使用静态列表获取数据
        var users = UserData.Users
            .Skip((request.PageIndex - 1) * request.PageSize)
            .Take(request.PageSize)
            .Select(u => new UserDto
            {
                Id = u.Id,
                Name = u.Name,
                Email = u.Email
            })
            .ToList();

        // 模拟异步操作
        return await Task.FromResult(users);
    }
}
```


Commands\CreateUserCommand.cs
``` cs
using MediatR;
using System.Threading;
using System.Threading.Tasks;

// 命令：创建用户
public class CreateUserCommand : IRequest<int>
{
    public string Name { get; set; } = string.Empty;
    public string Email { get; set; } = string.Empty;
    public string Password { get; set; } = string.Empty;
}

// 命令处理程序
public class CreateUserCommandHandler : IRequestHandler<CreateUserCommand, int>
{
    public async Task<int> Handle(CreateUserCommand request, CancellationToken cancellationToken)
    {
        // 生成新ID
        var newId = UserData.Users.Count > 0 ? UserData.Users.Max(u => u.Id) + 1 : 1;
        
        // 添加到静态列表
        UserData.Users.Add(new User
        {
            Id = newId,
            Name = request.Name,
            Email = request.Email,
            Password = request.Password // 实际项目中应加密存储
        });

        // 模拟异步操作
        return await Task.FromResult(newId);
    }
}
```

Controllers\UsersController.cs
``` cs
using MediatR;
using Microsoft.AspNetCore.Mvc;
using web.Commands;
using web.Models;
using web.Queries;

namespace web.Controller;

[ApiController]
[Route("api/[controller]")]
public class UsersController : ControllerBase
{
    private readonly IMediator _mediator;

    public UsersController(IMediator mediator)
    {
        _mediator = mediator;
    }

    // 获取用户列表
    [HttpGet]
    public async Task<ActionResult<List<UserDto>>> GetUsers(
        [FromQuery] int pageIndex = 1, 
        [FromQuery] int pageSize = 10,
        CancellationToken cancellationToken = default)
    {
        var query = new GetUsersQuery 
        { 
            PageIndex = pageIndex, 
            PageSize = pageSize 
        };
        
        var result = await _mediator.Send(query, cancellationToken);
        return Ok(result);
    }
    
    // 创建新用户
    [HttpPost]
    public async Task<ActionResult<int>> CreateUser(
        [FromBody] CreateUserCommand command,
        CancellationToken cancellationToken = default)
    {
        // 发送命令创建用户
        var userId = await _mediator.Send(command, cancellationToken);
        return CreatedAtAction(nameof(GetUsers), new { id = userId }, userId);
    }
    
}
```