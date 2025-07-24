package socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class _06_ChatServerSelector {

    public static void main(String[] args) {
        int listenPort = 20000;
        Selector selector;
        ServerSocketChannel serverChannel;

        try {
            selector = Selector.open();
            serverChannel = ServerSocketChannel.open();
            serverChannel.bind(new InetSocketAddress(listenPort));
            serverChannel.configureBlocking(false);
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("NIO Chat Server start... ");
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            while (true) {
                selector.select(); // 이벤트 대기

                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> it = keys.iterator();

                // accept
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    it.remove();

                    if (key.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel clientSocket = server.accept();
                        clientSocket.configureBlocking(false);
                        clientSocket.register(selector, SelectionKey.OP_READ);
                        System.out.println("New client connected...");
                        System.out.println("[" + clientSocket.getRemoteAddress() + "]");

                    } else if (key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        buffer.clear();
                        int bytesRead;

                        try {
                            bytesRead = client.read(buffer);
                        } catch (IOException e) {
                            System.out.println("Client terminated: " + client);
                            key.cancel();
                            client.close();
                            continue;
                        }

                        if (bytesRead == -1) {
                            System.out.println("Client disconnected: " + client);
                            key.cancel();
                            client.close();
                            continue;
                        }

                        buffer.flip();
                        String msg = new String(buffer.array(), 0, buffer.limit()).trim();
                        if (msg.equalsIgnoreCase("exit")) {
                            System.out.println("Client exit commend received: " + client);
                            key.cancel();
                            client.close();
                            continue;
                        }

                        System.out.println(msg);
                        sendMessageAll(selector, client, msg);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendMessageAll(Selector selector, SocketChannel sender, String msg) throws IOException {
        ByteBuffer msgBuffer = ByteBuffer.wrap((msg + "\n").getBytes());
        for (SelectionKey key : selector.keys()) {
            Channel channel = key.channel();
            if (channel instanceof SocketChannel) {
                SocketChannel target = (SocketChannel) channel;
                target.write(msgBuffer);
            }
        }
    }
}
