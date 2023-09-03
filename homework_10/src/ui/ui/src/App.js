import React from 'react';
import Fragment from 'react';
import { useState, useEffect } from "react";
import ReactDOM from 'react-dom';
import { Link, Routes, Route, useNavigate } from 'react-router-dom';
import { Button, Toaster, Position } from "@blueprintjs/core";
import "./App.css";

const AppToaster = Toaster.create({
  position: Position.TOP,
})

const App = () => {
  const [books, setBooks] = useState([]);

  useEffect(() => {
    fetch('api/books')
      .then((response) => response.json())
      .then((json) => setBooks(json));
  }, []);

  const deleteUser = (id) => {
      fetch(`/api/books/${id}`, {
        method: "DELETE",
      })
        .then(() => {
          setBooks((values) => {
            return values.filter((item) => item.id !== id);
          });
          AppToaster.show({
            message: "Book deleted successfully",
            intent: "success",
            timeout: 3000,
          });
        });
    };

    const navigate = useNavigate();

  return (
    <div className="App">
        <table class="bp4-html-table .modifier">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Authors</th>
                    <th>Genres</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                {books.map((book, i) => (
                    <tr key={i}>
                        <td>{book.id}</td>
                        <td>{book.name}</td>
                        <td><ul>{book.authors.map((author, index) => {
                            return (
                                <li key={index}>{author.firstname} {author.lastname}</li>
                            );
                        })}</ul></td>
                        <td><ul>{book.genres.map((genre, index) => {
                            return (
                                <li key={index}>{genre.name}</li>
                            );
                        })}</ul></td>
                        <td>
                            <Button intent="primary" onClick={() => navigate(`/${book.id}`)}>Update</Button>
                            &nbsp;
                            <Button intent="danger" onClick={() => deleteUser(book.id)}>
                                Delete
                            </Button>
                        </td>
                    </tr>
                ))}
            </tbody>
        </table>
        <Button intent="primary" onClick={() => navigate('/new')}>Add</Button>
    </div>
  );
};

export default App;