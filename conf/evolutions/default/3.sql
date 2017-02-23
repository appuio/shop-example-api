# --- !Ups
ALTER TABLE public.products
    ADD COLUMN category_id INT REFERENCES categories (id),
    ADD COLUMN license_type_id INT REFERENCES license_types (id),
    ADD COLUMN publisher_id INT REFERENCES publishers (id);

# --- !Downs
ALTER TABLE public.products
    DELETE COLUMN category_id,
    DELETE COLUMN license_type_id,
    DELETE COLUMN publisher_id;