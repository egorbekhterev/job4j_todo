ALTER TABLE tasks ADD COLUMN user_id int NOT NULL REFERENCES users(id);
