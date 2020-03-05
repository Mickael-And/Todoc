package com.cleanup.todoc.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.models.Project;
import com.cleanup.todoc.models.Task;
import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;
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
     * Liste des projets.
     */
    @Getter
    private LiveData<List<Project>> projects;

    /**
     * Liste des tâches.
     */
    @Getter
    private LiveData<List<Task>> tasks;

    public MainActivityViewModel(TaskRepository pTaskRepository, ProjectRepository pProjectRepository) {
        this.taskRepository = pTaskRepository;
        this.projectRepository = pProjectRepository;
        this.tasks = this.taskRepository.getTasks();
        this.projects = this.projectRepository.getProjects();
    }

    /**
     * Insère une ou plusieurs tâches à la liste des tâches.
     *
     * @param task tâches à insérer
     * @return la liste des identifiants des tâches insérées en BDD
     */
    public Single<List<Long>> insertTask(Task task) {
        return this.taskRepository.insertTask(task);
    }

    /**
     * Supprime une ou plusieurs tâches de la liste des tâches.
     *
     * @param tasks tâches à supprimer.
     * @return le nombre de tâche(s) supprimée(s)
     */
    public Single<Integer> deleteTask(Task... tasks) {
        return this.taskRepository.deleteTask(tasks);
    }

    /**
     * Récupère un projet depuis son identifiant.
     *
     * @param projectId identifiant du projet à récupérer
     * @return projet récupéré
     */
    public Maybe<Project> getProject(String projectId) {
        return this.projectRepository.getProject(projectId);
    }
}
