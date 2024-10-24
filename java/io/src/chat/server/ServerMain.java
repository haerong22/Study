package chat.server;

import java.io.IOException;

public class ServerMain {

    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        SessionManager sessionManager = new SessionManager();

//        CommandManager commandManager = new CommandManagerV1(sessionManager);
//        CommandManager commandManager = new CommandManagerV2(sessionManager);
        CommandManager commandManager = new CommandManagerV3(sessionManager);

        Server server = new Server(PORT, commandManager, sessionManager);
        server.start();
    }

}
