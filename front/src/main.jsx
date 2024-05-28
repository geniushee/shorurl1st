import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import './index.css'
import { createBrowserRouter, RouterProvider } from "react-router-dom"
import Errorpage from './errors/Errorpage.jsx'
import SUrl, { loader as SUrlLoader } from "./domain/surl/SUrl.jsx"
import CreateSUrl, { action as createAction, loader as newLoader } from './domain/surl/CreateSUrl.jsx'
import Main from './domain/Main.jsx'
import SignIn, { action as signinAction } from './domain/member/SignIn.jsx'
import AuthProvider from './global/AuthProvider.jsx'
import MySUrlList, {loader as myListLoader} from './domain/surl/MySUrlList.jsx'
import ModifySUrl, {loader as SUrlInfoLoader, action as modifyAction} from './domain/surl/ModifySUrl.jsx'


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
      {
        path: "/modify/:id",
        element: <ModifySUrl/>,
        loader: SUrlInfoLoader,
        action: modifyAction,
      },
      {
        path: "/mylist",
        element: <MySUrlList/>,
        loader: myListLoader,
      },
      {
        path: "/signin",
        element: <SignIn />,
        action: signinAction,
      }
    ]
  }, {
    path: "/:surl",
    element: <SUrl />,
    errorElement: <Errorpage />,
    loader: SUrlLoader,
  }
])

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <AuthProvider>
      <RouterProvider router={router}>
      </RouterProvider>
    </AuthProvider>
  </React.StrictMode>,
)
