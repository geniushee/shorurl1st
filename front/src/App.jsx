import './App.css'
import { Outlet, Link, redirect } from 'react-router-dom'
import Navbar from './global/Navbar';

function App() {

  return (
    <>
      <div>
        <Navbar></Navbar>
      </div>

      <div id="detail">
        <Outlet />
      </div>
    </>
  )
}

export default App
