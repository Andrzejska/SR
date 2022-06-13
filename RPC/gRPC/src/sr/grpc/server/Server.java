package sr.grpc.server;

import io.grpc.ServerBuilder;
import messages.Message;
import messages.MessagesServiceGrpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Server extends MessagesServiceGrpc.MessagesServiceImplBase {
    private final int port = 50021;
    private io.grpc.Server gRPCServer;

    private final MessagesHandler messagesHandler = new MessagesHandler();

    public static void main(String[] args) throws IOException {
        Server server = new Server();

        // starting server
        server.gRPCServer = ServerBuilder.forPort(server.port)
                .addService(new MessagesService(server.messagesHandler))
                .build()
                .start();

        System.out.println("Started listening on port: " + server.port);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Use stderr here since the logger may have been reset by its JVM shutdown hook.
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            server.gRPCServer.shutdown();
            System.err.println("*** server shut down");
        }));

        // managing messages
        System.out.println("Waiting for messages...");

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String line = in.readLine();
            if (line.equalsIgnoreCase("exit")) break;


            String[] command = line.split(" ");
            Message message = Message.newBuilder().setMessage(command[1]).build();

            server.messagesHandler.sendMessage(message, command[0].equals("news"));
        }

        server.messagesHandler.shutdown();
    }
}
