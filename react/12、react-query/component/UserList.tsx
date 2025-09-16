import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { fetchUsers, deleteUser, type User } from '../api/user';

export default function UserList() {
  const queryClient = useQueryClient();

  // 获取用户列表
  const { data: users, isLoading, isError } = useQuery<User[], Error>({
    queryKey: ['users'],
    queryFn: fetchUsers,
  });

  // 删除用户的mutation
  const { mutate: deleteUserMutate, isPending: isDeleting } = useMutation({
    mutationFn: deleteUser,
    onSuccess: () => {
      // 删除成功后刷新列表缓存
      queryClient.invalidateQueries({ queryKey: ['users'] });
    },
  });

  if (isLoading) return <div className="p-4">加载用户列表中...</div>;
  if (isError) return <div className="p-4 text-red-500">获取用户失败，请重试</div>;

  return (
    <div className="p-4">
      <h2 className="text-xl font-bold mb-4">用户列表</h2>
      <ul className="space-y-2">
        {users?.map((user) => (
          <li key={user.id} className="flex items-center justify-between p-2 border rounded">
            <div>
              <span className="font-medium">{user.name}</span>
              <span className="ml-2 text-gray-500">{user.email}</span>
            </div>
            <button
              onClick={() => deleteUserMutate(user.id)}
              disabled={isDeleting}
              className="px-2 py-1 bg-red-500 text-white rounded hover:bg-red-600 disabled:opacity-50"
            >
              {isDeleting ? '删除中...' : '删除'}
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
}