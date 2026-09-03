module.exports = {
    apps: [
        {
            name: "user-service",
            script: "java",
            args: "-jar ./user-service/target/user-service-0.0.1-SNAPSHOT.jar",
            instances: 1,
            env: {
                SERVER_PORT: 8081
            }
        },
        {
            name: "user-service-2",
            script: "java",
            args: "-jar ./user-service/target/user-service-0.0.1-SNAPSHOT.jar",
            instances: 1,
            env: {
                SERVER_PORT: 8082
            }
        },

        {
            name: "book-service",
            script: "java",
            args: "-jar ./book-service/target/book-service-0.0.1-SNAPSHOT.jar",
            instances: 1,
            env: {
                SERVER_PORT: 8083
            }
        },
        {
            name: "book-service-2",
            script: "java",
            args: "-jar ./book-service/target/book-service-0.0.1-SNAPSHOT.jar",
            instances: 1,
            env: {
                SERVER_PORT: 8084
            }
        },

        {
            name: "borrow-service",
            script: "java",
            args: "-jar ./borrow-service/target/borrow-service-0.0.1-SNAPSHOT.jar",
            instances: 1,
            env: {
                SERVER_PORT: 8085
            }
        },
        {
            name: "borrow-service-2",
            script: "java",
            args: "-jar ./borrow-service/target/borrow-service-0.0.1-SNAPSHOT.jar",
            instances: 1,
            env: {
                SERVER_PORT: 8086
            }
        }
    ]
};