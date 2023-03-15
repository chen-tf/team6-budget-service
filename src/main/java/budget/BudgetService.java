package budget;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class BudgetService {

    private BudgetRepository budgetRepository;

    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    public double query(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            return 0;
        }

        return Arrays.stream(budgetRepository.getAll())
                     .mapToDouble(budget -> budget.getAmountBetween(start, end))
                     .sum();
    }

}
