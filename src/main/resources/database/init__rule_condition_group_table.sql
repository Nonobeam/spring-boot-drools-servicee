CREATE TABLE rule_condition_group (
    id UUID PRIMARY KEY,
    rule_definition_id UUID NOT NULL REFERENCES rule_definition(id)  ON DELETE CASCADE,
    parent_group_id UUID REFERENCES rule_condition_group(id),
    operator VARCHAR(10) NOT NULL,
    group_order INT NOT NULL
);
