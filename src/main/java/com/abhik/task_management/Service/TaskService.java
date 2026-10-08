package com.abhik.task_management.Service;

import com.abhik.task_management.Repository.TaskRepository;
import com.abhik.task_management.Repository.UserRepository;
import com.abhik.task_management.dto.TaskRequestDTO;
import com.abhik.task_management.dto.TaskResponseDTO;
import com.abhik.task_management.exception.ResourceNotFoundException;
import com.abhik.task_management.model.Task;
import com.abhik.task_management.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository repo;
    private final UserRepository userRepo;

    public TaskService(TaskRepository repo, UserRepository userRepo) {
        this.userRepo=userRepo;
        this.repo = repo;
    }

    private TaskResponseDTO mapToDTO(Task task) {
        return new TaskResponseDTO(task.getId(), task.getName(),
                task.isCompletionStatus());
    }
    private Task findTaskEntity(int id) {
        User user = getCurrentUser();
        return repo.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Task with ID " + id + " does not exist or you do not have permission."));
    }

    public List<TaskResponseDTO> getTasks() {
        User user = getCurrentUser();
        return repo.findByUserId(user.getId()).stream().map(o->mapToDTO(o)).collect(Collectors.toList());
    }

    public TaskResponseDTO getTaskbyId(int id) {
        User user = getCurrentUser();

        Task tsk=repo.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                                "Task with ID " + id + " does not exist."));
        return mapToDTO(tsk);
    }

    public TaskResponseDTO addTask(TaskRequestDTO t){
        User user = getCurrentUser();
        Task task=new Task(t.getName(),t.isCompletionStatus());
        user.addTask(task);
        Task tsk= repo.save(task);
        return mapToDTO(tsk);
    }

    public TaskResponseDTO updateTask(int id, TaskRequestDTO updated){
        Task t=findTaskEntity(id);
        t.setName(updated.getName());
        t.setCompletionStatus(updated.isCompletionStatus());
        Task tsk=repo.save(t);
        return mapToDTO(tsk);
    }

    public void deleteTask(int id){
        Task t=findTaskEntity(id);
        repo.delete(t);
    }

    public List<Task> getTasksByStatus(boolean status) {
        return repo.findByCompletionStatus(status);
    }

    public List<Task> searchTasksByName(String keyword) {
        return repo.findByNameContainingIgnoreCase(keyword);
    }

    public Page<Task> getTasksPaginated(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return repo.findAll(pageable);
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
