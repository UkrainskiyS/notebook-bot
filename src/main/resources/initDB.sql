DROP TABLE IF EXISTS notes;
DROP TABLE IF EXISTS chats;

CREATE TABLE notes (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    chat_id INTEGER NOT NULL,
    name VARCHAR(100) NOT NULL,
    date timestamp,
    text TEXT,
    uuid VARCHAR(60)
);

CREATE TABLE chats (
   id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
   chat_id INTEGER NOT NULL,
   uuid VARCHAR(50),
   mode VARCHAR(30)
);

-- insert into chats (chat_id, mode, uuid) values (1004338253, 'IGNORED', '9275c396-e645-45c1-be0e-8ca4b456d3c7');
--
-- insert into notes (chat_id, name, date, uuid, text)
-- values (1004338253,
--         'bot',
--         current_date,
--         '1004338253-9275c396-e645-45c1-be0e-8ca4b456d3c7',
--         '## Клонировать репозиторий
-- `https://git.heroku.com/notebook-bot.git`
--
-- ## Внести изменения
--
-- ## Пушить:
-- >  `git add .`
-- >  `git commit -m "..."`
-- >  `git push heroku master`
-- >  `heroku ps:scale worker=1`
--
-- ## Логи смотреть по адресу https://dashboard.heroku.com/apps/notebook-bot/logs'
-- );