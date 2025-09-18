import { useState, useEffect } from 'react';
import { getAdminData } from '../api/identityApi';

const AdminPage = () => {
  const [adminData, setAdminData] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchAdminData = async () => {
      try {
        const res = await getAdminData();
        setAdminData(res.data); // 后端返回的管理员专属数据（如 "只有管理员能看到这条消息"）
      } catch (err) {
        setError('获取管理员数据失败！');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchAdminData();
  }, []);

  if (loading) return <div className="container mt-5">加载管理员数据中...</div>;
  if (error) return <div className="container mt-5 alert alert-danger">{error}</div>;

  return (
    <div className="container mt-5">
      <h2 className="mb-4">管理员专属页面</h2>
      <div className="card bg-primary text-white">
        <div className="card-body">
          <h5 className="card-title">管理员权限验证通过</h5>
          <p className="card-text">{adminData}</p>
        </div>
      </div>
    </div>
  );
};

export default AdminPage;