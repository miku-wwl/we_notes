避免子组件不必要的重渲染

Client.tsx
``` tsx
import { memo } from 'react'

type ChildProps = {
    onButtonClick: () => void;
};

// 使用memo包装组件，避免不必要的重渲染
const Child = memo(({ onButtonClick }: ChildProps) => {
    console.log('子组件重渲染了');

    return (
        <button onClick={onButtonClick}>
            点击
        </button>
    );
});

export default Child;

```

Parent.tsx
``` tsx
import { useCallback, useState } from 'react'
import Child from './Child';

export default function Parent() {
    const [count, setCount] = useState(0);

    // 缓存函数：依赖不变时，引用始终一致
    const handleClick = useCallback(() => {
        console.log('点击了按钮');
    }, []); // 空依赖：函数永远不会重新创建

    return (
        <div>
            <p>计数：{count}</p>
            <button onClick={() => setCount((prev: number) => prev + 1)}>加 1</button>
            <Child onButtonClick={handleClick} />
        </div>
    );
}

```

App.tsx
``` tsx
import './App.css'
import Parent from './Parent';

function App() {

  return (
    <Parent />
  );
}

export default App
```