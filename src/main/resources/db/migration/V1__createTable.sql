CREATE TABLE history_of_calls (
    call_id SERIAL PRIMARY KEY ,
    date varchar(30) NOT NULL,
    description varchar(100) NOT NULL
);
