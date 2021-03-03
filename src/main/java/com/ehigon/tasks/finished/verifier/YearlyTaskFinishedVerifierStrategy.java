package com.ehigon.tasks.finished.verifier;

import com.ehigon.tasks.tasks.RepeatType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.ehigon.tasks.tasks.RepeatType.YEARLY;

@Component
public class YearlyTaskFinishedVerifierStrategy extends RepeatTaskFinishedVerifierStrategyTemplate {

    @Override
    public boolean canHandle(RepeatType type) {
        return YEARLY.equals(type);
    }

    protected LocalDateTime increment(LocalDateTime date) {
        return date.plusYears(1);
    }
}
