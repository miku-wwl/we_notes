using web.Models;

namespace web.Service;

public class TodoService : ITodoService
{
    private static readonly List<TodoItem> _todos = new();
    private static int _nextId = 1;

    public Task<IEnumerable<TodoItem>> GetAllAsync()
    {
        return Task.FromResult(_todos.AsEnumerable());
    }

    public Task<TodoItem?> GetByIdAsync(int id)
    {
        var todo = _todos.FirstOrDefault(t => t.Id == id);
        return Task.FromResult(todo);
    }

    public Task<TodoItem?> CreateAsync(CreateTodoDto todo)
    {
        var newTodo = new TodoItem
        {
            Id = _nextId++,
            Name = todo.Name,
            Description = todo.Description,
            DueDate = todo.DueDate,
            IsComplete = false
        };

        _todos.Add(newTodo);
        return Task.FromResult(newTodo);
    }

    public Task<bool> UpdateAsync(int id, UpdateTodoDto todo)
    {
        var existingTodo = _todos.FirstOrDefault(t => t.Id == id);
        if (existingTodo == null)
        {
            return Task.FromResult(false);
        }

        if (todo.Name != null)
            existingTodo.Name = todo.Name;
        
        if (todo.IsComplete.HasValue)
            existingTodo.IsComplete = todo.IsComplete.Value;
        
        if (todo.Description != null)
            existingTodo.Description = todo.Description;
        
        if (todo.DueDate.HasValue)
            existingTodo.DueDate = todo.DueDate.Value;

        return Task.FromResult(true);
    }

    public Task<bool> PatchAsync(int id, PatchTodoDto todo)
    {
        var existingTodo = _todos.FirstOrDefault(t => t.Id == id);
        if (existingTodo == null)
        {
            return Task.FromResult(false);
        }

        if (todo.Name != null)
            existingTodo.Name = todo.Name;
        
        if (todo.IsComplete.HasValue)
            existingTodo.IsComplete = todo.IsComplete.Value;

        return Task.FromResult(true);
    }

    public Task<bool> DeleteAsync(int id)
    {
        var todo = _todos.FirstOrDefault(t => t.Id == id);
        if (todo == null)
        {
            return Task.FromResult(false);
        }

        _todos.Remove(todo);
        return Task.FromResult(true);
    }
}