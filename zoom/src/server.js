import http from "http";
import SocketIo from "socket.io";
import express, { application } from "express";

const app = express();

app.set("view engine", "pug");
app.set("views", __dirname + "/views");

app.use("/public", express.static(__dirname + "/public"));

app.get("/", (req, res) => res.render("home"));
app.get("/*", (req, res) => res.redirect("/"));
const handleListen = () => console.log(`Listening on http://localhost:3000`);

const httpServer = http.createServer(app);
const wsServer = SocketIo(httpServer);

wsServer.on("connection", socket => {
    socket['nickname'] = "Anonymous";
    socket.onAny((event) => {
        console.log(`Socket Event: ${event}`)
    });
    socket.on("enter_room", (roomName, done) => {
        socket.join(roomName);
        done();
        socket.to(roomName).emit("welcome", socket.nickname);
    });
    socket.on("disconnecting", () => {
        socket.rooms.forEach((room) => {
            socket.to(room).emit("bye", socket.nickname);
        });
    })
    socket.on("message", (msg, room, done) => {
        socket.to(room).emit("message", `${socket.nickname}: ${msg}`);
        done();
    })
    socket.on("nickname", (nickname) => {
        socket["nickname"] = nickname;
    })
});

httpServer.listen(3000, handleListen);