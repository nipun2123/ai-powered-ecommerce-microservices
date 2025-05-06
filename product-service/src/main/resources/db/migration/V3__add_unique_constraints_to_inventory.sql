ALTER TABLE inventory_item ADD CONSTRAINT unique_product_batch UNIQUE (product_id, batch_number);
