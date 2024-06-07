import React, { useEffect, useState } from 'react';
import './Lists.css';

const Lists = ({ currentList, setCurrentList }) => {
  const [lists, setLists] = useState([]);

  // Fetch lists when component mounts
  useEffect(() => {
    const fetchLists = async () => {
        var data = null;
        try{const response = await fetch('https://localhost:3000/lists');
        data = await response.json();}
        catch (error) {
            console.error('Error fetching lists:', error);
        }

        if(!data || !data.lists) {
            console.error('Invalid data received:', data);
            return [];
        }
        return data.lists;
    };


    // Replace this with your actual code to fetch lists
    fetchLists().then(fetchedLists => {
        setLists(fetchedLists);
      });
  }, []);

  const handleListChange = (event) => {
    setCurrentList(event.target.value);
  };

  return (
    <select className='lists-select' value={currentList} onChange={handleListChange}>
      {lists && lists.map((list) => (
        <option key={list.id} value={list.id}>
          {list.name}
        </option>
      ))}
    </select>
  );
};

export default Lists;