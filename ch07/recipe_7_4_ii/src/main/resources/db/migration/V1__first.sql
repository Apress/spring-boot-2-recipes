CREATE TABLE customer (
  id SERIAL PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(255) NOT NULL,
  UNIQUE(name)
);

INSERT INTO customer (name, email) VALUES
    ('Marten Deinum', 'marten.deinum@conspect.nl'),
    ('Josh Long', 'jlong@pivotal.com'),
    ('John Doe', 'john.doe@island.io'),
    ('Jane Doe', 'jane.doe@island.io');
