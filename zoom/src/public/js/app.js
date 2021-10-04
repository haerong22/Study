const messageList = document.querySelector("ul");
const messageForm = document.querySelector("form");

const socket = new WebSocket(`ws://${window.location.host}`);

socket.onopen = () => {
    console.log("Connected to Server");
}

socket.onmessage = (message) => {
    console.log("Just got this: ", message.data, " from the server");
}

socket.onclose = () => {
    console.log("Disconnected")
}


messageForm.addEventListener("submit", (event) => {
    event.preventDefault();
    const input = messageForm.querySelector("input");
    socket.send(input.value);
    input.value = "";
})