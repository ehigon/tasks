package com.ehigon.tasks.tasks;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.Date;
import static com.ehigon.tasks.tasks.RepeatType.*;
import static org.hamcrest.Matchers.anything;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class TaskControllerTest {

    private static int TOTAL_TASKS = 4;

    @Autowired
    private TaskRepository repository;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc();
        createTasks();
    }

    @AfterEach
    public void cleanUp() {
        wipeTasks();
    }

    @Test
    public void given_listOfTasks_when_getTasks_then_allTasksAreReturned() throws Exception {
        mockMvc.perform(get("/tasks"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value(TOTAL_TASKS));
    }

    @Test
    public void given_task_when_getTaskById_then_taskIsReturned() throws Exception {
        Task task = getOneTask();

        mockMvc.perform(get("/tasks/" + task.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(task.getTitle()))
                .andExpect(jsonPath("$.details").value(task.getDetails()));
    }

    private Task getOneTask() {
        return repository.findAll().get(0);
    }

    private void mockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    private void createTasks() {
        for(int i=0; i<TOTAL_TASKS; i++) {
            createTask("title" + i, "details" + i, NONE);
        }
    }

    private void createTask(String title, String details, RepeatType type) {
        Task task = Task.builder()
                .title(title)
                .details(details)
                .date(new Date())
                .repeatType(type)
                .build();
        repository.save(task);
    }

    private void wipeTasks() {
        repository.deleteAll();
    }

}
