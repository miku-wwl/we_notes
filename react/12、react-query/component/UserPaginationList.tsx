import { useInfiniteQuery } from '@tanstack/react-query';
import { fetchUsersByPage } from '../api/user';

export default function UserPaginationList() {
  const {
    data,
    isLoading,
    isError,
    fetchNextPage,
    hasNextPage,
    isFetchingNextPage,
  } = useInfiniteQuery({
    queryKey: ['users', 'pagination'], // 分页查询的唯一键
    queryFn: fetchUsersByPage,
    getNextPageParam: (lastPage) => lastPage.nextPage, // 计算下一页参数
    initialPageParam: 1, // 设置初始页码为1
  });

  if (isLoading) return <div className="p-4">加载分页列表中...</div>;
  if (isError) return <div className="p-4 text-red-500">加载失败，请重试</div>;

  // 合并所有页的用户数据
  const allUsers = data?.pages.flatMap((page) => page.users) || [];

  return (
    <div className="p-4">
      <h2 className="text-xl font-bold mb-4">分页用户列表</h2>
      <ul className="space-y-2 mb-4">
        {allUsers.map((user) => (
          <li key={user.id} className="p-2 border rounded">
            {user.id} - {user.name}
          </li>
        ))}
      </ul>
      <button
        onClick={() => fetchNextPage()}
        disabled={!hasNextPage || isFetchingNextPage}
        className="p-2 bg-gray-100 rounded hover:bg-gray-200 disabled:opacity-50"
      >
        {isFetchingNextPage
          ? '加载中...'
          : hasNextPage
          ? '加载更多'
          : '没有更多数据'}
      </button>
    </div>
  );
}