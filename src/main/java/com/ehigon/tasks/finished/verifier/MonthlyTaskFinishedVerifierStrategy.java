package com.ehigon.tasks.finished.verifier;

import com.ehigon.tasks.tasks.RepeatType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.ehigon.tasks.tasks.RepeatType.MONTHLY;

@Component
public class MonthlyTaskFinishedVerifierStrategy extends RepeatTaskFinishedVerifierStrategyTemplate {

    @Override
    public boolean canHandle(RepeatType type) {
        return MONTHLY.equals(type);
    }

    @Override
    protected LocalDateTime increment(LocalDateTime date) {
        return date.plusMonths(1);
    }
}
