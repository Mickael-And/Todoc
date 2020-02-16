package com.cleanup.todoc.model;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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

//    /**  TODO: Récupérer pour l'init
//     * Returns all the projects of the application.
//     *
//     * @return all the projects of the application
//     */
//    @NonNull
//    public static Project[] getAllProjects() {
//        return new Project[]{
//                new Project(1L, "Projet Tartampion", 0xFFEADAD1),
//                new Project(2L, "Projet Lucidia", 0xFFB4CDBA),
//                new Project(3L, "Projet Circus", 0xFFA3CED2),
//        };
//    }
//
//
//    /**
//     * Returns the unique identifier of the project.
//     *
//     * @return the unique identifier of the project
//     */
//    public long getId() {
//        return id;
//    }
//
//    /**
//     * Returns the name of the project.
//     *
//     * @return the name of the project
//     */
//    @NonNull
//    public String getName() {
//        return name;
//    }
//
//    /**
//     * Returns the hex (ARGB) code of the color associated to the project.
//     *
//     * @return the hex (ARGB) code of the color associated to the project
//     */
//    @ColorInt
//    public int getColor() {
//        return color;
//    }
}
