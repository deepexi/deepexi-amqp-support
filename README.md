# DeepEXI AMQP Support

[CHANGELOG](./CHANGELOG.md)

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
}
```

support placeHolder to resolve exchange and code

```yml
# application.yml
eg:
  exchange: DEFAULT-EXCHANGE
  code: DEFAULT-CODE
```

```java
// exchange will be set to DEFAULT-EXCHANGE and code will be DEFAULT-CODE
@EventMessage(exchange = "${eg.exchange}", code = "${eg.code}")
public class FooEvent {
    // ...
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

## InvocableHandlerMethodDecorator

define beans of `InvocableHandlerMethodDecorator` and make enhancements for all methods that annotated with `@RabbitHandler`.

```java
@Bean
public InvocableHandlerMethodDecorator invocableHandlerMethodDecorator() {
    return invocableHandlerMethod -> new InvocableHandlerMethodDecoration(invocableHandlerMethod) {
        @Override
        public Object invoke(Message<?> message, Object... providedArgs) throws Exception {
            // do something here...
            return super.invoke(message, providedArgs);
        }
    };
}
```

### IdempotentValidator

you can valid if message is handled repeatedly by define bean of `IdempotentValidator`

```java
@Bean
public IdempotentValidator idempotentValidator() {
    return new IdempotentValidator() {
        @Override
        public void valid(Message<?> message) {
            // do valid here
        }
    };
}
```

### Authenticator

you can do login before consume message and do logout after consume message by define bean of `Authenticator`

```java
@Bean
public Authenticator authenticator() {
    return new Authenticator() {
        @Override
        public void login(Message<?> message) {
            // do login here
        }

        @Override
        public void logout(Message<?> message) {
            // do logout here
        }
    };
}
```

### MessageRecorder

you can record message and other info after consume message by define bean of `MessageRecorder`

```java
@Bean
public MessageRecorder messageRecorder() {
    return new MessageRecorder() {
        @Override
        public void record(Message<?> message, boolean success, Object result, Exception error) {
            // do record here
        }
    }
}
```

