package com.cleanup.todoc.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.models.Project;
import com.cleanup.todoc.room.AppDataBase;
import com.cleanup.todoc.room.ProjectDao;

import java.util.List;

import lombok.Getter;

/**
 * Repository permettant la manipulation des données de type {@link Project}
 */
public class ProjectRepository {

    /**
     * Dao pour les données de type {@link Project}
     */
    private ProjectDao projectDao;

    /**
     * Liste des projets observés.
     */
    @Getter
    private LiveData<List<Project>> projects;

    public ProjectRepository(Application application) {
        this.projectDao = AppDataBase.getInstance(application).projectDao();
        this.projects = projectDao.getProjects();
    }

    /**
     * Permet de réupérer un projet grâce à son identifiant depuis la BDD.
     *
     * @param projectId identifiant du projet à récupérer.
     * @return projet récupéré
     */
    public Project getProject(String projectId) {
        return this.projectDao.getProject(projectId);
    }
}