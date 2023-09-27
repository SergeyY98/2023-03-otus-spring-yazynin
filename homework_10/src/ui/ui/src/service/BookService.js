import React, { useState } from 'react';
import axios from 'axios';

const BookService = {
    get: async (id) =>
        await axios.get(`api/books/${id}`),
    getAll: async () =>
        await axios.get('api/books'),
    update: async (book) =>
        await axios.post(`/api/books`, JSON.stringify(book), {
            headers: {
                "Content-type": "application/json; charset=UTF-8",
            }
        }),
    deleteBook: async (id) =>
        await axios.delete(`/api/books/${id}`)
}

export default BookService;