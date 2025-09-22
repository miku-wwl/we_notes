using User.Core;
using User.Domain;

namespace User.Service;

public class ProfileService(ILogger<ProfileService> logger) : IProfileService
{
    public async Task<Result<Photo>> AddPhoto(IFormFile file)
    {
        
        logger.LogInformation(file.FileName);
        var photo = new Photo
        {
            Url = "www.baidu.com",
            PublicId = "123",
            UserId = "321"
        };
        return Result<Photo>.Success(photo);
    }
}