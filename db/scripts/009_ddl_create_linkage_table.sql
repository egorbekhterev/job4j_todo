CREATE TABLE linkage
(
    id SERIAL PRIMARY KEY,
    task_id int NOT NULL REFERENCES tasks(id),
    category_id int NOT NULL REFERENCES categories(id)
);
