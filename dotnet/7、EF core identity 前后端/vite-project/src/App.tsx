import { BrowserRouter as Router, Routes, Route } from 'react-router';

// 引入 Bootstrap 样式
import 'bootstrap/dist/css/bootstrap.min.css';
import LoginForm from './pages/LoginForm';
import ActivityDashboard from './pages/ActivityDashboard';
import RequireAuth from './routes/RequireAuth';
import ProfilePage from './pages/ProfilePage';

const App: React.FC = () => {
  return (
    <Router>
      <Routes>
        {/* 公开路由 */}
        {/* TODO: 
        <Route path="/register" element={<RegisterForm />} /> */}
        <Route path="/login" element={<LoginForm />} />
        <Route path="/activities" element={<ActivityDashboard />} />

        <Route path="/" element={<RequireAuth />}>
          <Route path="profiles" element={<ProfilePage />} />
       </Route>
      </Routes>
    </Router>


    // <Router>
    //   <Navbar />
    //   <Routes>
    //     <Route path="/" element={<Home />} />
    //     <Route path="/about" element={<About />} />
    //     <Route path="/contact" element={<Contact />} />
    //     <Route path="/dashboard" element={<Dashboard />}>
    //       <Route path="profile" element={<Profile />} />
    //       <Route path="settings" element={<Settings />} />
    //       <Route path="stats" element={<Stats />} />
    //     </Route>
    // </Routes>
    // </Router >


  );
};

export default App;