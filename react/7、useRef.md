访问 DOM 元素（最常用）

InputExample.ts
``` ts
import { useRef } from 'react'

export default function InputExample() {
  const inputRef = useRef<HTMLInputElement>(null);

  const handleFocus = () => {
    inputRef.current?.focus();
  }

  const handleGetSize = () => {
    const rect = inputRef.current?.getBoundingClientRect();
    console.log("输入框尺寸", rect);
  }

  return (
    <div>
      <input ref={inputRef} type="text" placeholder="点击按钮聚焦我" />
      <button onClick={handleFocus}>聚焦输入框</button>
      <button onClick={handleGetSize}>获取输入框尺寸</button>
    </div>
  )
}

```

``` App.tsx
import './App.css'
import InputExample from './InputExample';

function App() {

  return (
    <InputExample />
  );
}

export default App


```