import React, { useEffect } from 'react';
import { Form, redirect, useLoaderData, useNavigate } from "react-router-dom"
import { createNewSUrl } from '../../SUrls';
import { useAuth } from '../../global/AuthProvider';

export async function loader({ request }) {   
    console.log(request)
    const url = new URL(request.url);
    const res = url.searchParams.get("res")
    return res
}

export async function action({ params, request }) {
    const formData = await request.formData()
    const origin = formData.get("origin")
    console.log(origin)
    const SUrl = await createNewSUrl({ origin })

    return redirect(`/create?res=${SUrl}`)
}

function CreateSUrl(props) {
    const {user, isLogin} = useAuth();
    const navigate = useNavigate();
    
    useEffect(() =>{
        if(!isLogin){
            return navigate("/signin");
        }
    },[])
    
    const loadData = useLoaderData();

    return (
        <>
            {!loadData ? (
                <Form method='POST'>
                    <p><span>Url 입력 : </span>
                        <input type="text" name='origin' />
                    </p>
                    <button type='submit'>SUrl 만들기</button>
                </Form>
            ) : (<p>
                새로운 Url : {loadData}
            </p>)
            }

        </>
    );
}

export default CreateSUrl;