using Microsoft.AspNetCore.Http.HttpResults;
using Microsoft.AspNetCore.Mvc;

namespace User.Core;

public class Result<T>
{
    public bool IsSuccess { get; set; }
    public T? Value { get; set; }
    public string? Error { get; set; }
    public int Code { get; set; }

    public static Result<T> Success(T value)
    {
        return new Result<T> { IsSuccess = true, Value = value };
    }

    public static Result<T> Failure(string? error, int code)
    {
        return new Result<T> { IsSuccess = false, Error = error, Code = code };
    }
    
    public static ActionResult HandleResult(Result<T> result)
    {
        if (!result.IsSuccess && result.Code == 404) 
            return new NotFoundResult();

        if (result.IsSuccess && result.Value != null) 
            return new OkObjectResult(result.Value);

        return new BadRequestObjectResult(result.Error);
    }
}