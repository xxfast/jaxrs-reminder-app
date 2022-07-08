import React, { useState, useEffect, createContext }  from 'react';
import useApiResource from '../../hooks/useApiResource';

const ResourceContext = createContext()

export default function ResourceContainer(props) {
  const [resourceResponse, getResource] = useApiResource(props.url);
  const useTimeout = Boolean(props.timeout)
  const [resourceDirty, setResourceDirty] = useState(false);
  const value = {setResourceDirty}

  useEffect(() => {
    getResource();
  }, [resourceDirty]);
  
  useEffect(() => {

    if (useTimeout)
    {
      const timer = setTimeout(() => {
        getResource();
      }, props.timeout);

      return () => clearTimeout(timer);
    }
  });

  if (resourceResponse.error) {
      return <div>Error: {resourceResponse.error.message}</div>;
  } else if (!props.ignoreFetchingView && resourceResponse.isFetching) {
      return <div>Loading...</div>;
  } else if (resourceResponse.data) {
    const new_children = React.Children.map(props.children, child =>
                            React.cloneElement(child, { data: resourceResponse.data, loading: resourceResponse.isFetching })
                          );
    return (
      <ResourceContext.Provider value={value}>
        { new_children }
      </ResourceContext.Provider>
    );
  } else {
    return <div>No data</div>;
  }
};

export {ResourceContext};
