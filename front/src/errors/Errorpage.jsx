import React from 'react';
import { useRouteError } from 'react-router';

function Errorpage(props) {
    const error = useRouteError();
    console.error(error);

    return (
        <div id='error-page'>
            <h1>Oops!</h1>
            <p>Sorry, an unexpected error has occurred.</p>
            <p>
                <i>{error.statusText || error.message}</i>
            </p>
            <a href={document.referrer}>back</a>
            <span>    </span>
            <a href="/">home</a>
        </div>
    );
}

export default Errorpage;