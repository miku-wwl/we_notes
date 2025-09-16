import { Link, Route, Routes, BrowserRouter } from 'react-router-dom';
import './App.css'
import UserList from './component/UserList'
import UserDetail from './component/UserDetail';
import AddUserForm from './component/AddUserForm';
import UserPaginationList from './component/UserPaginationList';

function App() {
  return (
    <BrowserRouter>
      <div className="container mx-auto p-4">
        <nav className="mb-6 border-b pb-2">
          <Link to="/" className="mr-4 text-blue-500 hover:underline">
            用户列表
          </Link>
          <Link to="/add" className="mr-4 text-blue-500 hover:underline">
            添加用户
          </Link>
          <Link to="/pagination" className="text-blue-500 hover:underline">
            分页列表
          </Link>
        </nav>

        <Routes>
          <Route path="/" element={<UserList />} />
          <Route path="/users/:id" element={<UserDetail />} />
          <Route path="/add" element={<AddUserForm />} />
          <Route path="/pagination" element={<UserPaginationList />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App
