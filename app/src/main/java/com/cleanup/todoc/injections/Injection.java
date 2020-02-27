package com.cleanup.todoc.injections;

import android.content.Context;

import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;

/**
 * Classe d'injection des instances souhaitées.
 */
public class Injection {

    /**
     * Fourni une instance de {@link TaskRepository}.
     *
     * @param pContext context applicatif
     * @return une instance de {@link TaskRepository}
     */
    public static TaskRepository provideTaskRepository(Context pContext) {
        return new TaskRepository(pContext);
    }

    /**
     * Fourni une instance de {@link ProjectRepository}.
     *
     * @param pContext context applicatif
     * @return une instance de {@link ProjectRepository}
     */
    public static ProjectRepository provideProjectRepository(Context pContext) {
        return new ProjectRepository(pContext);
    }

    /**
     * Fourni une instance de {@link ViewModelFactory}.
     *
     * @param pContext context applicatif
     * @return une instance de {@link ViewModelFactory}
     */
    public static ViewModelFactory provideViewModelFactory(Context pContext) {
        return new ViewModelFactory(provideTaskRepository(pContext), provideProjectRepository(pContext));
    }
}
