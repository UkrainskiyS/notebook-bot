<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Новая заметка</title>
<#--    <link href="/css/new.css" rel="stylesheet">-->
</head>
<body>

    <div>
        <div id="name">
            <label for="name_note">Название: </label>
            <input id="name_note" type="text"/>
        </div>

        <br>

        <div id="text">
            <label for="text_note">Содержание: </label>
            <textarea id="text_note"></textarea>
        </div>
    </div>

    <br>

    <div id="button">
        <button type="submit" onclick="add()">Сохранить</button>
    </div>

<script type="text/javascript" src="/js/add.js"></script>
</body>
</html>