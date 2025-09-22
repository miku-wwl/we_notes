using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;
using User.Domain;

namespace User.Persistence;


public class AppDbContext(DbContextOptions<AppDbContext> options) : IdentityDbContext<Domain.User>(options)
{
    
}
