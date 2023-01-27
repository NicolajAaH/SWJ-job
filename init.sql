CREATE TABLE companies
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255)   NOT NULL,
    website     VARCHAR(255)   NOT NULL,
    email       VARCHAR(255)   NOT NULL,
    created_at  TIMESTAMP      NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP      NOT NULL DEFAULT NOW(),
    UNIQUE (name)
);

CREATE TABLE jobs
(
    id          SERIAL PRIMARY KEY,
    title       VARCHAR(255)   NOT NULL,
    description TEXT           NOT NULL,
    company_id  INTEGER        NOT NULL REFERENCES companies (id),
    location    VARCHAR(255)   NOT NULL,
    salary      DECIMAL(10, 2) NOT NULL,
    job_type    VARCHAR(10)    NOT NULL,
    created_at  TIMESTAMP      NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP      NOT NULL DEFAULT NOW(),
    expires_at  TIMESTAMP      NOT NULL DEFAULT NOW()+INTERVAL '30 days'
);

CREATE TABLE users
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255)   NOT NULL,
    email       VARCHAR(255)   NOT NULL,
    password    VARCHAR(255)   NOT NULL,
    created_at  TIMESTAMP      NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP      NOT NULL DEFAULT NOW()
);

CREATE TABLE applications
(
    id          SERIAL PRIMARY KEY,
    job_id      INTEGER        NOT NULL REFERENCES jobs (id),
    user_id     INTEGER        NOT NULL REFERENCES users (id),
    created_at  TIMESTAMP      NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP      NOT NULL DEFAULT NOW(),
    UNIQUE (job_id, user_id)
);