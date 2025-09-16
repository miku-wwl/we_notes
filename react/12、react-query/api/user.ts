import axios from 'axios';

// 定义用户类型
export type User  = {
  id: number;
  name: string;
  email: string;
  // 可根据实际接口扩展其他字段（如username、address等）
}

// 获取所有用户
export const fetchUsers = async (): Promise<User[]> => {
  const { data } = await axios.get('https://jsonplaceholder.typicode.com/users');
  return data;
};

// 根据ID获取单个用户
export const fetchUserById = async (id: number): Promise<User> => {
  const { data } = await axios.get(`https://jsonplaceholder.typicode.com/users/${id}`);
  return data;
};

// 添加新用户（不含id，由服务器生成）
export const addUser = async (newUser: Omit<User, 'id'>): Promise<User> => {
  const { data } = await axios.post('https://jsonplaceholder.typicode.com/users', newUser);
  return data;
};

// 删除用户
export const deleteUser = async (id: number): Promise<void> => {
  await axios.delete(`https://jsonplaceholder.typicode.com/users/${id}`);
};

// 分页获取用户
export const fetchUsersByPage = async ({ pageParam = 1 }): Promise<{
  users: User[];
  nextPage: number | null;
}> => {
  const { data } = await axios.get(
    `https://jsonplaceholder.typicode.com/users?_page=${pageParam}&_limit=5`
  );
  // 模拟分页逻辑（实际应根据接口返回的总页数判断）
  return {
    users: data,
    nextPage: pageParam < 3 ? pageParam + 1 : null,
  };
};
