using Microsoft.AspNetCore.Identity;

namespace web.Models;

// 扩展 IdentityUser，可添加自定义属性（如昵称、年龄等）
public class AppUser : IdentityUser
{
    public string? NickName { get; set; } // 示例：添加昵称
}