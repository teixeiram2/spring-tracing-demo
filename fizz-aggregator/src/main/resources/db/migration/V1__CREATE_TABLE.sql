USE TRACE;

CREATE TABLE `fizz_history`
(
 `id` bigint NOT NULL AUTO_INCREMENT,
 `message` varchar(32) NOT NULL,
 primary key (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;