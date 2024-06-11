import React from 'react';
import { Form, redirect, useNavigate } from 'react-router-dom'
import { useAuth } from '../../global/AuthProvider';
import axios from 'axios';

function Withdrawal(props) {
    const { user, setLogin } = useAuth();
    const navigate = useNavigate();

    const goback = () => {
        window.history.back();
    }

    const onSubmitHandler = async (event) => {
        event.preventDefault();
        const username = user.username
        const password = event.target.password.value;

        console.log(username, password);

        try {
            const data = await axios({
                method: "DELETE",
                url: "http://localhost:8080/api/v1/members/withdrawal",
                data: {
                    username: username,
                    password: password,
                },
                withCredentials: true,
            }).then(response => response.data)
            setLogin(false)
            navigate("/")
        } catch {
            console.log("에러 발생")
        }
    }

    return (
        <div>
            <form method='POST' onSubmit={onSubmitHandler}>
                <p>
                    아이디 : <span>{user.username}</span>
                </p>
                <p>
                    비밀번호 :
                </p>
                <input type="password" name='password' placeholder='비밀번호를 입력해주세요.' />
                <div>
                    <button type='submit'>탈퇴하기</button>
                    <button onClick={goback}>이전으로</button>
                </div>

            </form>
        </div>
    );
}

export default Withdrawal;