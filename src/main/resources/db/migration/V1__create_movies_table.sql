CREATE TABLE movies(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    release_date DATE,
    duration INTEGER,
    director VARCHAR(255)
    );

CREATE TABLE series (
     id BIGSERIAL PRIMARY KEY,
     name VARCHAR(255) NOT NULL,
     release_date DATE,
     duration INTEGER,
     director VARCHAR(255),
     creator VARCHAR(255),
     episodes INTEGER,
     finished BOOLEAN
);