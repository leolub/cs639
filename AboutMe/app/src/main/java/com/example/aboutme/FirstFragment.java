package com.example.aboutme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.example.aboutme.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {
    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        Button btnHobbies = view.findViewById(R.id.btn_hobbies);
        Button btnContact = view.findViewById(R.id.btn_contact);

        btnHobbies.setOnClickListener(v ->
                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment)
        );

        btnContact.setOnClickListener(v -> {
            Toast.makeText(requireContext(),
                    getString(R.string.toast_going_contact), Toast.LENGTH_SHORT).show();
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_FirstFragment_to_fragmentThird);
        });
    }

    @Override
    public void onDestroyView() { super.onDestroyView(); binding = null; }
}
