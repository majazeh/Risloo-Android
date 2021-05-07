package com.majazeh.risloo.Views.Fragments.Create;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.FragmentCreateReportBinding;

public class CreateReportFragment extends Fragment {

    // Binding
    private FragmentCreateReportBinding binding;

    // Vars
    private String encryption = "", description = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateReportBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        binding.encryptionIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateReportFragmentEncryptionHeader));
        binding.descriptionIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateReportFragmentDescriptionHeader));

        InitManager.spinner(requireActivity(), binding.encryptionIncludeLayout.selectSpinner, R.array.EncryptionStates, "main");

        InitManager.txtTextColor(binding.cryptoTextView.getRoot(), getResources().getString(R.string.CreateReportFragmentCryptoButton), getResources().getColor(R.color.Blue600));
        InitManager.txtTextColor(binding.createTextView.getRoot(), getResources().getString(R.string.CreateReportFragmentButton), getResources().getColor(R.color.White));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.cryptoTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_blue600_ripple_blue300);
            binding.createTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        } else {
            binding.cryptoTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_blue600);
            binding.createTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.encryptionIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                encryption = parent.getItemAtPosition(position).toString();

                if (position == 0) {
                    binding.cryptoConstraintLayout.setVisibility(View.GONE);
                } else {
                    binding.cryptoConstraintLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.descriptionIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.descriptionIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.descriptionIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(binding.cryptoTextView.getRoot());

        ClickManager.onDelayedClickListener(() -> {
            if (binding.descriptionIncludeLayout.inputEditText.length() == 0) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.descriptionIncludeLayout.inputEditText, null, null, getResources().getString(R.string.AppInputEmpty));
            }

            if (binding.descriptionIncludeLayout.inputEditText.length() != 0) {
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.descriptionIncludeLayout.inputEditText, null, null);

                doWork();
            }
        }).widget(binding.createTextView.getRoot());
    }

    private void setData() {
        if (!((MainActivity) requireActivity()).singleton.getAddress().equals("")) {
            encryption = ((MainActivity) requireActivity()).singleton.getAddress();
            for (int i=0; i<binding.encryptionIncludeLayout.selectSpinner.getCount(); i++) {
                if (binding.encryptionIncludeLayout.selectSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(encryption)) {
                    binding.encryptionIncludeLayout.selectSpinner.setSelection(i);
                }
            }
        }
        if (!((MainActivity) requireActivity()).singleton.getDescription().equals("")) {
            description = ((MainActivity) requireActivity()).singleton.getDescription();
            binding.descriptionIncludeLayout.inputEditText.setText(description);
        }
    }

    private void doWork() {
        description = binding.descriptionIncludeLayout.inputEditText.getText().toString().trim();

        // TODO : Call Work Method
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}