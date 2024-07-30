import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from './AuthProvider';
import { Modal, ModalHeader, ModalTitle, ModalBody } from 'react-bootstrap';
import MeiliApp from './../Meilisearch';

function Navbar(props) {
    const { user, isLogin, logout } = useAuth();

    const [isModalOpen, setIsModalOpen] = useState(false);

    const openModal = () => {
        setIsModalOpen(true);
    }

    const closeModal = () => {
        setIsModalOpen(false);
    }

    return (
        <div className='navbar navbar-expand-lg bg-body-tertiary'>
            <Modal show={isModalOpen} onHide={closeModal}>
                    <ModalHeader closeButton>
                        <ModalTitle>SUrl 검색</ModalTitle>
                    </ModalHeader>
                    <ModalBody>
                    <MeiliApp></MeiliApp>
                    </ModalBody>
            </Modal>       
            <div className='container-fluid'>
                <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className='collapse navbar-collapse' id='navbarNav'>
                    <ul className='navbar-nav'>
                        <li className='nav-item'>
                            <Link className='nav-link mx-1' to={"/"}>Main</Link>
                        </li>
                        <li className='nav-item'>
                            <Link className='nav-link mx-1' to={"/create"} >New SUrl</Link>
                        </li>
                    </ul>
                    <div className='mx-1 d-flex'>
                        <div>
                            <button className='d-flex border-0 bg-body-tertiary' onClick={openModal}><input className='form-control' type='search' placeholder='search' /><button className='btn btn-primary'>search</button></button>
                        </div>
                    </div>
                </div>
                <div className='d-flex align-items-center'>
                    <span className='nav-item'>
                        {isLogin ? (<span className='mx-1 navbar-text'>{user.name}님 환영합니다.</span>) : (<span className='mx-1 navbar-text'><Link to={"/signin"} className='text-primary-emphasis'>로그인</Link>이 필요합니다.</span>)}
                    </span>
                    {isLogin ?
                        (<>
                            <span className='nav-item'>
                                <Link className='mx-1 btn btn-primary' to={"/mypage"}>My Page</Link>
                            </span>
                            <span className='nav-item'>
                                <button className='mx-1 btn btn-primary' onClick={logout}>logout</button>
                            </span>
                        </>)
                        : (<>
                            <span className='nav-item'>
                                <Link className='mx-1 btn btn-primary' to={"/signin"}>Sign In</Link><Link className='mx-1 btn btn-primary' to={"/signup"}>Sign Up</Link>
                            </span>
                        </>
                        )
                    }
                </div>

            </div>
        </div>
    );
}

export default Navbar;