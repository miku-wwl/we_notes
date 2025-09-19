using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using web.Models;
using web.Service;

namespace web.Controller;


[Route("api/[controller]")]
[ApiController]
public class TodosController(ITodoService _todoService) :ControllerBase
{
    // GET: api/Todos
    // 获取所有任务
    [HttpGet]
    public async Task<ActionResult<IEnumerable<TodoItem>>> GetTodos()
    {
        var todos = await _todoService.GetAllAsync();
        return Ok(todos);
    }

    // GET: api/Todos/5
    // 根据ID获取单个任务
    [HttpGet("{id}")]
    public async Task<ActionResult<TodoItem>> GetTodoItem(int id)
    {
        var todoItem = await _todoService.GetByIdAsync(id);

        if (todoItem == null)
        {
            return NotFound();
        }

        return Ok(todoItem);
    }

    // PUT: api/Todos/5
    // 更新整个任务
    [HttpPut("{id}")]
    public async Task<IActionResult> PutTodoItem(int id, UpdateTodoDto todo)
    {
        var result = await _todoService.UpdateAsync(id, todo);
        if (!result)
        {
            return NotFound();
        }

        return NoContent();
    }

    // POST: api/Todos
    // 创建新任务
    [HttpPost]
    public async Task<ActionResult<TodoItem>> PostTodoItem(CreateTodoDto todo)
    {
        var newTodo = await _todoService.CreateAsync(todo);
        return CreatedAtAction(nameof(GetTodoItem), new { id = newTodo.Id }, newTodo);
    }

    // DELETE: api/Todos/5
    // 删除任务
    [HttpDelete("{id}")]
    public async Task<IActionResult> DeleteTodoItem(int id)
    {
        var result = await _todoService.DeleteAsync(id);
        if (!result)
        {
            return NotFound();
        }

        return NoContent();
    }

    // PATCH: api/Todos/5
    // 部分更新任务
    [HttpPatch("{id}")]
    public async Task<IActionResult> PatchTodoItem(int id, PatchTodoDto todo)
    {
        var result = await _todoService.PatchAsync(id, todo);
        if (!result)
        {
            return NotFound();
        }

        return NoContent();
    }

    // HEAD: api/Todos
    // 检查资源是否存在
    [HttpHead]
    public async Task<IActionResult> HeadTodos()
    {
        var todos = await _todoService.GetAllAsync();
        if (todos.Any())
        {
            return Ok();
        }
        return NoContent();
    }

    // OPTIONS: api/Todos
    // 获取支持的HTTP方法
    [HttpOptions]
    public IActionResult OptionsTodos()
    {
        Response.Headers.Append("Allow", "GET, POST, PUT, PATCH, DELETE, HEAD, OPTIONS");
        return Ok();
    }
    
}