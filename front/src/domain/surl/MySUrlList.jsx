import React, { useEffect, useState } from 'react';
import { getMySUrls } from '../../SUrls';
import { useLoaderData } from 'react-router';

export async function loader({ params }) {
    const sUrls = getMySUrls();
    console.log(sUrls);
    return sUrls;
}

function MySUrlList(props) {
    const listData = useLoaderData();
    console.log(listData);
    const [list, setList] = useState([]);

    useEffect(() => {
        if(listData){
        setList(listData);
        }else{
            console.log("빈 배열입니다.")
        }
    }, [listData])

    return (
        <>
            <p>목록 보여주기</p>
            <ul>
                {list.map((item, index) => (
                    <li key={index}>
                        <p>index : {index + 1}</p>
                        <p>origin : {item.origin}</p>
                        <p>shortUrl : {item.shortUrl}</p>
                        <p>title : {item.title}</p>
                        </li>
                ))
                }
            </ul>
        </>
    );
}

export default MySUrlList;  