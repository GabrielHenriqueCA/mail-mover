## Documentation Microservice Email

This controller class provides endpoints for sending emails and retrieving email information.

### Sending Email

Endpoint: `POST /sending-email`

This endpoint is used to send an email.

Request Body:
- `emailDto`: The email details. See the `EmailDto` class for the required fields.

Example Request:
```http
POST /sending-email
Content-Type: application/json

{
  "ownerRef": "Gabriel",
  "emailFrom": "recipient@gmail.com",
  "emailTo": "recipient@gmail.com",
  "subject": "Test",
  "text": "Test e-mail"
}
```

Example Response:
```http
HTTP/1.1 200 OK
Content-Type: text/plain

Email sent successfully.
```

### Get Emails

Endpoint: `GET mailMover/emails`

This endpoint is used to retrieve a list of emails.

Query Parameters:
- `page` (optional): The page number for pagination. Default is 0.
- `size` (optional): The number of emails per page. Default is 5.
- `sort` (optional): The field to sort the emails by. Default is "emailId".
- `direction` (optional): The sort direction. Values can be "ASC" for ascending or "DESC" for descending. Default is "DESC".

Examples Request:
```http
GET /emails
or
GET /emails?page=0&size=10&sort=emailId&direction=DESC
```

Example Response:
```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "content": [
    {
  "ownerRef": "Gabriel",
  "emailFrom": "recipient@gmail.com",
  "emailTo": "recipient@gmail.com",
  "subject": "Test",
  "text": "Test e-mail"
},
    {
  "ownerRef": "Gabriel",
  "emailFrom": "recipient@gmail.com",
  "emailTo": "recipient@gmail.com",
  "subject": "Test",
  "text": "Test e-mail"
}
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    "sort": {
      "sorted": true,
      "unsorted": false,
      "empty": false
    }
  },
  "totalElements": 2,
  "totalPages": 1,
  "last": true,
  "size": 10,
  "number": 0,
  "sort": {
    "sorted": true,
    "unsorted": false,
    "empty": false
  },
  "numberOfElements": 2,
  "first": true,
  "empty": false
}
```

### Get Email by ID

Endpoint: `GET mailMover/emails/{emailId}`

This endpoint is used to retrieve an email by its ID.

Path Parameters:
- `emailId`: The ID of the email to retrieve.

Example Request:
```http
GET /emails/123456
```

Example Response:
```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "ownerRef": "Gabriel",
  "emailFrom": "recipient@gmail.com",
  "emailTo": "recipient@gmail.com",
  "subject": "Test",
  "text": "Test e-mail"
}
```

If the email ID is not found, a `404 Not Found` response will be returned.

## Email Messaging

The email messaging feature allows you to process email messages asynchronously using RabbitMQ and implement circuit breaker functionality.

### Listening for Email Messages

The `listen` method in the `EmailConsumer` class is annotated with `@RabbitListener` to listen for email messages from a RabbitMQ queue.

```java
@CircuitBreaker(name = "email-service-circuit-breaker", fallbackMethod = "fallbackMethod")
@RabbitListener(queues = "${spring.rabbitmq.queue}")
public void listen(@Payload EmailDto emailDto) {
    emailService.sendEmailToRecipient(emailDto);
}
```
That's it! You can use these endpoints to send emails, retrieve email information, and manage your email microservice.
