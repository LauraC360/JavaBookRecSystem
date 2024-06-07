import React, { useState, useEffect } from 'react';
import './MyListsContent.css';
import Lists from '../Lists/Lists';
import Cards from '../Cards/Cards'; // Assuming you have a Card component for the books

export const MyListsContent = () => {
  const [selectedList, setSelectedList] = useState(null);
  const [books, setBooks] = useState([]);

  // Update books when selectedList changes
  useEffect(() => {
    if (selectedList) {
        
      setBooks(selectedList.books);
    }
  }, [selectedList]);

  const handleListChange = (newList) => {
    setSelectedList(newList);
  };

  return (
    <div>
      <div className='spinner-container'><Lists onChange={handleListChange} /></div>
      <div className="book-cards">
        {books.map((book) => (
          <Cards key={book.id} book={book} />  ))}
       
      </div>
    </div>
  );
};