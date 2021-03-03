package com.ehigon.tasks.finished.verifier;

import com.ehigon.tasks.finished.Finished;
import com.ehigon.tasks.tasks.Task;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;

public abstract class RepeatTaskFinishedVerifierStrategyTemplate implements TaskFinishedVerifierStrategy{

    @Override
    public boolean isFinished(Task task) {
        Optional<LocalDateTime> optLastRepeatFinished = getLastRepeatFinished(task);
        if(!optLastRepeatFinished.isPresent()) {
            return false;
        }
        LocalDateTime lastRepeatDate = getLastRepeatDate(task);
        return optLastRepeatFinished.get().isAfter(lastRepeatDate);
    }

    private LocalDateTime getLastRepeatDate(Task task) {
        LocalDateTime lastRepeat = task.getDate();
        LocalDateTime now = LocalDateTime.now();
        while(increment(lastRepeat).isBefore(now)) {
            lastRepeat = increment(lastRepeat);
        }
        return lastRepeat;
    }

    protected abstract LocalDateTime increment(LocalDateTime date);

    private Optional<LocalDateTime> getLastRepeatFinished(Task task) {
        return task.getFinished().stream()
                .min(Comparator.comparing(Finished::getFinishDate))
                .map(Finished::getFinishDate);
    }
}
