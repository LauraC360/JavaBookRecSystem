// src/components/RecipeCards.jsx
import React, { useState, useEffect } from 'react';
import Cards from '../Cards/Cards';
import PageButtons from '../PageButtons/pageButtons';
import "./RecipeCards.css";

function RecipeCards(eliminatePagination) {
  //console.log('eliminatePagination:', eliminatePagination.eliminatePagination);
  const [data, setData] = useState([]);
  const [pages, setPages] = useState(1);
  const [currentPage, setCurrentPage] = useState(0); // Java page starts from 0

  useEffect(() => {

    const fetchData = async () => {
      try {
        


        // console.log('currentPage:', currentPage);
        // console.log('fetching data...', `http://localhost:8082/api/v1/books/getBookPage/${currentPage}`);
        const response = await fetch(`http://localhost:8082/api/v1/books/getBookPage/${currentPage}`);
        if (!response.ok) {
          console.error('response:', response);
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        const json = await response.json();
        console.log('Data:', json);
        setData(json);


        try{
          const response = await fetch(`http://localhost:8082/api/v1/books/getTotalBookPages`);
          if (!response.ok) {
            // console.error('response:', response);
            throw new Error(`HTTP error! status: ${response.status}`);
          }
          const json = await response.json();
          console.log('Data:', json);
          setPages(json);
        } catch (error) {
          console.error('Error:', error);
        }

      } catch (error) {
        console.error('Error:', error);
      }
      
    console.log('currentPage:', currentPage);
    console.log('all pages:', pages);
    };

    fetchData();
  }, [currentPage]);

  const handleLike = async (recipeId) => {//TODO
    try {
      const userId = 1; // Replace with actual user ID
      const response = await fetch(`http://localhost:9091/api/v1/recipes/addLike?recipeId=${recipeId}&userId=${userId}`, {
        method: 'POST',
      });
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      const data = await response.json();
      console.log('Recipe liked:', data);
    } catch (error) {
      console.error('Error:', error);
    }
  };

  const handleDislike = async (recipeId) => {//TODO
    try {
      const userId = 1; // Replace with actual user ID
      const response = await fetch(`http://localhost:9091/api/v1/recipes/removeLike?recipeId=${recipeId}&userId=${userId}`, {
        method: 'POST',
      });
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      const data = await response.json();
      console.log('Recipe disliked:', data);
    } catch (error) {
      console.error('Error:', error);
    }
  };

  return (
    <>
      <div className='cards-container'>
        {data ? data.map((book) => (
          // console.log('book:', book),
          // console.log('book.id:', book.id),
          <Cards 
            key={book.id} 
            book={book} 
            handleLike={handleLike} 
            handleDislike={handleDislike} 
          />
        )) : <div>Loading...</div>}
      </div>
      
      { eliminatePagination && eliminatePagination.eliminatePagination === false ? 
       <PageButtons 
        pages={pages} 
        setCurrentPage={setCurrentPage} 
        currentPage={currentPage}
      /> : null}
    </>
  );
}

export default RecipeCards;
