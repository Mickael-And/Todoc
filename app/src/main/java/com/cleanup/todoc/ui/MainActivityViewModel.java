package com.cleanup.todoc.ui;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.ProjectRepository;
import com.cleanup.todoc.repository.TaskRepository;

import java.util.List;

import lombok.Getter;

public class MainActivityViewModel extends AndroidViewModel {
    private TaskRepository taskRepository;
    private ProjectRepository projectRepository;

    @Getter
    private LiveData<List<Task>> tasks;
    @Getter
    private LiveData<List<Project>> projects;

    public MainActivityViewModel(Application application) {
        super(application);
        this.taskRepository = new TaskRepository(application);
        this.projectRepository = new ProjectRepository(application);
        this.tasks = taskRepository.getTasks();
        this.projects = projectRepository.getProjects();
    }

    public void insertTask(Task task) {
        this.taskRepository.insertTask(task);
    }

    void deleteTask(Task... tasks) {
        this.taskRepository.deleteTask(tasks);
    }

    Project getProject(String projectId) {
        return this.projectRepository.getProject(projectId);
    }
}
