-- Create category type (ENUM)
CREATE TYPE category AS ENUM ('phone', 'tablet');

-- Create product table
CREATE TABLE "product" (
  "id" uuid PRIMARY KEY DEFAULT (gen_random_uuid()), -- Primary key
  "sku" varchar(32) UNIQUE NOT NULL,
  "barcode" varchar(14) UNIQUE,
  "name" varchar(255) NOT NULL,
  "brand" varchar(50) NOT NULL,
  "description" varchar(1000),
  "price" decimal(10,2) NOT NULL,
  "category" category NOT NULL,
  "os" varchar(10) NOT NULL,
  "chipset" varchar(50),
  "ram_gb" int,
  "storage_gb" int,
  "screen_size_inches" decimal(3,1),
  "battery_mah" int,
  "refresh_rate_hz" int,
  "main_cam_mp" decimal(4,1),
  "front_cam_mp" decimal(4,1),
  "is_active" boolean DEFAULT true,
  "created_at" timestamptz DEFAULT (now()),
  "updated_at" timestamptz
);

-- Create phone_spec table
CREATE TABLE "phone_spec" (
  "id" uuid PRIMARY KEY,
  "product_id" uuid, -- Foreign key column
  "ultra_wide_cam_mp" decimal(4,1),
  "ip_rating" varchar(5),
  "has_esim" boolean DEFAULT false
);

-- Create tablet_spec table
CREATE TABLE "tablet_spec" (
  "id" uuid PRIMARY KEY,
  "product_id" uuid, -- Foreign key column
  "has_cellular" boolean DEFAULT false,
  "stylus_supported" boolean,
  "keyboard_support" boolean
);

-- Create inventory_item table
CREATE TABLE "inventory_item" (
  "id" uuid PRIMARY KEY,
  "product_id" uuid, -- Foreign key column
  "batch_number" varchar(20),
  "available_quantity" integer DEFAULT 0
);

-- Add foreign key constraints
ALTER TABLE "phone_spec" ADD CONSTRAINT fk_product_id FOREIGN KEY ("product_id") REFERENCES "product" ("id");
ALTER TABLE "tablet_spec" ADD CONSTRAINT fk_product_id FOREIGN KEY ("product_id") REFERENCES "product" ("id");
ALTER TABLE "inventory_item" ADD CONSTRAINT fk_product_id FOREIGN KEY ("product_id") REFERENCES "product" ("id");
