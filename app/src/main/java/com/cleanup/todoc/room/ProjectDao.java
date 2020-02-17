package com.cleanup.todoc.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cleanup.todoc.models.Project;

import java.util.List;

/**
 * Dao des données de type {@link com.cleanup.todoc.models.Project}
 */
@Dao
public interface ProjectDao {

    /**
     * Insertion d'un ou plusieurs {@link Project} en BDD.
     *
     * @param projects projets à insérer
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProject(Project... projects);

    /**
     * Requête de suppression des projets en BDD.
     */
    @Query("DELETE FROM project")
    void deleteAllProjects();

    /**
     * Requête pour la récupération de la liste des projets en BDD sous forme observable.
     *
     * @return la liste des projets
     */
    @Query("SELECT * FROM project")
    LiveData<List<Project>> getProjects();

    /**
     * Requête permettant de récupérer un projet en BDD depuis son identifiant.
     *
     * @param projectId id du projet à récupérer
     * @return projet récupéré
     */
    @Query("SELECT * FROM project WHERE project_id = :projectId")
    Project getProject(String projectId);
}
