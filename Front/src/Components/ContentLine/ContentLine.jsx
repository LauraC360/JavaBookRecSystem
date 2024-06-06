import React, {useState, useEffect} from 'react';
import './ContentLine.css';
import BooksLine from '../BooksLine/BooksLine';

const ContentLine = ({type}) => {
  const [books , setBooks] = useState([]);

  useEffect(() => {
    


    const fetchData = async (type) => {//TODO: Add the URL of the API
        const response = await fetch(``);
        const data = await response.json();
        
        setBooks(books);
    }


    setBooks(['Book1', 'Book2', 'Book3', 'Book4', 'Book5']);
    //fetchData(type);
  }, []);

  return (
    <div>
      <h2>{type}</h2>
      <BooksLine books={books} />

      
    </div>
  );
};

export default ContentLine;