create table carCheckUp(
    id bigint PRIMARY KEY,
    performed_at timestamp without time zone,
    worker_name text,
    price bigint,
    car_id UUID constraint car_fk references car(id)
    );