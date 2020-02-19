package com.cleanup.todoc.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.models.Project;
import com.cleanup.todoc.models.Task;
import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;

import java.util.List;
import java.util.concurrent.Executor;

import lombok.Getter;

/**
 * ViewModel de la {@link MainActivity}.
 */
public class MainActivityViewModel extends ViewModel {

    /**
     * Repository des tâches.
     */
    private final TaskRepository taskRepository;

    /**
     * Repository des projets.
     */
    private final ProjectRepository projectRepository;

    /**
     * Permet l'éxecution de manière asynchrone des requêtes en BDD.
     */
    private final Executor executor;

    /**
     * Liste des tâches.
     */
    @Getter
    private LiveData<List<Task>> tasks;

    /**
     * Miste des projets.
     */
    @Getter
    private LiveData<List<Project>> projects;

    public MainActivityViewModel(TaskRepository pTaskRepository, ProjectRepository pProjectRepository, Executor pExecutor) {
        this.taskRepository = pTaskRepository;
        this.projectRepository = pProjectRepository;
        this.executor = pExecutor;
        this.tasks = taskRepository.getTasks();
        this.projects = projectRepository.getProjects();
    }

    /**
     * Insère une ou plusieurs tâches à la liste des tâches.
     *
     * @param task tâches à insérer
     */
    void insertTask(Task task) {
        executor.execute(() -> this.taskRepository.insertTask(task));
    }

    /**
     * Supprime une ou plusieurs tâches de la liste des tâches.
     *
     * @param tasks tâches à supprimer.
     */
    void deleteTask(Task... tasks) {
        executor.execute(() -> this.taskRepository.deleteTask(tasks));
    }

    /**
     * Récupère un projet depuis son identifiant.
     *
     * @param projectId identifiant du projet à récupérer
     * @return projet récupéré
     */
    Project getProject(String projectId) {
        return this.projectRepository.getProject(projectId);
    }
}
