package com.ehigon.tasks.finished.verifier;

import com.ehigon.tasks.tasks.RepeatType;
import com.ehigon.tasks.tasks.Task;
import org.springframework.stereotype.Component;
import static com.ehigon.tasks.tasks.RepeatType.NONE;

@Component
public class NoneTaskFinishedVerifierStrategy implements TaskFinishedVerifierStrategy {

    @Override
    public boolean canHandle(RepeatType type) {
        return NONE.equals(type);
    }

    @Override
    public boolean isFinished(Task task) {
        return !task.getFinished().isEmpty();
    }
}
