DO $$
BEGIN
    if not exists (select 1 from pg_type where typname = 'category') then
         CREATE TYPE category AS ENUM ('phone', 'tablet');
    end if;
END
$$;

CREATE TABLE IF NOT EXISTS "product" (
  "id" uuid PRIMARY KEY DEFAULT (gen_random_uuid()),
  "sku" varchar(32) UNIQUE NOT NULL,
  "barcode" varchar(14) UNIQUE,
  "name" varchar(255) NOT NULL,
  "brand" varchar(50) NOT NULL,
  "description" varchar(1000),
  "price" decimal(10,2) NOT NULL,
  "category" category NOT NULL,
  "product_id" uuid PRIMARY KEY,
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


CREATE TABLE IF NOT EXISTS "phone_spec" (
  "id" uuid PRIMARY KEY,
  "product_id" uuid,
  "ultra_wide_cam_mp" decimal(4,1),
  "ip_rating" varchar(5),
  "has_esim" boolean DEFAULT false
);

CREATE TABLE IF NOT EXISTS "tablet_spec" (
  "id" uuid PRIMARY KEY,
  "product_id" uuid,
  "has_cellular" boolean DEFAULT false,
  "stylus_supported" boolean,
  "keyboard_support" boolean
);

CREATE TABLE IF NOT EXISTS "inventory_item" (
  "id" uuid PRIMARY KEY,
  "product_id" uuid,
  "batch_number" varchar(20),
  "available_quantity" integer DEFAULT 0
);

COMMENT ON COLUMN "product"."price" IS 'USD';

ALTER TABLE "device_spec" ADD FOREIGN KEY ("product_id") REFERENCES "product" ("id");

ALTER TABLE "phone_spec" ADD FOREIGN KEY ("product_id") REFERENCES "product" ("id");

ALTER TABLE "tablet_spec" ADD FOREIGN KEY ("product_id") REFERENCES "product" ("id");

ALTER TABLE "inventory_item" ADD FOREIGN KEY ("product_id") REFERENCES "product" ("id");
