package com.cleanup.todoc.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.room.AppDataBase;
import com.cleanup.todoc.room.TaskDao;

import java.util.List;

import lombok.Getter;

public class TaskRepository {

    private TaskDao taskDao;
    @Getter
    private LiveData<List<Task>> tasks;

    public TaskRepository(Application application) {
        this.taskDao = AppDataBase.getInstance(application).taskDao();
        this.tasks = taskDao.getTasks();
    }

    public void insertTask(Task task) {
        new InsertAsyncTask(this.taskDao).execute(task);
    }

    public void deleteTask(Task...tasks){
        this.taskDao.deleteTask(tasks);
    }





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
