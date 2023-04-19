package com.example.pictosec;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.pictosec.databinding.FragmentFirstBinding;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    TextView showPasswordView;
    databaseManagement db;

    passObj pass;
    scrambler scramble;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        db = new databaseManagement();
        scramble = new scrambler();
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
        view.findViewById(R.id.generate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] arr;
                binding.textview1.setText("");
                    pass = scramble.generatePassword(1, 0,9);
                    arr = pass.imageSet;
                    db.addPassword("ppurushothaman1", pass.password);
                    scrambler.generateImage(arr, FirstFragment.this.getView(), FirstFragment.this.getContext());
                    binding.textview1.append(pass.password);
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}