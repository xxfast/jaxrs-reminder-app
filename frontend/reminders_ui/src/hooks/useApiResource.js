import { useState } from 'react';

export default function useApiResource(url) {
  const [response, setResponse] = useState({
      data: null,
      isFetching: false,
      error: null
    });


  const getResource = () => {
    setResponse({ ...response, isFetching: true});

    console.log("get(" + url + ")")
    fetch(url)
      .then(res => res.json())
      .then(
        (data) => {
          setResponse({ ...response, data, isFetching: false })
        },
        (error) => {
          setResponse({ ...response, isFetching: false, error })
        }
      )
      .catch(err => {
        setResponse({ ...response, isFetching: false, error: err.message })
      })
      
  };
    
  return [response, getResource];
}