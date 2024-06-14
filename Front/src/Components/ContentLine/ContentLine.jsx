import React, {useState, useEffect} from 'react';
import './ContentLine.css';
import BooksLine from '../BooksLine/BooksLine';

const ContentLine = ({type}) => {
  const [books , setBooks] = useState([]);


  const pickAPI = (type) => {
    switch (type) {
      case 'Popular':
        return 'http://localhost:8082/api/v1/books/mostPopular';
      case 'Some recommendations':
        return '';
      case 'My List':
        return 'http://localhost:8082/api/v1/books/someReadingList';
      default:
        return '';
  }};


  useEffect(() => {
    


    const fetchData = async (type) => {//TODO: Add the URL of the API
        try{
          const api = pickAPI(type);
          console.log(api);
          const response = await fetch(api);
          const data = await response.json();
          console.log(data);

          setBooks(data);
         }catch(error) {
          console.log("error: ", error);
         }

    }



    // setBooks(['Book1', 'Book2', 'Book3', 'Book4', 'Book5']);
    fetchData(type);
  }, []);

  return (
    <div>
      <h2>{type}</h2>
      <BooksLine books={books} />

      
    </div>
  );
};

export default ContentLine;