import http from "http";
import WebSocket from "ws";
import express, { application } from "express";

const app = express();

app.set("view engine", "pug");
app.set("views", __dirname + "/views");

app.use("/public", express.static(__dirname + "/public"));

app.get("/", (req, res) => res.render("home"));
app.get("/*", (req, res) => res.redirect("/"));
const handleListen = () => console.log(`Listening on http://localhost:3000`);

const server = http.createServer(app);
const wss = new WebSocket.Server({server});

const sockets = [];

wss.on("connection", (socket) => {
    sockets.push(socket)
    console.log("Connected to Browser");
    socket.onclose = () => {
        console.log("Disconnect from the Browser")
    }
    socket.onmessage = (message) => {
        sockets.forEach(aSocket => aSocket.send(message.data))
    }
    socket.send("Hello!!");
});

server.listen(3000, handleListen);