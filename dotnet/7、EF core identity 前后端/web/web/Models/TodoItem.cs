using System;
using System.ComponentModel.DataAnnotations;

namespace web.Models;

public class TodoItem
{
    public int Id { get; set; }
    public string? Name { get; set; }
    public bool IsComplete { get; set; }
    public string? Description { get; set; }
    public DateTime? DueDate { get; set; }
}

public class CreateTodoDto
{
    [Required] public string? Name { get; set; }

    public string? Description { get; set; }
    public DateTime? DueDate { get; set; }
}

public class UpdateTodoDto
{
    public string? Name { get; set; }
    public bool? IsComplete { get; set; }
    public string? Description { get; set; }
    public DateTime? DueDate { get; set; }
}

public class PatchTodoDto
{
    public string? Name { get; set; }
    public bool? IsComplete { get; set; }
}