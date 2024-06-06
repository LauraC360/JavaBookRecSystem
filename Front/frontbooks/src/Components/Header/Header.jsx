import React, { useEffect, useState } from 'react';
import { Button } from '../Button/Button';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import './Header.css';

export const Header = () => {

    

    return (
        <nav>
            <a href="#"><img src="img/ico/Logo.png" alt="logo" /></a>
            <ul>
                <li className="homeChores"><Link to="/">Home</Link></li>
                <li className="aboutChores"><a href="#">About</a></li>
                <li className="contactChores"><a href="#">Contact Us</a></li>
               
                <li className="dropdownChores">
                    <div className="menuChores"></div>
                    <div className="contentChores">
                        <div className="homeHideChores"><a href="/">Home</a></div>
                        
                        <Link to="/">Firts</Link>
                        <Link to="/">Firts</Link>
                        <Link to="/">Firts</Link>
                        <Link to="/">Firts</Link>

                        <div className="accountHideChores"><a href="/account"><Button text={"Account"}/></a></div>
                    </div>   
                </li>
                <li className="regBtnChores"><a href='/#/account'><Button text={"Account"}/></a></li> 
            </ul>                
        </nav>
    )
}
