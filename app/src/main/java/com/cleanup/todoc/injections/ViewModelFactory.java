package com.cleanup.todoc.injections;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;
import com.cleanup.todoc.ui.MainActivityViewModel;

/**
 * Factory permettant la fabrication d'un {@link androidx.lifecycle.ViewModel}
 */
public class ViewModelFactory implements ViewModelProvider.Factory {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public ViewModelFactory(TaskRepository pTaskRepository, ProjectRepository pProjectRepository) {
        this.taskRepository = pTaskRepository;
        this.projectRepository = pProjectRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainActivityViewModel.class)) {
            return (T) new MainActivityViewModel(this.taskRepository, this.projectRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
