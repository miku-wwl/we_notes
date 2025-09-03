### **第一步：确认.NET SDK已安装（关键）**
Rider只是开发工具，真正运行.NET程序需要.NET SDK（软件开发工具包）。检查方法：
1. 打开Rider，点击顶部菜单 `File → Settings`（Windows）或 `Rider → Preferences`（Mac）
2. 左侧导航找到 `Build, Execution, Deployment → SDKs`
3. 查看右侧是否有.NET SDK（比如.NET 6/7/8），如果没有：
   - 去微软官网下载：[https://dotnet.microsoft.com/download](https://dotnet.microsoft.com/download)
   - 选择适合你系统的版本（新手推荐LTS长期支持版，如.NET 8）
   - 安装时勾选“添加到环境变量”，方便命令行使用


### **第二步：创建第一个.NET项目（Hello World）**
用最简单的控制台程序入门，熟悉Rider的基本操作：
1. 打开Rider，首次启动会显示欢迎界面，点击 `New Solution`
2. 在左侧模板中选择：`C# → Console Application`（控制台应用）
3. 右侧配置项目：
   - `Name`：输入项目名（比如 `HelloWorld`）
   - `Location`：选择项目保存的文件夹
   - `Framework`：选择已安装的.NET版本（如.NET 8.0）
4. 点击 `Create`，Rider会自动生成一个基础项目


### **第三步：认识Rider界面（新手必看）**
生成项目后，先熟悉界面布局（避免迷茫）：
- **左侧：解决方案窗口**：显示项目文件结构，类似文件夹（`Program.cs` 是主代码文件）
- **中间：编辑器**：编写代码的地方（默认会有一行 `Console.WriteLine("Hello, World!");`）
- **底部：终端/输出窗口**：运行程序后会显示结果
- **顶部工具栏**：有运行（▶️）、调试（🐞）、停止（■）等按钮


### **第四步：运行你的第一个程序**
让代码跑起来，感受成就感：
1. 确保 `Program.cs` 在编辑器中打开
2. 点击顶部工具栏的 **绿色运行按钮**（▶️），或按快捷键 `Ctrl+F5`（Windows）/ `Control+R`（Mac）
3. 底部会弹出终端窗口，显示结果：`Hello, World!`

如果出错，大概率是.NET SDK没装好，回头检查第一步。


### **第五步：修改代码，尝试互动**
稍微改一下代码，体验开发过程：
1. 在编辑器中修改代码为：
   ```csharp
   Console.WriteLine("请输入你的名字：");
   string name = Console.ReadLine(); // 读取用户输入
   Console.WriteLine($"你好，{name}！欢迎学习.NET！");
   ```
2. 再次运行（▶️），在终端中输入你的名字，会看到程序回应你


### **第六步：了解基础调试功能**
调试是找bug的关键，新手尽早学会：
1. 在代码行号左侧点击，添加 **断点**（会出现红色圆点）
2. 点击 **调试按钮**（🐞，或按 `F5`），程序会在断点处暂停
3. 此时可以：
   - 一步步执行（按 `F10` 单步执行）
   - 查看变量值（鼠标悬停在变量上，如 `name`）
   - 观察程序执行流程
