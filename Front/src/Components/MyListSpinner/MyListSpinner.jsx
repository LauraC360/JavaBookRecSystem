



const MyListSpinner = ({ allLists, changeList }) => {
    return (
      <select onChange={changeList}>
        {allLists.map((list) => (
          <option key={list.id} value={list.id}>
            {list.name}
          </option>
        ))}
      </select>
    );
  };
  
  export default MyListSpinner;