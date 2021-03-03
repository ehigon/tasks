package com.ehigon.tasks.finished.verifier;

import com.ehigon.tasks.tasks.RepeatType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.ehigon.tasks.tasks.RepeatType.WEEKLY;

@Component
public class WeeklyTaskFinishedVerifierStratgy extends RepeatTaskFinishedVerifierStrategyTemplate {

    @Override
    public boolean canHandle(RepeatType type) {
        return WEEKLY.equals(type);
    }

    @Override
    protected LocalDateTime increment(LocalDateTime date) {
        return date.plusWeeks(1L);
    }
}
