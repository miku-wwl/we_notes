import React from 'react';
import { toast, type ToastOptions } from 'react-toastify';
import { FaCheckCircle, FaSyncAlt, FaSpinner } from 'react-icons/fa';

const CustomContentToast: React.FC = () => {
  // 自定义内容：图标 + 文字 + 按钮
  const handleCustomContent = () => {
    const toastOptions: ToastOptions = {
      autoClose: 6000,
      position: "top-right"
    };

    toast.success(
      <div style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
        {/* 自定义图标 */}
        <FaCheckCircle size={20} />
        <div>
          <p style={{ margin: 0 }}>数据同步成功！</p>
          {/* 自定义按钮：点击刷新页面 */}
          <button 
            onClick={() => window.location.reload()}
            style={{ 
              marginTop: '8px', 
              padding: '4px 8px', 
              border: 'none', 
              background: '#4CAF50', 
              color: 'white', 
              borderRadius: '4px',
              cursor: 'pointer'
            }}
          >
            <FaSyncAlt size={14} style={{ marginRight: '4px' }} />
            刷新页面
          </button>
        </div>
      </div>,
      toastOptions
    );
  };

  // 动态加载通知
  const handleLoadingToast = () => {
    // 1. 先触发一个“加载中”的通知，并记录其 toastId
    const toastOptions: ToastOptions = {
      autoClose: false, // 加载中不自动关闭
      hideProgressBar: true, // 隐藏进度条
    };

    const toastId: string | number = toast.info(
      <div style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
        <FaSpinner size={18} style={{ animation: 'spin 1s linear infinite' }} />
        <span>正在上传文件... 📤</span>
      </div>,
      toastOptions
    );

    // 2. 模拟异步操作（如文件上传）
    const timer = setTimeout(() => {
      // 3. 异步操作完成后，更新通知内容为“成功”
      toast.update(toastId, {
        render: '文件上传成功！ 🎊',
        type: 'success', // 切换为成功类型
        autoClose: 3000, // 3秒后关闭
        hideProgressBar: false, // 显示进度条
      });
      clearTimeout(timer);
    }, 2000); // 模拟2秒加载时间
  };

  return (
    <>
      <style>
        {`
          @keyframes spin {
            100% { transform: rotate(360deg); }
          }
        `}
      </style>
      <div style={{ padding: '20px' }}>
        <h2>自定义通知内容</h2>
        <button onClick={handleCustomContent} style={{ margin: '0 8px' }}>
          触发带图标和按钮的通知
        </button>
        <button onClick={handleLoadingToast} style={{ margin: '0 8px' }}>
          触发加载状态通知
        </button>
      </div>
    </>
  );
};

export default CustomContentToast;
