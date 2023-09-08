import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import Book from './Book';
import BookService from './service/BookService';
import NewBook from './NewBook';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <Router>
      <Routes>
        <Route path="/" element={<App />} key={document.location.href} />
        <Route path="/:id" element={<Book />} />
        <Route path="/new" element={<NewBook />} />
      </Routes>
    </Router>
  </React.StrictMode>
);
