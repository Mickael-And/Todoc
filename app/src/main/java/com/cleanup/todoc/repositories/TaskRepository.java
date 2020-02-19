package com.cleanup.todoc.repositories;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.models.Task;
import com.cleanup.todoc.room.AppDataBase;
import com.cleanup.todoc.room.TaskDao;

import java.util.List;

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
        this.tasks = taskDao.getTasks();
    }

    /**
     * Permet l'insertion en BDD d'une tâche.
     *
     * @param task tâche à persister
     */
    public void insertTask(Task task) {
        new InsertAsyncTask(this.taskDao).execute(task);
    }

    /**
     * Permet la suppression d'une ou plusieurs tâches.
     *
     * @param tasks la ou les tâches à supprimer en BDD
     */
    public void deleteTask(Task... tasks) {
        this.taskDao.deleteTask(tasks);
    }

    /**
     * Classe permettant l'insertion d'une tâche en BDD de manière asynchrone.
     */
    private static class InsertAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDao mAsyncTaskDao;

        InsertAsyncTask(TaskDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Task... tasks) {
            mAsyncTaskDao.insertTask(tasks[0]);
            return null;
        }
    }
}
