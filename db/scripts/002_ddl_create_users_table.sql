CREATE TABLE users (
   id SERIAL PRIMARY KEY,
   name TEXT NOT NULL,
   login TEXT NOT NULL unique,
   password TEXT NOT NULL
);
