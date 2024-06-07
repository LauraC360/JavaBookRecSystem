import React, { useState, useEffect } from 'react';
import './MyListsContent.css';
import Lists from '../Lists/Lists';
import PrepCards from '../PrepCards/PrepCards';

export const MyListsContent = () => {
  const [selectedList, setSelectedList] = useState(null);
  const [books, setBooks] = useState([]);
  const [setListObjCurr, setListObjCurrr] = useState(null);


  const fetchBooks = async () => {
    console.log('in fetch books,selectedList',selectedList);
    if(!setListObjCurr) return;
    console.log('valid list',setListObjCurr);
    for (var i = 0; i < setListObjCurr.length; i++) {
      try{const response = await fetch(`http://localhost:8082/api/v1/books/getBook/${setListObjCurr[i]}`);
      console.log(response);
      const data = await response.json();
      setBooks(data);}
      catch (error) {
        console.error('Error fetching books:', error);
    }
  }}


  // const handleListChange = (newList) => {
  //   console.log('newlist',newList);
  //   setSelectedList(newList);
  //   setBooks(setListObjCurr || []);
  //   fetchBooks();
  // };



  const setListObj = (listObj) => {
    console.log('list obj',listObj);
    setListObjCurrr(listObj);
    setSelectedList(listObj.id);
    setBooks(listObj.books);

    let books = [];
    const fetchBook = async (bookId) => {
      if (bookId && bookId !== 'undefined') {
        try{
          console.log('fetching book');
        const data = await fetch(`http://localhost:8082/api/v1/books/getBook/${bookId}`);
        const bookData = await data.json();
        return bookData;
      } catch (error) {
        console.error('Error fetching book:', error);
      }
    }}
  
      for(var i=0;i<listObj.books.length;i++){
        books[i] = fetchBook(listObj.books[i]);
      }

      setBooks(books);
  }

  
  

  return (
    <div>
      <div className='spinner-container'><Lists  currentList={selectedList} setListObj={setListObj} /></div>
      <div className="book-cards">
        <PrepCards books={books} />
       
      </div>
    </div>
  );
}