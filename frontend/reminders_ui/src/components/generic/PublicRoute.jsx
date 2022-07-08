import React, { useContext } from 'react';
import { Navigate } from 'react-router-dom';
import { UserContext } from '../../hooks/UserContext'

function PublicRoute({ children, restricted }) {
  const { user } = useContext(UserContext); 

  return user && restricted ? <Navigate to="/"/> : children;
}

export default PublicRoute;