package org.example.websocket;

import jakarta.websocket.DeploymentException;
import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.tyrus.server.Server;

import java.io.IOException;

public class MainServer {

    public static void main(String[] args) {
        HttpServer httpServer = HttpServer.createSimpleServer("/web", 8080);

        httpServer.getServerConfiguration()
                .addHttpHandler(new CLStaticHttpHandler(MainServer.class.getClassLoader(), "/"), "/");

        Server webSocketServer = new Server("localhost", 8025, "/", null, WebSocketServer.class);

        try {
            httpServer.start();
            webSocketServer.start();

            System.out.println("Server started");
            System.in.read();
        } catch (IOException | DeploymentException e) {
            throw new RuntimeException(e);
        } finally {
            httpServer.shutdownNow();
            webSocketServer.stop();
        }
    }
}
