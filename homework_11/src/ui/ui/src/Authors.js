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

const Authors = () => {
  const [authors, setAuthors] = useState([]);

  useEffect(() => {
    fetch('api/authors')
      .then((response) => response.json())
      .then((json) => setAuthors(json));
  }, []);

  const deleteAuthor = (id) => {
      fetch(`/api/authors/${id}`, {
        method: "DELETE",
      })
        .then(() => {
          setAuthors((values) => {
            return values.filter((item) => item.id !== id);
          });
          AppToaster.show({
            message: "Author deleted successfully",
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
                    <th>Firstname</th>
                    <th>Lastname</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                {authors.map((author, i) => (
                    <tr key={i}>
                        <td>{author.id}</td>
                        <td>{author.firstname}</td>
                        <td>{author.lastname}</td>
                        <td>
                            <Button intent="primary" onClick={() => navigate(`/authors/${author.id}`)}>Update</Button>
                            &nbsp;
                            <Button intent="danger" onClick={() => deleteAuthor(author.id)}>
                                Delete
                            </Button>
                        </td>
                    </tr>
                ))}
            </tbody>
        </table>
        <Button intent="primary" onClick={() => navigate('/authors/new')}>Add</Button>
    </div>
  );
};

export default Authors;