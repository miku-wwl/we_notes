import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { register } from '../api/identityApi';
import type { RegisterFormData, ApiError } from '../types';
import { AxiosError } from 'axios';

const Register: React.FC = () => {
  // 表单数据（初始值符合 RegisterFormData 类型）
  const [formData, setFormData] = useState<RegisterFormData>({
    email: '',
    password: '',
    confirmPassword: '',
  });
  const [error, setError] = useState<string>(''); // 错误提示
  const [success, setSuccess] = useState<string>(''); // 成功提示
  const [isSubmitting, setIsSubmitting] = useState<boolean>(false); // 提交状态
  const navigate = useNavigate();

  // 表单输入变化（事件类型：ChangeEvent<HTMLInputElement>）
  const handleChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
    setError(''); // 输入时清空错误
  };

  // 提交注册
  const handleSubmit = async (e: React.FormEvent): Promise<void> => {
    e.preventDefault();
    setIsSubmitting(true);
    setError('');

    // 前端基础验证
    if (formData.password !== formData.confirmPassword) {
      setError('两次密码输入不一致！');
      setIsSubmitting(false);
      return;
    }
    if (formData.password.length < 6) {
      setError('密码长度不能少于 6 位！');
      setIsSubmitting(false);
      return;
    }

    try {
      await register(formData); // 调用注册接口（类型匹配）
      setSuccess('注册成功！请查收邮箱验证邮件后登录(邮箱验证功能待实现)～');
      // 3秒后跳转登录页
      setTimeout(() => navigate('/login'), 3000);
    } catch (err) {
      const error = err as AxiosError<ApiError>;
      let errorMsg = '注册失败，请稍后重试！';

      if (error.response?.data.errors) {
        // 遍历 errors 对象，收集所有错误信息
        const errorsObj = error.response.data.errors;
        const errorMessages: string[] = [];
        for (const errorType in errorsObj) {
          if (Object.prototype.hasOwnProperty.call(errorsObj, errorType)) {
            // 每个错误类型（如DuplicateUserName）对应的所有提示，都加入数组
            errorsObj[errorType].forEach(msg => {
              errorMessages.push(msg);
            });
          }
        }
        // 拼接所有错误信息（用分号或换行分隔）
        errorMsg = errorMessages.join('; ');
      } else if (error.response?.data.message) {
        // 有 message 则用 message
        errorMsg = error.response.data.message;
      } else if (error.response?.data.title) {
        // 最后 fallback 到 title
        errorMsg = error.response.data.title;
      }

      setError(errorMsg);
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="container mt-5">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <h2 className="mb-4">用户注册</h2>
          {error && <div className="alert alert-danger">{error}</div>}
          {success && <div className="alert alert-success">{success}</div>}

          <form onSubmit={handleSubmit}>
            {/* 邮箱 */}
            <div className="mb-3">
              <label className="form-label">邮箱（唯一）</label>
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
              <label className="form-label">密码（需含数字和小写字母，至少 6 位）</label>
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

            {/* 确认密码 */}
            <div className="mb-3">
              <label className="form-label">确认密码</label>
              <input
                type="password"
                name="confirmPassword"
                value={formData.confirmPassword}
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
              {isSubmitting ? '注册中...' : '注册'}
            </button>
            <p className="mt-3">
              已有账号？<Link to="/login">去登录</Link>
            </p>
          </form>
        </div>
      </div>
    </div>
  );
};

export default Register;