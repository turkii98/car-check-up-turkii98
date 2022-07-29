create table car(
    id UUID PRIMARY KEY,
    added_date date,
    model_id UUID constraint model_fk references manufacturermodel(id),
    production_year text,
    vin text
    );