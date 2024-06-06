import React, { useEffect, useState } from 'react';
import { Button } from '../Button/Button';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import './Header.css';

interface Notification {
    id: number;
    message: string;
}

export const Header = () => {

   

    return (
        <nav>
            <a href="#"><img src="img/ico/Logo.png" alt="logo" /></a>
            <ul>
                <li className="homeChores"><Link to="/">Home</Link></li>
                <li className="aboutChores"><a href="#">Recommendation</a></li>
                <li className="contactChores"><a href="#">Browse</a></li>
                <li className='aboutChores'><a href='#'>My lists</a></li> 
            </ul>                
        </nav>
    )
}
