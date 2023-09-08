import React from 'react';
import Fragment from 'react';
import { useForm, useFieldArray } from "react-hook-form";
import { useState, useEffect } from "react";
import ReactDOM from 'react-dom';
import { Link, Routes, Route, useNavigate, useParams } from 'react-router-dom';
import { Button, Card, FormGroup, InputGroup } from "@blueprintjs/core";
import BookService from './service/BookService';
import FormInput from './components/FormInput';
import "./Book.css";

const NewBook = () => {

  const [book, setBook] = useState({
    id: 0,
    name: "",
    authors: [{
        id: "",
        firstname: "",
        lastname: ""
    }],
    genres: [{
        id: "",
        name: ""
    }]
  });
  const navigate = useNavigate();

  const updateBook = () => {
        BookService.update(book);
        navigate('/books');
    };

    const handleChange = (e) => {
      const { name, value } = e.target;
      setBook((prev) => ({
        ...prev,
        [name]: value,
      }));
    };

    const handleArrayChange = (e, property, index) => {
      const { name, value } = e.target;
      setBook(prevBook => {
          const newBook = {...prevBook};
          newBook[property][index][name] = value;
          return newBook;
      });
  }

    const addRow = (property) => {
      setBook(prevBook => {
          const newBook = {...prevBook};
          newBook[property].push({});
          return newBook;
      });
    };

    const deleteRow = (id, property) => {
      setBook(prevBook => {
          const newBook = {...prevBook};
          newBook[property].splice(id, 1);
          return newBook;
      });
    };

  return (
    <Card interactive={true}>
        <h3>Book Info</h3>
        <FormInput
            type="text"
            value={book.name}
            placeholder="Book Name"
            label="Name"
            name="name"
            onChange={handleChange}
        />
        <FormGroup label="Authors">
            {book.authors.map((author, index) => {
                return (
                <FormGroup>
                    <FormInput
                        type="text"
                        value={author.firstname}
                        placeholder="Author Firstname"
                        label="Firstname"
                        name={"firstname"}
                    onChange={(e) => handleArrayChange(e, "authors", index)}
                    />
                    <FormInput
                        type="text"
                        value={author.lastname}
                        placeholder="Author Lastname"
                        label="Lastname"
                        name={"lastname"}
                    onChange={(e) => handleArrayChange(e, "authors", index)}
                    />
                    <Button intent="primary" onClick={() => deleteRow(index, "authors")}>Delete</Button>
                </FormGroup>
                );
            })}
            <Button intent="primary" onClick={() => addRow("authors")}>Add</Button>
        </FormGroup>
        <Button intent="primary" onClick={() => updateBook()}>
            Save
        </Button>
        <Button intent="secondary" onClick={() => navigate('/books')}>
            Cancel
        </Button>
    </Card>
  );
};

export default NewBook;