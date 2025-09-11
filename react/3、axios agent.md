``` ts
import axios from "axios";

const agent = axios.create({
    baseURL: import.meta.env.VITE_API_URL,
    // withCredentials: true
    timeout: 1000,
});

// agent.interceptors.request.use(config => {
//     store.uiStore.isBusy();
//     return config;
// })

// agent.interceptors.response.use(
//     async response => {
//         store.uiStore.isIdle()
//         return response;
//     },
//     async error => {
//         store.uiStore.isIdle();

//         const { status, data } = error.response;
//         switch (status) {
//             case 400:
//                 if (data.errors) {
//                     const modalStateErrors = [];
//                     for (const key in data.errors) {
//                         if (data.errors[key]) {
//                             modalStateErrors.push(data.errors[key]);
//                         }
//                     }
//                     throw modalStateErrors.flat();
//                 } else {
//                     toast.error(data);
//                 }
//                 break;
//             case 401:
//                 if (data.detail === 'NotAllowed') {
//                     throw new Error(data.detail)
//                 } else {
//                     toast.error('Unauthorised');
//                 }
//                 break;
//             case 404:
//                 router.navigate('/not-found');
//                 break;
//             case 500:
//                 router.navigate('/server-error', {state: {error: data}})
//                 break;
//             default:
//                 break;
//         }

//         return Promise.reject(error);
//     }
// );

export default agent;
```


``` .env
VITE_API_URL=http://localhost:5222/api
```


``` ts
import { useEffect, useState } from 'react'
import './App.css'
import agent from './utils/agent';

// 定义Todo数据类型
interface TodoItem {
  id: number;
  name: string;
  completed: boolean;
  description?: string;
}


function App() {
  // 状态管理
  const [todos, setTodos] = useState<TodoItem[]>([]);
  const [loading, setLoading] = useState(true);


  // 获取所有任务
  const fetchTodos = async () => {
    try {
      setLoading(true);
      const response = await agent.get<TodoItem[]>('/todos');
      setTodos(response.data);
    } catch (error) {
      console.error('获取任务失败:', error);
    } finally {
      setLoading(false);
    }
  };

  // 组件挂载时获取数据
  useEffect(() => {
    fetchTodos();
  }, []);

  if (loading) return <div>加载中...</div>;

  return (
    <div style={{ margin: '20px' }}>
      <h2>任务列表</h2>
      {todos.length === 0 ? (
        <p>暂无任务</p>
      ) : (
        <ul style={{ listStyle: 'none', padding: 0 }}>
          {todos.map(todo => (
            <li key={todo.id} style={{ 
              padding: '8px', 
              margin: '5px 0', 
              border: '1px solid #ddd',
              textDecoration: todo.completed ? 'line-through' : 'none'
            }}>
              {todo.name}
              {todo.description && 
              <span style={{ marginLeft: '10px', color: '#666' }}>
              {todo.description}
              </span>}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

export default App

```