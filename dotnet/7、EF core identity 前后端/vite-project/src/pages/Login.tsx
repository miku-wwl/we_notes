import { useState, useEffect } from 'react';
import { useNavigate, useLocation, Link } from 'react-router-dom';
import { login } from '../api/identityApi';
import { setToken, isLoggedIn } from '../utils/tokenUtils';
import type { ApiResponse, LoginFormData, LoginResponse } from '../types';
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
      // 调用登录接口，返回的是 ApiResponse<LoginResponse> 类型
      const apiResponse: LoginResponse = await login(formData);
      console.log('登录成功，返回数据：', apiResponse);

      // 验证 Token 是否存在
      if (apiResponse.accessToken) {
        setToken(apiResponse.accessToken); // 存储 Token
        navigate(fromPath, { replace: true }); // 跳转来源页
      } else {
        setError('登录失败：未获取到认证 Token！');
      }
    } catch (err) {
      // 捕获 HTTP 错误（如 401/500 等状态码）
      const error = err as AxiosError<ApiResponse<unknown>>;
      // 优先取后端返回的错误信息，否则用默认提示
      setError(
        error.response?.data?.message ||
        '登录失败：网络错误或服务器异常！'
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