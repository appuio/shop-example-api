# --- !Ups
CREATE TABLE public.products
(
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL,
    price FLOAT(10) NOT NULL
);

# --- !Downs
DROP TABLE public.products;