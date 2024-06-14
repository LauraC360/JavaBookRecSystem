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
    const [newListName, setNewListName] = useState("");


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



    const createList = async () => {
        try{
            console.log('creating list:', newListName);
            const response = await fetch(`http://localhost:8082/api/v1/reading-lists/new-list`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ name: newListName })
            });
            const data = await response.json();
            console.log(data);

            fetchLists();

        }   catch (error) {
            console.error('Error:', error);
        }
    }



    const deleteList = async () => {
        try{
            const response = await fetch(`http://localhost:8082/api/v1/reading-lists/excluded/${currentListId}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            const data = await response.json();
            console.log(data);
            fetchLists();
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
            <div className="my-list-header">
                <MyListSpinner allLists={allLists} changeList={handleList} />
                <input type="text" placeholder="New List Name..." className="new-list-input" value={newListName} 
                    onChange={e => setNewListName(e.target.value)}   />
                <button onClick={createList} className="create-list-button">Create </button>
                <button onClick={deleteList} className="delete-list-button">Delete </button>
            </div>

            <div className="my-cards-container">
             {!loading && books &&  books.map(book => (<Cards key={book.id} book={book} listId={currentListId} />))}
             {!loading && books && books.length === 0 && <h1>No books in this list</h1>}
            </div>
        </div>
    )
}


export default MyListsContent;