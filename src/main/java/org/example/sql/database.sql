CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    phone VARCHAR(30) UNIQUE
);

CREATE TABLE training_applications (
    id SERIAL PRIMARY KEY,

    user_id INTEGER NOT NULL,

    course_name VARCHAR(200) NOT NULL,

    application_date DATE NOT NULL,

    status VARCHAR(30) NOT NULL,

    education_type VARCHAR(30) NOT NULL,

    comment TEXT,

    CONSTRAINT fk_application_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT chk_application_status
        CHECK (status IN (
            'NEW',
            'UNDER_REVIEW',
            'APPROVED',
            'REJECTED',
            'CANCELLED'
        )),

    CONSTRAINT chk_education_type
        CHECK (education_type IN (
            'FULL_TIME',
            'PART_TIME',
            'ONLINE'
        ))
);

INSERT INTO users (full_name, email, phone)
VALUES
('Иванов Иван Иванович', 'ivanov@mail.ru', '+79990000001'),
('Петров Петр Петрович', 'petrov@mail.ru', '+79990000002'),
('Сидорова Анна Сергеевна', 'sidorova@mail.ru', '+79990000003'),
('Кузнецов Алексей Дмитриевич', 'kuznetsov@mail.ru', '+79990000004'),
('Смирнова Мария Андреевна', 'smirnova@mail.ru', '+79990000005');

INSERT INTO training_applications
(user_id, course_name, application_date, status, education_type, comment)
VALUES
(1, 'Java Developer', '2026-09-01', 'NEW', 'FULL_TIME', 'Хочу изучить Java'),
(2, 'Python Developer', '2026-09-02', 'UNDER_REVIEW', 'ONLINE', 'Интересует backend'),
(3, 'Web Development', '2026-09-03', 'APPROVED', 'PART_TIME', 'Есть опыт HTML и CSS'),
(4, 'Data Science', '2026-09-04', 'REJECTED', 'ONLINE', 'Недостаточно опыта'),
(5, 'Java Developer', '2026-09-05', 'NEW', 'ONLINE', 'Хочу сменить профессию'),
(1, 'Database Administration', '2026-09-06', 'APPROVED', 'FULL_TIME', 'Интересуют базы данных'),
(2, 'DevOps', '2026-09-07', 'UNDER_REVIEW', 'ONLINE', 'Хочу изучить Docker'),
(3, 'Python Developer', '2026-09-08', 'CANCELLED', 'PART_TIME', 'Изменились планы'),
(4, 'Java Developer', '2026-09-09', 'NEW', 'FULL_TIME', 'Интересует разработка'),
(5, 'Web Development', '2026-09-10', 'APPROVED', 'ONLINE', 'Хочу работать frontend-разработчиком');

