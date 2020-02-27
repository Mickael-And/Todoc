package com.cleanup.todoc.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.models.Project;
import com.cleanup.todoc.room.AppDataBase;
import com.cleanup.todoc.room.ProjectDao;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
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

    public ProjectRepository(Context pContext) {
        this.projectDao = AppDataBase.getInstance(pContext).projectDao();
        this.projects = this.projectDao.getProjects();
    }

    /**
     * Permet de réupérer un projet grâce à son identifiant depuis la BDD.
     *
     * @param projectId identifiant du projet à récupérer.
     * @return projet récupéré
     */
    public Maybe<Project> getProject(String projectId) {
        return this.projectDao.getProjectById(projectId);
    }
}
