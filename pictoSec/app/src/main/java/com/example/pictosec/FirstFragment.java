package com.example.pictosec;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.pictosec.databinding.FragmentFirstBinding;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private TextView showPasswordView;
    private databaseManagement db;
    private passObj pass;

    private String cont;
    private scrambler scramble;
    private SharedPreferences pref;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        scramble = new scrambler();
        pref = getActivity().getSharedPreferences("SettingsPref", Context.MODE_PRIVATE);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText context = (EditText) view.findViewById(R.id.context);
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
                db = new databaseManagement();
                int[] arr;
                String encPass;
                int setChoice = 1;
                int imageChoice = pref.getInt("ImageSet", 1);
                cont = context.getText().toString();
                if(cont.isEmpty()) {
                    Toast.makeText(getActivity(), "Make sure you add a context!", Toast.LENGTH_SHORT).show();
                } else {
                    if(pref.getBoolean("toggle", false)){
                        setChoice = 2;
                    }
                    binding.textview1.setText("");
                    pass = scramble.generatePassword(setChoice, imageChoice, 9);
                    encPass = aesEncryp.encrypt(pass.password);
                    arr = pass.imageSet;
                    db.addPassword(users.username, encPass, cont);
                    scrambler.generateImage(arr, FirstFragment.this.getView(), FirstFragment.this.getContext());
                    binding.textview1.append(pass.password);
                    context.getText().clear();
                }
            }
        });
        view.findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finishAffinity();
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}