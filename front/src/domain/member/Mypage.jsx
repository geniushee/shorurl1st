import React from 'react';
import { Link } from 'react-router-dom';

function Mypage(props) {

    return (
        <>
            <ul>
                <li><Link to="/mypage/profile">내 정보</Link></li>
                <li><Link to="/mypage/mylist">My SUrl List</Link></li>
                <li><Link to="/mypage/withdrawal">회원 탈퇴</Link></li>
            </ul>
        </>
    );
}

export default Mypage;