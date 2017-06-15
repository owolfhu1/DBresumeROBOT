CREATE SCHEMA resume_db;

CREATE TABLE resumes (
    id TEXT,
    user_name TEXT,
    email TEXT,
    pass TEXT
);

CREATE TABLE edu (
    resume_id TEXT,
    school TEXT,
    degree TEXT
);

CREATE TABLE work_xp (
    id TEXT,
    resume_id TEXT,
    company TEXT,
    title TEXT
);

CREATE TABLE work_tasks (
    work_xp_id TEXT,
    task TEXT
);

CREATE TABLE skills (
    resume_id TEXT,
    skill TEXT,
    proficiency TEXT
);

