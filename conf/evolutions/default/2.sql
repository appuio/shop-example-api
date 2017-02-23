# --- !Ups
CREATE TABLE public.categories
(
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE public.languages
(
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE public.license_types
(
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE public.publishers
(
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE public.product_languages
(
    product_id INT REFERENCES products (id),
    language_id INT REFERENCES languages (id),
    CONSTRAINT product_language_pkey PRIMARY KEY (product_id, language_id)
);

ALTER TABLE public.products
    ADD COLUMN uuid UUID,
    ADD COLUMN description TEXT;

# --- !Downs
DROP TABLE public.categories;
DROP TABLE public.languages;
DROP TABLE public.license_types;
DROP TABLE public.publishers;
DROP TABLE public.product_languages;

ALTER TABLE public.products
    DELETE COLUMN uuid,
    DELETE COLUMN description;