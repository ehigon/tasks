package com.ehigon.tasks.finished.verifier;

import com.ehigon.tasks.tasks.RepeatType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.ehigon.tasks.tasks.RepeatType.DAILY;

@Component
public class DaylyTaskFinishedVerifierStrategy extends RepeatTaskFinishedVerifierStrategyTemplate {

    @Override
    public boolean canHandle(RepeatType type) {
        return DAILY.equals(type);
    }

    protected LocalDateTime increment(LocalDateTime date) {
        return date.plusDays(1);
    }
}
