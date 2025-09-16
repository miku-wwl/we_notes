import { useQuery } from '@tanstack/react-query';
import { fetchUserById, type User } from '../api/user';
import { useParams } from 'react-router-dom';

export default function UserDetail() {
  const { id } = useParams<{ id: string }>();
  const userId = Number(id);

  // 根据ID查询用户详情
  const { data: user, isLoading, isError } = useQuery<User, Error>({
    queryKey: ['user', userId], // 带参数的查询键
    queryFn: () => fetchUserById(userId),
    enabled: !!userId, // 只有userId有效时才发起请求
  });

  if (isLoading) return <div className="p-4">加载用户详情中...</div>;
  if (isError || !user) return <div className="p-4 text-red-500">用户不存在或加载失败</div>;

  return (
    <div className="p-4 max-w-md mx-auto">
      <h2 className="text-xl font-bold mb-4">用户详情</h2>
      <div className="space-y-2 p-4 border rounded">
        <p><span className="font-medium">ID：</span>{user.id}</p>
        <p><span className="font-medium">姓名：</span>{user.name}</p>
        <p><span className="font-medium">邮箱：</span>{user.email}</p>
      </div>
    </div>
  );
}