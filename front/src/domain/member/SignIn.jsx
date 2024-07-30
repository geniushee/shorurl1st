import axios from 'axios';
import React, { useContext, useRef } from 'react';
import { Form, Link, redirect, useNavigate } from 'react-router-dom';
import { useAuth } from '../../global/AuthProvider';
import toastr from 'toastr';

export async function action({ params, request }) {

    return redirect("/")
}

function SignIn(props) {
    const {isLogin, setLogin } = useAuth();
    const navigate = useNavigate();

    const usernameRef = useRef();
    const passwordRef = useRef();

    if(isLogin){
        window.history.back();
    }

    const submitHandler = async (e) => {
        e.preventDefault();
        const username = usernameRef.current.value;
        const password = passwordRef.current.value;
        let redirectUrl = ""

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
            toastr["success"]("로그인에 성공했습니다.")
            redirectUrl = "/";
            return response.data;
        }).catch(() => {
            toastr["warning"]("아이디와 비밀번호가 다릅니다.")
            redirectUrl = "/signin"
            return;
        })
        console.log(data)
        navigate(redirectUrl)
    }

    const signUpHandler = () => {
        navigate("/signup")
    }

    const redirectUrl = encodeURI("http://localhost:5173")

    return (
        <div className='containter w-75'>
            <form className='m-5' method='POST' onSubmit={submitHandler}>
                <div className='mb-3'>
                    <label for="formUsername" className='form-label'>아이디</label>
                    <input className='form-control' type="text" id='formUsername' ref={usernameRef} name='username' placeholder='아이디를 입력해주세요.' />
                </div>
                <div className='mb-3'>
                    <label for="formPassword" className='form-label'>비밀번호</label>
                    <input className='form-control' type="password" ref={passwordRef} id='formPassword' name='password' placeholder='비밀번호를 입력해주세요.' />
                </div>
                <div className='d-flex justify-content-around'>
                    <button className='btn btn-primary' type='submit'>Sign In</button>
                    <button className='btn btn-primary' onClick={signUpHandler}>Sign Up</button>
                </div>
            </form>
            <section className='d-flex justify-content-center' >
                {/* OAuth2로 로그인 구현 시 axois나 fetch로 사용할 경우 CORS 오류를 받는다. 이유는 Auth서버에서 CORS를 지원하지 않기 때문에 엑세스 오리진 헤더를 포함하지 않는다. */}
                <a href={`http://localhost:8080/api/v1/members/socialLogin/kakao?redirectUrlAfterSocialLogin=${redirectUrl}`}><img src="src/assets/kakao_login_medium_narrow.png" style={{radius : 12+'px', padding : 0 + 'px', border: 'none'}} /></a>
            </section>
        </div>
    );
}

export default SignIn;