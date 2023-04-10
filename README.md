# Spring Boot Application with JMS and ActiveMQ Docker Compose

This is a sample Spring Boot application that uses JMS (Java Messaging Service) with ActiveMQ message broker. It also includes a Docker Compose file that sets up an ActiveMQ broker instance as a Docker container.

## Requirements

- Docker
- JDK 8 or later
- Gradle

## Running the Application

To run the application, follow these steps:

1. Clone the repository: `git clone https://github.com/clancinio/spring-jms.git`
2. Build the application: `mvn clean install`
3. Start the ActiveMQ broker using Docker Compose: `docker-compose up`
4. Run the application

The application should start up and connect to the ActiveMQ broker. You can then send messages to the application using a JMS client.

## Docker Compose

The `docker-compose.yml` file in the project directory sets up an ActiveMQ instance as a Docker container. The container is configured to expose the default ActiveMQ ports (61616 and 8161).

To view the ActiveMQ console, visit: http://localhost:8161/

## Sending a message to the queue 

Use a HTTP client such as Postman to make a POST by calling the REST API with the URL:

```
http://localhost:8088/process/order
```
And a request body. Example:

```
{
    "bookOrderId": "101",
    "bookId": "201",
    "customerId": "301"
}
```

Check out the application logs once the message has been send.

*****


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

```dockerfile
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

### MessageConverter

MessageConverter is an interface in Spring Framework that provides a way to convert between Java objects and JMS messages. It is used to serialize and deserialize Java objects into JMS messages and vice versa, allowing Java applications to send and receive complex objects over a JMS provider.

MessageConverter defines two main methods: toMessage() and fromMessage(). The toMessage() method takes a Java object and converts it into a JMS message, while the fromMessage() method takes a JMS message and converts it into a Java object.

Spring provides several implementations of the MessageConverter interface, including:

- SimpleMessageConverter: A simple message converter that supports conversion between JMS TextMessages and Java objects using serialization.
- MappingJackson2MessageConverter: A message converter that uses the Jackson library to convert Java objects to and from JSON format.
- MarshallingMessageConverter: A message converter that uses Spring's Marshaller and Unmarshaller interfaces to convert Java objects to and from XML format.
- BytesMessageConverter: A message converter that converts Java objects to and from JMS BytesMessages.
MessageConverter is often used in combination with Spring's JmsTemplate and JmsListenerContainerFactory to simplify the sending and receiving of messages over JMS. By using a message converter, you can send and receive complex Java objects over JMS without having to deal with low-level JMS APIs.