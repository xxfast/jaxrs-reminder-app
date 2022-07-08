import * as React from 'react';
import { useForm, Controller } from "react-hook-form";
import Button from '@mui/material/Button';
import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import TextField from '@mui/material/TextField';
import Typography from '@mui/material/Typography';

import useAuth from '../hooks/useAuth';  

export default function Login() {

    const { handleSubmit, control } = useForm({
        initialValues: {
            username: '',
            password: ''
        }
    });

    const { loginUser, error } = useAuth();

    const handleLogin = async (data) => {
        await loginUser(data);
    }

    return (
        <main>
            <Box sx={{ bgcolor: '#2e2e2e', display:"flex", justifyContent:"center", alignItems:"center", minHeight:"100vh", minWidth: 275 }} >
                <Card>
                    <form onSubmit={handleSubmit(handleLogin)}>
                        <CardContent>
                            <Typography component={'span'} variant="h3" gutterBottom>
                                Login
                            </Typography>
                            <br/>
                            <br/>
                            <Controller
                                name="username"
                                control={control}
                                rules={{ required: true }}

                                render={({ field: { onChange, value } }) => (
                                    <TextField fullWidth onChange={onChange} value={value || ''} label={"User"} />
                                )}
                            />
                            <br/>
                            <br/>
                            <Controller
                                name="password"
                                control={control}
                                render={({ field: { onChange, value } }) => (
                                    <TextField fullWidth onChange={onChange} value={value || ''} type="password" label={"Password"} />
                                )}
                            />
                        </CardContent>
                        <CardActions>
                            <Button type="submit" variant="contained">Login</Button>
                        </CardActions>
                    </form>
                </Card>
            </Box>
        </main>
      );
}
