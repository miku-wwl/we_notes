初始化操作（仅执行一次）
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

  const fetchTodos = async () => {
    const response = await agent.get<TodoItem[]>('/todos');
    setTodos(response.data);
  }


  // 组件挂载时获取数据
  useEffect(() => { fetchTodos(); }, []);


  return (
    <div>
      <ul>
        {todos.map(todo => (
          <li key={todo.id}>
            <h3>{todo.name} {todo.completed ? "(Completed)" : "(Pending)"}</h3>
          </li>
        ))}
      </ul>

      <button onClick={fetchTodos}>Refresh Todos</button>
    </div>
  );
}

export default App
```

依赖参数变化的操作z
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
  const [id, setId] = useState(1);
  const [todo, setTodo] = useState<TodoItem>();

  const fetchTodos = async (id: number) => {
    const response = await agent.get<TodoItem>(`/todos/${id}`);
    setTodo(response.data);
  }

  // 组件挂载时获取数据
  useEffect(() => { fetchTodos(id); }, [id]);

  return (
    <div>
      {todo && (
        <h3>{todo.name} {todo.completed ? "(Completed)" : "(Pending)"}</h3>
      )}


      <button onClick={() => setId(id => id + 1)}>id={id} id++</button>
    </div>
  );
}

export default App


```