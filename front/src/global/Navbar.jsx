import React from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from './AuthProvider';

function Navbar(props) {
    const {user, isLogin, logout} = useAuth();
    return (
        <div>
            <span>{user.name}님 환영합니다.</span>
            <Link to={"/"}>Main</Link>
            <Link to={"/create"} >New SUrl</Link>
            {isLogin ?
                (<span><Link to={"/mypage"}>My Page</Link>
                <button onClick={logout}>logout</button></span>)
                : (
                    <span><Link to={"/signin"}>Sign In</Link> <Link to={"/signup"}>Sign Up</Link></span>
                )
            }
        </div>
    );
}

export default Navbar;