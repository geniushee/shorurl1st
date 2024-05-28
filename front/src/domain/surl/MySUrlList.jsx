import React, { useEffect, useState } from 'react';
import { deleteSUrl, getMySUrls } from '../../SUrls';
import { useLoaderData, Link } from 'react-router-dom';

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

    const clickDelete = async (item) => {
        console.log(item);
        const message = await deleteSUrl({id : item.id})
        console.log(message);
        setList(prevList => prevList.filter(sUrl => sUrl.id !== item.id));
    }

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
                        <div>
                            <Link to={`/modify/${item.id}`}>설정하기</Link>
                            <button onClick={() => clickDelete(item)}>삭제하기</button>
                        </div>
                        </li>
                ))
                }
            </ul>
        </>
    );
}

export default MySUrlList;  