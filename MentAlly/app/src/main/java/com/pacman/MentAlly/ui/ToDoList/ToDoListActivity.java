package com.pacman.MentAlly.ui.ToDoList;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import com.pacman.MentAlly.R;

import java.util.ArrayList;
import java.util.List;

enum State {
    INCOMPLETE_TASKS,
    COMPLETE_TASKS
}

public class ToDoListActivity extends AppCompatActivity {

    private Button addButton;
    private Button completedButton;
    private Button incompleteButton;
    private Button deleteListButton;
    private ListView myListView;
    private MyListAdapter mylistadapter;
    private List<Task> listObject;
    private List<Task> completedList;

    private State currentState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        currentState = State.INCOMPLETE_TASKS;
        addButton = findViewById(R.id.addButton);
        completedButton = findViewById(R.id.viewCompletedButton);
        incompleteButton = findViewById(R.id.viewIncompletedButton);
        deleteListButton = findViewById(R.id.delete_list_btn);
        myListView = findViewById(R.id.myList);
        mylistadapter = new MyListAdapter();
        listObject = new ArrayList<>();
        completedList = new ArrayList<>();

        mylistadapter.setData(listObject);
        myListView.setAdapter(mylistadapter);

        if (currentState == State.INCOMPLETE_TASKS) {
            incompleteButton.setEnabled(false);
        } else {
            completedButton.setEnabled(false);
            addButton.setEnabled(false);
        }

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                final View infoTaskDialogView = getLayoutInflater().inflate(R.layout.info_task_dialog, null);
                final TextView taskLabel = infoTaskDialogView.findViewById(R.id.taskLabel);
                final TextView startDateLabel = infoTaskDialogView.findViewById(R.id.startDateLabel);
                final TextView endDateLabel = infoTaskDialogView.findViewById(R.id.endDateLabel);



                if (currentState == State.INCOMPLETE_TASKS) {
                    final Task task = listObject.get(pos);
                    taskLabel.setText(task.getTaskName());
                    startDateLabel.setText(task.getStart_date());
                    endDateLabel.setText(task.getEnd_date());

                    AlertDialog infoDialog = new AlertDialog.Builder(ToDoListActivity.this)
                            .setView(infoTaskDialogView)
                            .setPositiveButton("Mark As Completed", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    completedList.add(task);
                                    listObject.remove(task);
                                    mylistadapter.setData(listObject);
                                }
                            })
                            .setNegativeButton("Cancel Task", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    listObject.remove(task);
                                    mylistadapter.setData(listObject);
                                }
                            })
                            .create();
                    infoDialog.show();
                } else {
                    final Task task = completedList.get(pos);
                    taskLabel.setText(task.getTaskName());
                    startDateLabel.setText(task.getStart_date());
                    endDateLabel.setText(task.getEnd_date());
                    AlertDialog infoDialog = new AlertDialog.Builder(ToDoListActivity.this)
                            .setView(infoTaskDialogView)
                            .setNegativeButton("Cancel Task", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    completedList.remove(task);
                                    mylistadapter.setData(completedList);
                                }
                            })
                            .create();
                    infoDialog.show();
                }

            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                View addTaskDialogView = getLayoutInflater().inflate(R.layout.add_new_task_dialog, null);
                final EditText taskName = addTaskDialogView.findViewById(R.id.task_name);
                final EditText startDate = addTaskDialogView.findViewById(R.id.start_date);
                final EditText finishDate = addTaskDialogView.findViewById(R.id.end_date);

                AlertDialog dialog = new AlertDialog.Builder(ToDoListActivity.this)
                        .setView(addTaskDialogView)
                        .setPositiveButton(null, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Task newTask = new Task(taskName.getText().toString(), startDate.getText().toString(), finishDate.getText().toString());
                                listObject.add(newTask);
                                mylistadapter.setData(listObject);
                            }
                        })
                        .setPositiveButtonIcon(AppCompatResources.getDrawable(ToDoListActivity.this, R.drawable.complete_task))
                        .create();
                dialog.show();
            }
        });

        completedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mylistadapter.setData(completedList);
                completedButton.setEnabled(false);
                incompleteButton.setEnabled(true);
                addButton.setEnabled(false);
                currentState = State.COMPLETE_TASKS;
            }
        });

        incompleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mylistadapter.setData(listObject);
                incompleteButton.setEnabled(false);
                completedButton.setEnabled(true);
                addButton.setEnabled(true);
                currentState = State.INCOMPLETE_TASKS;
            }
        });

        deleteListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentState == State.INCOMPLETE_TASKS) {
                    listObject.clear();
                    mylistadapter.setData(listObject);
                } else {
                    completedList.clear();
                    mylistadapter.setData(completedList);
                }
            }
        });
    }

    class MyListAdapter extends BaseAdapter {
        List<Task> taskList = new ArrayList<>();

        public void setData(List<Task> mList) {
            taskList.clear();
            taskList.addAll(mList);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return taskList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflateLayout = (LayoutInflater) ToDoListActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View taskRow = inflateLayout.inflate(R.layout.task, parent, false);
            TextView taskObject = taskRow.findViewById(R.id.taskItem);
            taskObject.setText(taskList.get(position).getTaskName());
            return taskRow;
        }
    }
}
