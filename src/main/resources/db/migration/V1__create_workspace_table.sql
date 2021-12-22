CREATE TABLE workspace (
    id UUID PRIMARY KEY,
    name VARCHAR (255) NOT NULL,
    description VARCHAR (255) NOT NULL,
    visibility VARCHAR (10) NOT NULL
);