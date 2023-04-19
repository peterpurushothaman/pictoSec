package com.example.pictosec;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        db = new databaseManagement();
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
        view.findViewById(R.id.retrieve).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] arr = new String[2];
                if(db.passwords.size() == 0){
                    Toast.makeText(getActivity(), "No passwords to display!", Toast.LENGTH_SHORT).show();
                }else {
                    for (int i = 0; i < db.passwords.size(); i++) {
                        arr = db.retrievePassword(users.username);
                        String text = arr[0] + ": " + arr[1] + "\n";
                        binding.textview.append(text);
                    }
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            binding.textview.setText("");
                        }
                    }, 10*1000);
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