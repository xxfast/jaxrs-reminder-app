import React from 'react';
import {
  BrowserRouter,
  Routes,
  Route
} from "react-router-dom";
import { UserContext } from './hooks/UserContext';
import Login from "./pages/login";
import Main from "./pages/main";
import Layout from "./components/Layout";
import PublicRoute from "./components/generic/PublicRoute";
import PrivateRoute from "./components/generic/PrivateRoute";
import useFindUser from './hooks/useFindUser';

export default function App() {
  
  const { 
    user, 
    setUser, 
    isLoading } = useFindUser();

  console.log("App - location - " + window.location)

  return (
    <BrowserRouter>
      <UserContext.Provider value={{user, setUser, isLoading}}>
        <Routes>
        <Route path="/login" element={<PublicRoute restricted={false}><Login /></PublicRoute>}/>
        <Route element={<Layout />}>
          <Route path="/" element={<PrivateRoute><Main/></PrivateRoute>}/>
        </Route>
        </Routes>
      </UserContext.Provider>
    </BrowserRouter>
  );
}
