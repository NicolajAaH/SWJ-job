CREATE TABLE jobs
(
    id          SERIAL PRIMARY KEY,
    title       VARCHAR(255)   NOT NULL,
    description TEXT           NOT NULL,
    company_id  INTEGER        NOT NULL,
    location    VARCHAR(255)   NOT NULL,
    salary      DECIMAL(10, 2) NOT NULL,
    job_type    VARCHAR(10)    NOT NULL,
    created_at  TIMESTAMP      NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP      NOT NULL DEFAULT NOW(),
    expires_at  TIMESTAMP      NOT NULL
);

CREATE TABLE applications
(
    id          SERIAL PRIMARY KEY,
    status      VARCHAR(10)    NOT NULL,
    job_id      INTEGER        NOT NULL REFERENCES jobs (id),
    user_id     VARCHAR(50)    NOT NULL,
    created_at  TIMESTAMP      NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP      NOT NULL DEFAULT NOW(),
    UNIQUE (job_id, user_id)
);