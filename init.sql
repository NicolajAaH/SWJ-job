CREATE TABLE jobs
(
    id          SERIAL PRIMARY KEY,
    title       VARCHAR(255)   NOT NULL,
    description TEXT           NOT NULL,
    company     VARCHAR(255)   NOT NULL,
    location    VARCHAR(255)   NOT NULL,
    salary      DECIMAL(10, 2) NOT NULL,
    job_type    VARCHAR(10)    NOT NULL,
    created_at  TIMESTAMP      NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP      NOT NULL DEFAULT NOW(),
    expires_at  TIMESTAMP      NOT NULL DEFAULT NOW()+INTERVAL '30 days'
);