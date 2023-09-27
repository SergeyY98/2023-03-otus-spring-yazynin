import React from 'react';
import Fragment from 'react';
import { useForm, useFieldArray } from "react-hook-form";
import { useState, useEffect } from "react";
import ReactDOM from 'react-dom';
import { Link, Routes, Route, useNavigate, useParams } from 'react-router-dom';
import { Button, Card, FormGroup, InputGroup } from "@blueprintjs/core";

const NewAuthor = () => {

  const { id } = useParams();

  const [author, setAuthor] = useState({
    id: "",
    firstname: "",
    lastname: ""
  });
  const navigate = useNavigate();

  useEffect(() => {
    fetch(`api/authors/${id}`)
      .then((response) => response.json())
      .then((json) => setAuthor(json));
  }, []);

  const updateAuthor = () => {
    console.log(JSON.stringify(author));
      fetch(`/api/authors`, {
        method: "POST",
        body: JSON.stringify(author),
        headers: {
          "Content-type": "application/json; charset=UTF-8",
        },
      });
      navigate('/authors');
  };

  return (
    <Card interactive={true}>
        <h3>Author Info</h3>
        <FormGroup label="Firstname" labelFor="firstname-input">
            <InputGroup id="author-firstname" placeholder="firstname"
            onChange={(event) => {setAuthor(prevAuthor => {
                const newAuthor = {...prevAuthor};
                author.firstname = event.target.value;
                return newAuthor;
            });}} />
        </FormGroup>
        <FormGroup label="Lastname" labelFor="lastname-input">
            <InputGroup id="author-lastname" placeholder="lastname"
            onChange={(event) => {setAuthor(prevAuthor => {
                const newAuthor = {...prevAuthor};
                author.lastname = event.target.value;
                return newAuthor;
            });}} />
        </FormGroup>
        <Button intent="primary" onClick={() => updateAuthor()}>
            Save
        </Button>
        <Button intent="secondary" onClick={() => navigate('/authors')}>
            Cancel
        </Button>
    </Card>
  );
};

export default NewAuthor;