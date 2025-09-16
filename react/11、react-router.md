``` bash
pnpm add react-router-dom
```

基础路由示例

App.tsx
``` ts
import { Link, Route, BrowserRouter as Router, Routes } from 'react-router-dom';

import './App.css'

function App() {


  const Home = () => {
    return <h2>Home</h2>
  };

  const About = () => {
    return <h2>About</h2>
  };

  const Contact = () => {
    return <h2>Contact</h2>
  }

  const Navbar = () => {
    return (
      <nav style={{ margin: 10 }}>
      <Link to="/" style={{ padding: 5 }}>首页</Link>
      <Link to="/about" style={{ padding: 5 }}>关于我们</Link>
      <Link to="/contact" style={{ padding: 5 }}>联系我们</Link>
    </nav>
    );
  }

  return (
    <Router>
      <Navbar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/about" element={<About />} />
        <Route path="/contact" element={<Contact />} />
      </Routes>
    
    </Router>
  )
}

export default App

```

路由参数示例
component\User.tsx
``` ts
import { useParams } from "react-router-dom";


type UserParams = {
    id: string
}

export default function User() {
    const params = useParams<UserParams>();

    return (
        <div>
            <h1> User page</h1>
            <p>User ID: {params.id}</p>
        </div>
    )
}
```

App.tsx
``` ts
import { Link, Route, BrowserRouter as Router, Routes } from 'react-router-dom';

import './App.css'
import User from './component/User';

function App() {


  const Home = () => {
    return <h2>Home</h2>
  };

  const About = () => {
    return <h2>About</h2>
  };

  const Contact = () => {
    return <h2>Contact</h2>
  }

  const Navbar = () => {
    return (
      <nav style={{ margin: 10 }}>
      <Link to="/" style={{ padding: 5 }}>首页</Link>
      <Link to="/about" style={{ padding: 5 }}>关于我们</Link>
      <Link to="/contact" style={{ padding: 5 }}>联系我们</Link>
      <Link to="/user/123" style={{ padding: 5 }}>用户123</Link>
      <Link to="/user/456" style={{ padding: 5 }}>用户456</Link>
    </nav>
    );
  }

  return (
    <Router>
      <Navbar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/about" element={<About />} />
        <Route path="/contact" element={<Contact />} />
        {/* 带参数的路由 */}
        <Route path="/user/:id" element={<User />} />
      </Routes>
    
    </Router>
  )
}

export default App

```

嵌套路由示例

App.tsx
``` ts
import { Link, Route, BrowserRouter as Router, Routes } from 'react-router-dom';

import './App.css'
import { Dashboard, Profile, Settings, Stats } from './component/Dashboard';

function App() {


  const Home = () => {
    return <h2>Home</h2>
  };

  const About = () => {
    return <h2>About</h2>
  };

  const Contact = () => {
    return <h2>Contact</h2>
  }

  const Navbar = () => {
    return (
      <nav style={{ margin: 10 }}>
        <Link to="/" style={{ padding: 5 }}>首页</Link>
        <Link to="/about" style={{ padding: 5 }}>关于我们</Link>
        <Link to="/contact" style={{ padding: 5 }}>联系我们</Link>
        <Link to="/dashboard" style={{ padding: 5 }}>仪表盘</Link>
      </nav>
    );
  }

  return (
    <Router>
      <Navbar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/about" element={<About />} />
        <Route path="/contact" element={<Contact />} />
        <Route path="/dashboard" element={<Dashboard />}>
          <Route path="profile" element={<Profile />} />
          <Route path="settings" element={<Settings />} />
          <Route path="stats" element={<Stats />} />
        </Route>
    </Routes>
    </Router >
  )
}

export default App

```

Dashboard.tsx
``` ts
import { Outlet, Link } from 'react-router-dom';

// 主仪表盘布局组件
export const Dashboard = () => {
    return (
        <div style={{ display: 'flex' }}>
            <div style={{ padding: 10, borderRight: '1px solid #ccc' }}>
                <Link to="profile" style={{ display: 'block', margin: '10px 0' }}>个人资料</Link>
                <Link to="settings" style={{ display: 'block', margin: '10px 0' }}>设置</Link>
                <Link to="stats" style={{ display: 'block', margin: '10px 0' }}>统计数据</Link>
            </div>
            <div style={{ padding: 10, flex: 1 }}>
                {/* Outlet 用于显示子路由内容 */}
                <Outlet />
            </div>
        </div>
    );
};

// 子组件
export const Profile = () => {
    return <h2>个人资料</h2>;
};

export const Settings = () => {
    return <h2>设置</h2>;
};

export const Stats = () => {
    return <h2>统计数据</h2>;
};
```

