import { useState, useContext } from 'react'; 
import { useNavigate, useLocation } from 'react-router';
import Cookies from 'js-cookie';

import { UserContext } from '../hooks/UserContext';  

export default function useAuth() {
    const { setUser } = useContext(UserContext);
    const [error, setError] = useState(null);

    let navigate = useNavigate(); 
    let location = useLocation();       
    let from = location.state?.from?.pathname || "/";

    //set user
    const setUserContext = async () => {

        const requestOptions = {
            headers: { 'Content-Type': 'application/json' },
            };

        console.log("useAuth.setUserContext - get(/api/user)")
        return await fetch("/api/user", requestOptions)
            .then(res => res.json())
            .then(
                (data) => {
                    setUser(data.username);
                },
                (error) => {
                    alert("/api/user - err - " + JSON.stringify(error));
                }
            )
    }

    //login user 
    const loginUser = async (data) => {

        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        };

        console.log("loginUser - post(/api/user/login) - " + requestOptions.body)
        return fetch("/api/user/login", requestOptions)
            .then(res => res.json())
            .then( 
                async (data) => {
                    await setUserContext();
                },
                (error) => {
                    console.log("loginUser - post(/api/user/login) ERROR")
                    alert("post(/api/user/login) - Error - " + JSON.stringify(error));
                }
            )
            .then( () => {
                    navigate(from, {replace:true})
                }
            )
    };

    //logout user 
    const logoutUser = async () => {

        const requestOptions = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': Cookies.get('csrf_access_token')
            }
        };

        console.log("logoutUser - post(/api/user/logout)")
        return fetch("/api/user/logout", requestOptions)
            .then(res => res.json())
            .then( 
                async () => {
                    await setUserContext();
                },
                (error) => {
                    console.log("logoutUser - post(/api/user/logout) ERROR")
                    alert("post(/api/user/logout) - Error - " + JSON.stringify(error));
                }
            )
            .then( () => {
                navigate("/")
            }
        )
    };

    return {
        loginUser,
        logoutUser,
        error
    }
}
