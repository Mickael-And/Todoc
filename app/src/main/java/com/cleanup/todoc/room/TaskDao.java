package com.cleanup.todoc.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cleanup.todoc.model.Task;

import java.util.List;
import java.util.UUID;

/**
 * Dao des {@link com.cleanup.todoc.model.Task}
 */
@Dao
public interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertTask(Task... tasks);

    @Delete
    public void deleteTask(Task... tasks);

    @Query("DELETE FROM task")
    public void deleteAllTasks();

    @Query("SELECT * FROM task")
    public LiveData<List<Task>> getTasks();

    @Query("SELECT * FROM task WHERE task_id = :projectId")
    public List<Task> getTasksByProjectId(String projectId);

}
