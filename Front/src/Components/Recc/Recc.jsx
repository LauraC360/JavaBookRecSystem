import RecipeCards from "../RecipeCards/RecipeCards";
import { Footer } from "../Footer/Footer";
import { Header } from "../Header/Header";
export const Recc = () => {

    return (
        <div>
            <Header />
            {/* <ImageContent image="img/header-background.jpg"/> */}
             <RecipeCards rec={true} />

            
            <Footer />
        </div>
    )
}
