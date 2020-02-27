package com.cleanup.todoc.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.models.Task;
import com.cleanup.todoc.room.AppDataBase;
import com.cleanup.todoc.room.TaskDao;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;
import lombok.Getter;

/**
 * Repository permettant la manipulation des données de type {@link Task}
 */
public class TaskRepository {

    /**
     * DAO pour les données de type {@link Task}
     */
    private TaskDao taskDao;

    /**
     * Liste des tâches observées.
     */
    @Getter
    private LiveData<List<Task>> tasks;

    public TaskRepository(Context pContext) {
        this.taskDao = AppDataBase.getInstance(pContext).taskDao();
        this.tasks = this.taskDao.getTasks();
    }

    /**
     * Permet l'insertion en BDD d'une ou plusieurs tâches.
     *
     * @param tasks tâches à persister
     */
    public Single<List<Long>> insertTask(Task... tasks) {
        return this.taskDao.insertTask(tasks);
    }

    /**
     * Permet la suppression d'une ou plusieurs tâches.
     *
     * @param tasks la ou les tâches à supprimer en BDD
     */
    public Single<Integer> deleteTask(Task... tasks) {
        return this.taskDao.deleteTask(tasks);
    }

}
