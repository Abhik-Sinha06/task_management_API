package com.abhik.task_management.Controller;

import com.abhik.task_management.Service.TaskService;
import com.abhik.task_management.model.Task;
import com.abhik.task_management.dto.TaskRequestDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {
    private final TaskService ts;

    public TaskController(TaskService ts) {  //constructor injection
        this.ts = ts;
    }

    @GetMapping("/tasks")
    List<Task> fetch(){
        return ts.getTasks();
    }

    @GetMapping("/tasks/{id}")
    ResponseEntity<Task> getSpecificTask(@PathVariable int id){
        Task t=ts.getTaskbyId(id);
        return ResponseEntity.ok(t);
    }

    @PostMapping("/tasks")
    ResponseEntity<Task> addTask(@Valid @RequestBody TaskRequestDTO tsk){
        Task t=ts.addTask(tsk);
        return ResponseEntity.status(HttpStatus.CREATED).body(t);
    }

    @PutMapping("/tasks/{id}")
    ResponseEntity<Task> updateTask(@PathVariable int id,@Valid @RequestBody TaskRequestDTO updatedTsk){
        Task t=ts.updateTask(id,updatedTsk);
        return ResponseEntity.ok(t);
    }

    @DeleteMapping("/tasks/{id}")
    ResponseEntity<String> deleteTask(@PathVariable int id){
        ts.deleteTask(id);
        return ResponseEntity.ok("Task with id "+id+" deleted successfully");
    }

    // GET /tasks/status?completed=true
    @GetMapping("/tasks/status")
    public ResponseEntity<List<Task>> getByStatus(@RequestParam boolean completed) {
        return ResponseEntity.ok(ts.getTasksByStatus(completed));
    }

    // GET /tasks/search?keyword=gym
    @GetMapping("/tasks/search/{keyword}")
    public ResponseEntity<List<Task>> searchTasks(@PathVariable String keyword) {
        return ResponseEntity.ok(ts.searchTasksByName(keyword));
    }

    // GET /tasks/pages?page=0&size=3&sortBy=name
    @GetMapping("/pages")
    public ResponseEntity<Page<Task>> getTasksPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return ResponseEntity.ok(ts.getTasksPaginated(page, size, sortBy));
    }
}
