import React from 'react';
import {getSUrl} from "../SUrls"
import { useLoaderData } from 'react-router';

export async function loader({ params }) {
    const sUrl = await getSUrl(params.surl);
    return {sUrl};
}

function SUrl() {
    const { sUrl } = useLoaderData();
    
    return (
        <div>
            하염
        </div>
    );
}

export default SUrl;
