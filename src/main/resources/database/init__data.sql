INSERT INTO drools_service.rule_template (id, name, description, created_at) VALUES ('b81ccd91-6e48-4dcc-a1db-395823f3e21e', 'Booking Rule Template', 'Template for salon booking rules', '2025-07-05 06:19:21.390705');

INSERT INTO drools_service.rule_template_version (id, template_id, version, content, is_active, created_at) VALUES ('c1e2f3d4-5678-90ab-cdef-1234567890ab', 'b81ccd91-6e48-4dcc-a1db-395823f3e21e', 1, e'package per.nonobeam.rules;

import per.nonobeam.rules.web.model.request.IncomingEvent;
import per.nonobeam.rules.EligibilityUnit;

rule "Dynamic Rule - ${name}"
salience ${priority}
when
	$unit : EligibilityUnit()
    $req: IncomingEvent(${conditions})
then
    $unit.getLogs().add("Dynamic Rule - ${name}");
    $unit.setTotalScore($unit.getTotalScore() + 1);
end', true, '2025-07-05 06:19:21.443587');
