import React, { useState } from 'react';
import { toast, type ToastOptions } from 'react-toastify';

const ManualControlToast: React.FC = () => {
  // 存储通知的 toastId，可能是 string、number 或 null
  const [toastId, setToastId] = useState<string | number | null>(null);

  // 触发通知并记录 toastId
  const handleShowToast = () => {
    const options: ToastOptions = {
      autoClose: false, // 不自动关闭
      toastId: 'manual-toast', // 自定义固定ID
    };

    const id = toast.info('点击下方按钮关闭我 🚪', options);
    setToastId(id);
  };

  // 手动关闭指定通知
  const handleCloseToast = () => {
    if (toastId) {
      toast.dismiss(toastId); // 通过 toastId 关闭
      // 或直接使用固定ID：toast.dismiss('manual-toast')
    }
  };

  // 批量关闭所有通知
  const handleCloseAllToasts = () => {
    toast.dismiss(); // 关闭所有通知
    setToastId(null);
  };

  // 检查通知是否显示
  const checkToastStatus = () => {
    const toastIdentifier = 'manual-toast';
    const isActive = toast.isActive(toastIdentifier);
    
    if (isActive) {
      toast.info('目标通知正在显示中 ✅', { autoClose: 2000 });
    } else {
      toast.info('目标通知已关闭 ❌', { autoClose: 2000 });
    }
  };

  return (
    <div style={{ padding: '20px' }}>
      <h2>手动控制通知</h2>
      <button onClick={handleShowToast} style={{ margin: '0 8px' }}>
        显示通知
      </button>
      <button onClick={handleCloseToast} style={{ margin: '0 8px' }}>
        关闭通知
      </button>
      <button onClick={handleCloseAllToasts} style={{ margin: '0 8px' }}>
        关闭所有通知
      </button>
      <button onClick={checkToastStatus} style={{ margin: '0 8px' }}>
        检查通知状态
      </button>
    </div>
  );
};

export default ManualControlToast;
