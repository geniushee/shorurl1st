import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import './index.css'
import { createBrowserRouter, RouterProvider } from "react-router-dom"
import Errorpage from './errors/Errorpage.jsx'
import SUrl, { loader as SUrlLoader } from './domain/SUrl.jsx'
import CreateSUrl, { action as createAction, loader as newLoader } from './domain/CreateSUrl.jsx'
import Main from './domain/Main.jsx'


const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    errorElement: <Errorpage />,
    children: [
      , {
        path: "/",
        element: <Main />,

      }, {
        path: "/create",
        element: <CreateSUrl />,
        loader: newLoader,
        action: createAction,
      },
    ]
  },{
    path: "/:surl",
    element: <SUrl />,
    loader: SUrlLoader,
  }
])

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>,
)
