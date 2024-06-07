import React, { useEffect, useState } from 'react';
import './Lists.css';

const Lists = ({  currentList, setListObj }) => {
  const [lists, setLists] = useState([]);

  // Fetch lists when component mounts
  useEffect(() => {
    const fetchLists = async () => {
        var data = null;
        try{const response = await fetch(`http://localhost:8082/api/v1/reading-lists/get-all-reading-lists`);
          console.log(response);
        data = await response.json();
          console.log(data);
      }
        catch (error) {
            console.error('Error fetching lists:', error);
        }

        if(!data) {
            console.error('Invalid data received:', data);
            return [];
        }
        return data;
    };


    // Replace this with your actual code to fetch lists
    fetchLists().then(fetchedLists => {
        setLists(fetchedLists);
      });
  }, []);

  const handleListChange = (event) => {
    
    console.log('event.target.value',event.target.value);

    for(var i=0;i<lists.length;i++){
      if(lists[i].id==event.target.value){
        console.log('curent list obj',lists[i]);
        setListObj(lists[i]);
      }
    }
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