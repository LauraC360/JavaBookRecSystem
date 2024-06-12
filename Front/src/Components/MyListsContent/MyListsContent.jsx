import { useEffect, useState } from "react";
import MyListSpinner from "../MyListSpinner/MyListSpinner.jsx";
import MyBookCards from "../MyBookCards/MyBookCards.jsx";
import Cards from "../Cards/Cards.jsx";
import "./MyListsContent.css";





const MyListsContent = () => {
    const [allLists, setAllLists] = useState([]);
    const [currentListId, setCurrentList] = useState(1);
    const [books, setBooks] = useState([]);
    const [loading, setLoading] = useState(true);


    const handleList = (event) => {
        setCurrentList(event.target.value);
    }

    const fetchLists = async () => {
        try{
        const response = await fetch(`http://localhost:8082/api/v1/reading-lists/all-reading-lists`);
        const data = await response.json();
        setAllLists(data);
        } catch (error) {
            console.error('Error:', error);
        }
    }


    const fetchBooks = async () => {
        try{
            console.log('all lists ', allLists);
            console.log('current list id ', currentListId);
        while(currentListId === undefined);
        const response = await fetch(`http://localhost:8082/api/v1/reading-lists/list/${currentListId}`);
        const data = await response.json();
        console.log('current list data ', data);
        

        let newBooks = [];
        for(let i = 0; i < data.books.length; i++){
            const response = await fetch(`http://localhost:8082/api/v1/books/${data.books[i]}`);
            const bookData = await response.json();
            newBooks.push(bookData);
        }
        setBooks(newBooks);
        console.log(newBooks);
        console.log(data);
        } catch (error) {
            console.error('Error:', error);
        }
    }

    useEffect(() => {
        setLoading(true);
        fetchLists();
        setLoading(false);
    }, []);


    useEffect(() => {
        setLoading(true);
        fetchBooks();
        setLoading(false);
    }, [currentListId]);


    return (

        <div className="my-list-container">
            <MyListSpinner allLists={allLists} changeList={handleList} />
        
            <div className="my-cards-container">
             {!loading && books &&  books.map(book => (<Cards key={book.id} book={book} />))}
             {!loading && books && books.length === 0 && <h1>No books in this list</h1>}
            </div>
        </div>
    )
}


export default MyListsContent;