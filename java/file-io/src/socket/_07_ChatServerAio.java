package socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class _07_ChatServerAio {
    private static final Set<AsynchronousSocketChannel> clients = ConcurrentHashMap.newKeySet();

    public static void main(String[] args) throws IOException {
        int port = 20000;

        AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open()
                .bind(new InetSocketAddress(port));
        System.out.println("AIO Chat Server start...");

        server.accept(
                null,
                new CompletionHandler<AsynchronousSocketChannel, Void>() {
                    @Override
                    public void completed(AsynchronousSocketChannel client, Void attachment) {
                        try {
                            System.out.println("New client connected: " + client.getRemoteAddress());
                        } catch (IOException e) {
                            System.out.println("Failed to get client address");
                        }

                        server.accept(null, this); // 재등록
                        clients.add(client);

                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        readMessage(client, buffer);
                    }

                    @Override
                    public void failed(Throwable exc, Void attachment) {
                        System.out.println("Failed to accept: " + exc.getMessage());
                    }
                }
        );

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            System.out.println("Server interrupted.");
        }
    }

    private static void readMessage(AsynchronousSocketChannel client, ByteBuffer buffer) {
        client.read(
                buffer,
                null,
                new CompletionHandler<Integer, ByteBuffer>() {
                    @Override
                    public void completed(Integer result, ByteBuffer attachment) {
                        if (result == -1) {
                            disconnectClient(client);
                            return;
                        }

                        buffer.flip();
                        String msg = new String(buffer.array(), 0, buffer.limit()).trim();
                        buffer.clear();

                        try {
                            System.out.println("Received: " + msg + " from " + client.getRemoteAddress());
                        } catch (IOException e) {
                            System.out.println("Unknown client message.");
                        }

                        if (msg.equalsIgnoreCase("exit")) {
                            disconnectClient(client);
                            return;
                        }

                        sendMessageAll(client, msg);
                        readMessage(client, buffer);
                    }


                    @Override
                    public void failed(Throwable exc, ByteBuffer attachment) {
                        System.out.println("Read failed: " + exc.getMessage());
                        disconnectClient(client);
                    }
                }
        );
    }

    private static void sendMessageAll(AsynchronousSocketChannel sender, String msg) {
        for (AsynchronousSocketChannel client : clients) {
            if (!client.equals(sender) && client.isOpen()) {
                ByteBuffer buffer = ByteBuffer.wrap((msg + "\n").getBytes());
                client.write(buffer);
            }
        }
    }

    private static void disconnectClient(AsynchronousSocketChannel client) {
        try {
            System.out.println("Client disconnected: " + client.getRemoteAddress());
        } catch (IOException e) {
            System.out.println("Unknown client disconnected.");
        }
        clients.remove(client);
        try {
            client.close();
        } catch (IOException e) {
            System.out.println("Error closing client channel.");
        }
    }
}
