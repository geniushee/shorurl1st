import React, { useEffect } from 'react';
import axios from 'axios';
import { Form, Outlet, useActionData, useNavigate } from 'react-router-dom';
import Errorpage from '../../errors/Errorpage';
import { useAuth } from '../../global/AuthProvider';

export async function action({ request }) {
    const formData = await request.formData()
    const username = formData.get("username")
    const password = formData.get("password")
    console.log("아이디 : ", username, "\n비밀번호 : ", password)
    const data = await axios({
        method: "POST",
        url: "http://localhost:8080/api/v1/members/login",
        data: {
            username: username,
            password: password,
        },
        withCredentials: true,
    }).then((response) => {
        console.log(response.data)
        return true;
    }).catch(() => {
        console.log("잘못된 정보입니다.")
        return false;
    })

    return data;
}

function ManageLogin(props) {
    const { isLogin, setLogin, isAdmin, setAdmin, getUser } = useAuth()
    const data = useActionData();
    const navigate = useNavigate();


    useEffect(() => {
        console.log(data)
        if (data) {
            setLogin(data);
        }
    }, [data])


    return (
        <>{isLogin ?
            (isAdmin ?
                (<>
                    <p>관리자입니다.</p>
                    <a href="/manage/main">관리자 페이지로</a>
                </>
                ) : (
                    <section>
                        <p>관리자가 아닙니다.</p>
                        <a href="/">홈으로</a>
                    </section>
                )
            ) : (
                <section>
                    <p>로그인이 필요합니다.</p>
                    <Form method="POST">
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
                            <a href="/">홈으로</a>
                        </div>
                    </Form>
                </section>
            )}

        </>
    );
}

export default ManageLogin;