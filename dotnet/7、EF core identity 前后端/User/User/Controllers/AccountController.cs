using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using User.Dtos;

namespace User.Controllers;



[ApiController]
[Route("[controller]")]
public class AccountController(SignInManager<Domain.User> signInManager, ILogger<AccountController> logger) : ControllerBase
{
    [AllowAnonymous]
    [HttpPost("register")]
    public async Task<ActionResult> RegisterUser(RegisterDto registerDto)
    {
        logger.LogInformation("RegisterUser, registerDto = " + registerDto);
        var user = new Domain.User
        {
            UserName = registerDto.Email,
            Email = registerDto.Email,
            DisplayName = registerDto.DisplayName
        };
        var result = await signInManager.UserManager.CreateAsync(user, registerDto.Password);
        if (result.Succeeded) return Ok();

        foreach (var error in result.Errors) ModelState.AddModelError(error.Code, error.Description);

        return ValidationProblem();
    }


    [AllowAnonymous]
    [HttpGet("user-info")]
    public async Task<ActionResult> GetUserInfo()
    {
        // if (User.Identity != null && !User.Identity.IsAuthenticated) return NotFound();
        if (User.Identity != null && !User.Identity.IsAuthenticated) return Unauthorized();
        
        var user = await signInManager.UserManager.GetUserAsync(User);
        if (user == null) return Unauthorized();

        return Ok(new
        {
            user.DisplayName,
            user.Email,
            user.Id,
            user.ImageUrl
        });
    }

    [HttpPost("logout")]
    public async Task<ActionResult> Logout()
    {
        await signInManager.SignOutAsync();
        return NoContent();
    }
}