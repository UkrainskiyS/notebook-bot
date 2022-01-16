async function add() {
    const urlSearchParams = new URLSearchParams(window.location.search);

    let object = {
        "chatId": parseInt(urlSearchParams.get('chat')),
        "name": document.getElementById("name_note").value,
        "text": document.getElementById("text_note").value
    };

    if (object.name.length > 150 || object.name.length < 1) {
        alert("Название должно быть 1-150 символов!")
    } else if (object.text.length > 4000 || object.text.length < 1) {
        alert("Содержание должно быть 1-4000 символов!")
    } else {
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
}

async function edit() {
    const urlSearchParams = new URLSearchParams(window.location.search);

    let object = {
        "id": urlSearchParams.get("id"),
        "text": document.getElementById("text_note").value
    };

    if (object.text.length > 4000 || object.text.length < 1) {
        alert("Содержание должно быть 1-4000 символов!")
    } else {
        let response = await fetch("/edit", {
            method: "PUT",
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(object)
        });

        if (response.ok) {
            alert("Заметка успешно изменена!");
        } else {
            alert("Упс... Что-то пошло не так!");
        }
    }
}
