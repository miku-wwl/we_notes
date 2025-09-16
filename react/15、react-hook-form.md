pnpm install react-hook-form

components\BasicForm.tsx

```ts
import { useForm } from "react-hook-form";

type Profile = {
  name: string;
  email: string;
  age: number;
};

const BasicForm = () => {
  // 初始化 useForm
  const { register, handleSubmit } = useForm<Profile>();

  // 表单提交处理函数
  const onSubmit = (data: Profile) => {
    console.log("表单数据:", data);
    alert(JSON.stringify(data, null, 2));
  };

  return (
    <div style={{ maxWidth: "500px", margin: "2rem auto", padding: "0 1rem" }}>
      <h2>基础表单示例</h2>
      <form
        onSubmit={handleSubmit(onSubmit)}
        style={{ display: "flex", flexDirection: "column", gap: "1rem" }}
      >
        {/* 姓名输入框 */}
        <div>
          <label htmlFor="name">姓名</label>
          <input
            id="name"
            type="text"
            {...register("name")} // 注册表单控件
            style={{ width: "100%", padding: "8px", marginTop: "4px" }}
          />
        </div>

        {/* 邮箱输入框 */}
        <div>
          <label htmlFor="email">邮箱</label>
          <input
            id="email"
            type="email"
            {...register("email")}
            style={{ width: "100%", padding: "8px", marginTop: "4px" }}
          />
        </div>

        {/* 年龄输入框 */}
        <div>
          <label htmlFor="age">年龄</label>
          <input
            id="age"
            type="number"
            {...register("age")}
            style={{ width: "100%", padding: "8px", marginTop: "4px" }}
          />
        </div>

        {/* 提交按钮 */}
        <button
          type="submit"
          style={{
            padding: "10px",
            backgroundColor: "#007bff",
            color: "white",
            border: "none",
            borderRadius: "4px",
            cursor: "pointer",
          }}
        >
          提交
        </button>
      </form>
    </div>
  );
};

export default BasicForm;
```

components\UserForm.tsx

```ts
import { useState, useEffect } from "react";
import { useForm } from "react-hook-form";
import axios from "axios";

// 定义用户数据类型
type User = {
  id: number;
  name: string;
  email: string;
  phone: string;
  website: string;
  [key: string]: unknown; // 允许其他可能存在的字段
};

// 定义表单数据类型
type FormData = {
  name: string;
  email: string;
  phone: string;
  website: string;
};

// 定义消息提示类型
type Message = {
  text: string;
  type: "success" | "error" | "";
};

const UserForm = () => {
  // 状态管理，添加类型注解
  const [users, setUsers] = useState<User[]>([]);
  const [selectedUserId, setSelectedUserId] = useState<number | null>(null);
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [message, setMessage] = useState<Message>({ text: "", type: "" });

  // 初始化表单，指定泛型类型
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm<FormData>();

  // API基础URL
  const API_URL: string = "https://jsonplaceholder.typicode.com/users";

  // 获取所有用户，添加返回类型注解
  const fetchUsers = async (): Promise<void> => {
    try {
      setIsLoading(true);
      const response = await axios.get<User[]>(API_URL);
      setUsers(response.data);
      setIsLoading(false);
    } catch (error) {
      console.error("获取用户失败:", error);
      setIsLoading(false);
      setMessage({ text: "获取用户失败", type: "error" });
      setTimeout(() => setMessage({ text: "", type: "" }), 3000);
    }
  };

  // 组件挂载时获取用户列表
  useEffect(() => {
    fetchUsers();
  }, []);

  // 选择用户进行编辑，添加参数类型注解
  const handleEdit = (user: User): void => {
    setSelectedUserId(user.id);
    // 重置表单并设置选中用户的数据
    reset({
      name: user.name,
      email: user.email,
      phone: user.phone,
      website: user.website,
    });
  };

  // 删除用户，添加参数类型注解
  const handleDelete = async (userId: number): Promise<void> => {
    if (window.confirm("确定要删除这个用户吗？")) {
      try {
        setIsLoading(true);
        await axios.delete(`${API_URL}/${userId}`);
        // 虽然jsonplaceholder不会真正删除数据，但我们模拟删除成功的效果
        setUsers(users.filter((user) => user.id !== userId));
        setIsLoading(false);
        setMessage({ text: "用户删除成功", type: "success" });
        setTimeout(() => setMessage({ text: "", type: "" }), 3000);

        // 如果删除的是当前编辑的用户，重置表单
        if (selectedUserId === userId) {
          reset();
          setSelectedUserId(null);
        }
      } catch (error) {
        console.error("删除用户失败:", error);
        setIsLoading(false);
        setMessage({ text: "删除用户失败", type: "error" });
        setTimeout(() => setMessage({ text: "", type: "" }), 3000);
      }
    }
  };

  // 表单提交处理：创建或更新用户，添加参数类型注解
  const onSubmit = async (data: FormData): Promise<void> => {
    try {
      setIsLoading(true);

      if (selectedUserId) {
        // 更新用户
        const response = await axios.put<User>(`${API_URL}/${selectedUserId}`, {
          ...data,
          id: selectedUserId,
        });

        // 更新本地用户列表
        setUsers(
          users.map((user) =>
            user.id === selectedUserId ? response.data : user
          )
        );

        setMessage({ text: "用户更新成功", type: "success" });
      } else {
        // 创建新用户
        const response = await axios.post<User>(API_URL, {
          ...data,
          id: users.length + 1,
        });

        // 添加到本地用户列表
        setUsers([...users, response.data]);

        setMessage({ text: "用户创建成功", type: "success" });
        // 重置表单
        reset();
      }

      setIsLoading(false);
      setTimeout(() => setMessage({ text: "", type: "" }), 3000);
    } catch (error) {
      console.error("提交表单失败:", error);
      setIsLoading(false);
      setMessage({ text: "操作失败", type: "error" });
      setTimeout(() => setMessage({ text: "", type: "" }), 3000);
    }
  };

  // 取消编辑
  const handleCancel = (): void => {
    reset();
    setSelectedUserId(null);
  };

  return (
    <div style={{ maxWidth: "800px", margin: "2rem auto", padding: "0 1rem" }}>
      <h2>用户管理系统</h2>

      {/* 消息提示 */}
      {message.text && (
        <div
          style={{
            padding: "10px",
            marginBottom: "1rem",
            borderRadius: "4px",
            backgroundColor: message.type === "success" ? "#d4edda" : "#f8d7da",
            color: message.type === "success" ? "#155724" : "#721c24",
          }}
        >
          {message.text}
        </div>
      )}

      <div style={{ display: "flex", gap: "2rem", flexWrap: "wrap" }}>
        {/* 表单区域 */}
        <div style={{ flex: 1, minWidth: "300px" }}>
          <h3>{selectedUserId ? "编辑用户" : "创建新用户"}</h3>
          <form
            onSubmit={handleSubmit(onSubmit)}
            style={{ display: "flex", flexDirection: "column", gap: "1rem" }}
          >
            {/* 姓名 */}
            <div>
              <label htmlFor="name">
                姓名 <span style={{ color: "red" }}>*</span>
              </label>
              <input
                id="name"
                type="text"
                {...register("name", {
                  required: "姓名不能为空",
                })}
                style={{
                  width: "100%",
                  padding: "8px",
                  marginTop: "4px",
                  border: errors.name ? "1px solid red" : "1px solid #ccc",
                }}
              />
              {errors.name && (
                <p
                  style={{
                    color: "red",
                    fontSize: "0.8rem",
                    margin: "4px 0 0 0",
                  }}
                >
                  {errors.name.message}
                </p>
              )}
            </div>

            {/* 邮箱 */}
            <div>
              <label htmlFor="email">
                邮箱 <span style={{ color: "red" }}>*</span>
              </label>
              <input
                id="email"
                type="email"
                {...register("email", {
                  required: "邮箱不能为空",
                  pattern: {
                    value: /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/,
                    message: "请输入有效的邮箱地址",
                  },
                })}
                style={{
                  width: "100%",
                  padding: "8px",
                  marginTop: "4px",
                  border: errors.email ? "1px solid red" : "1px solid #ccc",
                }}
              />
              {errors.email && (
                <p
                  style={{
                    color: "red",
                    fontSize: "0.8rem",
                    margin: "4px 0 0 0",
                  }}
                >
                  {errors.email.message}
                </p>
              )}
            </div>

            {/* 电话 */}
            <div>
              <label htmlFor="phone">电话</label>
              <input
                id="phone"
                type="text"
                {...register("phone")}
                style={{
                  width: "100%",
                  padding: "8px",
                  marginTop: "4px",
                  border: "1px solid #ccc",
                }}
              />
            </div>

            {/* 网站 */}
            <div>
              <label htmlFor="website">网站</label>
              <input
                id="website"
                type="url"
                {...register("website", {
                  pattern: {
                    value:
                      /^(https?:\/\/)?([\da-z.-]+)\.([a-z.]{2,6})([\\/\w .-]*)*\/?$/,
                    message: "请输入有效的网址",
                  },
                })}
                style={{
                  width: "100%",
                  padding: "8px",
                  marginTop: "4px",
                  border: errors.website ? "1px solid red" : "1px solid #ccc",
                }}
              />
              {errors.website && (
                <p
                  style={{
                    color: "red",
                    fontSize: "0.8rem",
                    margin: "4px 0 0 0",
                  }}
                >
                  {errors.website.message}
                </p>
              )}
            </div>

            {/* 按钮区域 */}
            <div style={{ display: "flex", gap: "1rem", marginTop: "1rem" }}>
              <button
                type="submit"
                disabled={isLoading}
                style={{
                  flex: 1,
                  padding: "10px",
                  backgroundColor: "#007bff",
                  color: "white",
                  border: "none",
                  borderRadius: "4px",
                  cursor: "pointer",
                  opacity: isLoading ? 0.7 : 1,
                }}
              >
                {isLoading ? "处理中..." : selectedUserId ? "更新" : "创建"}
              </button>

              {selectedUserId && (
                <button
                  type="button"
                  onClick={handleCancel}
                  style={{
                    flex: 1,
                    padding: "10px",
                    backgroundColor: "#6c757d",
                    color: "white",
                    border: "none",
                    borderRadius: "4px",
                    cursor: "pointer",
                  }}
                >
                  取消
                </button>
              )}
            </div>
          </form>
        </div>

        {/* 用户列表 */}
        <div style={{ flex: 1, minWidth: "300px" }}>
          <h3>用户列表</h3>
          {isLoading ? (
            <p>加载中...</p>
          ) : (
            <div
              style={{
                maxHeight: "400px",
                overflowY: "auto",
                border: "1px solid #ccc",
                borderRadius: "4px",
                padding: "10px",
              }}
            >
              {users.length === 0 ? (
                <p>暂无用户数据</p>
              ) : (
                <ul style={{ listStyle: "none", padding: 0, margin: 0 }}>
                  {users.map((user) => (
                    <li
                      key={user.id}
                      style={{
                        padding: "10px",
                        marginBottom: "10px",
                        borderBottom: "1px solid #eee",
                        backgroundColor:
                          selectedUserId === user.id
                            ? "#e9f7fe"
                            : "transparent",
                      }}
                    >
                      <div style={{ fontWeight: "bold" }}>{user.name}</div>
                      <div style={{ fontSize: "0.9rem", color: "#666" }}>
                        {user.email}
                      </div>
                      <div
                        style={{
                          display: "flex",
                          gap: "8px",
                          marginTop: "8px",
                        }}
                      >
                        <button
                          onClick={() => handleEdit(user)}
                          style={{
                            padding: "4px 8px",
                            backgroundColor: "#28a745",
                            color: "white",
                            border: "none",
                            borderRadius: "2px",
                            cursor: "pointer",
                            fontSize: "0.8rem",
                          }}
                        >
                          编辑
                        </button>
                        <button
                          onClick={() => handleDelete(user.id)}
                          style={{
                            padding: "4px 8px",
                            backgroundColor: "#dc3545",
                            color: "white",
                            border: "none",
                            borderRadius: "2px",
                            cursor: "pointer",
                            fontSize: "0.8rem",
                          }}
                        >
                          删除
                        </button>
                      </div>
                    </li>
                  ))}
                </ul>
              )}
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default UserForm;
```

components\ValidatedForm.tsx

```ts
import { useForm } from "react-hook-form";

type Profile = {
  name: string;
  email: string;
  age: string;
  agree: boolean;
};

const ValidatedForm = () => {
  // 初始化 useForm，可以设置默认值
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<Profile>({
    defaultValues: {
      name: "",
      email: "",
      age: "",
      agree: false,
    },
  });

  // 表单提交处理函数
  const onSubmit = (data: Profile) => {
    console.log("表单数据:", data);
    alert(JSON.stringify(data, null, 2));
  };

  return (
    <div style={{ maxWidth: "500px", margin: "2rem auto", padding: "0 1rem" }}>
      <h2>带验证的表单示例</h2>
      <form
        onSubmit={handleSubmit(onSubmit)}
        style={{ display: "flex", flexDirection: "column", gap: "1rem" }}
      >
        {/* 姓名输入框 - 必选 */}
        <div>
          <label htmlFor="name">
            姓名 <span style={{ color: "red" }}>*</span>
          </label>
          <input
            id="name"
            type="text"
            {...register("name", {
              required: "姓名不能为空",
              minLength: {
                value: 2,
                message: "姓名至少需要2个字符",
              },
            })}
            style={{
              width: "100%",
              padding: "8px",
              marginTop: "4px",
              border: errors.name ? "1px solid red" : "1px solid #ccc",
            }}
          />
          {errors.name && (
            <p
              style={{ color: "red", fontSize: "0.8rem", margin: "4px 0 0 0" }}
            >
              {errors.name.message}
            </p>
          )}
        </div>

        {/* 邮箱输入框 - 必选且格式验证 */}
        <div>
          <label htmlFor="email">
            邮箱 <span style={{ color: "red" }}>*</span>
          </label>
          <input
            id="email"
            type="email"
            {...register("email", {
              required: "邮箱不能为空",
              pattern: {
                value: /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/,
                message: "请输入有效的邮箱地址",
              },
            })}
            style={{
              width: "100%",
              padding: "8px",
              marginTop: "4px",
              border: errors.email ? "1px solid red" : "1px solid #ccc",
            }}
          />
          {errors.email && (
            <p
              style={{ color: "red", fontSize: "0.8rem", margin: "4px 0 0 0" }}
            >
              {errors.email.message}
            </p>
          )}
        </div>

        {/* 年龄输入框 - 范围验证 */}
        <div>
          <label htmlFor="age">年龄</label>
          <input
            id="age"
            type="number"
            {...register("age", {
              min: {
                value: 0,
                message: "年龄不能小于0",
              },
              max: {
                value: 120,
                message: "年龄不能大于120",
              },
            })}
            style={{
              width: "100%",
              padding: "8px",
              marginTop: "4px",
              border: errors.age ? "1px solid red" : "1px solid #ccc",
            }}
          />
          {errors.age && (
            <p
              style={{ color: "red", fontSize: "0.8rem", margin: "4px 0 0 0" }}
            >
              {errors.age.message}
            </p>
          )}
        </div>

        {/* 同意条款 - 必选复选框 */}
        <div style={{ display: "flex", alignItems: "center", gap: "8px" }}>
          <input
            id="agree"
            type="checkbox"
            {...register("agree", {
              required: "请同意条款",
            })}
          />
          <label htmlFor="agree">
            我已阅读并同意条款 <span style={{ color: "red" }}>*</span>
          </label>
        </div>
        {errors.agree && (
          <p style={{ color: "red", fontSize: "0.8rem", margin: "0" }}>
            {errors.agree.message}
          </p>
        )}

        {/* 提交按钮 */}
        <button
          type="submit"
          style={{
            padding: "10px",
            backgroundColor: "#007bff",
            color: "white",
            border: "none",
            borderRadius: "4px",
            cursor: "pointer",
          }}
        >
          提交
        </button>
      </form>
    </div>
  );
};

export default ValidatedForm;
```

App.tsx

```ts
import "./App.css";
import BasicForm from "./components/BasicForm";
import UserForm from "./components/UserForm";
import ValidatedForm from "./components/ValidatedForm";

function App() {
  return (
    <div className="App">
      <BasicForm />
      -------------------------
      <br />
      <ValidatedForm />
      -------------------------
      <br />
      <UserForm />
    </div>
  );
}

export default App;
```
