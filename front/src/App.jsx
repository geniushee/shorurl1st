import './App.css'
import { Outlet, Link, redirect } from 'react-router-dom'

function App() {

  return (
    <>
      <div>
        <Link to='/'>메인 페이지</Link><Link to={'/create'}>새로 만들기</Link>
        </div>
      <Link to="/1">시험 링크 : /1</Link>

      <div id="detail">
        <Outlet />
      </div>
    </>
  )
}

export default App
