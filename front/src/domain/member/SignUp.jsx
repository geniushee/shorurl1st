import axios from 'axios';
import React from 'react';
import { Form, redirect } from 'react-router-dom';

export async function action({ request }) {
    const formData = await request.formData();

    const usernameRegex = /[^\s]{4,10}/;
    const username = formData.get("username")
    if (!usernameRegex.test(username)) {
        alert("아이디를 바르게 입력해주세요.")
        return null;
    }

    const passwordRegex = /[^\s]{4,}/;
    const password = formData.get("password")
    if (!passwordRegex.test(password)) {
        alert("비밀번호를 바르게 입력해주세요.")
        return null;
    }

    //^[^\s@]+@[^\s@]+\.[^\s@]+$
    // ^: 문자열의 시작을 나타냅니다.
    // [^\s@]+: 공백이나 @ 문자를 제외한 하나 이상의 문자.
    // @: @ 문자.
    // [^\s@]+: 공백이나 @ 문자를 제외한 하나 이상의 문자.
    // \.: . 문자.
    // [^\s@]+: 공백이나 @ 문자를 제외한 하나 이상의 문자.
    // $: 문자열의 끝을 나타냅니다.
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const email = formData.get("email");

    if (!emailRegex.test(email)) {
        alert("이메일을 바르게 입력해주세요.")
        return null;
    }

    const name = formData.get("name");

    const data = await axios({
        method: "POST",
        url: "http://localhost:8080/api/v1/members/register",
        data:
        {
            username: username,
            password: password,
            email: email,
            name: name,
        },
    }).then(
        reponse => reponse.data
    ).catch(() => {
        console.log("회원가입 에러")
        return null;
    })
    
    if(data === null){
        return null;
    }


    return redirect("/signin");
}

function SignUp(props) {

    const goback = () => {
        window.history.back();
    }

    return (
        <div>
            <Form method='POST'>
                <p>아이디</p>
                <input type="text" name='username' placeholder='아이디를 입력하세요. 영문,숫자 4~10자' />
                <p>비밀번호</p>
                <input type="password" name='password' placeholder='비밀번호를 입력하세요. 4자 이상' />
                <p>email</p>
                <input type="text" name='email' placeholder='email을 입력하세요.' />
                <p>닉네임</p>
                <input type="text" name='name' placeholder='닉네임을 입력하세요.' />
                <div>
                    <button type='submit'>Sign Up</button>
                    <button onClick={goback}>이전으로</button>
                </div>
            </Form>
        </div>
    );
}

export default SignUp;