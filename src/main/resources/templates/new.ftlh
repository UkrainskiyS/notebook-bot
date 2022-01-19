<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.css">
    <script src="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.js"></script>
    <title>Создание заметка</title>
</head>
<body>

    <div>
        <div id="name">
            <label for="name_note">
                Название заметки:
                <input type="text" id="name_note" placeholder="Писать сюда">
            </label>
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

    <script>var simplemde = new SimpleMDE({
            blockStyles: {
                bold: "*",
                italic: "_"
            }});</script>
    <script type="text/javascript" src="/js/add.js"></script>
</body>
</html>