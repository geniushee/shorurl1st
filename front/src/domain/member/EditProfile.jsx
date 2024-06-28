import React, { useState, useEffect } from 'react';
import { useAuth } from '../../global/AuthProvider';
import toastr from 'toastr';
import axios from 'axios';

function EditProfile(props) {
    const { getUser } = useAuth()
    const [userInfo, setInfo] = useState({
        username: null,
        password: null,
        name: null,
        email: null,
    })

    useEffect(() => {
        async function loadData() {
            const data = await getUser()
            console.log(data)
            setInfo(data)
        }
        loadData()
    }, [])

    const goback = () => {
        window.history.back();
    }

    const onSubmitHandler = async (event) => {
        event.preventDefault();

        const passwordRegex = /[^\s]{4,}/;
        const password = event.target.password.value
        if (password) {
            if (!passwordRegex.test(password)) {
                toastr["warning"]("비밀번호를 바르게 입력해주세요.")
                return null;
            }
        }

        const passwordConfirm = event.target.passwordConfirm.value
        if (passwordConfirm) {
            if (!passwordRegex.test(passwordConfirm)) {
                toastr["warning"]("비밀번호를 바르게 입력해주세요.")
                return null;
            }
            if (password !== passwordConfirm) {
                toastr["warning"]("비밀번호 확인이 일치하지 않습니다.")
                return null;
            }
        }

        const nameRegex = /[\w\s]{2,}/;
        const name = event.target.name.value
        if (!nameRegex.test(name)) {
            toastr["warning"]("닉네임을 바르게 입력해주세요.")
            return null;
        }

        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        const email = event.target.email.value

        if (!emailRegex.test(email)) {
            toastr["warning"]("이메일을 바르게 입력해주세요.")
            return null;
        }

        //프로필 수정 메소드 구현 필요
        try {
            const data = await axios({
                method: "PUT",
                url: "http://localhost:8080/api/v1/members/editProfile",
                data: {
                    password: password,
                    passwordConfirm: passwordConfirm,
                    name: name,
                    email: email,
                },
                withCredentials: true,
            }).then(response => response.data)
            setInfo(preInfo => ({
                ...preInfo,
                password: null,
                name : data.name,
                email : data.email,
            }))
            toastr["success"]("성공적으로 변경했습니다.")
        } catch {
            console.log("에러")
        }
    }

    const onChangeHandler = (e) => {
        const { name, value } = e.target;

        setInfo(preModifyData => ({
            ...preModifyData,
            [name]: value
        }))
    }

    let nameValue = typeof userInfo.name === "string" ? userInfo.name : "";
    let emailValue = typeof userInfo.email === "string" ? userInfo.email : "";

    return (
        <>
            <form method='PUT' onSubmit={onSubmitHandler} >
                <p>아이디 : {userInfo.username}</p>
                <p>비밀번호 : <input type="password" name="password" placeholder='수정할 비밀번호를 입력해주세요' onChange={onChangeHandler} /></p>
                <p>비밀번호 확인 : <input type="password" name='passwordConfirm' placeholder='수정할 비밀번호를 다시 입력해주세요' onChange={onChangeHandler} /></p>
                <p>닉네임 : <input type="text" name='name' value={nameValue} onChange={onChangeHandler} placeholder='변경할 닉네임을 입력해주세요' /><span>닉네임은 2자 이상(영문,숫자,공백)</span></p>
                <p>email : <input type="text" name='email' value={emailValue} onChange={onChangeHandler} /></p>
                <div>
                    <button type='submit'>수정하기</button>
                    <button onClick={goback}>이전으로</button>
                </div>
            </ form>
        </>
    );
}

export default EditProfile;