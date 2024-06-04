import React from 'react';
import { Link } from 'react-router-dom';

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
            <p>대시보드</p>
            <p>총 SUrl</p>
            <p>총 가입자</p>
        </>
    );
}

export default ManageMain;