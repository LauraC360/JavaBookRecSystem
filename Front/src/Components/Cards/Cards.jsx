// src/components/Cards.jsx
import React, { useState, useEffect } from 'react';
import './Cards.css';
import loadingGIF from '../loading.gif';
import Modal from '../Modal/modal';
import { fetchImage} from "../../pexelsApiFetch";
import fetchGoodreadsImage from "../FetchGoodreadsImage/FetchGoodreadsImage";


function Cards({ book, bookId, handleLike, handleDislike }) {
  const [modalOpen, setModalOpen] = useState(false);
  const [imageSrc, setImageSrc] = useState(loadingGIF);
  const [liked, setLiked] = useState(false);
  const [bookData, setBookData] = useState(null);



  const toggleModal = () => {
    setModalOpen(!modalOpen);
  };

  const handleLikeClick = () => {
    if (liked) {
      handleDislike(book.id);
    } else {
      handleLike(book.id);
    }
    setLiked(!liked);
  };

  // useEffect(() => {//get fetch image from goodreads
  //   if (recipe.imageList && recipe.imageList.length > 0) {
  //     setImageSrc(recipe.imageList[0]);
  //   } else {
  //     fetchImage(recipe.recipeTitle.includes("recipe") ? recipe.recipeTitle : recipe.recipeTitle + ' recipe')
  //       .then((url) => {
  //         setImageSrc(url);
  //       });
  //   }
  // }, [recipe]);

  useEffect(() => {
    fetchGoodreadsImage(book)
      .then((url) => {
        setImageSrc(url);
      });


    if (modalOpen) {
      document.body.classList.add('no-scroll');
    } else {
      document.body.classList.remove('no-scroll');
    }
  }, [modalOpen]);

  return (
    <div className="card">
      <img src={imageSrc} alt={book && book.title} className="card-img" />       
      <div className="card-body">
        <h5 className="card-title">{book&&book.title}</h5>
        <button className="btn btn-primary" onClick={toggleModal}>Details</button>
        <button className={`like-button ${liked ? 'liked' : ''}`} onClick={handleLikeClick}>
          {liked ? 'Liked' : 'Like'}
        </button>
      </div>
      {modalOpen && <Modal book={book} toggleModal={toggleModal} img={imageSrc} />}
    </div>
  );
}

export default Cards;
