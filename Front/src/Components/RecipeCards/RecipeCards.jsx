// src/components/RecipeCards.jsx
import React, { useState, useEffect } from 'react';
import Cards from '../Cards/Cards';
import PageButtons from '../PageButtons/pageButtons';
import "./RecipeCards.css";

function RecipeCards(eliminatePagination) {
  console.log('eliminatePagination:', eliminatePagination.eliminatePagination);
  const [data, setData] = useState(null);
  const [pages, setPages] = useState(1);
  const [currentPage, setCurrentPage] = useState(0); // Java page starts from 0

  useEffect(() => {
    const fetchData = async () => {//TODO
      try {
        const logResp = await fetch('http://localhost:3000/api/login',
          {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify({
              username: 'testuser4',
              password: 'password123',
            }),
      });
        console.log('logResp:', logResp);



        const response = await fetch('http://localhost:9091/api/v1/recipes/recipePage?pageNo=' + currentPage);
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        const json = await response.json();
        setData(Array.isArray(json.content) ? json.content : [json.content]);

        setPages(json.totalPages);
      } catch (error) {
        console.error('Error:', error);
      }
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

  const handleDislike = async (recipeId) => {
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
          <Cards 
            key={book.id} 
            recipe={book} 
            handleLike={handleLike} 
            handleDislike={handleDislike} 
          />
        )) : <div>Loading...</div>}
      </div>
      
      {eliminatePagination && eliminatePagination.eliminatePagination === false ? <PageButtons 
        pages={pages} 
        setCurrentPage={setCurrentPage} 
        currentPage={currentPage}
      /> : null}
    </>
  );
}

export default RecipeCards;
