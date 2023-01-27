INSERT INTO companies (id, name, website, email, created_at, updated_at)
VALUES (1, 'test1', 'https://google.com', 'info@google.com', '2023-01-26 21:47:55.000000', '2023-01-26 21:47:58.000000'),
        (2, 'test2', 'https://google.com', 'info@google.com', '2023-01-26 21:47:55.000000', '2023-01-26 21:47:58.000000');

INSERT INTO jobs (id, title, description, company_id, location, salary, job_type, created_at, updated_at, expires_at)
VALUES (1, 'test', 'testDesc', 1, 'DK', 2000.00, 'FRONTEND',
        '2023-01-26 21:47:55.000000', '2023-01-26 21:47:58.000000', '2023-01-27 21:47:59.000000'),
        (2, 'test', 'testDesc', 1, 'DK', 2000.00, 'BACKEND',
        '2023-01-26 21:47:55.000000', '2023-01-26 21:47:58.000000', '2023-01-27 21:47:59.000000');

INSERT INTO users (id, email, password, name, created_at, updated_at)
VALUES (1, 'test@email.dk', 'password', 'name', '2023-01-26 21:47:55.000000', '2023-01-26 21:47:58.000000');

INSERT INTO applications (id, user_id, job_id, created_at, updated_at)
VALUES (1, 1, 1, '2023-01-26 21:47:55.000000', '2023-01-26 21:47:58.000000');