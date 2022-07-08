import { useState, useEffect } from 'react';

export default function useFindUser() {
    const [user, setUser] = useState(null);
    const [isLoading, setLoading] = useState(true);

    useEffect(() =>{
        async function findUser() {
            const requestOptions = {
                headers: { 'Content-Type': 'application/json' },
                };

            console.log("findUser - get(/api/user)")
            await fetch("/api/user", requestOptions)
                .then(res => res.json())
                .then(
                    (data) => {
                        if (data.username !== '')
                        {
                            setUser(data.username);
                        }
                        setLoading(false);
                    },
                    (error) => {
                        alert("/api/user - err - " + JSON.stringify(error));
                        setLoading(false);
                    }
                )
        }
    
        findUser();
    }, []);
    
    return {
        user,
        setUser,
        isLoading
    }
}
