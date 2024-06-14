

const MyListSpinner = ({ allLists, changeList }) => {
    return (
      <select className="my-list-spinner" onChange={changeList}>
        {allLists && allLists[0] && allLists.map((list) => (
          <option key={list.id} value={list.id}>
            {list.name}
          </option>
        ))}
      </select>
    );
  };
  
  export default MyListSpinner;