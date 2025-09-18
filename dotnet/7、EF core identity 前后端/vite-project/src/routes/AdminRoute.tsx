import { Navigate, useLocation, useNavigate } from 'react-router-dom';
import { type ReactNode, useState, useEffect } from 'react';
import { isLoggedIn } from '../utils/tokenUtils';
import { getCurrentProfile } from '../api/identityApi';
import type { ApiError, UserProfile } from '../types';
import type { AxiosError } from 'axios';


interface AdminRouteProps {
  children: ReactNode;
}

const AdminRoute: React.FC<AdminRouteProps> = ({ children }) => {
  const [isAdmin, setIsAdmin] = useState<boolean | null>(null); // 初始：未判断
  const [loading, setLoading] = useState<boolean>(true);
  const location = useLocation();
  const navigate = useNavigate();

  useEffect(() => {
    // 1. 先检查登录状态
    if (!isLoggedIn()) {
      navigate('/login', { state: { from: location.pathname }, replace: true });
      setLoading(false);
      setIsAdmin(false);
      return;
    }

    // 2. 检查是否为 Admin 角色
    const checkAdminRole = async () => {
      try {
        const res = await getCurrentProfile();
        const user: UserProfile = res.data; // 后端响应格式：{ success: true, data: UserProfile }
        setIsAdmin(user.Roles.includes('Admin')); // 判断是否包含 Admin 角色
      } catch (error) {
        const err = error as AxiosError<ApiError>;
        console.error('检查管理员角色失败：', err.response?.data.message);
        setIsAdmin(false);
      } finally {
        setLoading(false);
      }
    };

    checkAdminRole();
  }, [navigate, location.pathname]);

  // 加载中 → 显示提示
  if (loading) {
    return <div className="container mt-5">权限验证中...</div>;
  }

  // 不是 Admin → 跳转个人资料页
  if (!isAdmin) {
    return <Navigate to="/profile" replace />;
  }

  // 是 Admin → 渲染子组件
  return <>{children}</>;
};

export default AdminRoute;