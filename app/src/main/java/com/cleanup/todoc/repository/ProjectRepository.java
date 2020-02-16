package com.cleanup.todoc.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.room.AppDataBase;
import com.cleanup.todoc.room.ProjectDao;

import java.util.List;

public class ProjectRepository {

    private ProjectDao projectDao;

    public ProjectRepository(Application application) {
        this.projectDao = AppDataBase.getInstance(application).projectDao();
    }

    public LiveData<List<Project>> getProjects() {
        return this.projectDao.getProjects();
    }

    public Project getProject(String projectId) {
        return this.projectDao.getProject(projectId);
    }
}
