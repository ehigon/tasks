package com.ehigon.tasks.finished.verifier;

import com.ehigon.tasks.tasks.RepeatType;
import com.ehigon.tasks.tasks.Task;

public interface TaskFinishedVerifierStrategy {

    boolean canHandle(RepeatType type);

    boolean isFinished(Task task);

}
