package hometask;

import com.rabbitmq.client.*;

import java.util.Arrays;
import java.util.Random;

public class Delivery {
    public static String generateOrderId() {
        // create a string of uppercase and lowercase characters and numbers
        String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";

        // combine all strings
        String alphaNumeric = upperAlphabet + lowerAlphabet + numbers;

        // create random string builder
        StringBuilder sb = new StringBuilder();

        // create an object of Random class
        Random random = new Random();

        // specify length of random string
        int length = 10;

        for (int i = 0; i < length; i++) {

            // generate random index number
            int index = random.nextInt(alphaNumeric.length());

            // get character specified by index
            // from the string
            char randomChar = alphaNumeric.charAt(index);

            // append the character to string builder
            sb.append(randomChar);
        }
        return sb.toString();
    }

    public static void main(String[] argv) throws Exception {
        if (argv.length < 2) {
            throw new Error("Provide 2 args: deliveryId and available items.");
        }
        String deliveryId = argv[0];
        String[] availableItems = argv[1].split(",");
        System.out.println("DELIVERY. ID: " + deliveryId+". "+ Arrays.toString(availableItems));

        // connection & channel
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.basicQos(1);

        // exchange
        String ORDER_EXCHANGE_NAME = "order";
        String CONF_EXCHANGE_NAME = "conf";
        channel.exchangeDeclare(ORDER_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        channel.exchangeDeclare(CONF_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);


        // bind queue
        for (String queueName : availableItems) {
            channel.queueDeclare(queueName, false, false, false, null);
            channel.queueBind(queueName, ORDER_EXCHANGE_NAME, queueName);
        }

        channel.queueDeclare(deliveryId, false, false, false, null);
        channel.queueBind(deliveryId, ORDER_EXCHANGE_NAME, deliveryId);

        String[] keys = {"deliveries", "all"};
        for (String routingKey : keys) {
            channel.queueBind(deliveryId, CONF_EXCHANGE_NAME, routingKey);
        }

        DeliverCallback orderCallback = (consumerTag, delivery) -> {
            if (delivery.getProperties().getReplyTo() != null) {
                String serviceKey = delivery.getEnvelope().getRoutingKey();
                String genOrderId = generateOrderId();

                System.out.println("\r[" + java.time.LocalTime.now() + "] Team: " + delivery.getProperties().getReplyTo() + " placed order " + genOrderId + " for " + serviceKey + ".");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Sending conf to exchange
                String confMsg = "\r[" + java.time.LocalTime.now() + "] Order " + genOrderId + " completed, delivery: " + deliveryId + ", team: " + delivery.getProperties().getReplyTo() + ", item: " + serviceKey;
                AMQP.BasicProperties properties = new AMQP.BasicProperties(null, null, null, 2, null, genOrderId, null, null, null, null, null, null, null, null);

                channel.basicPublish(CONF_EXCHANGE_NAME, delivery.getProperties().getReplyTo(), properties, confMsg.getBytes());
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            } else {
                System.out.println("\r[" + java.time.LocalTime.now() + "] Received msg: " + new String(delivery.getBody()) + ".");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }

        };

        for (String queueName : availableItems) {
            channel.basicConsume(queueName, false, orderCallback, consumerTag -> {
            });
        }

        channel.basicConsume(deliveryId, false, orderCallback, consumeTag -> {
        });
    }


}
