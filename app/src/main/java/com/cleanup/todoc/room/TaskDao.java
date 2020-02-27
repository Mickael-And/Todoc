package com.cleanup.todoc.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cleanup.todoc.models.Task;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * Dao des données de type {@link com.cleanup.todoc.models.Task}
 */
@Dao
public interface TaskDao {

    /**
     * Permet l'insertion en BDD d'une ou plusieurs {@link Task}
     *
     * @param tasks tâches à insérer
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<List<Long>> insertTask(Task... tasks);

    /**
     * Permet la suppression d'une ou plusieurs tâches en BDD.
     *
     * @param tasks tâches à supprimer
     */
    @Delete
    Single<Integer> deleteTask(Task... tasks);

    /**
     * Requête pour la récupération de la liste des tâches en BDD sous forme observable.
     *
     * @return la liste des tâches
     */
    @Query("SELECT * FROM task")
    LiveData<List<Task>> getTasks();
}
