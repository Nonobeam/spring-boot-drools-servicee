CREATE TABLE rule_condition (
    id UUID PRIMARY KEY,
    group_id UUID NOT NULL REFERENCES rule_condition_group(id) ON DELETE CASCADE,
    left_operand VARCHAR(100) NOT NULL,

    operator VARCHAR(5) NOT NULL CHECK (operator IN ('==', '!=', '<', '>', '<=', '>=')),

    right_operand VARCHAR(100) NOT NULL,
    data_type VARCHAR(20) NOT NULL CHECK (data_type IN ('string', 'number', 'boolean', 'variable')),

    condition_order INT NOT NULL
);
