package com.frank.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@Configuration
public class RabbitMqConfig implements RabbitListenerConfigurer {

    @Value("${my.rabbitmq.queue}")
    private String queueName;
    @Value("${my.rabbitmq.secondQueue}")
    private String secondQueue;
    @Value("${my.rabbitmq.exchange}")
    private String myExchange;
    @Value("${my.rabbitmq.connection.host}")
    private String host;
    @Value("${my.rabbitmq.connection.port}")
    private Integer port;
    @Value("${my.rabbitmq.connection.user}")
    private String user;
    @Value("${my.rabbitmq.connection.password}")
    private String password;
    @Value("${my.rabbitmq.connection.virtual-host}")
    private String virtualHost;


    @Bean(name = "connectionFactory")
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setChannelCacheSize(1024);
        connectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CONNECTION);
        connectionFactory.setChannelCacheSize(180 * 1000);
        connectionFactory.setConnectionCacheSize(1024);
        connectionFactory.setUsername(user);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setPublisherReturns(false);
        connectionFactory.setPublisherConfirms(false);
        return connectionFactory;
    }

    @Bean(name = "myAmqpAdmin")
    public AmqpAdmin amqpAdmin(@Qualifier("connectionFactory") ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }


    @Bean(name = "myExchange")
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(myExchange);
    }

    @Bean(name = "myQueue")
    public Queue myQueue() {
        return new Queue(queueName);
    }

    @Bean(name = "secondQueue")
    public Queue secondQueue() {
        return new Queue(secondQueue);
    }

    @Bean(name = "myBinding")
    public Binding binding(@Qualifier("myAmqpAdmin") AmqpAdmin amqpAdmin, @Qualifier("myQueue") Queue myQueue, @Qualifier("myExchange") FanoutExchange fanoutExchange) {
        Binding binding = BindingBuilder.bind(myQueue).to(fanoutExchange);
        //  amqpAdmin.declareBinding(binding);
        return binding;
    }

    @Bean(name = "myBinding2")
    public Binding binding2(@Qualifier("myAmqpAdmin") AmqpAdmin amqpAdmin, @Qualifier("secondQueue") Queue secondQueue, @Qualifier("myExchange") FanoutExchange fanoutExchange) {
        Binding binding = BindingBuilder.bind(secondQueue).to(fanoutExchange);
        //  amqpAdmin.declareBinding(binding);
        return binding;
    }

    @Bean(name = "rabbitTemplate")
    public RabbitTemplate notifyTemplate(@Qualifier("connectionFactory") ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setExchange(myExchange);
        //template.setBeforePublishPostProcessors(mqBeforeInterceptor());
        //template.setAfterReceivePostProcessors(mqAfterInterceptor());
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(@Qualifier("connectionFactory")ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        return factory;
    }

    @Bean
    public DefaultMessageHandlerMethodFactory myHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(new MappingJackson2MessageConverter());
        return factory;
    }

//    @Bean
//    SimpleMessageListenerContainer container(@Qualifier("connectionFactory") ConnectionFactory connectionFactory,
//                                             @Qualifier("listenerAdapter") MessageListenerAdapter listenerAdapter
//    ) {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.setQueueNames(queueName, secondQueue);
//        container.setMessageListener(listenerAdapter);
//        /**
//         * 会把上一个覆盖
//         * 通过方法就能看出来是单数
//         */
//        // container.setMessageListener(l2);
//        return container;
//    }

    /**
     * 自定义的监听类，当收到消息的时候，自动消费
     *
     * @param receiver
     * @return
     */
//    @Bean
//    MessageListenerAdapter listenerAdapter(Receiver receiver) {
//        return new MessageListenerAdapter(receiver, "receiveMessage");
//    }
//
//    @Bean
//    MessageListenerAdapter receiveMethod(Receiver2 receiver) {
//        return new MessageListenerAdapter(receiver, "receiveMethod");
//
//    }


    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(myHandlerMethodFactory());
    }
}
