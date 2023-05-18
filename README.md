# Mail Mover - Email Microservice

This microservice provides functionality to send emails both through API calls and message queuing. It is built using [Spring Boot](https://spring.io/projects/spring-boot) framework and supports various configurations for SMTP, database, and RabbitMQ.

## Getting Started

To run the email microservice locally, follow the steps below:

### Prerequisites

- Java 11 or higher
- Maven
- MySQL database
- RabbitMQ server

### Configuration

Before running the email microservice, make sure to configure the properties file with the necessary settings for SMTP, database, and RabbitMQ. Follow the instructions below to perform the configuration:

#### SMTP Configuration

Open the `application.properties` file and locate the section responsible for SMTP configuration. The SMTP-related properties are as follows:

```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your_email@gmail.com
spring.mail.password=your_token_password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

Replace `your_email@gmail.com` with your Gmail email address. For the `spring.mail.password` property, you need to generate an application-specific password or an OAuth token password from the [Google Account Security](https://myaccount.google.com/security) settings. This password will be used to authenticate the email sending process.

#### Database Configuration

The microservice uses a MySQL database to store information related to emails. To configure the database, follow these steps:

1. Open the `application.properties` file and locate the section responsible for database configuration. The database-related properties are as follows:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost/mailMover?createDatabaseIfNotExist=true&useSSL=false
   spring.datasource.username=root
   spring.datasource.password=your_database_password
   spring.jpa.show-sql=true
   spring.jpa.hibernate.ddl-auto=update
   ```

2. Replace `your_database_password` with the correct password for your MySQL database. Also, verify if the database URL is correct according to your configuration.

#### RabbitMQ Configuration

The microservice uses [RabbitMQ](https://customer.cloudamqp.com/signup?_gl=1*1x4dehe*_gcl_au*MTU0NTcxMzkzMC4xNjg0MzQ3ODAy) as a messaging system to receive and process email messages. To configure RabbitMQ, follow these steps:

1. Open the `application.properties` file and locate the section responsible for RabbitMQ configuration. The RabbitMQ-related properties are as follows:

   ```properties
   spring.rabbitmq.addresses=amqps://your_username:password@your_host/your_virtualhost
   spring.rabbitmq.queue=ms.email
   ```

2. Replace `your_username`, `password`, `your_host`, and `your_virtualhost` with the correct information from your RabbitMQ configuration. Also, verify if the defined queue (`queue`) is correct according to your needs.

After performing the necessary configuration in the properties file, you are ready to run the email microservice with the correct settings.

### Building and Running

To build and run the email microservice, follow these steps:

1. Open a terminal or command prompt and navigate to the root directory of the project.
2. Run the following command to build the project:

   ```shell
   mvn clean install
   ```

3. Once the build is successful, run the following command to start the microservice:

   ```shell
   mvn spring-boot:run or start it the IDE
   ```

The email microservice will start running on the configured port, and you can now use it to send emails via API or message queuing.

## Usage

The email microservice provides endpoints for sending emails through API calls and message queuing. Refer to the [API documentation](docs) or the message queuing guidelines

 for detailed usage instructions.

## Contributing

Thank you for considering contributing to the email microservice! To contribute, follow these steps:

1. Fork the repository and clone it to your local machine.
2. Create a new branch for your feature or bug fix.
3. Make the necessary changes and ensure that the code is properly tested.
4. Commit your changes and push them to your forked repository.
5. Submit a pull request describing your changes and why they should be merged.

We appreciate your contributions and will review the pull request as soon as possible.

## License

This project is licensed under the [Apache License](LICENSE).
