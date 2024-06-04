import React from 'react';
import {Outlet} from "react-router-dom"

function ManageLayout(props) {
    return (
        <div>
            <Outlet></Outlet>
        </div>
    );
}

export default ManageLayout;