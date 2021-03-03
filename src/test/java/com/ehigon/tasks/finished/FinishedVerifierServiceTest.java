package com.ehigon.tasks.finished;

import com.ehigon.tasks.finished.verifier.FinishedVerifierService;
import com.ehigon.tasks.tasks.RepeatType;
import com.ehigon.tasks.tasks.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static com.ehigon.tasks.tasks.RepeatType.*;

@SpringBootTest
public class FinishedVerifierServiceTest {

    private static final String NO_FINISHED_MESSAGE = "Task without Finished shouldn't be finished";
    private static final String FINISHED_MESSAGE = "Task with Finished should be finished";
    private static final String ACTUAL_FINISHED_MESSAGE = "Task with actual Finished should be finished";
    private static final String NON_ACTUAL_FINISHED_MESSAGE = "Task with non actual Finished shouldn't be finished";

    @Autowired
    private FinishedVerifierService service;

    @Test
    public void given_noneTaskWithoutFinished_when_verifyIsFinished_then_isNotFinished() {
        testTaskWithoutFinished(NONE, 2L, NO_FINISHED_MESSAGE);
    }

    @Test
    public void given_noneTaskWithFinished_when_verifyIsFinished_then_isFinished() {
        testTaskWithFinished(NONE, 2L, 1L, FINISHED_MESSAGE);
    }

    @Test
    public void given_dailyTaskWithoutFinished_when_verifyIsFinished_then_isNotFinished() {
        testTaskWithoutFinished(DAILY, 2L, NO_FINISHED_MESSAGE);
    }

    @Test
    public void given_dailyTaskWithActualFinished_when_verifyIsFinished_then_isFinished() {
        testTaskWithFinished(DAILY, 27L, 2L, ACTUAL_FINISHED_MESSAGE);
    }

    @Test
    public void given_dailyTaskWithNonActualFinished_when_verifyIsFinished_then_isNotFinished() {
        testTaskWithNonActualFinished(DAILY, 27L, 26L, NON_ACTUAL_FINISHED_MESSAGE);
    }

    @Test
    public void given_weeklyTaskWithoutFinished_when_verifyIsFinished_then_isNotFinished() {
        testTaskWithoutFinished(WEEKLY, 2L, NO_FINISHED_MESSAGE);
    }

    @Test
    public void given_weeklyTaskWithActualFinished_when_verifyIsFinished_then_isFinished() {
        testTaskWithFinished(WEEKLY, 180L, 2L, ACTUAL_FINISHED_MESSAGE);
    }

    @Test
    public void given_weeklyTaskWithNonActualFinished_when_verifyIsFinished_then_isNotFinished() {
        testTaskWithNonActualFinished(WEEKLY, 180L, 178L, NON_ACTUAL_FINISHED_MESSAGE);
    }

    @Test
    public void given_monthlyTaskWithoutFinished_when_verifyIsFinished_then_isNotFinished() {
        testTaskWithoutFinished(MONTHLY, 2L, NO_FINISHED_MESSAGE);
    }

    @Test
    public void given_monthlyTaskWithActualFinished_when_verifyIsFinished_then_isFinished() {
        testTaskWithFinished(MONTHLY, 792L, 2L, ACTUAL_FINISHED_MESSAGE);
    }

    @Test
    public void given_monthlyTaskWithNonActualFinished_when_verifyIsFinished_then_isNotFinished() {
        testTaskWithNonActualFinished(MONTHLY, 792L, 790L, NON_ACTUAL_FINISHED_MESSAGE);
    }

    @Test
    public void given_yearlyTaskWithoutFinished_when_verifyIsFinished_then_isNotFinished() {
        testTaskWithoutFinished(YEARLY, 2L, NO_FINISHED_MESSAGE);
    }

    @Test
    public void given_yearlyTaskWithActualFinished_when_verifyIsFinished_then_isFinished() {
        testTaskWithFinished(YEARLY, 8880L, 2L, ACTUAL_FINISHED_MESSAGE);
    }

    @Test
    public void given_yearlyTaskWithNonActualFinished_when_verifyIsFinished_then_isNotFinished() {
        testTaskWithNonActualFinished(YEARLY, 8880L, 8875L, NON_ACTUAL_FINISHED_MESSAGE);
    }

    private void testTaskWithoutFinished(RepeatType type, long taskHourAgo, String assertMessage) {
        Task task = createTask(type, taskHourAgo);

        boolean finished = service.isFinished(task);

        Assertions.assertFalse(finished, assertMessage);
    }

    private void testTaskWithFinished(RepeatType type, long taskHourAgo, long finishedHourAgo, String assertMessage) {
        Task task = createTaskWithFinished(type, taskHourAgo, finishedHourAgo);

        boolean finished = service.isFinished(task);

        Assertions.assertTrue(finished, assertMessage);
    }

    private void testTaskWithNonActualFinished(RepeatType type, long taskHourAgo, long finishedHourAgo, String assertMessage) {
        Task task = createTaskWithFinished(type, taskHourAgo, finishedHourAgo);

        boolean finished = service.isFinished(task);

        Assertions.assertFalse(finished, assertMessage);
    }


    private Task createTaskWithFinished(RepeatType type, Long taskHoursAgo, Long finishedHoursAgo) {
        Task task = createTask(type, taskHoursAgo);
        Finished finished = Finished.builder()
                .finishDate(LocalDateTime.now().minusHours(finishedHoursAgo))
                .task(task)
                .build();
        task.getFinished().add(finished);
        return task;
    }

    private Task createTask(RepeatType type, Long taskHoursAgo) {
        return Task.builder()
                .title("title")
                .details("details")
                .date(LocalDateTime.now().minusHours(taskHoursAgo))
                .repeatType(type)
                .build();
    }

}
