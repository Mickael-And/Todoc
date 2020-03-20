package com.cleanup.todoc.models;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Comparator;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>Models for project in which tasks are included.</p>
 *
 * @author Gaëtan HERFRAY
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "project")
public class Project {
    /**
     * The unique identifier of the project
     */
    @PrimaryKey
    @ColumnInfo(name = "project_id")
    @Getter
    @Setter
    @NonNull
    private String id = UUID.randomUUID().toString();

    /**
     * The name of the project
     */
    @Getter
    @Setter
    @ColumnInfo(name = "name")
    private String name;

    /**
     * The hex (ARGB) code of the color associated to the project
     */
    @ColorInt
    @Getter
    @Setter
    @ColumnInfo(name = "color")
    private int color;

    @Override
    @NonNull
    public String toString() {
        return getName();
    }

    /**
     * Comparator to sort project from A to Z.
     */
    public static class ProjectAZComparator implements Comparator<Project> {
        @Override
        public int compare(Project o1, Project o2) {
            return o1.name.compareTo(o2.name);
        }
    }

    /**
     * Comparator to sort project from Z to A.
     */
    public static class ProjectZAComparator implements Comparator<Project> {
        @Override
        public int compare(Project o1, Project o2) {
            return o2.name.compareTo(o1.name);
        }
    }
}
