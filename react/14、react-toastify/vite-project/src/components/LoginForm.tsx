import React, { useState } from 'react';
import { toast, type ToastOptions } from 'react-toastify';
import { FaSpinner, FaSignInAlt } from 'react-icons/fa';

// 定义表单数据类型
interface FormData {
  username: string;
  password: string;
}

// 定义登录API响应类型
interface LoginResponse {
  success: boolean;
  message: string;
}

const LoginForm: React.FC = () => {
  // 表单状态
  const [formData, setFormData] = useState<FormData>({
    username: '',
    password: '',
  });
  
  // 加载状态（避免重复点击）
  const [isLoading, setIsLoading] = useState<boolean>(false);

  // 处理表单输入变化
  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  // 模拟登录 API 请求（成功/失败随机）
  const mockLoginAPI = async (username: string, password: string): Promise<LoginResponse> => {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        // 模拟逻辑：用户名=admin，密码=123456 则成功
        if (username === 'admin' && password === '123456') {
          resolve({ success: true, message: '登录成功' });
        } else {
          reject(new Error('用户名或密码错误，请重试'));
        }
      }, 1500); // 模拟1.5秒请求时间
    });
  };

  // 登录按钮点击事件
  const handleLogin = async () => {
    // 1. 表单验证
    if (!formData.username || !formData.password) {
      toast.warning('请填写用户名和密码 ⚠️', { autoClose: 2000 });
      return;
    }

    // 2. 配置加载中通知选项
    const loadingToastOptions: ToastOptions = {
      autoClose: false, // 加载中不自动关闭
      hideProgressBar: true, // 隐藏进度条
      toastId: 'login-loading',
    };

    // 3. 触发加载中通知并记录ID
    const loadingToastId = toast.info(
      <div style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
        <FaSpinner size={18} className="fa-spin" />
        <span>正在登录中，请稍候...</span>
      </div>,
      loadingToastOptions
    );

    // 4. 触发 API 请求
    setIsLoading(true);
    try {
      const response = await mockLoginAPI(formData.username, formData.password);
      
      // 5. 请求成功：更新通知为成功，并跳转
      toast.update(loadingToastId, {
        render: <div>🎉 {response.message}，即将跳转首页...</div>,
        type: 'success',
        autoClose: 2000,
        hideProgressBar: false,
      });

      // 2秒后跳转（实际项目中可使用 react-router 的 useNavigate）
      const redirectTimer = setTimeout(() => {
        window.location.href = '/home'; // 模拟跳转
        clearTimeout(redirectTimer);
      }, 2000);

    } catch (error) {
      // 6. 请求失败：更新通知为错误
      const errorMessage = error instanceof Error ? error.message : '登录失败，请重试';
      toast.update(loadingToastId, {
        render: <div>❌ {errorMessage}</div>,
        type: 'error',
        autoClose: 3000,
        hideProgressBar: false,
      });

    } finally {
      // 7. 无论成功/失败，重置加载状态
      setIsLoading(false);
    }
  };

  // 关闭所有通知
  const handleCloseAll = () => {
    toast.dismiss();
  };

  return (
    <>
      <style>
        {`
          .fa-spin {
            animation: fa-spin 1s infinite linear;
          }
          @keyframes fa-spin {
            0% { transform: rotate(0deg);}
            100% { transform: rotate(360deg);}
          }
        `}
      </style>
      <div style={{ 
        maxWidth: '400px', 
      margin: '20px auto', 
      padding: '24px', 
      border: '1px solid #eee', 
      borderRadius: '8px' 
    }}>
      <h2 style={{ textAlign: 'center', marginBottom: '20px' }}>
        <FaSignInAlt style={{ marginRight: '8px' }} /> 登录表单
      </h2>

      {/* 用户名输入框 */}
      <div style={{ marginBottom: '16px' }}>
        <label style={{ display: 'block', marginBottom: '4px' }}>用户名</label>
        <input
          type="text"
          name="username"
          value={formData.username}
          onChange={handleInputChange}
          style={{
            width: '100%',
            padding: '8px 12px',
            border: '1px solid #ddd',
            borderRadius: '4px',
          }}
          disabled={isLoading} // 加载中禁用输入
        />
      </div>

      {/* 密码输入框 */}
      <div style={{ marginBottom: '20px' }}>
        <label style={{ display: 'block', marginBottom: '4px' }}>密码</label>
        <input
          type="password"
          name="password"
          value={formData.password}
          onChange={handleInputChange}
          style={{
            width: '100%',
            padding: '8px 12px',
            border: '1px solid #ddd',
            borderRadius: '4px',
          }}
          disabled={isLoading}
        />
      </div>

      {/* 操作按钮 */}
      <div style={{ display: 'flex', gap: '12px' }}>
        <button
          onClick={handleLogin}
          style={{
            flex: 1,
            padding: '10px',
            border: 'none',
            borderRadius: '4px',
            background: '#2196F3',
            color: 'white',
            cursor: isLoading ? 'not-allowed' : 'pointer',
            opacity: isLoading ? 0.7 : 1,
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
          }}
          disabled={isLoading}
        >
          {isLoading ? (
            <FaSpinner size={16} className="fa-spin" style={{ marginRight: '8px' }} />
          ) : null}
          登录
        </button>
        <button
          onClick={handleCloseAll}
          style={{
            flex: 1,
            padding: '10px',
            border: '1px solid #ddd',
            borderRadius: '4px',
            background: 'white',
            color: '#666',
            cursor: 'pointer',
          }}
          disabled={isLoading}
        >
          关闭所有通知
        </button>
      </div>
    </div>
    </>
  );
};

export default LoginForm;
