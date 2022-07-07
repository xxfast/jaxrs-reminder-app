import React from 'react';
import { Outlet } from "react-router-dom";
import './Layout.css';
import AppHeader from "./AppHeader";

export default function Layout() {
  return (
    <div className="App">
      <AppHeader/>
      <section className="App-main">
        <main>
        <Outlet />
        </main>
      </section>
      <footer className="App-footer">
        (c) 2022
      </footer>
    </div>
  );
}
