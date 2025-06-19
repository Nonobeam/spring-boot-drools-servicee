CREATE TABLE rule_template_version (
    id UUID PRIMARY KEY,
    template_id UUID NOT NULL REFERENCES rule_template(id) ON DELETE CASCADE,
    version INT NOT NULL,
    content TEXT NOT NULL, -- full DRL template with placeholders
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT NOW(),
    UNIQUE(template_id, version)
);
