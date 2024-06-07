import React, { useEffect } from 'react';


export default  Lists =(currentList, setCurrentList) =>  {
    const [listIds, setListIds] = useState([]);


    useEffect(() => {
        const response = await fetch('http://localhost:3000/lists')
            .then(response => response.json())
            .then(data => setListIds(data));

        if(!response.ok) {
            console.log('Error fetching list ids');
            return;
        }

        const json = await response.json();

        setListsIds(json.map(list => list.id));
    }), []};

}