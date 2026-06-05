ALTER TABLE IF EXISTS production_order
    ADD COLUMN IF NOT EXISTS status VARCHAR(255);

UPDATE production_order
SET status = 'COMPLETED'
WHERE status IS NULL;

ALTER TABLE IF EXISTS production_order
    ALTER COLUMN status SET DEFAULT 'ACTIVE';

ALTER TABLE IF EXISTS production_order
    ALTER COLUMN status SET NOT NULL;
