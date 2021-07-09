workspace "Todo" "Workspace for Todo application" {

     model {
        user = person "User"
        softwareSystem = softwareSystem "Todo Application" {
            webapp = container "Todo Service" "Provides the static content and Rest API" "Java and Spring Boot" {
                taskApi = component "TaskApi" "Provides api for task management" "Spring MVC Rest Controller"
                taskController = component "TaskController" "Provides static content" "Spring MVC Controller" "TaskController"

                createTaskUseCase = component "CreateTaskUseCase" "Creates tasks" "Spring Bean"
                updateTaskUseCase = component "UpdateTaskStatusUseCase" "Updates task statuses" "Spring Bean"
                getTasksQuery = component "GetTasksQuery" "Retrieves tasks" "Spring Bean"
            }
            database = container "Database" "Stores task information" "MySql" "Database"

            # relationships between people and software systems
            user -> webapp "Creates and retrieves tasks" "HTTP"

            # relationships to/from containers
            webapp -> database "Reads from and writes to" "JDBC"

            # relationships to/from components
            taskApi -> createTaskUseCase "Uses"
            taskApi -> updateTaskUseCase "Uses"
            taskApi -> getTasksQuery "Uses"
            taskController -> createTaskUseCase "Uses"
            taskController -> updateTaskUseCase "Uses"
            taskController -> getTasksQuery "Uses"
            createTaskUseCase -> database
            updateTaskUseCase -> database
            getTasksQuery -> database
        }
    }

    views {
        systemContext softwareSystem {
            include *
            autolayout lr

        }

        container softwareSystem {
            include *
            autolayout lr
        }

        component webapp {
            include *
            autolayout lr
        }

        styles {
         element "Database" {
            shape Cylinder
        }

        element "TaskController" {
            background #228B22
        }
    }

        theme default
    }

}
