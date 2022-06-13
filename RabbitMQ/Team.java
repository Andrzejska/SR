package hometask;


import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Team {
    public static void main(String[] argv) throws Exception {
        if (argv.length < 1) {
            throw new Error("Provide at least one option");
        }
        String teamId = argv[0];
        System.out.println("TEAM. ID: " + teamId);

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


        channel.queueDeclare(teamId, true, false, false, null).getQueue();
        channel.queueBind(teamId, CONF_EXCHANGE_NAME, teamId);

        String[] keys = {"teams", "all"};
        for (String routingKey : keys) {
            channel.queueBind(teamId, CONF_EXCHANGE_NAME, routingKey);
        }


        DeliverCallback confCallback = (consumerTag, delivery) -> {
            if (delivery.getProperties().getCorrelationId() != null) {
                System.out.println(new String(delivery.getBody()));
            } else {
                System.out.println("\r[" + java.time.LocalTime.now() + "] Received msg: " + new String(delivery.getBody()) + ".");
            }
        };

        channel.basicConsume(teamId, true, confCallback, consumerTag -> {
        });

        while (true) {
            // read msg
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String itemToOrder = br.readLine();

            System.out.println("\r[" + java.time.LocalTime.now() + "] Team ID: " + teamId + ". Ordered item: " + itemToOrder);

            String orderMsg = "\r[" + java.time.LocalTime.now() + "] Team: " + teamId + " placed order for service " + itemToOrder;
            AMQP.BasicProperties properties = new AMQP.BasicProperties(null, null, null, 2, null, null, teamId, null, null, null, null, null, null, null);
            channel.basicPublish(ORDER_EXCHANGE_NAME, itemToOrder, properties, orderMsg.getBytes());
        }
    }
}
