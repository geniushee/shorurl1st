import React, { useEffect, useRef, useState } from 'react';
import { getChatMessages, getChatRoomDetail } from '../../../SUrlChat';
import { Form, useLoaderData } from 'react-router-dom';
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
    const { chatRoom, messages: initMessages } = useLoaderData();
    const [messages, setMessages] = useState(initMessages);
    const chatRoomId = chatRoom.roomId;

    const stompClientRef = useRef(null);
    const subscriptionRef = useRef(null);

    // 컴포넌트 마운트시 웹소켓 활성화 및 구독
    useEffect(() => {
        const socket = new SockJS('http://localhost:8080/ws');
        const stompClient = Stomp.over(socket);

        stompClient.connect({}, function (frame) {
            console.log("Connected: " + frame)

            if (subscriptionRef.current) {
                subscriptionRef.current.unsubscribe();
            }

            subscriptionRef.current = stompClient.subscribe(`/topic/chatRoom${chatRoomId}MessageCreated`, function (data) {
                const jsonData = JSON.parse(data.body);
                setMessages(preMessages =>
                    [...preMessages, jsonData]
                )
            })
        })

        stompClientRef.current = stompClient;

        // 컴포넌트 언마운트 시 WebSocket 연결 해제
        return () => {
            if (subscriptionRef.current) {
                subscriptionRef.current.unsubscribe();
            }
            
            if (stompClientRef.current.connected) {
                console.log("스톰프 연결 해체")
                stompClientRef.current.disconnect();
            }
        };
    }, [chatRoomId])




    console.log(chatRoom + " /\n " + messages.map(message => message.body))


    const submitMessage = (event) => {
        event.preventDefault();
        console.log(event)
        const form = event.target;
        const body = form.body.value.trim();

        if(body && stompClientRef.current.connected){
            stompClientRef.current.send(`/app/chat/${chatRoomId}/createChat`,
                {},
                JSON.stringify({ body: body })
            )
            form.reset();
        }
    }

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
            <div>
                <Form method='POST' onSubmit={submitMessage}>
                    <p><span>메세지 : </span><input type="text" name='body' placeholder='메세지를 입력해주세요' /></p>
                    <p><button type='submit'>보내기</button></p>
                </Form>
            </div>
        </>
    );
}

export default ChatRoom;