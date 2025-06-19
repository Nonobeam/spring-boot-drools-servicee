package per.nonobeam.rules.web.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import per.nonobeam.rules.web.model.core.LoanDecision;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanRequest {
    private int age;
    private double salary;
    private double loanAmount;
    private double creditScore;
    private int loanTermMonths;
    private String employmentType;
    private double yearsEmployed;
    private boolean hasDefaultHistory;
    private double currentDebt;
    private double monthlyExpenses;
    private String maritalStatus;
    private int numberOfDependents;
    private String educationLevel;
    private String homeOwnership;
    private boolean isFirstTimeBorrower;
    private String region;
    private String purposeOfLoan;
    private double requestedInterestRate;
    private double employerReputation; // 0-100
    private double assetValue;

    private LoanDecision decision;
}
