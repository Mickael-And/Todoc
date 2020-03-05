package com.cleanup.todoc;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cleanup.todoc.models.Task;
import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;
import com.cleanup.todoc.ui.MainActivityViewModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for tasks
 *
 * @author Gaëtan HERFRAY
 */
public class TaskUnitTest {

    private MainActivityViewModel mainActivityViewModel;

    @Mock
    TaskRepository taskRepository;
    @Mock
    ProjectRepository projectRepository;

    private LiveData projects;

    private LiveData tasks;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        projects = new MutableLiveData();
        tasks = new MutableLiveData();
        when(taskRepository.getTasks()).thenReturn(tasks);
        when(projectRepository.getProjects()).thenReturn(projects);
        this.mainActivityViewModel = new MainActivityViewModel(taskRepository, projectRepository);
    }

    @Test
    public void vmGetProjetById() {
        String id = UUID.randomUUID().toString();
        this.mainActivityViewModel.getProject(id);
        verify(projectRepository, times(1)).getProject(id);
    }

    @Test
    public void vmInsertTask() {
        Task task = new Task();
        this.mainActivityViewModel.insertTask(task);
        verify(taskRepository, times(1)).insertTask(task);
    }

    @Test
    public void vmDeleteTask() {
        Task task = new Task();
        this.mainActivityViewModel.deleteTask(task);
        verify(taskRepository, times(1)).deleteTask(task);
    }

    @Test
    public void vmGetProjects() {
        assertSame(projects, this.mainActivityViewModel.getProjects());
    }

    @Test
    public void vmGetTasks() {
        assertSame(tasks, this.mainActivityViewModel.getTasks());
    }

    @Test
    public void test_az_comparator() {
        final Task task1 = new Task(UUID.randomUUID().toString(), "aaa", new Date().getTime(), UUID.randomUUID().toString());
        final Task task2 = new Task(UUID.randomUUID().toString(), "zzz", new Date().getTime(), UUID.randomUUID().toString());
        final Task task3 = new Task(UUID.randomUUID().toString(), "hhh", new Date().getTime(), UUID.randomUUID().toString());

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskAZComparator());

        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task3);
        assertSame(tasks.get(2), task2);
    }

    @Test
    public void test_za_comparator() {
        final Task task1 = new Task(UUID.randomUUID().toString(), "aaa", new Date().getTime(), UUID.randomUUID().toString());
        final Task task2 = new Task(UUID.randomUUID().toString(), "zzz", new Date().getTime(), UUID.randomUUID().toString());
        final Task task3 = new Task(UUID.randomUUID().toString(), "hhh", new Date().getTime(), UUID.randomUUID().toString());

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskZAComparator());

        assertSame(tasks.get(0), task2);
        assertSame(tasks.get(1), task3);
        assertSame(tasks.get(2), task1);
    }

    @Test
    public void test_recent_comparator() {
        final Task task1 = new Task(UUID.randomUUID().toString(), "aaa", 123, UUID.randomUUID().toString());
        final Task task2 = new Task(UUID.randomUUID().toString(), "zzz", 124, UUID.randomUUID().toString());
        final Task task3 = new Task(UUID.randomUUID().toString(), "hhh", 125, UUID.randomUUID().toString());

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskRecentComparator());

        assertSame(tasks.get(0), task3);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task1);
    }

    @Test
    public void test_old_comparator() {
        final Task task1 = new Task(UUID.randomUUID().toString(), "aaa", 123, UUID.randomUUID().toString());
        final Task task2 = new Task(UUID.randomUUID().toString(), "zzz", 124, UUID.randomUUID().toString());
        final Task task3 = new Task(UUID.randomUUID().toString(), "hhh", 125, UUID.randomUUID().toString());

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskOldComparator());

        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task3);
    }
}