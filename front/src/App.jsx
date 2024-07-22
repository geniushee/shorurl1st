import './App.css'
import { Outlet, Link, redirect } from 'react-router-dom'
import Navbar from './global/Navbar';
import { useAuth } from './global/AuthProvider';
import { useState } from 'react';
import Modal from './Modal';

function App() {
  const { user, isLogin } = useAuth();
  console.log(user.username);

  const [isModalOpen, setIsModalOpen] = useState(false);

  const openModal = () => {
    setIsModalOpen(true);
  }

  const closeModal = () => {
    setIsModalOpen(false);
  }

  return (
    <>
      <div>
        <Navbar></Navbar>
      </div>
      <div>
      <button onClick={openModal}><input type='search' placeholder='search'/><button>검색</button></button>
      {isModalOpen && <Modal onClose={closeModal} />}
      </div>
      {isLogin ? <nav><Link to={"/mypage/mylist"}>내 SUrl 목록</Link>
      <Link to={"/chatRoom/roomList"}>SUrl 채팅방 목록</Link></nav> : <p></p>}
      <p>{isLogin ? "로그인" : "로그아웃"}</p>
      {!!user.username ? <div>
        <p>{user.username}</p>
        <p>{user.name}</p>
      </div> : <div></div>}

      <div id="detail">
        <Outlet />
      </div>
      <footer>
        <Link to="/manage">관리자 페이지</Link>
      </footer>
    </>
  )
}

export default App
