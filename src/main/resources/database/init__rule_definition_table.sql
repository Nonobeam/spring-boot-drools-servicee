CREATE TABLE rule_definition (
     id UUID PRIMARY KEY,
     external_id VARCHAR NOT NULL,
     name VARCHAR(100) NOT NULL,
     type VARCHAR(50) NOT NULL, -- e.g., 'eligibility', 'validation'
     action VARCHAR(50) NOT NULL, -- e.g., 'APPROVED', 'REJECTED'
     priority INT NOT NULL,
     effective_start TIMESTAMP NOT NULL,
     effective_end TIMESTAMP NOT NULL,
     status VARCHAR(20) NOT NULL,
     template_version_id UUID NOT NULL REFERENCES rule_template_version(id),
     created_at TIMESTAMP DEFAULT NOW(),
     updated_at TIMESTAMP DEFAULT NOW()
);
