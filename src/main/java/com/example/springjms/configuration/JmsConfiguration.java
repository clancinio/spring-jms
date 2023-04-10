package com.example.springjms.configuration;

import com.example.springjms.listener.BookOrderProcessingMessageListener;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListenerConfigurer;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerEndpointRegistrar;
import org.springframework.jms.config.SimpleJmsListenerEndpoint;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@EnableJms
@Configuration
public class JmsConfiguration implements JmsListenerConfigurer {

  @Value("${activemq.broker-url}")
  private String brokerUrl;

  @Value("${activemq.user}")
  private String user;

  @Value("${activemq.user}")
  private String password;

  @Value("${jms.template.default-destination-type}")
  private String defaultDestinationName;

  @Value("${jms.listener.concurrency}")
  private String concurrency;

  @Bean
  public MessageConverter messageConverter() {
    ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    converter.setTargetType(MessageType.TEXT);
    converter.setTypeIdPropertyName("_type");
    converter.setObjectMapper(mapper);
    return converter;
  }

  @Bean
  public JmsListenerContainerFactory jmsListenerContainerFactory(
      ConnectionFactory connectionFactory,
      DefaultJmsListenerContainerFactoryConfigurer configurer) {

    DefaultJmsListenerContainerFactory containerFactory = new DefaultJmsListenerContainerFactory();
    configurer.configure(containerFactory, connectionFactory);
    return containerFactory;
  }

  @Bean
  public ConnectionFactory connectionFactory() {
    ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
    connectionFactory.setBrokerURL(brokerUrl);
    connectionFactory.setUserName(user);
    connectionFactory.setPassword(password);
    return connectionFactory;
  }

//  @Bean
//  public JmsTemplate jmsTemplate() {
//    JmsTemplate jmsTemplate = new JmsTemplate();
//    jmsTemplate.setConnectionFactory(connectionFactory());
//    jmsTemplate.setDefaultDestinationName(defaultDestinationName);
//    return jmsTemplate;
//  }

  @Bean
  DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory());
    factory.setMessageConverter(messageConverter());
    factory.setConcurrency(concurrency);
    return factory;
  }

  @Bean
  public BookOrderProcessingMessageListener jmsMessageListener() {
    return new BookOrderProcessingMessageListener();
  }

  @Override
  public void configureJmsListeners(JmsListenerEndpointRegistrar registrar) {
    SimpleJmsListenerEndpoint endpoint = new SimpleJmsListenerEndpoint();
    endpoint.setMessageListener(jmsMessageListener());
    endpoint.setDestination("book.order.processed.queue");
    endpoint.setId("book-order-processed-queue");
    endpoint.setConcurrency("1");
    endpoint.setSubscription("my-subscription");
    registrar.registerEndpoint(endpoint, jmsListenerContainerFactory());
    registrar.setContainerFactory(jmsListenerContainerFactory());
  }

}
