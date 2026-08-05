CREATE TABLE roles(
    id UUID PRIMARY KEY,
    code VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uk_roles_code UNIQUE (code),
    CONSTRAINT ck_roles_code_not_blank
                  CHECK ( char_length(trim(code)) > 0 ),
    CONSTRAINT ck_roles_name_not_blankk
                  CHECK ( char_length(trim(name)) > 0 )
);