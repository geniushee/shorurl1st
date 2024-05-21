import React from 'react';
import {getSUrl} from "../../SUrls"
import { redirect, useLoaderData } from 'react-router-dom';

export async function loader({ params }) {
    console.log(params)
    if (!params) {
            console.log("스트레스 받아")
            return redirect("/main")
          }
    let sUrl  = await getSUrl({params});
    console.log(sUrl)
    if(!sUrl.includes("http://") && !sUrl.includes("https://")){
        sUrl = "https://" + sUrl;
    }
    window.location.href = sUrl;
}

function SUrl() {
    // const { sUrl } = useLoaderData();


    return (
        <div>
            redirect
        </div>
    );
}

export default SUrl;
