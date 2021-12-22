CREATE TABLE "user" (
    id UUID PRIMARY KEY,
    firstName VARCHAR (255) NOT NULL,
    lastName VARCHAR (255) NOT NULL,
    email VARCHAR (64) NOT NULL,
    workspace_id_id UUID CONSTRAINT user_details_id_fk REFERENCES "workspace" (id)
);