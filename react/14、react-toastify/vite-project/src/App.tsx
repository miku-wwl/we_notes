import React from 'react';
import { ToastContainer, type ToastContainerProps } from 'react-toastify';
import BasicToast from './components/BasicToast';
import CustomSingleToast from './components/CustomSingleToast';
import CustomContentToast from './components/CustomContentToast';
import ManualControlToast from './components/ManualControlToast';
import LoginForm from './components/LoginForm';

const App: React.FC = () => {
  // 全局 Toast 配置
  const toastContainerProps: ToastContainerProps = {
    position: "bottom-center",
    autoClose: 4000,
    hideProgressBar: false,
    newestOnTop: false,
    closeOnClick: true,
    rtl: false,
    pauseOnFocusLoss: true,
    draggable: true,
    pauseOnHover: true,
    theme: "colored",
  };

  return (
    <div className="App" style={{ padding: '20px' }}>
      <h1>React Toastify TypeScript 实战</h1>
      <hr style={{ margin: '20px 0' }} />
      
      <BasicToast />
      <hr style={{ margin: '20px 0' }} />
      
      <CustomSingleToast />
      <hr style={{ margin: '20px 0' }} />
      
      <CustomContentToast />
      <hr style={{ margin: '20px 0' }} />
      
      <ManualControlToast />
      <hr style={{ margin: '20px 0' }} />
      
      <LoginForm />
      
      {/* 全局 Toast 容器 */}
      <ToastContainer {...toastContainerProps} />
    </div>
  );
};

export default App;
