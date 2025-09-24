import React from 'react';
import { toast, type ToastOptions } from 'react-toastify';

const CustomSingleToast: React.FC = () => {
  // 单个通知：不自动关闭、点击关闭、自定义关闭回调
  const handleCustomToast = () => {
    const toastOptions: ToastOptions = {
      autoClose: false, // 取消自动关闭
      toastId: 'persistent-toast', // 自定义ID
      onClose: (reason?: string | boolean) => {
        // 关闭时触发回调，可判断关闭原因
          console.log('reason', reason);
      },
    };

    toast.info('这条通知不会自动关闭，点击才会消失 👆', toastOptions);
  };

  return (
    <div style={{ padding: '20px' }}>
      <h2>单个通知自定义</h2>
      <button onClick={handleCustomToast}>
        触发持久化通知
      </button>
    </div>
  );
};

export default CustomSingleToast;
