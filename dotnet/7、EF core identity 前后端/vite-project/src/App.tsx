import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import Register from './pages/Register';
import Login from './pages/Login';
import EmailConfirm from './pages/EmailConfirm';
import Profile from './pages/Profile';
import AdminPage from './pages/AdminPage';
import PrivateRoute from './routes/PrivateRoute';
import AdminRoute from './routes/AdminRoute';
// 引入 Bootstrap 样式
import 'bootstrap/dist/css/bootstrap.min.css';

const App: React.FC = () => {
  return (
    <Router>
      <Navbar />
      <Routes>
        {/* 公开路由 */}
        <Route path="/register" element={<Register />} />
        <Route path="/login" element={<Login />} />
        <Route path="/confirm-email" element={<EmailConfirm />} />

        {/* 受保护路由（需登录） */}
        <Route
          path="/profile"
          element={
            <PrivateRoute>
              <Profile />
            </PrivateRoute>
          }
        />

        {/* 管理员路由（需 Admin 角色） */}
        <Route
          path="/admin"
          element={
            <AdminRoute>
              <AdminPage />
            </AdminRoute>
          }
        />

        {/* 默认路由：跳转登录 */}
        <Route path="/" element={<Login />} />
      </Routes>
    </Router>
  );
};

export default App;