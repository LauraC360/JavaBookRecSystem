import RecipeCards from "../Components/RecipeCards/RecipeCards";
import AIPart from "../AIPart";
import { Footer } from "../Components/Footer/Footer";
import { Header } from "../Components/Header/Header";
import { ImageContent } from "../Components/ImageContent/ImageContent";

export const Recipe = () => {

    return (
        <div>
            <Header />
            {/* <ImageContent image="img/header-background.jpg"/> */}
             <RecipeCards />

            
            <Footer />
        </div>
    )
}
