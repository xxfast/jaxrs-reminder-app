import React, { useContext } from 'react';

import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';

import LogoutBtn from './LogoutBtn'
import { UserContext } from '../hooks/UserContext';
import './Layout.css';

function UserHeaderMenu() {
    const { user } = useContext(UserContext); 

    return user ? <><AccountCircleIcon/><span>{user}</span><LogoutBtn/></> : <Button variant="contained">Login</Button>;
}

export default function AppHeader() {

    return (
        <header className="App-header">
            <div className="App-header-user">
                <UserHeaderMenu/>
            </div>
        </header>
    );
  }