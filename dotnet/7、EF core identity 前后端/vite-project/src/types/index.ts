// 1. 用户相关类型
export interface AppUser {
  id: string;
  userName: string;
  email: string;
  nickName?: string | null; // 对应后端 AppUser 的 NickName（可选）
  emailConfirmed: boolean;
}

// 2. 用户资料（含角色）- 个人资料接口返回类型
export interface UserProfile extends AppUser {
  Roles: string[]; // 用户所属角色（如 ["Admin", "User"]）
}

// 3. 表单数据类型
export interface RegisterFormData {
  email: string;
  userName?: string;
  nickName?: string;
  password: string;
  confirmPassword: string;
}

export interface LoginFormData {
  email: string;
  password: string;
}

// 4. API 响应类型（统一后端响应格式，根据实际后端调整）
export interface ApiResponse<T> {
  success: boolean;
  data: T;
  message?: string;
}

// 5. 登录响应（后端返回 accessToken）
export interface LoginResponse {
  data: LoginResponse;
  accessToken: string;
  expiresIn?: number; // Token 过期时间（可选）
  user?: Partial<AppUser>; // 可选：返回部分用户信息
}

// 6. 错误响应类型
export interface ApiError {
  title?: string;
  message?: string;
  errors?: Record<string, string[]>; // 键为错误类型（如DuplicateUserName），值为错误信息数组
  status?: number;
}