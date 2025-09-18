import { Link, useNavigate } from 'react-router-dom';
import { useState, useEffect } from 'react';
import { isLoggedIn, removeToken } from '../utils/tokenUtils';
import { getCurrentProfile, logout } from '../api/identityApi';
import type { ApiError, UserProfile } from '../types';
import type { AxiosError } from 'axios';

const Navbar: React.FC = () => {
  const [userRoles, setUserRoles] = useState<string[]>([]); // 角色数组
  const [loadingRoles, setLoadingRoles] = useState<boolean>(true);
  const navigate = useNavigate();
  const loggedIn = isLoggedIn();

  // 获取用户角色（用于显示管理员菜单）
  useEffect(() => {
    if (loggedIn) {
      const fetchRoles = async (): Promise<void> => {
        try {
          const res = await getCurrentProfile();
          const user: UserProfile = res.data;
          setUserRoles(user.Roles);
        } catch (err) {
          const error = err as AxiosError<ApiError>;
          console.error('获取用户角色失败：', error.response?.data.message);
        } finally {
          setLoadingRoles(false);
        }
      };
      fetchRoles();
    } else {
      setUserRoles([]);
      setLoadingRoles(false);
    }
  }, [loggedIn]);

  // 登出处理
  const handleLogout = async (): Promise<void> => {
    try {
      await logout(); // 调用后端登出接口
    } catch (err) {
      const error = err as AxiosError<ApiError>;
      console.error('登出失败：', error.response?.data.message);
    } finally {
      removeToken();
      navigate('/login');
    }
  };

  if (loadingRoles) return <nav className="navbar navbar-dark bg-primary">加载中...</nav>;

  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-primary">
      <div className="container">
        <Link className="navbar-brand" to="/">
          Identity TS Demo
        </Link>
        <div className="collapse navbar-collapse">
          <ul className="navbar-nav me-auto">
            {/* 未登录：注册/登录 */}
            {!loggedIn && (
              <>
                <li className="nav-item">
                  <Link className="nav-link" to="/register">
                    注册
                  </Link>
                </li>
                <li className="nav-item">
                  <Link className="nav-link" to="/login">
                    登录
                  </Link>
                </li>
              </>
            )}

            {/* 已登录：个人资料 + 管理员菜单（有 Admin 角色才显示） */}
            {loggedIn && (
              <>
                <li className="nav-item">
                  <Link className="nav-link" to="/profile">
                    个人资料
                  </Link>
                </li>
                {userRoles.includes('Admin') && (
                  <li className="nav-item">
                    <Link className="nav-link" to="/admin">
                      管理员页面
                    </Link>
                  </li>
                )}
                <li className="nav-item">
                  <button
                    className="btn btn-outline-light nav-link"
                    onClick={handleLogout}
                  >
                    登出
                  </button>
                </li>
              </>
            )}
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;