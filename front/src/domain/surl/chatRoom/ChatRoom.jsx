import React from 'react';
import { getChatMessages, getChatRoomDetail } from '../../../SUrlChat';
import { useLoaderData } from 'react-router';

export async function loader({ params }) {
    const roomId = params.roomId;
    const chatRoomResponse = await getChatRoomDetail({ roomId })
    console.log(chatRoomResponse)
    const messagesResponse = await getChatMessages({ roomId })
    console.log(messagesResponse)
    const chatRoom = chatRoomResponse.data
    const messages = messagesResponse.data
    return { chatRoom, messages };
}

function ChatRoom(props) {
    const { chatRoom, messages } = useLoaderData();
    console.log(chatRoom + " /\n " + messages.map(message => message.body))

    return (
        <>
            <h1>채팅방 이름 : {chatRoom.roomName}</h1>
            <p>만든이 : {chatRoom.creater.username}</p>
            <div className='container'>
                {messages ? (
                    <ol>
                        {messages.map((item, idx) => (
                            <li key={item.id}>
                                <span>작성자 : {item.writer.username}   </span> <span>{item.body}</span>
                            </li>
                        ))}
                        
                    </ol>
                ) : (<div> 채팅이 없습니다. </div>)}


            </div>
        </>
    );
}

export default ChatRoom;