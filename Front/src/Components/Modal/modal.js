import React, {useEffect, useState} from "react";
import "./modal.css";

export default function Modal({ book, toggleModal, img }) {
    console.log('Modal rendered with book:', book);
    

    //console.log(book.imageList);//its ok
    //const imageSrc = (book.imageList && book.imageList.length > 0) ? book.imageList[0] : titleImage;
    //console.log(book.instructionsList);
    
    return (
        <div className="modal">
            <div onClick={toggleModal} className="overlay"></div>
            <div className="modal-content">
                <h2>{book && book.bookTitle}</h2>
                
                <div className=".modal-title-image-container"><img src={img} alt="Title Image" className="modal-title-image" /> </div>

                <div className="small-info-container">
                    <div className="small-info-left">
                        <h5>Author: </h5>
                        <p>{book && book.author}</p>
                    </div>
                    <div className="small-info-center">
                        <h5>Rating: </h5>
                        <p>{book && book.avg_rating}</p>
                    </div>
                    <div className="small-info-right">
                    {/* add rating module here */}
                    </div>
                </div>


                <div className="text-box-container">
                    <div className="text-box-left">
                    <h3>GENRES</h3>
                    <ul>
                        { book && book.genres && book.genres.length > 0 ? book.genres.map((genre, index) => <li key={index}>{genre}</li>) : null }
                    </ul>
                    </div>
                    
                     {/* Add your left text box here */}
                    <div className="text-box-right">
                    <h3>DESCRIPTION</h3>
                    <div className="text-box-right">
                        { book && book.description}
                    </div>
                </div>
                <button className="close-modal" onClick={toggleModal}>
                    
                </button>
            </div>
        </div>
    </div>
    );
}
