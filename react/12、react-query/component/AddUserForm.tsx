import { useMutation, useQueryClient } from '@tanstack/react-query';
import { addUser, type User } from '../api/user';
import { useState } from 'react';

export default function AddUserForm() {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const queryClient = useQueryClient();

  // 添加用户的mutation
  const { mutate, isPending, isError, isSuccess } = useMutation<User, Error, Omit<User, 'id'>>({
    mutationFn: addUser,
    onSuccess: () => {
      // 成功后刷新用户列表
      queryClient.invalidateQueries({ queryKey: ['users'] });
    },
  });

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!name || !email) return;
    mutate({ name, email });
    // 清空表单
    setName('');
    setEmail('');
  };

  return (
    <form onSubmit={handleSubmit} className="p-4 max-w-md mx-auto">
      <h3 className="text-lg font-bold mb-4">添加新用户</h3>
      <div className="space-y-4">
        <div>
          <label className="block mb-1">姓名</label>
          <input
            type="text"
            value={name}
            onChange={(e) => setName(e.target.value)}
            className="w-full p-2 border rounded"
            placeholder="请输入姓名"
          />
        </div>
        <div>
          <label className="block mb-1">邮箱</label>
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            className="w-full p-2 border rounded"
            placeholder="请输入邮箱"
          />
        </div>
        <button
          type="submit"
          disabled={isPending}
          className="w-full p-2 bg-blue-500 text-white rounded hover:bg-blue-600 disabled:opacity-50"
        >
          {isPending ? '提交中...' : '添加用户'}
        </button>
        {isError && <p className="text-red-500 text-sm">添加失败，请重试</p>}
        {isSuccess && <p className="text-green-500 text-sm">添加成功！</p>}
      </div>
    </form>
  );
}