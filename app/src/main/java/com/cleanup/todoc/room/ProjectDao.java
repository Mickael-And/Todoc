package com.cleanup.todoc.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cleanup.todoc.model.Project;

import java.util.List;

/**
 * Dao des {@link com.cleanup.todoc.model.Project}
 */
@Dao
public interface ProjectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertProject(Project... projects);

    @Query("DELETE FROM project")
    public void deleteAllProjects();

    @Query("SELECT * FROM project")
    public LiveData<List<Project>> getProjects();

    @Query("SELECT * FROM project WHERE project_id = :projectId")
    public Project getProject(String projectId);
}
