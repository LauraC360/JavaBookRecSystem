import { HashRouter as Router, Routes, Route } from 'react-router-dom';
import { Home } from './Pages/Home';
import { SignIn } from './Pages/SignIn';
import { SignUp } from './Pages/SignUp';
import { Account } from './Pages/Account';
import { AddHousehold } from './Pages/AddHousehold';
import { HouseholdProvider } from './HouseholdContext';
import { CodeEmailPage } from './Pages/CodeEmail';
import { PreferencesAndAllergens } from './Pages/PreferencesAndAllergens';
import { Household } from './Pages/Household';
import { HouseholdManage } from './Pages/HouseholdManage';
import ChoreListPage from './Pages/ChoreList';
import InvetoryListPage from './Pages/InventoryList';
import { EfficientRoute } from './Pages/EfficientRoute.jsx';
import { Recipe } from './Pages/Recipe.jsx';
import { Menu } from './Pages/Menu.jsx';
import './App.css';
import { MyLists } from './Pages/MyLists';
import {Recc}  from './Components/Recc/Recc';

export function App() {
  return (
    <HouseholdProvider>
      <Router>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/signin" element={<SignIn />} />
          <Route path="/signup" element={<SignUp />} />
          <Route path="/my-lists" element={<MyLists/>} />
          <Route path="/browse" element={<Recipe  />} />
          <Route path="/recommendations" element={<Recc />} />
        </Routes>  
      </Router>    
    </HouseholdProvider>
  );
}
