import React, { useEffect, useState } from 'react';
import '@fortawesome/fontawesome-free/css/all.css';

function StarRating(  ) {
  const [rating, setRating] = useState(0);
  const [hover, setHover] = useState(0);

  const fetchRating = () => {
    //todo fetch get
    console.log('Rating fetched: 2<static>');
    return 2;
    }

  const saveRating = (input) => {
    console.log('Rating:', input);
    setRating(input);
    //todo fetch post
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