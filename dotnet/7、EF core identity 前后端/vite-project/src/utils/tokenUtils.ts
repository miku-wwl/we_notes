// 存储 Token（类型：string）
export const setToken = (token: string): void => {
  localStorage.setItem('identityToken', token);
};

// 获取 Token（返回：string | null）
export const getToken = (): string | null => {
  return localStorage.getItem('identityToken');
};

// 删除 Token
export const removeToken = (): void => {
  localStorage.removeItem('identityToken');
};

// 检查登录状态（返回：boolean）
export const isLoggedIn = (): boolean => {
  return !!getToken();
};