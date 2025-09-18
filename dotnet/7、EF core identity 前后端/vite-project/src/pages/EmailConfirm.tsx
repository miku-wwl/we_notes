import { useState, useEffect } from 'react';
import { useLocation, Link } from 'react-router-dom';
import { confirmEmail } from '../api/identityApi';

const EmailConfirm = () => {
  const [status, setStatus] = useState(''); // 验证结果（成功/失败）
  const [loading, setLoading] = useState(true);
  const location = useLocation();

  useEffect(() => {
    // 从 URL 查询参数中获取 userId 和 code（如 /confirm-email?userId=xxx&code=xxx）
    const searchParams = new URLSearchParams(location.search);
    const userId = searchParams.get('userId');
    const code = searchParams.get('code');

    // 验证参数是否存在
    if (!userId || !code) {
      setStatus('无效的验证链接！');
      setLoading(false);
      return;
    }

    // 调用邮箱验证接口
    const verifyEmail = async () => {
      try {
        await confirmEmail(userId, code);
        setStatus('邮箱验证成功！现在可以登录了～');
      } catch {
        setStatus(
          '邮箱验证失败，请重新获取验证链接！'
        );
      } finally {
        setLoading(false);
      }
    };

    verifyEmail();
  }, [location.search]);

  if (loading) return <div className="container mt-5">验证中...</div>;

  return (
    <div className="container mt-5">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <h2 className="mb-4">邮箱验证</h2>
          <div className={`alert ${status.includes('成功') ? 'alert-success' : 'alert-danger'}`}>
            {status}
          </div>
          <Link to="/login" className="btn btn-primary">
            去登录
          </Link>
        </div>
      </div>
    </div>
  );
};

export default EmailConfirm;