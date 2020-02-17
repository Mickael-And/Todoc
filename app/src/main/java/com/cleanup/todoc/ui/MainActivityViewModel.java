package com.cleanup.todoc.ui;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

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
public class MainActivityViewModel extends AndroidViewModel {

    /**
     * Repository des tâches.
     */
    private TaskRepository taskRepository;

    /**
     * Repository des projets.
     */
    private ProjectRepository projectRepository;

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

    /**
     * Permet l'éxecution de manière asynchrone des requêtes en BDD.
     */
    private final Executor executor;

    public MainActivityViewModel(Application application, Executor pExecutor) {
        super(application);
        this.taskRepository = new TaskRepository(application);
        this.projectRepository = new ProjectRepository(application);
        this.tasks = taskRepository.getTasks();
        this.projects = projectRepository.getProjects();
        this.executor = pExecutor;
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
