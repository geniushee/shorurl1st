import React from 'react';
import { Link } from 'react-router-dom';

function Main(props) {
    return (
        <div className='container'>
            <p className='fs-1 mt-5'>긴 URL을 단축할 수 있게 해주는 서비스입니다.</p>
            <div className='mt-5'>
                <div className='row mb-3'>
                    <div className='text-bg-secondary rounded col-8'>
                        <p className='my-3'>긴 URL을 단축하자!</p>
                    </div>
                    <div className='col-4'>

                    </div>
                </div>
                <div className='row'>
                    <div className='col-4'>
                    </div>
                    <div className='text-bg-secondary rounded col-8'>
                        <p className='my-3 text-end'>기억하기 힘들고 어려운 긴 url을 숫자로 바꿔보자!</p>
                    </div>
                </div>
            </div>



        </div>
    );
}

export default Main;