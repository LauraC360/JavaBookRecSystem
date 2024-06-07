import 'MyListsContent.css';
import { Lists } from '../Lists';
import { ListItems } from '../ListItems';


export const MyListsContent = () => {
    const [currentList, setCurrentList] = useState(0);

    return(
        <>
            <Lists currentList={currentList} setCurrentList={setCurrentList}/>
            <ListItems currentList={currentList}/>

        </>
    )
}