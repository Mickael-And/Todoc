package com.cleanup.todoc.room;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.models.Project;
import com.cleanup.todoc.models.Task;

import java.util.Locale;
import java.util.UUID;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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

            Disposable disposable = appDataBaseInstance.projectDao().insertProject(new Project(UUID.randomUUID().toString(), "Projet Tartampion", 0xFFEADAD1),
                    new Project(UUID.randomUUID().toString(), "Projet Lucidia", 0xFFB4CDBA),
                    new Project(UUID.randomUUID().toString(), "Projet Circus", 0xFFA3CED2))
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(longs -> {
                        System.out.println(longs);
                        Log.w("Init Database", String.format(Locale.getDefault(), "%d projet(s) inséré(s)", longs.size()));
                    }, throwable -> Log.w("Init Database", throwable));
        }
    };
}