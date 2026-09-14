package com.abhik.task_management.Service;

import com.abhik.task_management.Repository.TaskRepository;
import com.abhik.task_management.Repository.UserRepository;
import com.abhik.task_management.model.Task;
import com.abhik.task_management.model.User;
import com.abhik.task_management.dto.TaskRequestDTO;
import com.abhik.task_management.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TaskService {

    private final TaskRepository repo;
    private final UserRepository userRepo;

    public TaskService(TaskRepository repo, UserRepository userRepo) {
        this.userRepo=userRepo;
        this.repo = repo;
    }

    public List<Task> getTasks(){
        return repo.findAll();
    }

    public Task getTaskbyId(int id){
        return repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Task with ID "+id+" does not exist."));
    }

    public Task addTask(int userId, TaskRequestDTO t){
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Task task=new Task(t.getName(),t.isCompletionStatus());
        user.addTask(task);
        return repo.save(task);
    }

    public Task updateTask(int id, TaskRequestDTO updated){
        Task t=getTaskbyId(id);
        t.setName(updated.getName());
        t.setCompletionStatus(updated.isCompletionStatus());
        return repo.save(t);
    }

    public void deleteTask(int id){
        if(!repo.existsById(id))
            throw new ResourceNotFoundException("Task with ID: " + id + " not found");
        repo.deleteById(id);
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
}
