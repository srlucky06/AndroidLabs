package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;



public class ChatRoomActivity extends AppCompatActivity {

    private ArrayList<Message> list = new ArrayList<>();
    private MessageAdaptor myAdapter;
    private EditText chat;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat_room);

        ListView myList = findViewById(R.id.listView);

        loadDataFromDatabase();

        myList.setAdapter(myAdapter = new MessageAdaptor());


        myList.setOnItemLongClickListener((parent, view, position, id) -> {
            Message selectedMessage = list.get(position);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(getResources().getString(R.string.deleteMessae))

                    .setMessage(getResources().getString(R.string.rowMessage) + position + "\n" + (getResources().getString(R.string.datebaseid) + id))

                    .setPositiveButton((getResources().getString(R.string.yesMessage)), (click, arg) -> {
                        deleteMessage(selectedMessage);
                        list.remove(position);
                        myAdapter.notifyDataSetChanged();
                    }).setNegativeButton((getResources().getString(R.string.noMessage)), (click, arg) -> {
            })

                    .create().show();

            return true;
        });

        chat = findViewById(R.id.chatEditText);

        Button sendButton = findViewById(R.id.BtnSend);
        sendButton.setOnClickListener(click -> {
            String sendText = chat.getText().toString();

            ContentValues newRowValue = new ContentValues();

            //Now provide a value for every database column defined in MyOpener.java:
            //put string name in the NAME column:
            newRowValue.put(MyOpener.COL_MESSAGE, sendText);
            newRowValue.put(MyOpener.COL_ISRECEIVEDMESSAGE, 0);

            //Now insert in the database:
            long newId = db.insert(MyOpener.TABLE_NAME, null, newRowValue);

            Message sendMessage = new Message(sendText, false, newId);
            list.add(sendMessage);
            chat.setText("");

            myAdapter.notifyDataSetChanged();


        });

        Button receiveButton = findViewById(R.id.BtnReceive);
        receiveButton.setOnClickListener(click -> {
            String receiveText = chat.getText().toString();

            ContentValues newRowValue = new ContentValues();

            //Now provide a value for every database column defined in MyOpener.java:
            //put string name in the NAME column:
            newRowValue.put(MyOpener.COL_MESSAGE, receiveText);
            newRowValue.put(MyOpener.COL_ISRECEIVEDMESSAGE, 1);


            //Now insert in the database:
            long newId = db.insert(MyOpener.TABLE_NAME, null, newRowValue);


            Message receiveMessage = new Message(receiveText, true, newId);
            list.add(receiveMessage);
            chat.setText("");
            myAdapter.notifyDataSetChanged();
        });


        EditText chat = findViewById(R.id.chatEditText);

    }

    private void loadDataFromDatabase() {
        //get a database connection:
        MyOpener dbOpener = new MyOpener(this);
        db = dbOpener.getWritableDatabase(); //This calls onCreate() if you've never built the table before, or onUpgrade if the version here is newer


        // We want to get all of the columns. Look at MyOpener.java for the definitions:
        String[] columns = {MyOpener.COL_ID, MyOpener.COL_MESSAGE, MyOpener.COL_ISRECEIVEDMESSAGE};
        //query all the results from the database:
        Cursor results = db.query(false, MyOpener.TABLE_NAME, columns, null, null, null, null, null, null);

        //Now the results object has rows of results that match the query.
        //find the column indices:

        int messageIndex = results.getColumnIndex(MyOpener.COL_MESSAGE);
        int isReceivedIndex = results.getColumnIndex(MyOpener.COL_ISRECEIVEDMESSAGE);
        int idColIndex = results.getColumnIndex(MyOpener.COL_ID);
        //iterate over the results, return true if there is a next item:
        while (results.moveToNext()) {
            String message = results.getString(messageIndex);
            String isReceived = results.getString(isReceivedIndex);
            boolean isReceivedMessage = isReceived.equals("1") ? true : false;

            long id = results.getLong(idColIndex);

            //add the new Contact to the array list:
            list.add(new Message(message, isReceivedMessage, id));
        }
        printCursor(results, db.getVersion());
    }

    private void printCursor(Cursor c, int version) {
        Log.i("database version number", String.valueOf(version));
        Log.i("Number of the columns ", String.valueOf(c.getColumnCount()));
        for (String col : c.getColumnNames()) {
            Log.i("Name of the columns ", col);
        }
        Log.i("Number of rows ", String.valueOf(c.getCount()));

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                String id = c.getString(c.getColumnIndex("_id"));
                String message = c.getString(c.getColumnIndex("MESSAGE"));
                String isReceived = c.getString(c.getColumnIndex("isReceived"));
                Log.i("The row value is", id + " " + message + " " + isReceived);
                c.moveToNext();
            }
        }

    }


    protected void updateContact(Message c) {
        //Create a ContentValues object to represent a database row:
        ContentValues updatedValues = new ContentValues();
        updatedValues.put(MyOpener.COL_MESSAGE, c.getMessage());
        updatedValues.put(MyOpener.COL_ISRECEIVEDMESSAGE, c.getReceivedMessage());

        //now call the update function:
        db.update(MyOpener.TABLE_NAME, updatedValues, MyOpener.COL_ID + "= ?", new String[]{Long.toString(c.getDatabaseId())});
    }

    protected void deleteMessage(Message c) {
        db.delete(MyOpener.TABLE_NAME, MyOpener.COL_ID + "= ?", new String[]{Long.toString(c.getDatabaseId())});
    }


    private class MessageAdaptor extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Message getItem(int position) {
            return list.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View newView = convertView;

            if (list.get(position).getReceivedMessage()) {
                newView = inflater.inflate(R.layout.receivedlayout, parent, false);
                TextView tv = newView.findViewById(R.id.msgReceived);
                tv.setText(getItem(position).getMessage());

            } else {
                newView = inflater.inflate(R.layout.sendlayout, parent, false);
                TextView tv = newView.findViewById(R.id.msgSent);
                tv.setText(getItem(position).getMessage());

            }

            return newView;
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).getDatabaseId();
        }

    }
}




