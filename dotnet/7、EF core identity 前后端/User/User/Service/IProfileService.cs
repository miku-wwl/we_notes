using User.Core;
using User.Domain;

namespace User.Service;

public interface IProfileService
{
    public Task<Result<Photo>> AddPhoto(IFormFile file);
}