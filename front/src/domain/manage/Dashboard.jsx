import React from 'react';
import axios from 'axios';
import { useLoaderData } from 'react-router';

export async function loader() {
    const totalUserCount = await axios({
        method: "GET",
        url: "http://localhost:8080/api/v1/manage/users",
        withCredentials: true,
    }).then(
        response => response.data
    ).catch(
        () => -1
    )

    const totalSUrlCount = await axios({
        method: "GET",
        url: "http://localhost:8080/api/v1/manage/surls",
        withCredentials: true,
    }).then(
        response => response.data
    ).catch(
        () => -1
    )

    const recentResister = await axios({
        method: "GET",
        url: "http://localhost:8080/api/v1/manage/recentResister",
        withCredentials: true,
    }).then(
        response => {
            console.log(response)
            return response.data
        }
    ).catch(
        () => []
    )

    const recentSUrls = await axios({
        method: "GET",
        url: "http://localhost:8080/api/v1/manage/recentSurls",
        withCredentials: true,
    }).then(
        response => {
            console.log(response)
            return response.data
        }
    ).catch(
        () => []
    )


    return { totalUserCount, totalSUrlCount, recentResister, recentSUrls };
}

function Dashboard(props) {
    const { totalUserCount, totalSUrlCount, recentResister, recentSUrls } = useLoaderData();

    console.log(recentResister);
    return (
        <div>
            <p>대시보드</p>
            <p>총 SUrl</p>
            <p>{totalSUrlCount} 개</p>
            <p>총 가입자</p>
            <p>{totalUserCount} 명</p>
            <p>최근 가입자</p>
            <ol>
                {recentResister.map((item, index) =>
                (<li key={index} >
                    <p>아이디 : {item.username}</p>
                    <p>닉네임 : {item.name}</p>
                    <p>생성일자 : {item.createDate}</p>
                </li>)
                )}
            </ol>
            <p>최근 생성된 SUrls</p>
            <ol>
                {recentSUrls.map((item, index)=>(
                    <li key={index}>
                        <p>SUrl : {item.shortUrl}</p>
                        <p>기존 주소 : {item.origin}</p>
                        <p>제목 : {item.title}</p>
                        <p>생성일자 : {item.createDate}</p>
                    </li>
                ))}
            </ol>

        </div>
    );
}

export default Dashboard;