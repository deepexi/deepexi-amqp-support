# DeepEXI AMQP Support

[![Build Status](https://travis-ci.com/deepexi/deepexi-amqp-support.svg?branch=master)](https://travis-ci.com/deepexi/deepexi-amqp-support)
[![codecov](https://codecov.io/gh/deepexi/deepexi-amqp-support/branch/master/graph/badge.svg)](https://codecov.io/gh/deepexi/deepexi-amqp-support)


enhancements for spring boot amqp.

    $ mvn install
    

add dependency

```xml
<!-- pom.xml -->
<dependency>
    <groupId>com.deepexi</groupId>
    <artifactId>deepexi-amqp-support</artifactId>
    <version>{version}</version>
</dependency>
```

## EventMessageClassMapper

```java
@Configuration
public class AmqpConfiguration {
    @Bean
    public MessageConverter messageConverter(EventMessageClassMapper classMapper) {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        converter.setClassMapper(classMapper);      // specify class mapper
        return converter;
    }

    @Bean
    public EventMessageClassMapper eventMessageClassMapper(EventMessageTypeMapping eventMessageTypeMapping) {
        return new EventMessageClassMapper(eventMessageTypeMapping);
    }

    @Bean
    public EventMessageTypeMapping eventMessageTypeMapping() {
        return new EventMessageTypeMapping.Builder()
                .pkg("my.package")
                .build();
    }
}
```

define event via @EventMessage

```java
// my.package.FooEvent
@EventMessage(exchange = "ex", code = "foo")
public class FooEvent {
    private String str;
    private Integer num;
}
```

send event message

```java
@Autowired
private RabbitTemplate rabbitTemplate;

rabbitTemplate.convertAndSend("ex", "routing-key", new FooEvent());
```

receive event message

```java
@Autowired
private RabbitTemplate rabbitTemplate;

FooEvent msg = rabbitTemplate.receiveAndConvert("test-queue", new ParameterizedTypeReference<FooEvent>() {
}
```

or

```java
@EventListener
public void listen(FooEvent event) {
   // do something with event
}
```
