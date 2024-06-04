import './App.css'
import { Outlet, Link, redirect } from 'react-router-dom'
import Navbar from './global/Navbar';
import { useAuth } from './global/AuthProvider';

function App() {
  const { user, isLogin } = useAuth();
  console.log(user.username);
  return (
    <>
      <div>
        <Navbar></Navbar>
      </div>
      <nav><Link to={"/mylist"}>내 SUrl 목록</Link></nav>
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
