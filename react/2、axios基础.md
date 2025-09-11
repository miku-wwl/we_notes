``` ts
import { useEffect, useState } from 'react'
import './App.css'
import axios from 'axios'

// 定义Todo数据类型
interface TodoItem {
  id: number;
  title: string;
  completed: boolean;
  description?: string;
}


function App() {
  // 状态管理
  const [todos, setTodos] = useState<TodoItem[]>([]);
  const [loading, setLoading] = useState(true);

  // 创建axios实例
  const api = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:5222/api',
    timeout: 1000,
  });

  // 获取所有任务
  const fetchTodos = async () => {
    try {
      setLoading(true);
      const response = await api.get<TodoItem[]>('/todos');
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
              {todo.title}
              {todo.description && <span style={{ marginLeft: '10px', color: '#666' }}>
                ({todo.description})
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