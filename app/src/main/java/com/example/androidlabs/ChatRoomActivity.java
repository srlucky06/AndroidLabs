package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ChatRoomActivity extends AppCompatActivity {

    private List<Message> list = new ArrayList<>();
    Message msg = null;
    //  ListView itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        ListView itemList = (ListView) findViewById(R.id.list_item);
        ChatAdapter a = new ChatAdapter();
        itemList.setAdapter(a);


        Button sendButton = findViewById(R.id.BtnSend);
        sendButton.setOnClickListener(click -> {
            EditText msgReturned = findViewById(R.id.typeText);
            list.add(new Message(msgReturned.getText().toString(), "send"));


            //            ChatAdapter cht = new ChatAdapter(list, getApplicationContext());
            //itemList.setAdapter(cht);
            msgReturned.setText("");
            a.notifyDataSetChanged();
        });
        Button recButton = findViewById(R.id.BtnReceive);
        recButton.setOnClickListener(click -> {
            EditText msgReturned = findViewById(R.id.typeText);
            list.add(new Message(msgReturned.getText().toString(), "receive"));


            //            ChatAdapter cht = new ChatAdapter(list, getApplicationContext());
            //itemList.setAdapter(cht);
            msgReturned.setText("");
            a.notifyDataSetChanged();
        });


        itemList.setOnItemLongClickListener((parent, view, position, id) -> {
            AlertDialog.Builder alertDialogBuilder = new  AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(getResources().getString(R.string.deletemessage));
            alertDialogBuilder.setMessage(getResources().getString(R.string.rowMessage)  + position + "\n"
                    + (getResources().getString(R.string.datebaseid) + id));
            alertDialogBuilder.setPositiveButton((getResources().getString(R.string.yesMessage)), (click, arg) -> {
                list.remove(position);
                a.notifyDataSetChanged();
            });
            alertDialogBuilder.setNegativeButton((getResources().getString(R.string.noMessage)), (click, arg) -> {});
            alertDialogBuilder.create().show();
            return true;

        });
    }
    //inner class Message
    public class Message {
        private String input;
        private String task;

        public Message(String input, String task) {
            this.input = input;
            this.task = task;
        }

        public String getInput() {
            return input;
        }
        public String getTask() {

            return task;
        }

        public void setInput(String input) {

            this.input = input;
        }

        public void setTask(String task) {

            this.task = task;
        }
    }

    public class ChatAdapter extends BaseAdapter {
        // List<Message> messageList = new ArrayList<>();
        Context context;

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public Message getItem(int position) {
            return list.get(position);
        }


        @Override
        public View getView(int p, View convertView, ViewGroup parent) {
            View newView = convertView;
            Message m = getItem(p);


            if (newView == null) {
                newView = getLayoutInflater().inflate(R.layout.activity_chat_room, parent, false);
            }

            if (m.getTask().equals("send")) {

                newView = getLayoutInflater().inflate(R.layout.activity_sendmessage, null);
                TextView msgArea = (TextView) newView.findViewById(R.id.msgSent);
                msgArea.setText(m.getInput());


            } else {
                newView = getLayoutInflater().inflate(R.layout.activity_receivedmessage, null);
                TextView msgArea = (TextView) newView.findViewById(R.id.msgReceived);
                msgArea.setText(m.getInput());
            }
            return newView;
        }

        @Override
        public int getCount() {
            return list.size();
        }

    }
}


/*

           myList.setOnItemClickListener(p,b,pos,id)->{
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("A title")
                    .setMessage("Do you want to add stuff")
                    .setNegativeButton("No", (click, arg) -> { })

                    //An optional third button:
                    .setNeutralButton("Maybe", (click, arg) -> {  })

                    //You can add extra layout elements:
                    .setView(getLayoutInflater().inflate(R.layout.row_layout, null) )

                    //Show the dialog
                    .create().show();
            return true;
        });
        SwipeRefreshLayout refresher = findViewById(R.id.refresher);
        refresher.setOnRefreshListener( () -> refresher.setRefreshing(false)  );
    }

        private MyListAdapter extends BaseAdapter {
            int getCount(){
                return list.size();

            }

        public Object getItem(int position){
            return list.get(position);
        }
        public View getView(int position, View convertView, ViewGroup parent) {
              View newView= convertView;
            //layoutInflater to load the XML file
            LayoutInflater inflater = getLayoutInflater();
            newView = inflater.inflate(R.layout.filename, parent, false );
            newView.findViewById(R.id.typeText);
            name.setText( getItem(position ).toString()  );

            return newView;

        }
        long getItemId(int i){
            return number:position;
        }
    }
}*/