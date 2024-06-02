CREATE TABLE user_detail (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE train_detail (
    train_number BIGINT AUTO_INCREMENT PRIMARY KEY,
    train_name VARCHAR(255) NOT NULL,
    from_location VARCHAR(255) NOT NULL,
    to_location VARCHAR(255) NOT NULL
);

CREATE TABLE train_section_and_seat_detail (
    section_and_seat_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    train_number BIGINT NOT NULL,
    section VARCHAR(255) NOT NULL,
    ticket_cost DOUBLE NOT NULL,
    seat VARCHAR(255) NOT NULL,
    is_seat_allotted BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_train_detail FOREIGN KEY (train_number) REFERENCES train_detail(train_number)
);

CREATE TABLE ticket_booking_detail (
    ticket_booking_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    train_number BIGINT NOT NULL,
    train_name VARCHAR(255) NOT NULL,
    from_location VARCHAR(255) NOT NULL,
    to_location VARCHAR(255) NOT NULL,
    section_and_seat_id BIGINT NOT NULL,
    ticket_cost DOUBLE NOT NULL,
    section VARCHAR(255) NOT NULL,
    seat VARCHAR(255) NOT NULL,
    user_id BIGINT,
    CONSTRAINT fk_user_detail_booking FOREIGN KEY (user_id) REFERENCES user_detail(user_id)
);
