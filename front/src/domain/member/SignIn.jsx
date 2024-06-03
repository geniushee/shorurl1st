import axios from 'axios';
import React, { useContext, useRef } from 'react';
import { Form, Link, redirect, useNavigate } from 'react-router-dom';
import { useAuth } from '../../global/AuthProvider';

export async function action({ params, request }) {

    return redirect("/")
}

function SignIn(props) {
    const { setLogin } = useAuth();
    const navigate = useNavigate();

    const usernameRef = useRef();
    const passwordRef = useRef();


    const submitHandler = async (e) => {
        e.preventDefault();
        const username = usernameRef.current.value;
        const password = passwordRef.current.value;

        console.log("username :", username, "\npassword :", password)

        const data = await axios({
            method: "POST",
            url: "http://localhost:8080/api/v1/members/login",
            data: {
                username: username,
                password: password,
            },
            withCredentials: true,
        }).then((response) => {
            console.log(response)
            setLogin(true);
            return response.data;
        }).catch(() => ("잘못된 정보입니다."))
        console.log(data)
        navigate("/")
    }

    const signUpHandler = () => {
        navigate("/signup")
    }

    const redirectUrl = encodeURI("http://localhost:5173")

    return (
        <div className='containter'>
            <form method='POST' onSubmit={submitHandler}>
                <div>
                    <label>아이디</label>
                    <input type="text" ref={usernameRef} name='username' placeholder='아이디를 입력해주세요.' />
                </div>
                <div>
                    <label>비밀번호</label>
                    <input type="password" ref={passwordRef} name='password' placeholder='비밀번호를 입력해주세요.' />
                </div>
                <div>
                    <button type='submit'>Sign In</button>
                    <button onClick={signUpHandler}>Sign Up</button>
                </div>
            </form>
            <section>
                <a href={`http://localhost:8080/api/v1/members/socialLogin/kakao?redirectUrlAfterSocialLogin=${redirectUrl}`}><img src="src/assets/kakao_login_medium_narrow.png" style={{radius : 12+'px', padding : 0 + 'px', border: 'none'}} /></a>
            </section>
        </div>
    );
}

export default SignIn;