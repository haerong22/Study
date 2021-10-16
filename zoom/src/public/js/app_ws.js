const messageList = document.querySelector("ul");
const messageForm = document.querySelector("#message");
const nickForm = document.querySelector("#nick");

const socket = new WebSocket(`ws://${window.location.host}`);

const makeMessage = (type, payload) => {
    const msg = {type, payload}
    return JSON.stringify(msg);
}

socket.onopen = () => {
    console.log("Connected to Server");
}

socket.onmessage = (message) => {
    const li = document.createElement("li");
    li.innerText = message.data;
    messageList.append(li)
}

socket.onclose = () => {
    console.log("Disconnected")
}


messageForm.addEventListener("submit", (event) => {
    event.preventDefault();
    const input = messageForm.querySelector("input");
    socket.send(makeMessage("new_message", input.value));
    const li = document.createElement("li");
    li.innerText = `You: ${input.value}`;
    messageList.append(li)
    input.value = "";
})

nickForm.addEventListener("submit", (event) => {
    event.preventDefault()
    const input = nickForm.querySelector("input")
    socket.send(makeMessage("nickname", input.value));
    input.value = "";
})