计数器（基础用法）
待办事项列表（复杂状态管理）

TodoApp.tsx
``` ts
import { useReducer, useState } from 'react';

// 定义Todo项的类型
interface Todo {
    id: number;
    text: string;
    completed: boolean;
}

// 定义状态类型
interface TodoState {
    todos: Todo[];
    nextId: number;
}

// 定义所有可能的action类型
type TodoAction =
    | { type: 'ADD_TODO'; payload: string }
    | { type: 'TOGGLE_TODO'; payload: number }
    | { type: 'DELETE_TODO'; payload: number };

// 初始状态
const initialState: TodoState = {
    todos: [],
    nextId: 1
};

// Reducer函数（带类型注解）
const todoReducer = (state: TodoState, action: TodoAction): TodoState => {
    switch (action.type) {
        case 'ADD_TODO':
            {
                const newTodo: Todo = {
                    id: state.nextId,
                    text: action.payload,
                    completed: false
                };
                return {
                    ...state,
                    todos: [...state.todos, newTodo],
                    nextId: state.nextId + 1
                };
            }

        case 'TOGGLE_TODO':
            return {
                ...state,
                todos: state.todos.map(todo =>
                    todo.id === action.payload
                        ? { ...todo, completed: !todo.completed }
                        : todo
                )
            };

        case 'DELETE_TODO':
            return {
                ...state,
                todos: state.todos.filter(todo => todo.id !== action.payload)
            };

        default:
            // 确保覆盖所有可能的action类型
            {
                const exhaustiveCheck: never = action;
                return exhaustiveCheck;
            }
    }
};

export function TodoApp() {
    const [state, dispatch] = useReducer(todoReducer, initialState);
    const [inputText, setInputText] = useState('');

    const handleAdd = () => {
        if (!inputText.trim()) return;
        dispatch({ type: 'ADD_TODO', payload: inputText });
        setInputText('');
    };

    return (
        <div style={{ padding: '20px' }}>
            <h2>待办事项列表</h2>
            <div>
                <input
                    type="text"
                    value={inputText}
                    onChange={(e) => setInputText(e.target.value)}
                    placeholder="请输入待办事项"
                />
                <button onClick={handleAdd}>添加</button>
            </div>

            <ul style={{ marginTop: '20px' }}>
                {state.todos.map(todo => (
                    <li
                        key={todo.id}
                        style={{
                            textDecoration: todo.completed ? 'line-through' : 'none',
                            margin: '5px 0',
                            cursor: 'pointer'
                        }}
                        onClick={() => dispatch({ type: 'TOGGLE_TODO', payload: todo.id })}
                    >
                        {todo.text}
                        <button
                            style={{ marginLeft: '10px' }}
                            onClick={(e) => {
                                e.stopPropagation();
                                dispatch({ type: 'DELETE_TODO', payload: todo.id });
                            }}
                        >
                            删除
                        </button>
                    </li>
                ))}
            </ul>
        </div>
    );
}

```

Counter.tsx
``` ts
import { useReducer } from 'react';

// 定义状态类型
interface CounterState {
    count: number;
}

// 定义所有可能的action类型
type CounterAction =
    | { type: 'INCREMENT' }
    | { type: 'DECREMENT' }
    | { type: 'RESET' };

// Reducer函数（带类型注解）
const countReducer = (state: CounterState, action: CounterAction): CounterState => {
    switch (action.type) {
        case 'INCREMENT':
            return { ...state, count: state.count + 1 };
        case 'DECREMENT':
            return { ...state, count: state.count - 1 };
        case 'RESET':
            return { ...state, count: 0 };
        default:
            // TypeScript会检查是否覆盖了所有可能的action类型
            {
                const exhaustiveCheck: never = action;
                return exhaustiveCheck;
            }
    }
};

export function Counter() {
    // 初始化状态，TypeScript会自动推断类型
    const [state, dispatch] = useReducer(countReducer, { count: 0 });

    return (
        <div>
            <h2>计数器: {state.count}</h2>
            <button onClick={() => dispatch({ type: 'INCREMENT' })}>+1</button>
            <button onClick={() => dispatch({ type: 'DECREMENT' })}>-1</button>
            <button onClick={() => dispatch({ type: 'RESET' })}>重置</button>
        </div>
    );
}

```

App.tsx
``` ts
import './App.css'
import { Counter } from './Counter';
import { TodoApp } from './TodoApp';


function App() {


  return (
    <>
      <Counter />
      <TodoApp />
    </>
  );
}

export default App
```