package com.example.pictosec;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.pictosec.databinding.FragmentSecondBinding;

import java.util.Timer;
import java.util.TimerTask;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    databaseManagement db;
    private EditText con;
    private EditText con2;
    private EditText pass;
    private Timer timer;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        con = (EditText) view.findViewById(R.id.contextEdit);
        con2 = (EditText) view.findViewById(R.id.newContext);
        pass = (EditText) view.findViewById(R.id.passDelete);
        db = new databaseManagement();

        view.findViewById(R.id.retrieve).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] arr;
                String decPass;
                if(db.passwords.size() == 0){
                    Toast.makeText(getActivity(), "No passwords to display!", Toast.LENGTH_SHORT).show();
                }else {
                    for (int i = 0; i < db.passwords.size(); i++) {
                        arr = db.retrievePassword(users.username);
                        decPass = aesEncryp.decrypt(arr[1]);
                        String text = arr[0] + ": " + decPass + "\n";
                        binding.textview.append(text);
                    }
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            binding.textview.setText("");
                        }
                    }, 10*1000);
                }
            }
        });
        view.findViewById(R.id.editButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = con.getText().toString();
                String str2 = con2.getText().toString();
                if(!db.contexts.contains(str)){
                    Toast.makeText(getActivity(), "This context does not exist!", Toast.LENGTH_SHORT).show();
                }else if(str2.isEmpty()){
                    Toast.makeText(getActivity(), "Must Provide a new context!", Toast.LENGTH_SHORT).show();
                }else{
                    db.editContext(str, str2);
                    con.getText().clear();
                    con2.getText().clear();
                }
            }
        });
        view.findViewById(R.id.passD).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = pass.getText().toString();
                str = aesEncryp.encrypt(str);
                if(db.passwords.contains(str)){
                    db.removePassword(str);
                    pass.getText().clear();
                }else{
                    Toast.makeText(getActivity(), "This password does not exist!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}