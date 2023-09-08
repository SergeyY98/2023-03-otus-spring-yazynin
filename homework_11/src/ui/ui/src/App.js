import React from 'react';
import Fragment from 'react';
import Navbar from './components/Navbar';
import './App.css';
import Authors from './Authors';
import Author from './Author';
import NewAuthor from './NewAuthor';
import Books from './Books';
import Book from './Book';
import NewBook from './NewBook';
import Home from './Home';
import { HashRouter, Routes, Route, Link } from "react-router-dom";
import ReactDOM from 'react-dom/client';

const App = () => {
  return (
    <HashRouter>
        <header>
            <nav>
              <ul>
                <li><Link to='/'>Домой</Link></li>
                <li><Link to='/books'>Книги</Link></li>
              </ul>
            </nav>
        </header>
        <main>
         <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/books" element={<Books />} />
            <Route path="/books/:id" element={<Book />} />
            <Route path="/books/new" element={<NewBook />} />
         </Routes>
        </main>
    </HashRouter>
  );
};

export default App;