缓存昂贵的计算结果
DataItem.tsx
``` ts
type DataItem = {
  id: number;
  name: string;
  value: number;
}

export type { DataItem };
```

DataProcessor.tsx
``` ts
import { useMemo } from "react";
import type { DataItem } from "./DataItem";


export default function DataProcessor({ data }: { data: DataItem[] }) {
const processedData = useMemo(() => {
    return data
      .filter(item => item.value > 100)
      .sort((a, b) => b.value - a.value)
      .map(item => ({ ...item, normalized: item.value / 100 }));
  }, [data]); // 依赖 data：仅 data 变化时重新计算

  return (
    <ul>
      {processedData.map(item => (
        <li key={item.id}>{item.name}</li>
      ))}
    </ul>
  );
}
```

App.tsx
``` ts
import { useState } from 'react';
import './App.css'
import DataProcessor from './DataProcessor';
import type { DataItem } from './DataItem';


function App() {
 // 1. 模拟原始数据（包含10条数据，用于放大计算开销）
  const [data, setData] = useState<DataItem[]>(() => {
    return Array.from({ length: 10 }, (_, i) => ({
      id: i,
      name: `Item ${i}`,
      value: Math.floor(Math.random() * 200) // 随机值 0-199
    }));
  });

  // 2. 定义不相关的状态（用于测试重渲染影响）
  const [count, setCount] = useState(0);

  // 3. 生成新数据的函数（触发 DataProcessor 重新计算）
  const generateNewData = () => {
    setData(prev => 
      prev.map(item => ({
        ...item,
        value: Math.floor(Math.random() * 200) // 更新 value 字段
      }))
    );
  };

  return (
    <div className="app">
      <h1>useMemo 优化演示</h1>
      
      {/* 交互区：用于触发数据变化和无关状态变化 */}
      <div className="controls">
        <button onClick={generateNewData}>
          生成新数据（触发计算）
        </button>
        <div className="counter">
          <p>无关计数器：{count}</p>
          <button onClick={() => setCount(prev => prev + 1)}>
            增加计数（不触发计算）
          </button>
        </div>
      </div>
      
      {/* 数据处理组件（使用 useMemo 优化） */}
      <DataProcessor data={data} />
    </div>
  );
}

export default App
```