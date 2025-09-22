import { Navigate, useLocation } from "react-router";
import { isLoggedIn } from "../utils/tokenUtils";
import type { ReactNode } from "react";

// 定义 Props 类型（仅子组件）
interface PrivateRouteProps {
  children: ReactNode;
}

// 泛型约束组件类型
const PrivateRoute: React.FC<PrivateRouteProps> = ({ children }) => {
  const location = useLocation();

  // 未登录 → 跳转登录页（记录来源地址）
  if (!isLoggedIn()) {
    return <Navigate to="/login" state={{ from: location.pathname }} replace />;
  }

  return <>{children}</>;
};

export default PrivateRoute;
