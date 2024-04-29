import './App.css'
import SUrl from './domain/SUrl'
import { Outlet,Link, useLoaderData, Form } from 'react-router-dom'
import {getSUrl} from "./SUrls";

export async function loader() {
  const SUrls = await getSUrl();
  return { SUrls };
}

function App() {
  const {SUrls} = useLoaderData();


  return (
    <>
    <div>메인 페이지</div>
    <Link to="/1">링크</Link>
    <div>{SUrls.length ? (SUrls.map((SUrl) => (
      <div>{SUrl}</div>))) : (<div>없음</div>)
    }</div>
    <div id="detail">
      <Outlet />
    </div>
    </>
  )
}

export default App
