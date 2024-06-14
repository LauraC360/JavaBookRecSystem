import React, { useEffect, useState } from 'react';
import '@fortawesome/fontawesome-free/css/all.css';

function StarRating( bookId ) {
  const [rating, setRating] = useState(0);
  const [hover, setHover] = useState(0);

  const fetchRating = () => {
    //todo fetch rating from backend
    return 0;
    }

  const saveRating = (input) => {
    try{
      console.log('BookId:', bookId.bookId);

      const response= fetch(`http://localhost:8082/api/v1/reviews/${bookId.bookId}/${input}`,
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
      }}
      );
      const data = response.json();
      console.log(data);
    } catch (error) {
      console.error('Error:', error);
    }


    console.log('Rating:', input);
    setRating(input);
  }

    useEffect(() => {
        setRating(fetchRating());
    }, []);

  return (
    <div>
      {[...Array(5)].map((star, i) => {
        const ratingValue = i + 1;

        return (
          <label key={i}>
            <input
              type="radio"
              name="rating"
              value={ratingValue}
              onClick={() => saveRating(ratingValue)}
              style={{ display: 'none' }} // Hide radio buttons
            />
            <i
              className="fas fa-star"
              style={{ color: ratingValue <= (hover || rating) ? "#ffc107" : "#e4e5e9" }}
              onMouseEnter={() => setHover(ratingValue)}
              onMouseLeave={() => setHover(0)}
            ></i>
          </label>
        );
      })}
    </div>
  );
}

export default StarRating;