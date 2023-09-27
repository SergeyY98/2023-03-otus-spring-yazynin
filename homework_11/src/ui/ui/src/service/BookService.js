import React, { useState } from 'react';
import { useParams } from 'react-router-dom';

const BookService = {
    get: (id) =>
        fetch(`api/books/${id}`)
            .then((response) => response.json()),
    getAll: () =>
        fetch('api/books')
          .then((response) => response.json()),
    save: (book) =>
        fetch(`/api/books`, {
            method: "POST",
            body: JSON.stringify(book),
            headers: {
                "Content-type": "application/json; charset=UTF-8",
            }
        }),
    update: (book) =>
        fetch(`/api/books`, {
            method: "PUT",
            body: JSON.stringify(book),
            headers: {
                "Content-type": "application/json; charset=UTF-8",
            }
        }),
    deleteBook: (id) =>
        fetch(`/api/books/${id}`, {
            method: "DELETE",
        })
}

export default BookService;