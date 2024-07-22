import axios from 'axios';
import React from 'react';

export async function sUrlChatCreateRoom({ roomName, sUrlId }) {
    try {
        const response = await axios({
            method: "POST",
            url: "http://localhost:8080/api/v1/surlChat/createRoom",
            data:
            {
                surlId: sUrlId,
                roomName: roomName
            },
            withCredentials: true
        }).then(response => response)
    } catch {

    }
    return;
}

export async function sUrlChatRoomList({ page }) {
    const response = await axios({
        method: "GET",
        url: "http://localhost:8080/api/v1/surlChat/roomList?page=" + page,
        withCredentials: true
    }).then(response => response)

    return response;
}

export async function getChatRoomDetail({ roomId }) {
    const response = await axios({
        method:"GET",
        url: "http://localhost:8080/api/v1/surlChat/" + roomId + "/detail",
        withCredentials: true,
    }).then(response => response)
    return response;
}

export async function getChatMessages({ roomId }) {
    const response = await axios({
        method:"GET",
        url: "http://localhost:8080/api/v1/surlChat/" + roomId + "/messages",
        withCredentials: true,
    }).then(response => response)
    return response;
}