import { createBrowserRouter, Navigate } from "react-router";
import App from "../layout/App";
import HomePage from "../../features/home/HomePage";
import NotFound from "../../features/errors/NotFound";
import LoginForm from "../../features/account/LoginForm";
import RequireAuth from "./RequireAuth";
import RegisterForm from "../../features/account/RegisterForm";
import ProfilePage from "../../features/profiles/ProfilePage";

export const router = createBrowserRouter([
    {
        path: '/',
        element: <App />,
        children: [
            {element: <RequireAuth />, children: [
                { path: 'profiles/:id', element: <ProfilePage /> },
                { path: 'activities', element: <div>Activities</div> },
            ]},
            { path: '', element: <HomePage /> },
            { path: 'not-found', element: <NotFound /> },
            { path: 'login', element: <LoginForm /> },
            { path: 'register', element: <RegisterForm /> },
            { path: '*', element: <Navigate replace to='/not-found' /> },
        ]
    }
])