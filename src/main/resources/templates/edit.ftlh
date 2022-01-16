<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Редактирование заметки</title>
</head>
<body>

    <h4>Редактирование заметки ${note.name}</h4>
<br>
    <div id="text">
        <label for="text_note">Содержание: </label>
        <textarea id="text_note">${note.text}</textarea>
    </div>
<br>
    <div id="button">
        <button type="submit" onclick="edit()">Сохранить</button>
    </div>

    <script type="text/javascript" src="/js/add.js"></script>
</body>
</html>