using System.Threading.Tasks;
using Microsoft.AspNetCore.Identity;
using web.Models;

namespace web.Data;

public static class SeedData
{
    public static async Task Initialize(UserManager<AppUser> userManager, RoleManager<IdentityRole> roleManager)
    {
        // 创建角色
        var roles = new[] { "Admin", "User" };
        foreach (var role in roles)
        {
            if (!await roleManager.RoleExistsAsync(role))
            {
                await roleManager.CreateAsync(new IdentityRole(role));
            }
        }

        // 创建管理员用户（已验证邮箱）
        var adminEmail = "admin@example.com";
        var adminUser = await userManager.FindByEmailAsync(adminEmail);
        if (adminUser == null)
        {
            adminUser = new AppUser
            {
                UserName = adminEmail,
                Email = adminEmail,
                EmailConfirmed = true, // 已验证邮箱（跳过邮箱验证步骤）
                NickName = "超级管理员"
            };
            await userManager.CreateAsync(adminUser, "Admin123!"); // 密码需符合复杂度要求
            await userManager.AddToRoleAsync(adminUser, "Admin");
        }
    }
}