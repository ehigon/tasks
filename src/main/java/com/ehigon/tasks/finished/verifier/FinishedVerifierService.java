package com.ehigon.tasks.finished.verifier;

import com.ehigon.tasks.error.InternalException;
import com.ehigon.tasks.tasks.RepeatType;
import com.ehigon.tasks.tasks.Task;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class FinishedVerifierService {

    private final Set<TaskFinishedVerifierStrategy> taskFinishedVerifierStrategies;

    public FinishedVerifierService(Set<TaskFinishedVerifierStrategy> taskFinishedVerifierStrategies) {
        this.taskFinishedVerifierStrategies = taskFinishedVerifierStrategies;
    }

    public boolean isFinished(Task task) {

        TaskFinishedVerifierStrategy taskFinishedVerifierStrategy = findStrategy(task.getRepeatType());

        return taskFinishedVerifierStrategy.isFinished(task);
    }

    private TaskFinishedVerifierStrategy findStrategy(RepeatType repeatType) {
        Optional<TaskFinishedVerifierStrategy> optTaskFinishedVerifierStrategy = taskFinishedVerifierStrategies.stream()
                .filter(fvs -> fvs.canHandle(repeatType))
                .findAny();

        if(!optTaskFinishedVerifierStrategy.isPresent()) {
            throw new InternalException();
        }

        return optTaskFinishedVerifierStrategy.get();
    }

}
