package com.abhik.task_management.Repository;

import com.abhik.task_management.model.Task;
import com.abhik.task_management.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task,Integer> {
    List<Task> findByCompletionStatus(Boolean status);

    List<Task> findByNameContainingIgnoreCase(String keyword);

    List<Task> findByUserId(Integer userId);

    Optional<Task> findByIdAndUserId(Integer id, Integer userId);

    Page<Task> findByUser(User user, Pageable pageable);
}
