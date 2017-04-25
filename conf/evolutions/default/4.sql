# --- !Ups
INSERT INTO public.languages (id, name) VALUES (1, 'DE');
INSERT INTO public.languages (id, name) VALUES (2, 'EN');
INSERT INTO public.languages (id, name) VALUES (3, 'FR');
INSERT INTO public.languages (id, name) VALUES (4, 'IT');

INSERT INTO public.categories (id, name) VALUES (1, 'VCS');
INSERT INTO public.categories (id, name) VALUES (2, 'Operating System');
INSERT INTO public.categories (id, name) VALUES (3, 'ERP');

INSERT INTO public.license_types (id, name) VALUES (1, 'Individual');
INSERT INTO public.license_types (id, name) VALUES (2, 'Volume');
INSERT INTO public.license_types (id, name) VALUES (3, 'Subscription');

INSERT INTO public.publishers (id, name) VALUES (1, 'Microsoft');
INSERT INTO public.publishers (id, name) VALUES (2, 'Gitlab');
INSERT INTO public.publishers (id, name) VALUES (3, 'Canonical');
INSERT INTO public.publishers (id, name) VALUES (4, 'Odoo');

INSERT INTO public.products (id, name, price, uuid, description, category_id, license_type_id, publisher_id) VALUES (1, 'Gitlab EES', 1000, 'f6c672c5-ea65-48aa-a039-ba41aa075827', 'Gitlab Enterprise Edition Starter', 1, 3, 2);
INSERT INTO public.products (id, name, price, uuid, description, category_id, license_type_id, publisher_id) VALUES (2, 'Windows 10 Enterprise', 500, '635726f7-83a3-41ab-a5e2-382488de267d', 'Best operating system there is!', 2, 2, 1);
INSERT INTO public.products (id, name, price, uuid, description, category_id, license_type_id, publisher_id) VALUES (3, 'Odoo ERP', 2000, '6254fb7f-cd94-40a1-a32f-7e02c0896a15', 'Enterprise Resource Planning', 3, 3, 4);
INSERT INTO public.products (id, name, price, uuid, description, category_id, license_type_id, publisher_id) VALUES (4, 'Ubuntu Server', 0, 'b71f56db-b1a5-46a3-bca5-3d538f5e1b05', 'Open-Source Linux Operating System', 2, 1, 3);

INSERT INTO public.product_languages (product_id, language_id) VALUES (1, 1);
INSERT INTO public.product_languages (product_id, language_id) VALUES (1, 2);
INSERT INTO public.product_languages (product_id, language_id) VALUES (2, 1);
INSERT INTO public.product_languages (product_id, language_id) VALUES (2, 2);
INSERT INTO public.product_languages (product_id, language_id) VALUES (2, 3);
INSERT INTO public.product_languages (product_id, language_id) VALUES (2, 4);
INSERT INTO public.product_languages (product_id, language_id) VALUES (3, 1);
INSERT INTO public.product_languages (product_id, language_id) VALUES (4, 1);
INSERT INTO public.product_languages (product_id, language_id) VALUES (4, 2);
INSERT INTO public.product_languages (product_id, language_id) VALUES (4, 3);
INSERT INTO public.product_languages (product_id, language_id) VALUES (4, 4);

# --- !Downs
TRUNCATE public.product_languages, public.products, public.publishers, public.license_types, public.categories, public.languages;
