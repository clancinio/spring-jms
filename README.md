## spring-jms application

*****

### Description 

This is a Spring application that utilizes JMS (Java Messaging Service) to send and receive messages to/from an ActiveMQ message broker.


### What is JMS?

JMS stands for Java Message Service, which is a Java-based messaging standard for sending messages between two or more clients. JMS provides a way for Java applications to communicate with each other by sending and receiving messages asynchronously.

### What is Queue? What is a Topic?

JMS supports two types of messaging: point-to-point (queue) and publish/subscribe (topic).

A queue is a messaging model in which messages are sent to and received by a specific client. A queue guarantees that each message is processed by only one consumer, ensuring that each message is processed in a first-in, first-out (FIFO) order. In a queue, messages are stored until a consumer is available to receive them. Once a message is delivered to a consumer, it is removed from the queue and no other consumer can receive it.

A topic is a messaging model in which messages are sent to multiple subscribers. In a topic, messages are published by a sender and delivered to all subscribers that are interested in receiving them. A topic allows for multiple consumers to receive the same message, ensuring that each message is processed by all subscribers. Topics are useful for broadcasting messages to multiple consumers, such as news updates or stock prices.

**In summary, the main differences between a queue and a topic are:**

- Queues provide point-to-point messaging while topics provide publish/subscribe messaging.
- In a queue, each message is consumed by only one consumer, while in a topic, each message is consumed by all interested subscribers.
- In a queue, messages are processed in a FIFO order, while in a topic, the order of message processing is not guaranteed.
- Queues are useful when messages need to be processed by only one consumer, while topics are useful for broadcasting messages to multiple consumers.

### DefaultJmsListenerContainerFactory
`DefaultJmsListenerContainerFactory` is a Spring Framework class that provides a default implementation for the JMS message listener container. It is used to create and configure JMS message listener containers for receiving messages from a JMS (Java Message Service) provider, such as ActiveMQ, IBM MQ, or Apache Kafka.

The `DefaultJmsListenerContainerFactory` class provides a convenient way to create message listener containers with sensible defaults for common use cases. It allows you to specify various settings for the message listener container, such as the concurrency level, transaction management, and error handling.

By default, `DefaultJmsListenerContainerFactory` creates a `SimpleJmsListenerContainer` object, which is a lightweight message listener container that uses a single thread to receive messages. However, you can customize the container by configuring the `DefaultJmsListenerContainerFactory` with various settings, such as the number of concurrent consumers, transaction management, message acknowledgement mode, and others.

To use `DefaultJmsListenerContainerFactory`, you need to define it as a bean in your Spring configuration file and specify the JMS connection factory to use for creating the message listener container. For example, the following code snippet shows how to define a `DefaultJmsListenerContainerFactory` bean in a Spring Boot application:

```java
@Configuration
public class JmsConfig {

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrency("3-10");
        factory.setErrorHandler(new DefaultErrorHandler());
        return factory;
    }
}
```

In this example, the `jmsListenerContainerFactory()` method creates a new `DefaultJmsListenerContainerFactory` object and sets the JMS connection factory to use for creating the message listener container. It also sets the concurrency level to 3-10, which means that between 3 and 10 concurrent consumers can receive messages from the JMS provider. Finally, it sets an error handler to handle any exceptions that occur during message processing.

Once you have defined the `DefaultJmsListenerContainerFactory` bean, you can use it to create message listener containers for receiving messages from a JMS provider. For example, you can use the @JmsListener annotation to define a method as a message listener and specify the containerFactory attribute to use the `DefaultJmsListenerContainerFactory` bean, as shown below:

```java
@Component
public class MyMessageListener {

    @JmsListener(destination = "myQueue", containerFactory = "jmsListenerContainerFactory")
    public void onMessage(Message message) {
        // Process the message
    }
}
```

In this example, the `onMessage()` method is annotated with `@JmsListener`, which tells Spring to create a message listener container for receiving messages from the myQueue JMS destination. The containerFactory attribute specifies the name of the `DefaultJmsListenerContainerFactory` bean to use for creating the message listener container.

### Docker Compose file - ActiveMQ

```properties
version: "3.9"
services:
  activemq:
    image: rmohr/activemq:5.16.0
    ports:
      - "61616:61616"
      - "8161:8161"
    environment:
      ACTIVEMQ_ADMIN_LOGIN: admin
      ACTIVEMQ_ADMIN_PASSWORD: admin
```

This Compose file defines a single service named activemq that uses the rmohr/activemq Docker image version 5.16.0. The service exposes two ports: 61616 for the ActiveMQ broker and 8161 for the web console.

You can run this Compose file using the docker-compose up command in the directory where the file is located.

Go to http://localhost:8161/ to access the console.