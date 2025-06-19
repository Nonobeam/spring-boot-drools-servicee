package per.nonobeam.rules.web.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import per.nonobeam.rules.web.model.request.LoanRequest;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanResponse {
    private LoanRequest request;
    private List<String> firedRules;
    private List<String> logs;
}
