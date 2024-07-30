import React, { useEffect, useState } from 'react';
import { Form, redirect, useLoaderData, useNavigate } from "react-router-dom"
import { createNewSUrl } from '../../SUrls';
import { useAuth } from '../../global/AuthProvider';
import { Button, Modal, ModalBody, ModalDialog, ModalFooter, ModalHeader, ModalTitle } from 'react-bootstrap';

export async function loader({ request }) {
    console.log(request)
    const url = new URL(request.url);
    const res = url.searchParams.get("res")
    return res;
}

export async function action({ params, request }) {
    const formData = await request.formData()
    const origin = formData.get("origin")
    console.log(origin)
    const SUrl = await createNewSUrl({ origin })

    return redirect(`/create?res=${SUrl}`)
}

function CreateSUrl(props) {
    const { user, isLogin } = useAuth();
    const [isModalOpen, setModalOpen] = useState(false);
    const [newUrl, setNewUrl] = useState("");
    const navigate = useNavigate();

    useEffect(() => {
        if (!isLogin) {
            return navigate("/signin");
        }
    }, [])

    const loadData = useLoaderData();
    console.log(loadData);

    useEffect(() => {
        if (loadData && loadData != null) {
            console.log("실행 여부 확인")
            console.log(loadData)
            openModal();
            console.log(isModalOpen)
            setNewUrl(loadData);
        }
        console.log(isModalOpen)
    }, [loadData])

    const openModal = () => {
        console.log("실행 여부 확인2")
        setModalOpen(true);
        console.log(isModalOpen)
    }

    const closeModal = () => {
        setModalOpen(false);
    }

    const movePage= () =>{
        const bits = loadData.split("/")
        console.log(bits, "length : " + bits.length)
        navigate(`/modify/${bits[bits.length - 1]}`)
    }




    return (
        <section className='w-75 d-flex justify-content-center mt-5'>
            <Modal show={isModalOpen} onHide={closeModal}>
                    <ModalHeader closeButton>
                        <ModalTitle>새로운 Url</ModalTitle>
                    </ModalHeader>
                    <ModalBody>
                        {newUrl}
                    </ModalBody>
                    <ModalFooter>
                        <Button onClick={movePage}
                        variant='primary'>
                            수정하기
                        </Button>
                        <Button onClick={closeModal} variant='secondary'>close</Button>
                    </ModalFooter>
            </Modal>            

            <Form method='POST'>
                <p>
                    <label htmlFor='originForm'>Url 입력 : </label>
                    <input className='form-control' id='originForm' type="text" name='origin' />
                </p>
                <button className='btn btn-primary' type='submit'>SUrl 만들기</button>
            </Form>
        </section>
    );
}

export default CreateSUrl;