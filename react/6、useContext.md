跨组件共享

ThemeContext.tsx
``` ts
import { createContext } from "react";

type Theme = "light" | "dark";
const ThemeContext = createContext<Theme>("light");
export default ThemeContext;
export type { Theme };
```

Child.tsx
``` ts
import { useContext } from 'react'
import ThemeContext from './ThemeContext';

export default function Child() {
    const theme = useContext(ThemeContext);

    return (
        <div>当前主题 {theme}</div>
    )
}

```

Parent.tsx
``` ts
import Child from './Child'

export default function Parent() {
  return (
    <Child/>
  )
}

```

App.tsx
``` ts
import { useState } from 'react'
import './App.css'
import type { Theme } from './ThemeContext';
import ThemeContext from './ThemeContext';
import Parent from './Parent';

function App() {
  const [theme, setTheme] = useState<Theme>("light");

  return (
    <ThemeContext.Provider value={theme}>
      <div className="App">
        <button onClick={() => setTheme(theme => theme === "light" ? "dark" : "light")}> switch the theme </button>
        <Parent />
      </div>
    </ThemeContext.Provider>
  );
}

export default App
```

共享复杂数据（对象 / 函数）

ThemeContext.tsx
``` ts
import { createContext } from "react";

type Theme = 'light' | 'dark';
type ThemeContextType =  {
    theme: Theme;
    toggleTheme: () => void;
}

const ThemeContext = createContext<ThemeContextType> ({
    theme: 'light',
    toggleTheme: () => {},
})

export default ThemeContext
export type { Theme };
```

Child.tsx
``` ts
import { useContext } from "react";
import ThemeContext from "./ThemeContext";


export default function Child() {
    const { theme, toggleTheme } = useContext(ThemeContext);

    return (
        <>
            <p>Current theme: {theme}</p>
            <button onClick={toggleTheme}>Toggle Theme</button>
        </>
    )
}
```

Parent.tsx
``` ts
import Child from './Child'

export default function Parent() {
  return (
    <Child/>
  )
}

```
App.tsx
``` ts
import { useState } from 'react'
import './App.css'
import type { Theme } from './ThemeContext';
import ThemeContext from './ThemeContext';
import Parent from './Parent';

function App() {

  const [theme, setTheme] = useState<Theme>("light");

  const toggleTheme = () => {
    setTheme(theme => theme === "light" ? "dark" : "light")
  }

  return (
    <ThemeContext.Provider value={{ theme, toggleTheme }}>
      <Parent />
    </ThemeContext.Provider>
  );
}

export default App


```


默认值的生效条件
```
```