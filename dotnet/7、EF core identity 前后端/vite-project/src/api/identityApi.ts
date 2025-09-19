import axios, { type AxiosInstance, AxiosError } from 'axios';
import { getToken, removeToken } from '../utils/tokenUtils';
import type {
  ApiResponse,
  LoginResponse,
  UserProfile,
  RegisterFormData,
  LoginFormData,
  ApiError,
} from '../types';

// 创建 Axios 实例（带类型）
const identityApi: AxiosInstance = axios.create({
  baseURL: 'http://localhost:5222/api', // 替换为你的 .NET 后端地址
  headers: {
    'Content-Type': 'application/json',
  },
});

// 请求拦截器：添加 Bearer Token
identityApi.interceptors.request.use(
  (config: import('axios').InternalAxiosRequestConfig): import('axios').InternalAxiosRequestConfig => {
    const token = getToken();
    if (token && config.headers) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error: AxiosError<ApiError>): Promise<AxiosError<ApiError>> => {
    return Promise.reject(error);
  }
);

// 响应拦截器：处理 401/403 错误
identityApi.interceptors.response.use(
  (response) => response,
  (error: AxiosError<ApiError>): Promise<AxiosError<ApiError>> => {
    if (error.response) {
      // 401：Token 无效/过期 → 清除 Token 跳转登录
      if (error.response.status === 401) {
        removeToken();
        window.location.href = '/login';
      }
      // 403：权限不足
      if (error.response.status === 403) {
        alert(error.response.data.message || '权限不足，无法访问！');
        window.location.href = '/profile';
      }
    }
    return Promise.reject(error);
  }
);

// -------------------------- API 接口封装（带类型）--------------------------
/**
 * 注册接口
 * @param data 注册表单数据
 * @returns 成功响应（无返回数据，仅确认状态）
 */
export const register = (
  data: RegisterFormData
): Promise<ApiResponse<null>> => {
  return identityApi.post<ApiResponse<null>>('/register', data).then(res => res.data);
};

/**
 * 登录接口
 * @param data 登录表单数据
 * @returns 登录响应（含 accessToken）
 */
export const login = (data: LoginFormData): Promise<LoginResponse> => {
  return identityApi.post<LoginResponse>('/login', data).then(res => res.data);
};

/**
 * 邮箱验证接口
 * @param userId 用户ID
 * @param code 验证码
 * @returns 验证结果
 */
export const confirmEmail = (
  userId: string,
  code: string
): Promise<ApiResponse<null>> => {
  return identityApi
    .get<ApiResponse<null>>(`/confirmEmail?userId=${userId}&code=${code}`)
    .then(res => res.data);
};

/**
 * 登出接口（后端可选实现）
 * @returns 登出结果
 */
export const logout = (): Promise<ApiResponse<null>> => {
  return identityApi.post<ApiResponse<null>>('/logout').then(res => res.data);
};

/**
 * 获取当前用户资料
 * @returns 用户资料（含角色）
 */
export const getCurrentProfile = (): Promise<ApiResponse<UserProfile>> => {
  return identityApi.get<ApiResponse<UserProfile>>('/Profile').then(res => res.data);
};

/**
 * 管理员专属接口
 * @returns 管理员数据
 */
export const getAdminData = (): Promise<ApiResponse<string>> => {
  return identityApi.get<ApiResponse<string>>('/Profile/admin').then(res => res.data);
};

export default identityApi;