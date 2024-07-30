import './App.css'
import { Outlet, Link, redirect } from 'react-router-dom'
import Navbar from './global/Navbar';
import { useAuth } from './global/AuthProvider';



function App() {
  const { user, isLogin } = useAuth();
  console.log(user.username);

  return (
    <div className='h-100 d-flex flex-column'>
      <nav className=''>
        <Navbar></Navbar>
      </nav>
      <main className='container-fluid flex-grow-1 d-flex justify-content-center'>
        <Outlet />
      </main>
      <footer className='bg-secondary bg-gradient'>
        <Link className='mx-1 text-white text-decoration-none' to="/manage">관리자 페이지</Link>
      </footer>
    </div>
  )
}

export default App
