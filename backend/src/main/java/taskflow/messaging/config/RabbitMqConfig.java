package taskflow.messaging.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    public static final String TASK_EXCHANGE = "taskflow.task.exchange";
    public static final String TASK_CREATED_QUEUE = "taskflow.task.queue";
    public static final String TASK_CREATED_ROUTING_KEY = "task.created";

    @Bean
    public TopicExchange taskExchange(){
        return new TopicExchange(TASK_EXCHANGE);
    }

    @Bean
    public Queue taskCreatedQueue(){
        return new Queue(TASK_CREATED_QUEUE);
    }

    @Bean
    public Binding taskCreatedBinding(TopicExchange taskExchange, Queue taskCreatedQueue){
        return BindingBuilder.bind(taskCreatedQueue)
                .to(taskExchange)
                .with(TASK_CREATED_ROUTING_KEY);
    }

    @Bean
    public JacksonJsonMessageConverter messageConverter() {
        return new JacksonJsonMessageConverter ();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(
            ConnectionFactory connectionFactory,
            JacksonJsonMessageConverter messageConverter
    ) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}
