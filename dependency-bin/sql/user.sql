CREATE TABLE `user`
(
    `id`      int(11) NOT NULL AUTO_INCREMENT,
    `userId`  varchar(50),
    `name`    varchar(50),
    `age`     int(11),
    `address` varchar(200),
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB;