import React from 'react';
import { Link, Outlet } from 'react-router-dom';

function ManageMain(props) {


    return (
        <>
            <nav>
                <Link to="/">사이트 메인</Link>
                <Link to="/manage">관리자 홈</Link>
            </nav>
            <section>
                관리자 페이지 입니다.
            </section>
            <Outlet></Outlet>
        </>
    );
}

export default ManageMain;