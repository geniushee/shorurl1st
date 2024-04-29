import React from 'react'
import ReactDOM from 'react-dom/client'
import App, { loader as AppLoader } from './App.jsx'
import './index.css'
import {createBrowserRouter, RouterProvider} from "react-router-dom"
import Errorpage from './errors/Errorpage.jsx'
import SUrl, {loader as SUrlLoader} from './domain/SUrl.jsx'


const router = createBrowserRouter([
  {
    path:"/",
    element : <App />,
    errorElement: <Errorpage />,
    loader: AppLoader,
    children:[
      {
        path:"/:surl",
        element: <SUrl />,
        loader: SUrlLoader,
      }
    ]
  },
])

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>,
)
