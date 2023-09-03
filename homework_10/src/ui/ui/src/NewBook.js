import React from 'react';
import Fragment from 'react';
import { useForm, useFieldArray } from "react-hook-form";
import { useState, useEffect } from "react";
import ReactDOM from 'react-dom';
import { Link, Routes, Route, useNavigate, useParams } from 'react-router-dom';
import { Button, Card, FormGroup, InputGroup } from "@blueprintjs/core";
import { useFormik } from "formik";

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

  const saveBook = () => {
    console.log(JSON.stringify(book));
      fetch(`/api/books`, {
        method: "POST",
        body: JSON.stringify(book),
        headers: {
          "Content-type": "application/json; charset=UTF-8",
        },
      });
      navigate('/');
  };

  const addAuthor = () => {
    setBook(prevBook => {
        const newBook = {...prevBook};
        newBook.authors.push({
            id: 0,
            firstname: "",
            lastname: ""
        });
        return newBook;
    });
  };

  const addGenre = () => {
    setBook(prevBook => {
        const newBook = {...prevBook};
        newBook.genres.push({
            id: 0,
            name: ""
        });
        return newBook;
    });
  };

  const deleteAuthor = (id) => {
    setBook(prevBook => {
        const newBook = {...prevBook};
        newBook.authors.splice(id, 1);
        console.log(newBook);
        return newBook;
    });
  }

  const deleteGenre = (id) => {
    setBook(prevBook => {
        const newBook = {...prevBook};
        newBook.genres.splice(id, 1);
        console.log(newBook);
        return newBook;
    });
  };

  return (
    <Card interactive={true}>
        <h3>Book Info</h3>
        <FormGroup label="Book Title" labelFor="title-input">
            <InputGroup id="title-input" type="text" name="name" placeholder="title"
            onChange={(event) => {setBook(prevBook => {
                const newBook = {...prevBook};
                newBook.name = event.target.value;
                return newBook;
            });}} />
        </FormGroup>
        <FormGroup label="Authors">
            {book.authors.map((author, index) => {
                return (
                <FormGroup>
                    <InputGroup id="author-firstname" placeholder="firstname"
                    onChange={(event) => {setBook(prevBook => {
                                    const newBook = {...prevBook};
                                    newBook.authors[index].firstname = event.target.value;
                                    return newBook;
                                });}} />
                    <InputGroup id="author-lastname" placeholder="lastname"
                    onChange={(event) => {setBook(prevBook => {
                                    const newBook = {...prevBook};
                                    newBook.authors[index].lastname = event.target.value;
                                    return newBook;
                                });}} />
                    <Button intent="primary" onClick={() => deleteAuthor(index)}>Delete</Button>
                </FormGroup>
                );
            })}
            <Button intent="primary" onClick={() => addAuthor()}>Add</Button>
        </FormGroup>
        <FormGroup label="Genres">
            {book.genres.map((genre, index) => {
                return (
                <FormGroup>
                    <InputGroup id="author-name" placeholder="name" defaultValue={genre.name}
                    onChange={(event) => {setBook(prevBook => {
                        const newBook = {...prevBook};
                        newBook.genres[index].name = event.target.value;
                        return newBook;
                    });}} />
                    <Button intent="primary" onClick={() => deleteGenre(index)}>Delete</Button>
                </FormGroup>
                );
            })}
            <Button intent="primary" onClick={() => addGenre()}>Add</Button>
        </FormGroup>
        <Button intent="primary" onClick={() => saveBook()}>
            Save
        </Button>
        <Button intent="secondary" onClick={() => navigate('/')}>
            Cancel
        </Button>
    </Card>
  );
};

export default NewBook;