import React from 'react';
import { Form, Link, redirect, useLoaderData } from 'react-router-dom';
import { useAuth } from '../../../global/AuthProvider';
import { sUrlChatCreateRoom } from '../../../SUrlChat';

export async function action({request}) {
    const formData = await request.formData();
    const roomName = formData.get("roomName")
    const sUrlId = formData.get("sUrlId")

    try{
        const response = await sUrlChatCreateRoom({roomName, sUrlId})
    return redirect("/")
    }catch{
        return;
    }
    
}

export async function loader({params}){
    console.log(params)
    const sUrlId = params.id;
    
    return sUrlId;
}

function CreateSUrlChatRoom(props) {
    const { isLogin, user } = useAuth();
    const sUrlId = useLoaderData();
    console.log(sUrlId);


    return (
        <>
            {isLogin ?
                (
                    <Form method='POST'>
                        <label>
                            <span>제목 : </span><input type="text" name='roomName' placeholder='채팅방 이름을 입력해주세요' />
                        </label>
                        <p>
                            만든이 : <span>{user.username}</span>
                        </p>
                        <input type="hidden" name='sUrlId' value={sUrlId}  />
                        <div>
                            <button type='submit'>만들기</button>
                        </div>
                    </Form>
                ) : (<Link to="/signin">로그인 하러 가기</Link>)}

        </>
    );
}

export default CreateSUrlChatRoom;