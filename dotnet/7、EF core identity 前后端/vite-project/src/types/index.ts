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
// 定义“登录成功后，后端返回的 Token 等数据结构”
export interface LoginResponse {
  accessToken: string;       // 后端返回的 accessToken
  tokenType?: string;        // 可选：token 类型（如 Bearer）
  expiresIn?: number;        // 可选：Token 过期时间（秒）
  refreshToken?: string;     // 可选：刷新 Token（如果有）
  user?: Partial<AppUser>;   // 可选：用户信息（如果后端返回）
}

// 6. 错误响应类型
export interface ApiError {
  title?: string;
  message?: string;
  errors?: Record<string, string[]>; // 键为错误类型（如DuplicateUserName），值为错误信息数组
  status?: number;
}