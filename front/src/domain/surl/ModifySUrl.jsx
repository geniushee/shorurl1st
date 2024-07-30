import React, { useEffect, useRef, useState } from 'react';
import { Form, useLoaderData, useNavigate, redirect, useActionData } from 'react-router-dom';
import { getSUrlInfo, modifySUrl } from '../../SUrls';

export async function loader({ params }) {
    const id = params.id;
    console.log(id)
    const data = getSUrlInfo({ id });
    return data;
}

export async function action({params, request}){
    const formData = await request.formData();
    const id = params.id;
    const title = formData.get("title").replace(/ /g, "_")
    const content = formData.get("content")
    const data = modifySUrl({ id: id, title:title, content:content });
    console.log("예스!")
    return data;
}

function ModifySUrl(props) {
    const data = useLoaderData();
    const actionData = useActionData();
    const [sUrl, setSUrl] = useState({
        id: "",
        origin: "",
        title: "",
        content: "",
        owner: "",
    });
    const navigate = useNavigate()

    const prePage = (event) =>{
        event.preventDefault();
        navigate("/mypage/mylist")
    }
    console.log(data);

    // 페이지 로드시 랜더링을 위한 훅
    useEffect(() => {
        if (data) {
            setSUrl({
                id: data.id,
                origin: data.origin,
                title: data.title,
                content: typeof data.content === "string" ? data.content : "",
                owner: data.member.username,
            })
        }
    }, [data])

    // 액션 이후 페이지 랜더링을 위한 훅
    useEffect(() => {
        if (actionData) {
            setSUrl({
                id: data.id,
                origin: data.origin,
                title: data.title,
                content: typeof data.content === "string" ? data.content : "",
                owner: data.member.username,
            })
        }
    }, [actionData])
    
    // form에 값 입력시 상태 갱신
    const onChangeHandler = (e) => {
        const {name, value} = e.target;

        const regex = /[^\w\sㄱ-힣]/g;

        if(name === "title" && regex.test(value)){
            alert("제목에는 특수문자를 입력할 수 없습니다.");
        }else{
            setSUrl(preSUrl => ({
                ...preSUrl,
                [name] : name === "title" ? value.replace(/ /g, "_") : value,
            }))
        }

    }
    let titleValue = typeof sUrl.title === "string" ? sUrl.title : "";
    let formTitle = titleValue.replace(/_/g," ");

    // action 이후 Form 내부의 값이 재랜더링 되지 않음.
    return (
        <div className='flex-column'>
        <h2 className='my-5 fs-2'>SUrl 수정하기</h2>
            <Form className='m-auto p-3 border border-primary rounded d-flex flex-column' method='PUT'>
                <label className='mb-3 form-label'>sUrl : http://localhost:5173/<span>{data.title ? data.title : data.id}</span></label>
                <div className='mb-3 form-label'>owner : {sUrl.owner}</div>
                <div className='mb-3'><label className='form-label'>origin : {sUrl.origin}</label></div>
                <div className='mb-3'><label className='form-label'>title : </label>
                <input className='form-control' type="text" name='title' value={formTitle} placeholder='이름을 작성해주세요' onChange={onChangeHandler} /><span>가능한 입력 : 영어대소문자, 공백, 밑줄, 한글</span>
                </div>
                <div className='mb-3'><label className='form-label'>content : </label>
                <textarea className='form-control' style={{width : 500}}  name='content' value={sUrl.content} placeholder='설명을 작성해주세요' onChange={onChangeHandler} />
                </div>
                <section className='d-flex justify-content-around'>
                    <button className='btn btn-primary' type='submit'>변경하기</button>  <button className='btn btn-secondary' onClick={prePage}>이전으로</button>
                </section>
            </Form>
        </div>
    );
}

export default ModifySUrl;