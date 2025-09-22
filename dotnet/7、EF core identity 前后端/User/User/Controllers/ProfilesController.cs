using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using User.Core;
using User.Domain;
using User.Service;

namespace User.Controllers;

[ApiController]
[Route("[controller]")]
    public class ProfilesController(IProfileService profileService,   ILogger<ProfilesController> logger) : ControllerBase
{
    [AllowAnonymous]
    [HttpPost("add-photo")]
    public async Task<ActionResult<Photo>> AddPhoto(IFormFile file)
    {
        return Result<Photo>.HandleResult(await profileService.AddPhoto(file));
    }
}