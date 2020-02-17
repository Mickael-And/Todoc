package com.cleanup.todoc.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.models.Project;
import com.cleanup.todoc.models.Task;

import java.util.UUID;

/**
 * Représente la base de données.
 */
@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    /**
     * Nom de la base de données.
     */
    private static final String DB_NAME = "todoc_db";

    /**
     * Instance de la base de données. Sous forme de Singleton
     */
    private static AppDataBase appDataBaseInstance;

    /**
     * Permet de récupérer la DAO des tâches.
     *
     * @return la DAO des tâches
     */
    public abstract TaskDao taskDao();

    /**
     * Permet de récupérer la DAO des projets.
     *
     * @return la DAO des projets
     */
    public abstract ProjectDao projectDao();

    /**
     * Constructeur static permettant de récupérer l'instance de la BDD.
     *
     * @param pContext context de l'application
     * @return l'instance de la base de données
     */
    public static AppDataBase getInstance(final Context pContext) {
        if (appDataBaseInstance == null) {
            synchronized (AppDataBase.class) {
                if (appDataBaseInstance == null) {
                    appDataBaseInstance = Room.databaseBuilder(pContext.getApplicationContext(), AppDataBase.class, DB_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(callback)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return appDataBaseInstance;
    }

    /**
     * Création d'un CallBack appelé lors de l'instanciation de {@link AppDataBase}.
     */
    private static RoomDatabase.Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsync(appDataBaseInstance).execute();
        }
    };

    /**
     * Classe permettant l'initialisation de la BDD de manière asynchrone.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final TaskDao taskDao;
        private final ProjectDao projectDao;

        PopulateDbAsync(AppDataBase db) {
            this.taskDao = db.taskDao();
            this.projectDao = db.projectDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created
            this.taskDao.deleteAllTasks();
            this.projectDao.deleteAllProjects();

            this.projectDao.insertProject(new Project(UUID.randomUUID().toString(), "Projet Tartampion", 0xFFEADAD1),
                    new Project(UUID.randomUUID().toString(), "Projet Lucidia", 0xFFB4CDBA),
                    new Project(UUID.randomUUID().toString(), "Projet Circus", 0xFFA3CED2));

            return null;
        }
    }
}
