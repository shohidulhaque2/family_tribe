-- liquibase formatted sql

-- changeset thepoetwarrior:1607560106396-1
CREATE TABLE storage (
    id INT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255) NULL,

    PRIMARY KEY (id)
);
