using System.Collections.Generic;
using System.Threading.Tasks;
using web.Models;

namespace web.Service;

public interface ITodoService
{
    Task<IEnumerable<TodoItem>> GetAllAsync();
    Task<TodoItem?> GetByIdAsync(int id);
    Task<TodoItem?> CreateAsync(CreateTodoDto todo);
    Task<bool> UpdateAsync(int id, UpdateTodoDto todo);
    Task<bool> PatchAsync(int id, PatchTodoDto todo);
    Task<bool> DeleteAsync(int id);
}