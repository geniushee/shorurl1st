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
import MySUrlList, { loader as myListLoader } from './domain/surl/MySUrlList.jsx'
import ModifySUrl, { loader as SUrlInfoLoader, action as modifyAction } from './domain/surl/ModifySUrl.jsx'
import SignUp, { action as signUpAction } from "./domain/member/SignUp.jsx"
import ManageMain from './domain/manage/ManageMain.jsx'
import ManageLogin, { action as manageLoginAction } from './domain/manage/ManageLogin.jsx'
import ManageLayout from './domain/manage/ManageLayout.jsx'
import Dashboard, { loader as dashLoader } from './domain/manage/Dashboard.jsx'
import Mypage from './domain/member/Mypage.jsx'
import MypageLayout from './domain/member/MypageLayout.jsx'
import Withdrawal from './domain/member/Withdrawal.jsx'


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
        element: <ModifySUrl />,
        loader: SUrlInfoLoader,
        action: modifyAction,
      },
      {
        path: "/signin",
        element: <SignIn />,
        action: signinAction,
      },
      {
        path: "/signup",
        element: <SignUp />,
        action: signUpAction,
      },
      {
        path: "/mypage",
        element: <MypageLayout />,
        children: [
          {
            path: "/mypage",
            element: <Mypage />,
          }, {
            path: "/mypage/mylist",
            element: <MySUrlList />,
            loader: myListLoader,
          },{
            path : "/mypage/withdrawal",
            element : <Withdrawal />,
          }

        ],
      }
    ]
  }, {
    path: "/:surl",
    element: <SUrl />,
    errorElement: <Errorpage />,
    loader: SUrlLoader,
  }, {
    path: "/manage",
    element: <ManageLayout />,
    errorElement: <Errorpage />,
    children: [
      {
        path: "/manage",
        element: <ManageLogin />,
        action: manageLoginAction,
      },
      {
        path: "/manage/main",
        element: <ManageMain />,
        children: [
          {
            path: "/manage/main",
            element: <Dashboard />,
            loader: dashLoader,
          }
        ]
      }
    ]
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
