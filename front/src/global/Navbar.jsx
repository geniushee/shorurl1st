import React from 'react';
import { Link } from 'react-router-dom';

function Navbar(props) {
    let isLongin = false;
    return (
        <div>
            <Link to={"/"}>Main</Link>
            <Link to={"/create"} >New SUrl</Link>
            {isLongin ?
                <Link to={"/mypage"}>My Page</Link>
                : (
                    <span><Link to={"/signin"}>Sign In</Link> <Link to={"/signup"}>Sign Up</Link></span>
                )
            }
        </div>
    );
}

export default Navbar;