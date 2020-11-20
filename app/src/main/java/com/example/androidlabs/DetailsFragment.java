package com.example.androidlabs;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {

    private Bundle dataFromActivity;
    private long id;
    private AppCompatActivity parentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dataFromActivity = getArguments();
        id = dataFromActivity.getLong(ChatRoomActivity.MESSAGE_ID );

        // Inflate the layout for this fragment

        View result =  inflater.inflate(R.layout.fragment_details, container, false);

        //show the message
        TextView message = (TextView)result.findViewById(R.id.message);
        message.setText(dataFromActivity.getString(ChatRoomActivity.MESSAGE_TEXT));

        //show the id:
        TextView idView = (TextView)result.findViewById(R.id.idEqual);
        idView.setText("ID=" + id);

        CheckBox ck = (CheckBox) result.findViewById(R.id.checkBox);
        if (dataFromActivity.getBoolean(ChatRoomActivity.MESSAGE_TYPE)) {
            ck.setChecked(false);
        }
        else {
            ck.setChecked(true);
        }

        // get the Hide button, and add a click listener:
        Button hideButton = (Button)result.findViewById(R.id.hideButton);
        hideButton.setOnClickListener( Clk  ->{
            //Tell the parent activity to remove
            parentActivity.getSupportFragmentManager().beginTransaction().remove(this).commit();
        });

        return result;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //context will either be FragmentExample for a tablet, or EmptyActivity for phone
        parentActivity = (AppCompatActivity)context;
    }
}