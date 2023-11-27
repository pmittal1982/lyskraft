-- Insert into country table
INSERT INTO public.country(id, name) VALUES (1, 'China');
INSERT INTO public.country(id, name) VALUES (2, 'Thailand');
INSERT INTO public.country(id, name) VALUES (3, 'Singapore');
INSERT INTO public.country(id, name) VALUES (4, 'Vietnam');
INSERT INTO public.country(id, name) VALUES (5, 'Indonesia');
INSERT INTO public.country(id, name) VALUES (6, 'Malaysia');
INSERT INTO public.country(id, name) VALUES (7, 'Philippines');
INSERT INTO public.country(id, name) VALUES (8, 'Spain');
ALTER SEQUENCE country_id_seq RESTART WITH 9;

-- Insert into Root Category Table
INSERT INTO public.root_category(id, name) VALUES (1, 'Carbon Steel');
INSERT INTO public.root_category(id, name) VALUES (2, 'Raw Materials');
ALTER SEQUENCE root_category_id_seq RESTART WITH 3;

-- Insert into Leaf Category Table
INSERT INTO public.leaf_category(id, name, root_category_id) VALUES (1, 'Longs', 1);
INSERT INTO public.leaf_category(id, name, root_category_id) VALUES (2, 'Flats', 1);
INSERT INTO public.leaf_category(id, name, root_category_id) VALUES (3, 'Semi finished', 1);
INSERT INTO public.leaf_category(id, name, root_category_id) VALUES (4, 'Raw Materials', 2);
ALTER SEQUENCE leaf_category_id_seq RESTART WITH 5;

-- Insert into SKU Table
INSERT INTO public.sku(id, title, image_url, description, leaf_category_id) VALUES (1, 'Angles', 'http://sampleImage.jpg', 'Steel Angles that can have Equal or Unequal Legs, commonly used in construction.', 1);
INSERT INTO public.sku(id, title, image_url, description, leaf_category_id) VALUES (2, 'Sections', 'http://sampleImage.jpg', 'Structural steel product having a profile of a specific cross section, like a H or I, usually used in construction and is designed to support heavy loads.', 1);
INSERT INTO public.sku(id, title, image_url, description, leaf_category_id) VALUES (3, 'Rebars', 'http://sampleImage.jpg', 'Reinforcing steel (rebar) is used in bridges, buildings, homes, warehouses, & foundations to increase the strength of a concrete structure.', 1);
INSERT INTO public.sku(id, title, image_url, description, leaf_category_id) VALUES (4, 'Steel Pipes', 'http://sampleImage.jpg', 'Steel pipes are cylindrical tubes made from steel that are used many ways in manufacturing and infrastructure.', 1);
INSERT INTO public.sku(id, title, image_url, description, leaf_category_id) VALUES (5, 'Sheet Piles', 'http://sampleImage.jpg', 'Structural steel sections with vertical interlocking edges that are driven into the ground to create a continuous retaining wall against soil or water.', 1);
INSERT INTO public.sku(id, title, image_url, description, leaf_category_id) VALUES (6, 'Wire Rods', 'http://sampleImage.jpg', 'a rolled steel product (alloy or non-alloy), manufactured from semi-finished steel with a rectangular, rounded, hexagonal or squared shape.', 2);
INSERT INTO public.sku(id, title, image_url, description, leaf_category_id) VALUES (7, 'Hot Rolled Coils', 'http://sampleImage.jpg', ' a steel product in the form of coiled steel strip produced from the hot rolling process in the Hot Strip Mill (HSM) or a CSP facility.', 2);
INSERT INTO public.sku(id, title, image_url, description, leaf_category_id) VALUES (8, 'Hot Rolled Sheets', 'http://sampleImage.jpg', 'Hot Rolled Coils further cut into the Sheet Form', 2);
INSERT INTO public.sku(id, title, image_url, description, leaf_category_id) VALUES (9, 'Cold Rolled Coils', 'http://sampleImage.jpg', 'Cold rolled steel is essentially hot rolled steel that has been further processed by being allowed to cool at room temperature and annealed or temper rolled. Cold rolling produces steel with closer dimensional tolerances and a wider range of surface finishes than hot rolling.', 2);
INSERT INTO public.sku(id, title, image_url, description, leaf_category_id) VALUES (10, 'Cold Rolled Sheets', 'http://sampleImage.jpg', 'Cold Rolled Coils further cut int Sheet form for various general Engineering and other applications', 2);
INSERT INTO public.sku(id, title, image_url, description, leaf_category_id) VALUES (11, 'Hot Dip Galvanized Coils', 'http://sampleImage.jpg', 'Hot-dipped galvanized steel coil is made from Hot Rolled or Cold Rolled Steel Coils, which are dipped into the dissolved zinc bath, with a zinc layer on the surface, thereby resulting in better Anti Corrosive properties', 2);
INSERT INTO public.sku(id, title, image_url, description, leaf_category_id) VALUES (12, 'Aluminium Zinc Coated Coils', 'http://sampleImage.jpg', 'These Coils are a form of cold rolled galvanized steel with metal coating composed of 55% aluminum, 43% zinc, and 1.6% silicon.', 2);
INSERT INTO public.sku(id, title, image_url, description, leaf_category_id) VALUES (13, 'Prepainted Coils (PPGI/PPGL)', 'http://sampleImage.jpg', 'It is a kind of colour coated steel coil (PPGI/PPGL coil). It uses galvanized steel or Aluminium Zinc Steel as the base metal. The zinc layer or the Zinc Aluminium Layer offers strong corrosion resistance. Plus with painting, it is more durable than galvanized steel.', 2);
INSERT INTO public.sku(id, title, image_url, description, leaf_category_id) VALUES (14, 'Hot Rolled Plates', 'http://sampleImage.jpg', 'Carbon Steel Plates produced directly from Steel Slabs being used in various industries like Offshore Wind, Oil and Gas, Construction etc.', 2);
INSERT INTO public.sku(id, title, image_url, description, leaf_category_id) VALUES (15, 'Square Billets', 'http://sampleImage.jpg', 'Billet is a type of steel that''s used for a variety of applications. It''s made from cast iron or plain carbon steel and is then heated and worked into its desired shape like Wire Rods, Rebars, Smaller Angles etc.', 3);
INSERT INTO public.sku(id, title, image_url, description, leaf_category_id) VALUES (16, 'Slabs', 'http://sampleImage.jpg', 'Steel slab is mostly used for further re rolling into Hot Rolled Coils as well as Hot Rolled Heavy Plates', 3);
INSERT INTO public.sku(id, title, image_url, description, leaf_category_id) VALUES (17, 'Cast Rounds (For Seamless Pipes)', 'http://sampleImage.jpg', 'Cast Rounds are Round Steel Billets used for further processing into Seamless Pipes used in Oil and Gas Industry', 3);
INSERT INTO public.sku(id, title, image_url, description, leaf_category_id) VALUES (18, 'Round Bars (For Engineering application)', 'http://sampleImage.jpg', 'Round Bars are typically used in Engineering and Automotive Industry and are available in Alloyed as well as Non Alloyed Form.', 3);
INSERT INTO public.sku(id, title, image_url, description, leaf_category_id) VALUES (19, 'Met Coke', '', '', 4);
INSERT INTO public.sku(id, title, image_url, description, leaf_category_id) VALUES (20, 'Iron Ore Pellets', '', '', 4);
INSERT INTO public.sku(id, title, image_url, description, leaf_category_id) VALUES (21, 'Iron Ore Concentrates', '', '', 4);
INSERT INTO public.sku(id, title, image_url, description, leaf_category_id) VALUES (22, 'Pig Iron', '', '', 4);
INSERT INTO public.sku(id, title, image_url, description, leaf_category_id) VALUES (23, 'Hot Briquette Iron', '', '', 4);
ALTER SEQUENCE sku_id_seq RESTART WITH 24;

-- Insert into Product Table
INSERT INTO public.product(id, image_url, title, sku_id) VALUES (1, '', 'Equal Angles', 1);
INSERT INTO public.product(id, image_url, title, sku_id) VALUES (2, '', 'Unequal Angles', 1);
INSERT INTO public.product(id, image_url, title, sku_id) VALUES (3, '', 'Parallel Flange I Sections (IPE)', 2);
INSERT INTO public.product(id, image_url, title, sku_id) VALUES (4, '', 'Wide Flange Beams (HE)', 2);
INSERT INTO public.product(id, image_url, title, sku_id) VALUES (5, '', 'Universal Beams (UB)', 2);
INSERT INTO public.product(id, image_url, title, sku_id) VALUES (6, '', 'Universal Columns (UC)', 2);
INSERT INTO public.product(id, image_url, title, sku_id) VALUES (7, '', 'Parallel Flange Channels (PFC)', 2);
INSERT INTO public.product(id, image_url, title, sku_id) VALUES (8, '', 'Taperes Flange Channels', 2);
INSERT INTO public.product(id, image_url, title, sku_id) VALUES (9, '', 'UPN', 2);
INSERT INTO public.product(id, image_url, title, sku_id) VALUES (10, '', 'U Type', 5);
INSERT INTO public.product(id, image_url, title, sku_id) VALUES (11, '', 'Z Type', 5);
INSERT INTO public.product(id, image_url, title, sku_id) VALUES (12, '', 'Hat Type', 5);
ALTER SEQUENCE product_id_seq RESTART WITH 13;

-- Create Root User and Company
INSERT INTO public.company(id, created_by, created_date, last_modified_date, modified_by, address, email, name, phone, status, country_id) VALUES (1, 'System', current_timestamp, current_timestamp, 'System', 'Singapore', 'piyush@metaltrade.io', 'Metal Trading Platform Pte Ltd.', '+6287858881256', 'Verified', 3);
ALTER SEQUENCE company_id_seq RESTART WITH 2;
INSERT INTO public.mtp_user(id, created_by, created_date, last_modified_date, modified_by, mobile_number, role, status, company_id) VALUES (1, 'System', current_timestamp, current_timestamp, 'System', '+6287858881256', 'ROOT', 0, 1);
ALTER SEQUENCE mtp_user_id_seq RESTART WITH 2;