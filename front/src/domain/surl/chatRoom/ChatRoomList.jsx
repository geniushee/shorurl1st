import React from 'react';
import { sUrlChatRoomList } from '../../../SUrlChat';
import { useLoaderData, Link } from 'react-router-dom';

export async function loader({params}){
    const response = await sUrlChatRoomList({page: 1});
    return response.data;
}

function ChatRoomList(props) {
    const loadedData = useLoaderData();
    console.log(loadedData)
    const list = loadedData.content
    console.log(list)
    return (
        <>
             <p>목록 보여주기</p>
            <ul>
                {list.map((item, index) => (
                    <li key={index}>
                        <p>RoomName : {item.roomName}</p>
                        <p>Creater : {item.creater.username}</p>
                        <div>
                            <Link to={`/chatRoom/${item.roomId}`}>채팅방 들어가기</Link>
                            <button onClick={() => clickDelete(item)}>삭제하기</button>
                        </div>
                        </li>
                ))
                }
            </ul>
        </>
    );
}

export default ChatRoomList;