import React from 'react';
import { Header } from '../Components/Header';
import { Footer } from '../Components/Footer';
import { MyListsContent } from '../Components/MyListsContent';


export const MyLists = () => {
    return (
        <div>
            <Header />
            <MyListsContent />
            <Footer />
        </div>
    );
}