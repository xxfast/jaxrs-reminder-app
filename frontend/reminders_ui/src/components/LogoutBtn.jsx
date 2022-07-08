import Button from '@mui/material/Button';
import useAuth from '../hooks/useAuth';  

export default function LogoutBtn() {

    const { logoutUser, error } = useAuth();

    const handleLogout = async () => {
        await logoutUser();
    }

    return (
        <Button onClick={handleLogout} variant="contained">Logout</Button>
    );
  }