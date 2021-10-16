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
    socket.on("enter_room", (roomName, done) => {
        console.log(roomName);
        setTimeout(() => {
            done("hello from the backend")            
        }, 10000);
    });
});

httpServer.listen(3000, handleListen);