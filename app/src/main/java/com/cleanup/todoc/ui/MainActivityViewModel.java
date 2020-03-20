package com.cleanup.todoc.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.models.Project;
import com.cleanup.todoc.models.Task;
import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;

import java.util.ArrayList;
import java.util.Collections;
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

    /**
     * Tri les tâches par nom de projet de A à Z.
     *
     * @param tasks tâches à trier.
     * @return tâches triées par nom de projet de A à Z
     */
    List<Task> getTasksByAZProject(List<Task> tasks) {
        List<Task> orderedTasks = new ArrayList<>();
        if (this.projects.getValue() != null) {
            List<Project> projects = this.projects.getValue();
            Collections.sort(projects, new Project.ProjectAZComparator());
            Collections.sort(tasks, new Task.TaskOldComparator());
            this.sortTasksByProjects(projects, tasks, orderedTasks);
        } else {
            orderedTasks = tasks;
        }
        return orderedTasks;
    }

    /**
     * Tri les tâches par nom de projet de Z à A.
     *
     * @param tasks tâches à trier.
     * @return tâches triées par nom de projet de Z à A
     */
    List<Task> getTasksByZAProject(List<Task> tasks) {
        List<Task> orderedTasks = new ArrayList<>();
        if (this.projects.getValue() != null) {
            List<Project> projects = this.projects.getValue();
            Collections.sort(projects, new Project.ProjectZAComparator());
            Collections.sort(tasks, new Task.TaskOldComparator());
            this.sortTasksByProjects(projects, tasks, orderedTasks);
        } else {
            orderedTasks = tasks;
        }
        return orderedTasks;
    }

    /**
     * Tri les tâches par projets.
     *
     * @param pProjects     projets pour le tri
     * @param pTasks        tâches à trier
     * @param pOrderedTasks liste pour les tâches triées
     */
    private void sortTasksByProjects(List<Project> pProjects, List<Task> pTasks, List<Task> pOrderedTasks) {
        for (Project project : pProjects) {
            for (Task task : pTasks) {
                if (task.getProjectId().equals(project.getId())) {
                    pOrderedTasks.add(task);
                }
            }
        }
    }
}
