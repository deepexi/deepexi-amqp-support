# Changelog

## 1.0.0

### Features

- provide EventMessageClassMapper

- provide InvocableHandlerMethodDecorator

- provide IdempotentValidator

- provide Authenticator

- provide MessageRecorder

## 1.1.0

### Features 

- support resolve @EventMessage(exchange) that contains placeHolder ${}

## 1.1.1 

- 修改 bean name authenticator -> authenticator0，避免与某些组件（如 shiro）的 bean name 冲突

## 1.1.2

- 修复 EventMessageUtils 无法解析 placeholder 的问题

