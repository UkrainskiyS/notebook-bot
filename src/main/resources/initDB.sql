DROP TABLE IF EXISTS notes;
DROP TABLE IF EXISTS chats;

CREATE TABLE notes (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    chat_id INTEGER NOT NULL,
    name VARCHAR(100) NOT NULL,
    date timestamp,
    text TEXT,
    update_mod VARCHAR(20) NOT NULL
);

CREATE TABLE chats (
   id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
   chat_id INTEGER NOT NULL,
   mode VARCHAR(30)
);