# 安装 MobX 核心库

npm install mobx

# 安装 React 与 MobX 连接的桥梁

npm install mobx-react-lite

types\jsonPlaceholder.ts

```ts
// types/jsonPlaceholder.ts
/**
 * JSONPlaceholder /users 资源接口（用户信息）
 * 对应 API: https://jsonplaceholder.typicode.com/users
 */
export interface User {
  id: number;
  name: string;
  username: string;
  email: string;
  address: {
    street: string;
    suite: string;
    city: string;
    zipcode: string;
    geo: {
      lat: string;
      lng: string;
    };
  };
  phone: string;
  website: string;
  company: {
    name: string;
    catchPhrase: string;
    bs: string;
  };
}

/**
 * JSONPlaceholder /posts 资源接口（文章信息）
 * 对应 API: https://jsonplaceholder.typicode.com/posts
 */
export interface Post {
  id: number;
  userId: number;
  title: string;
  body: string;
}

/**
 * JSONPlaceholder /todos 资源接口（待办信息）
 * 对应 API: https://jsonplaceholder.typicode.com/todos
 */
export interface Todo {
  id: number;
  userId: number;
  title: string;
  completed: boolean;
}

/**
 * 支持的资源类型枚举（限定可请求的 API 路径）
 */
export type ResourceType = "users" | "posts" | "todos";

// 联合类型：所有支持的资源数据类型
export type JsonPlaceholderData = User[] | Post[] | Todo[];
```

stores\dataStore.ts

```ts
// stores/dataStore.ts
import { makeAutoObservable } from "mobx";
import axios, { AxiosError, type AxiosResponse } from "axios";
import type {
  JsonPlaceholderData,
  ResourceType,
  User,
  Post,
  Todo,
} from "../types/jsonPlaceholder";

class DataStore {
  // ===== 状态定义（带明确 TypeScript 类型）=====
  /** 接口返回的数据（初始为 null） */
  data: JsonPlaceholderData | null = null;
  /** 加载状态（初始为 false） */
  loading: boolean = false;
  /** 错误信息（初始为 null，出错时存储错误描述） */
  error: string | null = null;
  /** 当前请求的资源类型（默认请求 users） */
  currentResource: ResourceType = "users";

  constructor() {
    // MobX 自动将属性标记为 observable，方法标记为 action
    makeAutoObservable(this);
  }

  // ===== 核心动作：通过 Axios 请求 JSONPlaceholder API =====
  /**
   * 请求 JSONPlaceholder 资源
   * @param resource 要请求的资源类型（默认使用 currentResource）
   */
  fetchData = async (
    resource: ResourceType = this.currentResource
  ): Promise<void> => {
    try {
      // 1. 重置状态：开始加载，清空之前的错误和数据
      this.loading = true;
      this.error = null;
      this.currentResource = resource;

      // 2. 拼接 API 地址（基于 JSONPlaceholder 规范）
      const apiUrl = `https://jsonplaceholder.typicode.com/${resource}`;

      // 3. Axios 请求（指定响应类型，确保 TypeScript 类型推导正确）
      let response: AxiosResponse<JsonPlaceholderData>;
      switch (resource) {
        case "users":
          response = await axios.get<User[]>(apiUrl); // 明确响应为 User 数组
          break;
        case "posts":
          response = await axios.get<Post[]>(apiUrl); // 明确响应为 Post 数组
          break;
        case "todos":
          response = await axios.get<Todo[]>(apiUrl); // 明确响应为 Todo 数组
          break;
        default:
          throw new Error(`不支持的资源类型：${resource}`);
      }

      // 4. 请求成功：更新数据状态
      this.data = response.data;
    } catch (err) {
      // 5. 错误处理：区分 Axios 错误和普通错误
      if (axios.isAxiosError(err)) {
        // Axios 错误（网络错误、4xx/5xx 状态码等）
        const axiosErr = err as AxiosError;
        this.error = axiosErr.response
          ? `请求失败 [${axiosErr.response.status}]: ${axiosErr.response.statusText}`
          : axiosErr.message || "网络错误，请检查连接";
      } else {
        // 普通错误（如自定义错误）
        this.error = (err as Error).message || "未知错误";
      }
    } finally {
      // 6. 无论成功/失败，结束加载状态
      this.loading = false;
    }
  };

  // ===== 辅助动作：清空数据 =====
  clearData = (): void => {
    this.data = null;
    this.error = null;
  };

  // ===== 计算属性：获取数据长度（可选，用于统计展示）=====
  get dataCount(): number {
    return this.data ? this.data.length : 0;
  }
}

// 创建 Store 实例并导出（单例模式，全局复用）
export const dataStore = new DataStore();
```

components\DataFetchComponent.tsx

```ts
// components/DataFetchComponent.tsx
import React from "react";
import { observer } from "mobx-react-lite"; // 使组件成为 MobX 观察者
import { dataStore } from "../stores/dataStore";
import type { ResourceType, User, Post, Todo } from "../types/jsonPlaceholder";

// 使用 observer 包装组件：当依赖的 observable 状态（data/loading/error）变化时自动重渲染
const DataFetchComponent = observer(() => {
  // ===== 本地状态：当前选择的资源类型（与 Store 同步）=====
  const [selectedResource, setSelectedResource] = React.useState<ResourceType>(
    dataStore.currentResource
  );

  // ===== 副作用：初始加载数据 =====
  React.useEffect(() => {
    dataStore.fetchData(); // 组件挂载时默认请求 users 数据
  }, []);

  // ===== 事件处理：切换资源类型（带 TypeScript 事件类型约束）=====
  const handleResourceChange = (
    e: React.ChangeEvent<HTMLSelectElement>
  ): void => {
    const selected = e.target.value as ResourceType;
    setSelectedResource(selected);
  };

  // ===== 事件处理：触发请求（根据选择的资源）=====
  const handleFetch = (): void => {
    dataStore.fetchData(selectedResource);
  };

  // ===== 辅助函数：渲染不同类型的数据列表（类型守卫，确保类型安全）=====
  const renderDataList = (): React.ReactNode => {
    const { data } = dataStore;
    if (!data) return null;

    // 根据资源类型渲染不同结构（TypeScript 类型守卫：区分 User/Post/Todo）
    if (Object.prototype.hasOwnProperty.call((data as User[])[0], "email")) {
      return (
        <ul style={{ listStyle: "none", padding: 0 }}>
          {(data as User[]).map((user) => (
            <li
              key={user.id}
              style={{ padding: "8px", borderBottom: "1px solid #eee" }}
            >
              <h4>
                {user.name} (@{user.username})
              </h4>
              <p>
                邮箱：<a href={`mailto:${user.email}`}>{user.email}</a>
              </p>
              <p>
                网站：
                <a
                  href={`https://${user.website}`}
                  target="_blank"
                  rel="noreferrer"
                >
                  {user.website}
                </a>
              </p>
            </li>
          ))}
        </ul>
      );
    } else if (
      Object.prototype.hasOwnProperty.call((data as Post[])[0], "body")
    ) {
      // 渲染文章列表（截取 body 前 100 字符避免过长）
      return (
        <ul style={{ listStyle: "none", padding: 0 }}>
          {(data as Post[]).map((post) => (
            <li
              key={post.id}
              style={{ padding: "8px", borderBottom: "1px solid #eee" }}
            >
              <h4>{post.title}</h4>
              <p>
                {post.body.slice(0, 100)}... (userId: {post.userId})
              </p>
            </li>
          ))}
        </ul>
      );
    } else if (
      Object.prototype.hasOwnProperty.call((data as Todo[])[0], "completed")
    ) {
      // 渲染待办列表（根据 completed 状态显示不同样式）
      return (
        <ul style={{ listStyle: "none", padding: 0 }}>
          {(data as Todo[]).map((todo) => (
            <li
              key={todo.id}
              style={{
                padding: "8px",
                borderBottom: "1px solid #eee",
                textDecoration: todo.completed ? "line-through" : "none",
                color: todo.completed ? "#666" : "#333",
              }}
            >
              <input
                type="checkbox"
                checked={todo.completed}
                readOnly // 仅展示，不允许修改（JSONPlaceholder 为模拟 API，修改无实际效果）
              />
              <span style={{ marginLeft: "8px" }}>
                {todo.title} (userId: {todo.userId})
              </span>
            </li>
          ))}
        </ul>
      );
    }

    return <p>不支持的数据格式</p>;
  };

  // ===== 组件渲染核心逻辑 =====
  return (
    <div style={{ maxWidth: "800px", margin: "20px auto", padding: "0 20px" }}>
      <h2>MobX + Axios + JSONPlaceholder 数据请求示例</h2>

      {/* 1. 资源选择与请求控制区 */}
      <div
        style={{
          marginBottom: "20px",
          display: "flex",
          gap: "10px",
          alignItems: "center",
        }}
      >
        <select
          value={selectedResource}
          onChange={handleResourceChange}
          style={{ padding: "8px", minWidth: "120px" }}
        >
          <option value={"users"}>用户列表 (users)</option>
          <option value={"posts"}>文章列表 (posts)</option>
          <option value={"todos"}>待办列表 (todos)</option>
        </select>

        <button
          onClick={handleFetch}
          disabled={dataStore.loading} // 加载中禁用按钮
          style={{
            padding: "8px 16px",
            backgroundColor: "#2196F3",
            color: "white",
            border: "none",
            borderRadius: "4px",
            cursor: dataStore.loading ? "not-allowed" : "pointer",
          }}
        >
          {dataStore.loading ? "加载中..." : "发起请求"}
        </button>

        <button
          onClick={dataStore.clearData}
          disabled={dataStore.loading || !dataStore.data} // 无数据或加载中禁用
          style={{
            padding: "8px 16px",
            backgroundColor: "#f44336",
            color: "white",
            border: "none",
            borderRadius: "4px",
            cursor:
              dataStore.loading || !dataStore.data ? "not-allowed" : "pointer",
          }}
        >
          清空数据
        </button>
      </div>

      {/* 2. 错误提示区 */}
      {dataStore.error && (
        <div
          style={{
            padding: "12px",
            backgroundColor: "#ffebee",
            color: "#b71c1c",
            borderRadius: "4px",
            marginBottom: "20px",
          }}
        >
          ❌ 错误：{dataStore.error}
        </div>
      )}

      {/* 3. 加载提示区 */}
      {dataStore.loading && (
        <div
          style={{
            padding: "20px",
            textAlign: "center",
            color: "#666",
          }}
        >
          ⏳ 正在加载 {selectedResource} 数据...
        </div>
      )}

      {/* 4. 数据展示区（含统计） */}
      {!dataStore.loading && dataStore.data && (
        <div>
          <div
            style={{
              marginBottom: "10px",
              color: "#666",
              fontSize: "0.9rem",
            }}
          >
            ✅ 加载完成：共 {dataStore.dataCount} 条 {selectedResource} 数据
          </div>
          {renderDataList()}
        </div>
      )}

      {/* 5. 空状态提示（无数据、无错误、非加载中） */}
      {!dataStore.loading && !dataStore.error && !dataStore.data && (
        <div
          style={{
            padding: "20px",
            textAlign: "center",
            color: "#666",
            border: "1px dashed #ddd",
            borderRadius: "4px",
          }}
        >
          📌 请选择资源类型并点击「发起请求」获取数据
        </div>
      )}
    </div>
  );
});

export default DataFetchComponent;
```

App.tsx

```ts
import "./App.css";
import DataFetchComponent from "./components/DataFetchComponent";

function App() {
  return (
    <div className="App">
      <DataFetchComponent />
    </div>
  );
}

export default App;
```
