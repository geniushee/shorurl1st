import axios from 'axios';
import React from 'react';
import { Form, redirect, useNavigate } from 'react-router-dom';

export async function action({ params, request }) {
    const formData = await request.formData();
    const username = formData.get("username")
    const password = formData.get("password")
    console.log("username :", username, "\npassword :", password)
    const data = await axios({
        method: "POST",
        url: "http://localhost:8080/api/v1/members/login",
        data: {
            username: username,
            password: password,
        },
        withCredentials : true,
    }).then(response => console.log(response))
    
    return redirect("/");
}

function SignIn(props) {

    const navigate = useNavigate();

    const submitHandler = (formData) => {


    }

    const signUpHandler = () => {
        navigate("/signup")
    }

    return (
        <div className='containter'>
            <Form method='POST' onSubmit={submitHandler}>
                <div>
                    <label>아이디</label>
                    <input type="text" name='username' placeholder='아이디를 입력해주세요.' />
                </div>
                <div>
                    <label>비밀번호</label>
                    <input type="password" name='password' placeholder='비밀번호를 입력해주세요.' />
                </div>
                <div>
                    <button type='submit'>Sign In</button>
                    <button onClick={signUpHandler}>Sign Up</button>
                </div>
            </Form>
        </div>
    );
}

export default SignIn;