import { useState, useEffect } from 'react';
import { getCurrentProfile } from '../api/identityApi';
import type { UserProfile, ApiError } from '../types';
import { AxiosError } from 'axios';

const Profile: React.FC = () => {
  // 状态类型：UserProfile | null（初始 null）
  const [profile, setProfile] = useState<UserProfile | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string>('');

  // 获取个人资料
  useEffect(() => {
    const fetchProfile = async (): Promise<void> => {
      try {
        const res = await getCurrentProfile();
        setProfile(res.data); // 提取用户资料

      } catch (err) {
        const error = err as AxiosError<ApiError>;
        setError(
          error.response?.data?.message || '获取个人资料失败，请重试！'
        );
      } finally {
        setLoading(false);
      }
    };

    fetchProfile();
  }, []);

  if (loading) return <div className="container mt-5">加载个人资料中...</div>;
  if (error) return <div className="container mt-5 alert alert-danger">{error}</div>;
  if (!profile) return <div className="container mt-5 alert alert-warning">未获取到用户信息！</div>;

  return (
    <div className="container mt-5">
      <h2 className="mb-4">个人资料</h2>
      <div className="card">
        <div className="card-body">
          <h5 className="card-title">用户名：{profile.userName}</h5>
          <h6 className="card-subtitle mb-2 text-muted">邮箱：{profile.email}</h6>
          <p className="card-text">昵称：{profile.nickName || '未设置'}</p>
          <p className="card-text">
            角色：{profile.Roles && profile.Roles.length > 0 ? profile.Roles.join(', ') : '无'}
          </p>
          <p className="card-text">
            邮箱状态：{profile.emailConfirmed ? '已验证' : '未验证'}
          </p>
        </div>
      </div>
    </div>
  );
};

export default Profile;