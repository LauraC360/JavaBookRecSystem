import React from 'react';
import { Header } from '../Components/Header/Header';
import { Footer } from '../Components/Footer/Footer';
import  MyListsContent  from '../Components/MyListsContent/MyListsContent.jsx';


export const MyLists = () => {
    return (
        <div>
            <Header />
            <MyListsContent />
            <Footer />
        </div>
    );
}