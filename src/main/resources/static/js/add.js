async function add() {
    const searchString = new URLSearchParams(window.location.search);

    let object = {
        "chatId": parseInt(searchString.get('chat')),
        "name": document.getElementById("name_note").value,
        "text": document.getElementById("text_note").value
    };

    let response = await fetch('/new', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(object)
    });

    if (response.ok) {
        alert("Заметка успешно сохранена!");
    } else if (response.status === 400) {
        alert("Заметка с таким именем уже существует!");
    } else {
        alert("Упс... Что-то пошло не так!");
    }
}