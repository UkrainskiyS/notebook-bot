DROP TABLE IF EXISTS notes;
DROP TABLE IF EXISTS chats;

CREATE TABLE notes (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    chat_id INTEGER NOT NULL,
    name VARCHAR(100) NOT NULL,
    text VARCHAR(255) NOT NULL
);

--CREATE TABLE chats (
--    id INTEGER PRIMARY KEY AUTO_INCREMENT,
--    chat_id INTEGER NOT NULL,
--    mode VARCHAR(20)
--);

INSERT INTO notes (chat_id, name, text)
VALUES(1004338253, 'test', 'bot:\nname: notebookmanager_bot\ntoken: 5048393640:AAGA0cYs7DKEm92a6v_QcTDUEFVkIX0p87Q');