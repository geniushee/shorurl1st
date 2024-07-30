import React, { useEffect } from 'react';
import { Outlet, useLocation, useNavigate } from 'react-router-dom';
import { useAuth } from '../../global/AuthProvider';
import Mypage from './Mypage';



function MypageLayout({ props }) {
    const { isLogin } = useAuth();
    const navigate = useNavigate();
    const location = useLocation();



    useEffect(() => {
        if (isLogin) {
            if (location.pathname.endsWith("/mypage")) {
                navigate("/mypage/profile");
            }
        } else {
            navigate("/");
        }
    }, [])

    return (
        <section className='container'>
            <div className='row my-3'>
                <nav className='col border-end'>
                    <Mypage location={location} />
                </nav>

                <div className='col-10'>
                    <Outlet />
                </div>
            </div>
        </section>
    );
}

export default MypageLayout;