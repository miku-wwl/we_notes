import { useState, useEffect } from 'react';
import { useNavigate, useLocation, Link } from 'react-router-dom';
import { login } from '../api/identityApi';
import { setToken, isLoggedIn } from '../utils/tokenUtils';
import type { ApiError, LoginFormData, LoginResponse } from '../types';
import type { AxiosError } from 'axios';

const Login: React.FC = () => {
  const [formData, setFormData] = useState<LoginFormData>({
    email: '',
    password: '',
  });
  const [error, setError] = useState<string>('');
  const [isSubmitting, setIsSubmitting] = useState<boolean>(false);
  const navigate = useNavigate();
  const location = useLocation();

  // 已登录 → 跳转个人资料页
  useEffect(() => {
    if (isLoggedIn()) {
      navigate('/profile');
    }
  }, [navigate]);

  // 登录后返回来源地址（类型：LocationState 含 from 字段）
  interface LocationState {
    from?: string;
  }
  const fromPath = (location.state as LocationState)?.from || '/profile';

  // 表单变化
  const handleChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
    setError('');
  };

  // 提交登录
  const handleSubmit = async (e: React.FormEvent): Promise<void> => {
    e.preventDefault();
    setIsSubmitting(true);
    setError('');

    try {
      const res = await login(formData); // 响应类型：ApiResponse<LoginResponse>
      const loginData: LoginResponse = res.data.data; // 提取 Token 数据
      
      // 存储 Token（类型：string）
      if (loginData.accessToken) {
        setToken(loginData.accessToken);
        navigate(fromPath, { replace: true }); // 跳转来源页
      } else {
        setError('登录失败：未获取到认证 Token！');
      }
    } catch (err) {
      const error = err as AxiosError<ApiError>;
      setError(
        error.response?.data.message ||
          error.response?.data.title ||
          '登录失败：邮箱未验证或账号密码错误！'
      );
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="container mt-5">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <h2 className="mb-4">用户登录</h2>
          {error && <div className="alert alert-danger">{error}</div>}

          <form onSubmit={handleSubmit}>
            {/* 邮箱 */}
            <div className="mb-3">
              <label className="form-label">邮箱</label>
              <input
                type="email"
                name="email"
                value={formData.email}
                onChange={handleChange}
                className="form-control"
                required
                disabled={isSubmitting}
              />
            </div>

            {/* 密码 */}
            <div className="mb-3">
              <label className="form-label">密码</label>
              <input
                type="password"
                name="password"
                value={formData.password}
                onChange={handleChange}
                className="form-control"
                required
                disabled={isSubmitting}
              />
            </div>

            <button
              type="submit"
              className="btn btn-primary"
              disabled={isSubmitting}
            >
              {isSubmitting ? '登录中...' : '登录'}
            </button>
            <p className="mt-3">
              还没账号？<Link to="/register">去注册</Link>
            </p>
            <p className="mt-1">
              未收到验证邮件？<Link to="/confirm-email">重新验证</Link>
            </p>
          </form>
        </div>
      </div>
    </div>
  );
};

export default Login;