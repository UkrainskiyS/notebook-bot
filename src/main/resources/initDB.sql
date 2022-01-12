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

-- my chat_id = 1004338253

-- INSERT INTO chats (chat_id, mode) VALUES (1004338253, 'IGNORED');

INSERT INTO notes (chat_id, name, text, update_mod, date)
VALUES (1004338253, 'sites', 'id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,', 'NOT', current_date);

INSERT INTO notes (chat_id, name, text, update_mod, date)
VALUES (1004338253, 'kafka', 'id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,', 'NOT', current_date);

INSERT INTO notes (chat_id, name, text, update_mod, date)
VALUES (1004338253, 'java', 'id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,', 'NOT', current_date);
