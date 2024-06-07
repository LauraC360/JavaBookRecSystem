import React, { useEffect, useState } from "react";
import Cards from "../Cards/Cards";

function PrepCards({ books }) {

  return (
    <div className="cards">
      {books.map((book) => (
        <Cards key={book.id} book={book} />
      ))}
    </div>
  );
}

export default PrepCards;
 