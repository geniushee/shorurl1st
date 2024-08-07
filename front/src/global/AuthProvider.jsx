import React, { createContext, useContext, useState, useEffect, useMemo } from 'react';
import axios from 'axios';
import { redirect, useNavigate } from 'react-router-dom';

const AuthContext = createContext();

export const useAuth = () => useContext(AuthContext);

function AuthProvider({ children }) {
    const [isLogin, setLogin] = useState(false);
    const [user, setUser] = useState({
        id: null,
        username: null,
        name: null,
    });
    const [isAdmin, setAdmin] = useState(false);

    async function getUser() {
        const data = await axios({
            method: "GET",
            url: "http://localhost:8080/api/v1/members/info",
            withCredentials: true,
        }).then((response) => {
            setLogin(true);
            if (response.data.authorities.some(authObj => authObj.authority === "ROLE_ADMIN")) {
                setAdmin(true);
            }
            setUser(
                {
                    id: response.data.id,
                    username: response.data.username,
                    name: response.data.name,
                }
            )
            return response.data
        })
            .catch(() => {
                console.log("토큰없음")
                return {
                    id: null,
                    username: null,
                    name: null,
                }
            })
        return data;
    }

    async function logout() {
        try {
            const data = await axios({
                method: "POST",
                url: "http://localhost:8080/api/v1/members/logout",
                withCredentials: true,
            }).then((response) => {
                setLogin(false)
                setAdmin(false)
                setUser({
                    id: null,
                    username: null,
                    name: null
                })
                return response.data
            })
            console.log(data)
            window.location.href = "/";
        } catch (error) {
            console.log("에러 발생")
        }
    }

    // accessToken이 있을 경우 바로 로그인 하도록 초기화
    useEffect(() => {
        getUser();
    }, [])

    // 로그인 시 user정보 가져오기
    useEffect(() => {
        if (!isLogin) {
            setUser({
                id: null,
                username: null,
                name: null
            })
            return;
        }
        getUser();
    }, [isLogin])



    // const relUser = useMemo(() => ({user, setUser}), [user, setUser]);

    return (
        <AuthContext.Provider value={{ user, getUser, isLogin, setLogin, logout, isAdmin, setAdmin }}>
            {children}
        </AuthContext.Provider>
    );
}

export default AuthProvider;