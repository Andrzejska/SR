package sr.grpc.server;

import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;
import messages.ListenRequest;
import messages.Message;
import messages.MessagesServiceGrpc;
import messages.Type;

public class MessagesService extends MessagesServiceGrpc.MessagesServiceImplBase {
    private MessagesHandler messagesHandler;

    public MessagesService(MessagesHandler messagesHandler) {
        this.messagesHandler = messagesHandler;
    }

    @Override
    public void listenToMessages(ListenRequest request, StreamObserver<Message> responseObserver) {
        System.out.println("Client connected.");

        ServerCallStreamObserver serverCallStreamObserver = (ServerCallStreamObserver) responseObserver;

        serverCallStreamObserver.setOnCancelHandler(() -> {
            System.out.println("Client disconnected.");
            this.messagesHandler.removeSubscriber(responseObserver, request.getType().equals(Type.News));
        });

        this.messagesHandler.addSubscriber(responseObserver, request.getType().equals(Type.News));
    }
}
