package hometask;

import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Admin {
    public static void main(String[] argv) throws Exception {
        System.out.println("ADMIN");

        // connection & channel
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // exchange
        String ORDER_EXCHANGE_NAME = "order";
        String CONF_EXCHANGE_NAME = "conf";
        channel.exchangeDeclare(ORDER_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        channel.exchangeDeclare(CONF_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        String adminQueue = channel.queueDeclare().getQueue();
        channel.queueBind(adminQueue, ORDER_EXCHANGE_NAME, "#");
        channel.queueBind(adminQueue, CONF_EXCHANGE_NAME, "#");

        DeliverCallback adminCallback = (consumerTag, delivery) -> {
            System.out.println("Received msg: " + new String(delivery.getBody()));
        };

        channel.basicConsume(adminQueue, false, adminCallback, consumerTag -> {
        });
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            // read msg
            System.out.println("Please provide option: teams, deliveries, all");
            String option = br.readLine();
            System.out.println("Please provide msg.");
            String msg = br.readLine();
            channel.basicPublish(CONF_EXCHANGE_NAME, option, null, msg.getBytes());
        }
    }
}
