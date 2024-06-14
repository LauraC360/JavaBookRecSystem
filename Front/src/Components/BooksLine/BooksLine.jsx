import React from 'react';
import './BooksLine.css';
import Cards from '../Cards/Cards';

const BooksLine = ({ books }) => {

    const handleLike = (bookId) => {
        console.log(`Liked book with id ${bookId}`);
    }

    const handleDislike = (bookId) => {
        console.log(`Disliked book with id ${bookId}`);
    }

  return (
        <div className="books-line-container">
            {books.map((book, index) => (
                <Cards book={book} handleDislike={handleDislike} handleLike={handleLike} />
            ))}
        </div>
    );
};

export default BooksLine;