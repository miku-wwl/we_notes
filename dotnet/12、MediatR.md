web.csproj
 ``` xml
    <ItemGroup>
        <PackageReference Include="AutoMapper" Version="13.0.1" />     
    </ItemGroup>
```

Program.cs
``` cs
// 注册AutoMapper
builder.Services.AddAutoMapper(typeof(BlogMappingProfile).Assembly);
```

Models.cs
``` cs
using System;

// 数据库实体（源类型）
public class Article
{
    public int Id { get; set; }
    public string Title { get; set; } = string.Empty;
    public string Content { get; set; } = string.Empty;
    public DateTime PublishTime { get; set; } // 发布时间（DateTime类型）
    public int AuthorId { get; set; }
    public Author Author { get; set; } = new(); // 嵌套的作者对象
    public bool IsDeleted { get; set; } // 逻辑删除标记
}

// 作者实体（嵌套对象）
public class Author
{
    public int Id { get; set; }
    public string Name { get; set; } = string.Empty;
    public int Age { get; set; }
}

// 前端展示用DTO（目标类型）
public class ArticleDto
{
    public int Id { get; set; }
    public string ArticleTitle { get; set; } = string.Empty; // 与实体的Title对应（字段名不同）
    public string Content { get; set; } = string.Empty;
    public string PublishDate { get; set; } = string.Empty; // 发布时间（字符串类型，需要格式化）
    public AuthorDto AuthorInfo { get; set; } = new(); // 嵌套的作者DTO
}

// 作者DTO（嵌套对象）
public class AuthorDto
{
    public int Id { get; set; }
    public string AuthorName { get; set; } = string.Empty; // 与实体的Name对应（字段名不同）
    public string AgeDesc { get; set; } = string.Empty; // 年龄描述（如"25岁"）
}

// 前端创建文章用DTO
public class CreateArticleDto
{
    public string Title { get; set; } = string.Empty;
    public string Content { get; set; } = string.Empty;
    public int AuthorId { get; set; }
}

```

InMemoryDataStore.cs
``` cs
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

// 内存数据存储类
public static class InMemoryDataStore
{
    // 静态列表模拟数据库表
    private static readonly List<Article> _articles = new List<Article>();
    private static readonly List<Author> _authors = new List<Author>();
    private static int _nextArticleId = 1;
    private static int _nextAuthorId = 1;

    // 静态构造函数，初始化一些测试数据
    static InMemoryDataStore()
    {
        // 添加测试作者
        var author1 = new Author { Id = _nextAuthorId++, Name = "张三", Age = 30 };
        var author2 = new Author { Id = _nextAuthorId++, Name = "李四", Age = 25 };
        
        _authors.Add(author1);
        _authors.Add(author2);

        // 添加测试文章
        _articles.Add(new Article 
        { 
            Id = _nextArticleId++, 
            Title = "C#入门教程", 
            Content = "这是一篇关于C#的入门教程...",
            PublishTime = System.DateTime.Now.AddDays(-5),
            AuthorId = author1.Id,
            Author = author1,
            IsDeleted = false
        });

        _articles.Add(new Article 
        { 
            Id = _nextArticleId++, 
            Title = "AutoMapper使用指南", 
            Content = "这是一篇关于AutoMapper的使用指南...",
            PublishTime = System.DateTime.Now.AddDays(-2),
            AuthorId = author2.Id,
            Author = author2,
            IsDeleted = false
        });
    }

    // 文章相关操作
    public static async Task<List<Article>> GetAllArticlesAsync()
    {
        // 模拟异步操作
        return await Task.FromResult(_articles.ToList());
    }

    public static async Task<Article> GetArticleByIdAsync(int id)
    {
        return await Task.FromResult(_articles.FirstOrDefault(a => a.Id == id));
    }

    public static async Task<Article> AddArticleAsync(Article article)
    {
        article.Id = _nextArticleId++;
        article.Author = _authors.FirstOrDefault(a => a.Id == article.AuthorId);
        _articles.Add(article);
        return await Task.FromResult(article);
    }

    // 作者相关操作
    public static async Task<Author> GetAuthorByIdAsync(int id)
    {
        return await Task.FromResult(_authors.FirstOrDefault(a => a.Id == id));
    }

    public static async Task<List<Author>> GetAllAuthorsAsync()
    {
        return await Task.FromResult(_authors.ToList());
    }
}
```

BlogMappingProfile.cs
``` cs
using AutoMapper;

// 映射配置类
public class BlogMappingProfile : Profile
{
    public BlogMappingProfile()
    {
        // 1. 基础映射：Article -> ArticleDto
        CreateMap<Article, ArticleDto>()
            // 字段名不同：将Article.Title映射到ArticleDto.ArticleTitle
            .ForMember(dest => dest.ArticleTitle, opt => opt.MapFrom(src => src.Title))
            // 类型转换：将DateTime转换为格式化字符串
            .ForMember(dest => dest.PublishDate, opt => opt.MapFrom(src => src.PublishTime.ToString("yyyy-MM-dd")))
            // 忽略不需要的字段
            .ForSourceMember(src => src.IsDeleted, opt => opt.DoNotValidate());

        // 2. 嵌套对象映射：Author -> AuthorDto
        CreateMap<Author, AuthorDto>()
            .ForMember(dest => dest.AuthorName, opt => opt.MapFrom(src => src.Name))
            .ForMember(dest => dest.AgeDesc, opt => opt.MapFrom(src => $"{src.Age}岁"));

        // 3. 反向映射：CreateArticleDto -> Article
        CreateMap<CreateArticleDto, Article>()
            .ForMember(dest => dest.Id, opt => opt.MapFrom(_ => 0))
            .ForMember(dest => dest.PublishTime, opt => opt.MapFrom(_ => DateTime.Now))
            .ForMember(dest => dest.IsDeleted, opt => opt.MapFrom(_ => false))
            .ForMember(dest => dest.Author, opt => opt.Ignore());
    }
}
```


ArticleController.cs
``` cs
using AutoMapper;
using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using System.Threading.Tasks;

[ApiController]
[Route("api/articles")]
public class ArticleController : ControllerBase
{
    private readonly IMapper _mapper;

    public ArticleController(IMapper mapper)
    {
        _mapper = mapper;
    }

    // 查询文章：将Article实体转换为ArticleDto
    [HttpGet("{id}")]
    public async Task<ActionResult<ArticleDto>> GetArticle(int id)
    {
        // 从内存存储获取实体
        var article = await InMemoryDataStore.GetArticleByIdAsync(id);

        if (article == null)
            return NotFound();

        // 执行映射：Article -> ArticleDto
        var articleDto = _mapper.Map<ArticleDto>(article);
        return Ok(articleDto);
    }

    // 创建文章：将CreateArticleDto转换为Article实体
    [HttpPost]
    public async Task<ActionResult<ArticleDto>> CreateArticle(CreateArticleDto createDto)
    {
        // 验证作者是否存在
        var author = await InMemoryDataStore.GetAuthorByIdAsync(createDto.AuthorId);
        if (author == null)
            return BadRequest("作者不存在");

        // 执行映射：CreateArticleDto -> Article
        var article = _mapper.Map<Article>(createDto);

        // 保存到内存存储
        var createdArticle = await InMemoryDataStore.AddArticleAsync(article);

        // 转换为DTO返回
        var articleDto = _mapper.Map<ArticleDto>(createdArticle);
        return CreatedAtAction(nameof(GetArticle), new { id = createdArticle.Id }, articleDto);
    }

    // 批量获取文章
    [HttpGet]
    public async Task<ActionResult<List<ArticleDto>>> GetAllArticles()
    {
        var articles = await InMemoryDataStore.GetAllArticlesAsync();
        
        // 集合映射：List<Article> -> List<ArticleDto>
        var articleDtos = _mapper.Map<List<ArticleDto>>(articles);
        return Ok(articleDtos);
    }
}

```