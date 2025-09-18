using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using web.Models;

namespace web.Controller;

[ApiController]
[Route("api/[controller]")]
[Authorize] // 需登录才能访问
public class ProfileController : ControllerBase
{
    private readonly UserManager<AppUser> _userManager;

    public ProfileController(UserManager<AppUser> userManager)
    {
        _userManager = userManager;
    }

    // 获取当前用户信息（所有登录用户可访问）
    [HttpGet]
    public async Task<IActionResult> GetProfile()
    {
        var user = await _userManager.GetUserAsync(User);
        if (user == null) return Unauthorized();

        return Ok(new
        {
            user.Id,
            user.UserName,
            user.Email,
            user.NickName,
            Roles = await _userManager.GetRolesAsync(user)
        });
    }

    // 管理员专属接口（仅 Admin 角色可访问）
    [HttpGet("admin")]
    [Authorize(Roles = "Admin")]
    public IActionResult AdminOnly()
    {
        return Ok("只有管理员能看到这条消息");
    }
}