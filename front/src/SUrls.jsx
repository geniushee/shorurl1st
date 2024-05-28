import axios from 'axios';
import React from 'react';

export async function getSUrl({ params }) {
    console.log(params.surl)

    const SUrl = axios({
        method: "GET",
        url: "http://localhost:8080/api/v1/surls",
        params: {
            sUrl: params.surl
        },
    }).then(response => response.data)
    return SUrl;
}

export async function getMySUrls() {

    const SUrls = axios({
        method: "GET",
        url: "http://localhost:8080/api/v1/surls/mylist",
        withCredentials: true,
    }).then(response => response.data)
    return SUrls;
}

export async function createNewSUrl({ origin }) {
    console.log(origin)

    const data = axios({
        method: "POST",
        url: "http://localhost:8080/api/v1/surls/create",
        data: {
            origin: origin
        },
        withCredentials: true,
    }).then((response) => {
        return response.data
    })
    console.log(data)
    return data;
}

export async function getSUrlInfo({ id }) {
    const SUrl = axios({
        method: "GET",
        url: "http://localhost:8080/api/v1/surls/mylist/" + id,
        withCredentials: true,
    }).then(response => response.data)
    return SUrl;
}

export async function modifySUrl({ id, title, content }) {
    const data = axios({
        method: "PUT",
        url: "http://localhost:8080/api/v1/surls/mylist/" + id,
        withCredentials: true,
        data: {
            id: id,
            title: title,
            content: content,
        }
    }).then(response => response.data)
    return data;
}

export async function deleteSUrl({id}) {
    const data = axios({
        method: "DELETE",
        url: "http://localhost:8080/api/v1/surls/delete/" + id,
        withCredentials: true,
    }).then("성공했습니다.").catch(console.log("실패했니다."))
    return data;
}


function SUrls(props) {
    return (
        <div>

        </div>
    );
}

export default SUrls;