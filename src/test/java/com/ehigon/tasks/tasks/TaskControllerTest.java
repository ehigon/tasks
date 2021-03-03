package com.ehigon.tasks.tasks;

import com.ehigon.tasks.finished.FinishedRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.ehigon.tasks.tasks.RepeatType.NONE;
import static com.ehigon.tasks.tasks.RepeatType.WEEKLY;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class TaskControllerTest {

    private static int TOTAL_TASKS = 4;

    @Autowired
    private TaskRepository repository;

    @Autowired
    private FinishedRepository finishedRepository;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    ObjectMapper mapper;

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

    @Test
    public void given_taskModel_when_createTask_then_taskIsCreated() throws Exception {
        TaskModel taskModel = createTaskModel();

        mockMvc.perform(post("/tasks/")
                .contentType("application/json")
                .content(serialize(taskModel)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(taskModel.getTitle()))
                .andExpect(jsonPath("$.details").value(taskModel.getDetails()))
                .andExpect(jsonPath("$.repeatType").value(taskModel.getRepeatType().toString()))
                .andExpect(jsonPath("$.date").value(serializeDate(taskModel.getDate())));
    }

    @Test
    @Transactional
    public void given_task_when_updateTask_then_taskIsUpdated() throws Exception {
        Task task = getOneTask();
        TaskModel taskModel = createTaskModel();

        mockMvc.perform(put("/tasks/" + task.getId())
                .contentType("application/json")
                .content(serialize(taskModel)))
                .andDo(print())
                .andExpect(status().isOk());

        Task updatedTask = repository.getOne(task.getId());
        Assertions.assertEquals(taskModel.getDetails(), updatedTask.getDetails());
        Assertions.assertEquals(taskModel.getDate(), updatedTask.getDate());
        Assertions.assertEquals(taskModel.getRepeatType(), updatedTask.getRepeatType());
        Assertions.assertEquals(taskModel.getTitle(), updatedTask.getTitle());
    }

    @Test
    @Transactional
    public void given_task_when_updateTaskAsPatch_then_taskIsUpdated() throws Exception {
        Task task = getOneTask();
        String details = "Finish Mimacom Technical Challenge including tests and readme file";

        mockMvc.perform(patch("/tasks/" + task.getId())
                .contentType("application/json")
                .content("{\"details\" : \"" + details + "\"}"))
                .andDo(print())
                .andExpect(status().isOk());

        Task updatedTask = repository.getOne(task.getId());
        Assertions.assertEquals(details, updatedTask.getDetails());
    }

    @Test
    public void given_task_when_setAsFinished_then_taskIsFinished() throws Exception {
        Task task = getOneTask();

        mockMvc.perform(patch("/tasks/" + task.getId())
                .contentType("application/json")
                .content("{\"finished\" : \"true\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.finished").value(true));
    }

    @Test
    @Transactional
    public void given_task_when_deleteTask_then_taskIsDeleted() throws Exception {
        Task task = getOneTask();

        mockMvc.perform(delete("/tasks/" + task.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());

        Optional<Task> optTask = repository.findById(task.getId());
        Assertions.assertFalse(optTask.isPresent(), "Deleted task shouldn't exist");
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
                .date(LocalDateTime.now().minusMinutes(1L))
                .repeatType(type)
                .build();
        repository.save(task);
    }

    private void wipeTasks() {
        finishedRepository.deleteAll();
        repository.deleteAll();
    }

    private TaskModel createTaskModel() {
        return TaskModel.builder()
                .repeatType(WEEKLY)
                .title("Mimacom Challenge")
                .details("Finish Mimacom Technical Challenge including tests")
                .date(LocalDateTime.now().plusDays(7).minusHours(1))
                .build();
    }

    private String serializeDate(LocalDateTime date) throws JsonProcessingException {
        return serialize(date).replace("\"", "");
    }

    private String serialize(Object o) throws JsonProcessingException {
        return mapper.writeValueAsString(o);
    }

}
