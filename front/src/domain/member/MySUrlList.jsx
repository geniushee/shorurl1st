import React, { useEffect, useState } from 'react';
import { deleteSUrl, getMySUrls } from '../../SUrls';
import { useLoaderData, Link, Form } from 'react-router-dom';

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
        if (listData) {
            setList(listData);
        } else {
            console.log("빈 배열입니다.")
        }
    }, [listData])

    const clickDelete = async (item) => {
        console.log(item);
        const message = await deleteSUrl({ id: item.id })
        console.log(message);
        setList(prevList => prevList.filter(sUrl => sUrl.id !== item.id));
    }

    return (
        <>
            {/* <p>
                <Form>
                    <input type="search" placeholder='검색어 입력' /><button type='sumit'>검색</button>
                </Form>
            </p> */}
            {list.map((item, index) => (
                <div className='card m-3' key={index}>
                    <div className='card-body'>
                        <p className='card-title row fs-3'>
                            <span className='col text-end'>Title :</span><span className='col-10'>{item.title ? item.title : "미정"}</span> 
                            </p>
                        <div className='card-body'>
                            <p className='row'>
                                <span className='col text-end'>ShortUrl :</span><span className='col-10'>{item.shortUrl}</span>
                            </p>
                            <p className='row'>
                                <span className='col text-end'>Origin :</span><span className='col-10'>{item.origin}</span> 
                                </p>
                        </div>
                        <div className='d-flex justify-content-end'>
                            <Link className='btn btn-primary mx-2' to={`/chatRoom/create/${item.id}`}>채팅방 만들기</Link>
                            <Link className='btn btn-primary mx-2' to={`/modify/${item.id}`}>설정하기</Link>
                            <button className='btn btn-secondary mx-2' onClick={() => clickDelete(item)}>삭제하기</button>
                        </div>
                    </div>
                </div>
            ))
            }
        </>
    );
}

export default MySUrlList;  