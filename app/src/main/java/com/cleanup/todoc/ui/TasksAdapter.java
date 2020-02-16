package com.cleanup.todoc.ui;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.cleanup.todoc.R;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <p>Adapter which handles the list of tasks to display in the dedicated RecyclerView.</p>
 *
 * @author Gaëtan HERFRAY
 */
public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder> {
    /**
     * The list of tasks the adapter deals with
     */
    private List<Task> tasks;

    /**
     * The listener for when a task needs to be deleted
     */
    @NonNull
    private final DeleteTaskListener deleteTaskListener;

    private final MainActivityViewModel mainActivityViewModel;

    /**
     * Instantiates a new TasksAdapter.
     *
     * @param deleteTaskListener interface permettant la suppresion d'un item de la liste.
     */
    TasksAdapter(@NonNull final DeleteTaskListener deleteTaskListener, MainActivityViewModel mainActivityViewModel) {
        this.deleteTaskListener = deleteTaskListener;
        this.mainActivityViewModel = mainActivityViewModel;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_task, viewGroup, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int position) {
        this.bind(taskViewHolder, tasks.get(position));
    }

    @Override
    public int getItemCount() {
        if (tasks != null) {
            return tasks.size();
        } else return 0;
    }

    /**
     * Binds a task to the item view.
     *
     * @param taskViewHolder viewHolder to bind
     * @param task           the task to bind in the item view
     */
    private void bind(TaskViewHolder taskViewHolder, Task task) {
        taskViewHolder.lblTaskName.setText(task.getName());
        taskViewHolder.imgDelete.setTag(task);

        final Project taskProject = mainActivityViewModel.getProject(task.getProjectId());
        if (taskProject != null) {
            taskViewHolder.imgProject.setSupportImageTintList(ColorStateList.valueOf(taskProject.getColor()));
            taskViewHolder.lblProjectName.setText(taskProject.getName());
        } else {
            taskViewHolder.imgProject.setVisibility(View.INVISIBLE);
            taskViewHolder.lblProjectName.setText("");
        }

        taskViewHolder.imgDelete.setOnClickListener(view -> {
            final Object tag = view.getTag();
            if (tag instanceof Task) {
                deleteTaskListener.onDeleteTask((Task) tag);
            }
        });
    }

    /**
     * Updates the list of tasks the adapter deals with.
     *
     * @param tasks the list of tasks the adapter deals with to set
     */
    void updateTasks(@NonNull final List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    /**
     * Listener for deleting tasks
     */
    public interface DeleteTaskListener {
        /**
         * Called when a task needs to be deleted.
         *
         * @param task the task that needs to be deleted
         */
        void onDeleteTask(Task task);
    }

    /**
     * <p>ViewHolder for task items in the tasks list</p>
     *
     * @author Gaëtan HERFRAY
     */
    class TaskViewHolder extends RecyclerView.ViewHolder {
        /**
         * The circle icon showing the color of the project
         */
        @BindView(R.id.img_project)
        AppCompatImageView imgProject;
        /**
         * The TextView displaying the name of the task
         */
        @BindView(R.id.lbl_task_name)
        TextView lblTaskName;
        /**
         * The TextView displaying the name of the project
         */
        @BindView(R.id.lbl_project_name)
        TextView lblProjectName;
        /**
         * The delete icon
         */
        @BindView(R.id.img_delete)
        AppCompatImageView imgDelete;

        /**
         * Instantiates a new TaskViewHolder.
         *
         * @param itemView the view of the task item
         */
        TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
