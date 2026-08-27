module.exports = {
  apps : [
      {
          name   : "user-service",
          script : "java -jar ./user-service/target/user-service-0.0.1-SNAPSHOT.jar",
          log_file: "./logs/user-service.log",
          instances: 2
      },
      {
          name   : "book-service",
          script : "java -jar ./book-service/target/book-service-0.0.1-SNAPSHOT.jar",
          log_file: "./logs/book-service.log",
          instances: 2
      },
      {
          name   : "borrow-service",
          script : "java -jar ./borrow-service/target/borrow-service-0.0.1-SNAPSHOT.jar",
          log_file: "./logs/borrow-service.log",
          instances: 2
      }
  ]
}
