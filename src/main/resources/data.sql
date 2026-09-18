INSERT INTO customers(id,customer_number,full_name) VALUES (1,'CUST-1001','Demo Customer');
INSERT INTO debit_cards(id,card_token,masked_pan,status,available_balance,expiry_date,customer_id) VALUES (1,'tok_demo_001','**** **** **** 4242','ACTIVE',50000.00,'2030-12-31',1);
ALTER TABLE customers ALTER COLUMN id RESTART WITH 2;
ALTER TABLE debit_cards ALTER COLUMN id RESTART WITH 2;
