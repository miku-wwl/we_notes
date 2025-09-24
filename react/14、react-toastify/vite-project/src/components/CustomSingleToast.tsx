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
        if (reason === 'click') {
          console.log('通知被点击关闭');
        } else if (reason === 'drag') {
          console.log('通知被拖拽关闭');
        } else if (reason === 'timeout') {
          console.log('通知超时自动关闭');
        } else if (reason === 'closeButton') {
          console.log('通过关闭按钮关闭');
        } else if (reason === 'escapeKey') {
          console.log('通过ESC键关闭');
        } else {
          console.log('通知被关闭，原因：', reason);
        }
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
