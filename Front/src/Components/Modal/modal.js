import React, {useEffect, useState} from "react";
import "./modal.css";
import StarRating from "../StarRating/StarRating";
import MyListSpinner from "../MyListSpinner/MyListSpinner";

export default function Modal({ book, toggleModal, img, startRating}) {
    console.log('Modal rendered with book:', book);
    const [rating, setRating] = useState(startRating);



    const [allLists, setAllLists] = useState([]);
    const [currentListId, setCurrentList] = useState(1);

    const handleList = (event) => {
        setCurrentList(event.target.value);
    }

    const fetchLists = async () => {
        try{
            const response = await fetch(`http://localhost:8082/api/v1/reading-lists/all-reading-lists`);
            const data = await response.json();
            console.log(data);
            setAllLists(data);
            setCurrentList(data[0].id);
        }
        catch (error) {
            console.error('Error:', error);
        }
    }

    useEffect(() => {
        console.log('book info: ', book);
        console.log('current list id:', currentListId);
        console.log('all lists:', allLists);
        console.log('start rating:', rating);


        fetchLists();
    }, []);


    const addToList = async () => {
        // console.log('current list id:', currentListId);
        try{
            console.log('adding to list: ', currentListId);
            console.log('book:', book);
            console.log('book id:', book.id);
            const response = await fetch(`http://localhost:8082/api/v1/reading-lists/${currentListId}/${book.id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            const data = await response.json();
            console.log(data);
        } catch (error) {
            console.error('Error:', error);
        }
    }


    
    return (
        <div className="modal">
            <div onClick={toggleModal} className="overlay"></div>
            <div className="modal-content">
                <h2>{book && book.bookTitle}</h2>
                
                <div className=".modal-title-image-container"><img src={img} alt="Title Image" className="modal-title-image" /> </div>

                <div className="small-info-container">
                    <div className="small-info-left">
                        <h5>Author: </h5>
                        <p>{book && (book.author.name || book.author)}</p>
                    </div>
                    <div className="small-info-center">
                        <h5>Rating: </h5>
                        <p>{book && book.avg_rating}</p>
                    </div>
                    <div className="small-info-right">
                     <StarRating bookId={book.id} />
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


                <MyListSpinner allLists={allLists} changeList={handleList} />
                <button className="add-to-list" onClick={addToList}>Add to List</button>
            </div>
        </div>
    </div>
    );
}
