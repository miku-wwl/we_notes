import React from 'react';
import { toast, type ToastOptions } from 'react-toastify';

const BasicToast: React.FC = () => {
  // 通用配置选项
  const commonOptions: ToastOptions = {
    position: "top-right",
    autoClose: 3000,
    hideProgressBar: false,
    closeOnClick: true,
    pauseOnHover: true,
    draggable: true,
    progress: undefined,
  };

  // 触发成功通知
  const handleSuccess = () => {
    toast.success('恭喜！表单提交成功 🎉', {
      ...commonOptions,
      autoClose: 3000,
    });
  };

  // 触发错误通知
  const handleError = () => {
    toast.error('抱歉！接口请求失败 ❌', {
      ...commonOptions,
      autoClose: 5000,
    });
  };

  // 触发信息通知
  const handleInfo = () => {
    toast.info('提示：请检查个人信息后提交 ℹ️', commonOptions);
  };

  // 触发警告通知
  const handleWarning = () => {
    toast.warning('注意：密码将于7天后过期 ⚠️', commonOptions);
  };

  // 触发默认通知
  const handleDefault = () => {
    toast('这是一条通用通知 📢', commonOptions);
  };

  return (
    <div style={{ padding: '20px' }}>
      <h2>基础通知示例</h2>
      <button onClick={handleSuccess} style={{ margin: '0 8px' }}>
        成功通知
      </button>
      <button onClick={handleError} style={{ margin: '0 8px' }}>
        错误通知
      </button>
      <button onClick={handleInfo} style={{ margin: '0 8px' }}>
        信息通知
      </button>
      <button onClick={handleWarning} style={{ margin: '0 8px' }}>
        警告通知
      </button>
      <button onClick={handleDefault} style={{ margin: '0 8px' }}>
        默认通知
      </button>
    </div>
  );
};

export default BasicToast;
