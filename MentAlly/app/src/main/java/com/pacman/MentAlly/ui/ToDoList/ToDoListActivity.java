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

import com.pacman.MentAlly.R;

import java.util.ArrayList;
import java.util.List;

public class ToDoListActivity extends AppCompatActivity {

    private Button addButton;
    private ListView myListView;
    private MyListAdapter mylistadapter;
    private List<String> listObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        addButton = findViewById(R.id.addButton);
        myListView = findViewById(R.id.myList);
        mylistadapter = new MyListAdapter();
        listObject = new ArrayList<>();

        mylistadapter.setData(listObject);
        myListView.setAdapter(mylistadapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                AlertDialog infoDialog = new AlertDialog.Builder(ToDoListActivity.this)
                        .setTitle("Info")
                        .setMessage(listObject.get(position))
                        .setPositiveButton("Mark As Completed", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listObject.remove(pos);
                                mylistadapter.setData(listObject);
                            }
                        })
                        .setNegativeButton("Cancel Task", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listObject.remove(pos);
                                mylistadapter.setData(listObject);
                            }
                        })
                        .create();
                infoDialog.show();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final EditText userInput = new EditText(ToDoListActivity.this);
                userInput.setSingleLine();
                AlertDialog dialog = new AlertDialog.Builder(ToDoListActivity.this)
                        .setTitle("Add a new task")
                        .setMessage("What do you want to do today?")
                        .setView(userInput)
                        .setPositiveButton("Add Task", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listObject.add(userInput.getText().toString());
                                Log.d("hello", userInput.getText().toString());
                                mylistadapter.setData(listObject);
                            }
                        })
                        .create();
                dialog.show();
            }
        });
    }

    class MyListAdapter extends BaseAdapter {
        List<String> taskList = new ArrayList<>();

        public void setData(List<String> mList) {
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
            taskObject.setText(taskList.get(position));
            return taskRow;
        }
    }
}
