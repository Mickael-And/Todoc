package com.cleanup.todoc.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Comparator;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>Model for the tasks of the application.</p>
 *
 * @author Gaëtan HERFRAY
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "task", foreignKeys = @ForeignKey(entity = Project.class, parentColumns = "project_id", childColumns = "task_project_id"))
public class Task {
    /**
     * The unique identifier of the task
     */
    @PrimaryKey
    @ColumnInfo(name = "task_id")
    @Getter
    @Setter
    @NonNull
    private String id = UUID.randomUUID().toString();

    /**
     * The name of the task
     */
    @Getter
    @Setter
    @ColumnInfo(name = "name")
    private String name;

    /**
     * The timestamp when the task has been created
     */
    @Getter
    @Setter
    @ColumnInfo(name = "creation_times_tamp")
    private long creationTimestamp;

    /**
     * Id du projet auquel est liée la tâche.
     */
    @Getter
    @Setter
    @ColumnInfo(name = "task_project_id")
    private String projectId;

    /**
     * Comparator to sort task from last created to first created
     */
    public static class TaskRecentComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return (int) (right.creationTimestamp - left.creationTimestamp);
        }
    }

    /**
     * Comparator to sort task from first created to last created
     */
    public static class TaskOldComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return (int) (left.creationTimestamp - right.creationTimestamp);
        }
    }
}
