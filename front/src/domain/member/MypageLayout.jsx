import React, { useEffect } from 'react';
import { Outlet, useNavigate } from 'react-router-dom';
import { useAuth } from '../../global/AuthProvider';

function MypageLayout(props) {
    const {isLogin} = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        if(isLogin){

        }else{
            navigate("/");
        }
    },[])

    return (
        <div>
                <Outlet/>
        </div>
    );
}

export default MypageLayout;