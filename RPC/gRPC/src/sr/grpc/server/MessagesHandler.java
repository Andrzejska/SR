package sr.grpc.server;


import io.grpc.stub.StreamObserver;
import messages.Message;

import java.util.ArrayList;

public class MessagesHandler {
    private final ArrayList<StreamObserver<Message>> subscribersNews = new ArrayList<>();
    private final ArrayList<StreamObserver<Message>> subscribersWork = new ArrayList<>();

    public void addSubscriber(StreamObserver<Message> responseObserver, boolean isNews) {
        if (isNews) this.subscribersNews.add(responseObserver);
        else this.subscribersWork.add(responseObserver);
    }

    public void removeSubscriber(StreamObserver<Message> responseObserver, boolean isNews) {
        if (isNews) this.subscribersNews.remove(responseObserver);
        else this.subscribersWork.remove(responseObserver);
    }

    public void sendMessage(Message message, boolean isNews) {
        ArrayList<StreamObserver<Message>> subscribers = isNews ? this.subscribersNews : this.subscribersWork;

        for (StreamObserver<Message> observer : subscribers) {
            observer.onNext(message);
        }

        System.out.println("Message has been send.");
    }

    public void shutdown() {
        this.subscribersNews.forEach(StreamObserver::onCompleted);
        this.subscribersWork.forEach(StreamObserver::onCompleted);
    }
}
