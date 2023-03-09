# job4j_todo

## Описание проекта

* Web-приложение, представляющее собой "TODO список".
* Пользователь может добавлять, редактировать и удалять задания. Ставить отметку об их завершении.

## Стек технологий

- **Java 17.0.2**
- **Spring Boot 2.7.6**
- **Thymeleaf**
- **Bootstrap 5.2.3**
- **HTML5**
- **PostgreSQL 15**
- **Hibernate 5.6.11**
- **Liquibase 4.15.0**
- **Commons-dbcp2 2.9.0**
- **Maven 3.8.1**

## Требования к окружению

- **Java 17.0.2**
- **Maven 3.8.1**
- **PostgresSQL 15**

## Сборка и запуск

- **Создать БД**

``` 
create database todo;
```

- **Запустить проект по команде**

``` 
mvn spring-boot:run -Pproduction
```

- **Перейти в браузере по ссылке**

``` 
http://localhost:8080/
```

## Взаимодействие с приложением

### Главная страница. Отображает список всех заданий. В таблице отображены название задачи, дата создание и текущее состояние. Имеется возможность добавить задание.
![](images/1.png)

### Страница с незавершенными задачами. Реализована аналогичная страница для выполненных задач.
![](images/2.png)

### При клике на задание осуществляется переход на страницу с подробным описанием.
![](images/3.png)

### При нажатии на Complete Task состояние задачи изменяется на завершенное.
![](images/4.png)

### При нажатии на Update появляется возможность отредактировать описание задачи и изменить ее состояние.
![](images/5.png)

### Кнопка Delete отвечает за удаление задачи из сервиса.
![](images/6.png)

### При передаче запроса с несуществующей задачей отправляется форма с ошибкой.
![](images/7.png)

## Контакты для связи
telegram: <a href="https://t.me/bekhterev_egor" target="blank">@bekhterev_egor</a>
