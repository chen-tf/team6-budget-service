package budget;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Budget {

    private String yearMonth;

    private int amount;

    public Budget(String yearMonth, int amount) {
        this.yearMonth = yearMonth;
        this.amount = amount;
    }

    public Double calculateAmountBetween(LocalDate queryStartTime, LocalDate queryEndTime) {
        if (!isBetween(queryStartTime, queryEndTime)) {
            return 0.0;
        }

        return Double.valueOf(amount) * calculateBudgetDaysBetween(queryStartTime, queryEndTime)
               / getYearMonth().lengthOfMonth();
    }

    private int calculateBudgetDaysBetween(LocalDate queryStartTime, LocalDate queryEndTime) {
        final YearMonth budgetYearMonth = getYearMonth();
        final LocalDate start = YearMonth.from(queryStartTime).equals(budgetYearMonth)
                                ? queryStartTime
                                : budgetYearMonth.atDay(1);
        final LocalDate end = YearMonth.from(queryEndTime).equals(budgetYearMonth)
                              ? queryEndTime
                              : budgetYearMonth.atEndOfMonth();
        return (int) ChronoUnit.DAYS.between(start, end) + 1;
    }

    private boolean isBetween(LocalDate queryStartTime, LocalDate queryEndTime) {
        // queryStartTime <= yearMonth <= queryEndTime
        return !(getYearMonth().isBefore(YearMonth.from(queryStartTime))
                 || getYearMonth().isAfter(YearMonth.from(queryEndTime)));
    }

    private YearMonth getYearMonth() {
        return YearMonth.parse(yearMonth, DateTimeFormatter.ofPattern("yyyyMM"));
    }

    public int getAmount() {
        return amount;
    }
}
