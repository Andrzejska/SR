package sr.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import messages.ListenRequest;
import messages.MessagesServiceGrpc;
import messages.Type;

import java.io.BufferedReader;
import java.util.concurrent.TimeUnit;

public class Client {
    private final ManagedChannel channel;
    private final MessagesServiceGrpc.MessagesServiceStub nonBlockingStub;

    public Client(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();

        nonBlockingStub = MessagesServiceGrpc.newStub(channel);
    }

    private static final StreamObserver streamObserver = new StreamObserver() {
        @Override
        public void onNext(Object o) {
            System.out.println(o.toString());
        }

        @Override
        public void onError(Throwable throwable) {
            if (throwable instanceof StatusRuntimeException) {
                System.out.println(throwable.getMessage());
                return;
            }
            System.out.println("There has been an error: ");
            throwable.printStackTrace();
        }

        @Override
        public void onCompleted() {
        }

    };

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws Exception {
        Client client = new Client("localhost", 50021);
        try {
            System.out.println("Subscribing to the messages.");

            Type listenedMessagesType = args[0].equals("news") ? Type.News : Type.Work;
            ListenRequest request = ListenRequest.newBuilder().setType(listenedMessagesType).build();

            client.nonBlockingStub.listenToMessages(request, streamObserver);

            BufferedReader in = new BufferedReader(new java.io.InputStreamReader(System.in));
            String line;
            do {
                line = in.readLine();
            } while (!line.equals("exit"));

        } finally {
            client.shutdown();
        }
    }
}
