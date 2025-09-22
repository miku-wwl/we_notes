using Microsoft.AspNetCore.Identity;
using User.Domain;

namespace User.Persistence;


public class DbInitializer
{
    public static async Task SeedData(AppDbContext context, UserManager<Domain.User> userManager)
    {
        var users = new List<Domain.User>
        {
            new() {Id = "bob-id", DisplayName = "Bob", UserName = "bob@test.com", Email = "bob@test.com"},
            new() {Id = "tom-id", DisplayName = "Tom", UserName = "tom@test.com", Email = "tom@test.com"},
            new() {Id = "jane-id", DisplayName = "Jane", UserName = "jane@test.com", Email = "jane@test.com"}
        };

        if (!userManager.Users.Any())
        {
            foreach (var user in users)
            {
                var result = await userManager.CreateAsync(user, "Pa$$w0rd");
                if (!result.Succeeded)
                {
                    // 输出错误信息以便调试
                    foreach (var error in result.Errors)
                    {
                        Console.WriteLine($"Error creating user: {error.Description}");
                    }
                }
            }
        }
    }
}