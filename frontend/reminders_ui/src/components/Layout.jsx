import React from 'react';
import { Outlet } from "react-router-dom";
import './Layout.css';
import AppHeader from "./AppHeader";

export default function Layout() {
  return (
    <div className="App">
      <AppHeader/>
      <section>
        <main className="App-main">
        <Outlet />
        </main>
      </section>
    </div>
  );
}
