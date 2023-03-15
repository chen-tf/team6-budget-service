/*
 * Copyright (c) 2023 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package budget;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BudgetServiceTest {

    private BudgetService budgetService;

    private BudgetRepository budgetRepository;

    @BeforeEach
    void setUp() {
        budgetRepository = Mockito.mock(BudgetRepository.class);
        budgetService = new BudgetService(budgetRepository);
    }

    @Test
    void end_time_before_start_time() {
        budgetBetweenShouldBe(LocalDate.of(2023, 1, 31),
                              LocalDate.of(2023, 1, 1),
                              0);
    }

    @Test
    void full_month() {
        givenBudget(new Budget("202301", 310));
        budgetBetweenShouldBe(LocalDate.of(2023, 1, 1),
                              LocalDate.of(2023, 1, 31),
                              310.00);
    }

    private void budgetBetweenShouldBe(LocalDate start, LocalDate end, double expectedAmount) {
        final double actual = budgetService.query(start,
                                                  end);
        assertThat(actual).isEqualTo(expectedAmount);
    }

    @Test
    void part_month() {
        givenBudget(new Budget("202301", 310));
        budgetBetweenShouldBe(LocalDate.of(2023, 1, 1),
                              LocalDate.of(2023, 1, 10),
                              100.00);
    }

    @Test
    void one_day() {
        givenBudget(new Budget("202303", 31));
        budgetBetweenShouldBe(LocalDate.of(2023, 3, 1),
                              LocalDate.of(2023, 3, 1),
                              1.00);
    }

    @Test
    void cross_month() {
        givenBudget(new Budget("202303", 31),
                    new Budget("202304", 300));
        budgetBetweenShouldBe(LocalDate.of(2023, 3, 31),
                              LocalDate.of(2023, 4, 3),
                              31.00);
    }

    private void givenBudget(Budget... budget) {
        when(budgetRepository.getAll())
                .thenReturn(budget);
    }
}
