import React, { useContext } from 'react';

import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import { Button, Grid, Link, Stack, Typography } from '@mui/material';

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
            <Grid container direction="row" justifyContent="space-between" alignItems="center">
                <Grid item>
                    <Link href="/doc/" variant="body2">Swagger API documentation</Link>
                </Grid>
                <Grid item>
                    <Typography component={'span'} variant="h3" gutterBottom>
                        Reminders App
                    </Typography>
                </Grid>
                <Grid item>
                    <Stack direction="row" spacing={1} justifyContent="flex-end" alignItems="center">
                        <UserHeaderMenu/>
                    </Stack>
                </Grid>
            </Grid>
        </header>
    );
  }