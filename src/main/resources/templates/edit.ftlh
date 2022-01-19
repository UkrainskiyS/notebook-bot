<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.css">
    <script src="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.js"></script>
    <title>Редактирование заметки</title>
</head>
<body>

    <h3>Редактирование заметки <i>${note.name}</i></h3>
<br>
    <div id="text">
        <label for="text_note">Содержание: </label>
        <textarea id="text_note">${note.text}</textarea>
    </div>
<br>
    <div id="button">
        <button type="submit" onclick="edit()">Сохранить</button>
    </div>

    <script>var simplemde = new SimpleMDE({
            blockStyles: {
                bold: "*",
                italic: "_"
            }});</script>
    <script type="text/javascript" src="/js/add.js"></script>
</body>
</html>