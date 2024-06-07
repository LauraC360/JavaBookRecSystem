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
                <li className="aboutChores"><Link to="/recommendations">Recommandations</Link></li>
                <li className="contactChores">  <Link to="/browse">Browse</Link> </li>
                <li className='aboutChores'><Link to='/my-lists'>My lists</Link></li> 
                <li className='contactChores'><Button text={"Account"}/></li>
            </ul>                
        </nav>
    )
}
