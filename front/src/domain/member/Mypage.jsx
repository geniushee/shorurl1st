import React from 'react';
import { Link } from 'react-router-dom';

function Mypage({location : location}) {

    const isActive = (path) => location.pathname.startsWith(path)

    return (
        <>

            <ul className='list-group w-100'>
                <li className={`list-group-item ${isActive("/mypage/profile") ? "active" : ""}`}><Link className='text-dark text-decoration-none' to="/mypage/profile">내 정보</Link></li>
                <li className={`list-group-item ${isActive("/mypage/mylist")  ? "active" : ""}`}><Link className='text-dark text-decoration-none' to="/mypage/mylist">My SUrl List</Link></li>
                <li className={`list-group-item ${isActive("/mypage/withdrawal")  ? "active" : ""}`}><Link className='text-dark text-decoration-none' to="/mypage/withdrawal">회원 탈퇴</Link></li>
            </ul>
        </>
    );
}

export default Mypage;