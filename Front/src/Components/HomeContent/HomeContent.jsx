import "./HomeContent.css";
import React, {useState} from "react";
import ContentLine from "../ContentLine/ContentLine.jsx";
import './HomeContent.css';
import  PageButtons  from "../PageButtons/pageButtons.js";

export const Content = () => {  

    return (
        <main className="home-main">
            
            <div className="content-line-div">
            <ContentLine type={'Popular'} /></div>

            <div className="content-line-div">
            <ContentLine type={'Some recommendations'} />
            </div>

            <div className="content-line-div">
            <ContentLine type={'My List'} />
            </div>

           
        </main>
    )
}
