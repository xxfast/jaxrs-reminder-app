import React, { useContext } from 'react';
import { Navigate, useLocation } from 'react-router-dom';
import { UserContext } from '../../hooks/UserContext';

function PrivateRoute({ children }) {
    let location = useLocation();
    const { user, isLoading } = useContext(UserContext); 

    if(isLoading) {
      return( <div className="loading"><span className="pulse">Loading</span></div> )
    }


    return user ? children : <Navigate to="/login" state={{ from: location }} replace />;
  }

export default PrivateRoute;