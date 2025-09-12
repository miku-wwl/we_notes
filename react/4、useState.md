useState 使用函数式更新
``` ts
import { useState } from 'react'
import './App.css'


function App() {
  const [count, setCount] = useState(0);

  const increment = () => {
    setCount(count => count + 1);
    console.log(count);
  }
  const decrement = () => {
    setCount(count => count - 1);
    console.log(count);
  }

  return (
    <>
      <div className="App">
        <button onClick={increment}>count is {count} 加1</button>
      </div>
      <div>
        <button onClick={decrement}>count is {count} 减1</button>
      </div>
    </>
  );
}

export default App

```

更新对象状态
``` ts
import { useState } from 'react'
import './App.css'


function App() {
  const [user, setUser] = useState({ name: '张三', age: 18 });


  const updateUser = () => {
    setUser((user) => ({ name: user.name + "1", age: user.age + 1 }));
  };

  return (
    <div>
      <p>姓名: {user.name}</p>
      <p>年龄: {user.age}</p>

      <button onClick={updateUser}>更新用户</button>
    </div>
  );
}

export default App

```

更新数组状态
``` ts
import { useState } from 'react'
import './App.css'


function App() {
  const [todos, setTodos] = useState(["t1", "t2"]);

  const addTodos = () => {
    setTodos((todos) => [...todos, `t${todos.length + 1}`]);
  }


  return (
    <div>
      <ul>
        {
          todos.map((todo) => (
            <li key={todo}>{todo}</li>
          ))
        }
      </ul>
      <button onClick={addTodos}>添加代办</button>
    </div>
  );
}

export default App

```

初始值的「惰性初始化」
``` ts
import { useState } from 'react'
import './App.css'

function expensive() {
  let sum = 0;

  for (let i = 0; i < 10000000; i++) {
    sum += Math.sqrt(i);
  }
  return sum
}

function App() {
  const [data, setData] = useState(
    () => expensive()
  )

  return (
    <div>
      <h1>{data} 首次初始化</h1>
    </div>
  );
}

export default App


```