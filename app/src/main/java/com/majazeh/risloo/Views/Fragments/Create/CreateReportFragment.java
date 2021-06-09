package com.majazeh.risloo.Views.Fragments.Create;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.FragmentCreateReportBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class CreateReportFragment extends Fragment {

    // Binding
    private FragmentCreateReportBinding binding;

    // Vars
    private HashMap data, header;
    private String sessionId = "", encryption = "", description = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateReportBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setExtra();

        return binding.getRoot();
    }

    private void initializer() {
        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

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

                if (position == 0)
                    binding.cryptoConstraintLayout.setVisibility(View.GONE);
                else
                    binding.cryptoConstraintLayout.setVisibility(View.VISIBLE);
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
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.descriptionIncludeLayout.inputEditText, binding.descriptionErrorLayout.getRoot(), binding.descriptionErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.descriptionIncludeLayout.inputEditText, binding.descriptionErrorLayout.getRoot(), binding.descriptionErrorLayout.errorTextView);
                doWork();
            }
        }).widget(binding.createTextView.getRoot());
    }

    private void setExtra() {
        if (getArguments() != null) {
            if (getArguments().getString("id") != null && !getArguments().getString("id").equals("")) {
                sessionId = getArguments().getString("id");
                data.put("id", sessionId);
            }

            if (getArguments().getString("encryption") != null && !getArguments().getString("encryption").equals("")) {
                encryption = SelectionManager.getEncryption(requireActivity(), "fa", getArguments().getString("encryption"));
                for (int i=0; i<binding.encryptionIncludeLayout.selectSpinner.getCount(); i++) {
                    if (binding.encryptionIncludeLayout.selectSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(encryption)) {
                        binding.encryptionIncludeLayout.selectSpinner.setSelection(i);

                        if (i == 0)
                            binding.cryptoConstraintLayout.setVisibility(View.GONE);
                        else
                            binding.cryptoConstraintLayout.setVisibility(View.VISIBLE);
                    }
                }
            }

            if (getArguments().getString("description") != null && !getArguments().getString("description").equals("")) {
                description = getArguments().getString("description");
                binding.descriptionIncludeLayout.inputEditText.setText(description);
            }
        }
    }

    private void doWork() {
        ((MainActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");

        description = binding.descriptionIncludeLayout.inputEditText.getText().toString().trim();

        data.put("encryption", SelectionManager.getEncryption(requireActivity(), "en", encryption));
        data.put("description", description);

//        Session.addReport(data, header, new Response() {
//            @Override
//            public void onOK(Object object) {
//                if (isAdded()) {
//                    requireActivity().runOnUiThread(() -> {
//                        Bundle extras = new Bundle();
//                        extras.putString("id", sessionId);
//
//                        ((MainActivity) requireActivity()).loadingDialog.dismiss();
//                        Toast.makeText(requireActivity(), requireActivity().getResources().getString(R.string.AppAdded), Toast.LENGTH_SHORT).show();
//                        ((MainActivity) requireActivity()).navigator(R.id.clientReportsFragment, extras);
//                    });
//                }
//            }
//
//            @Override
//            public void onFailure(String response) {
//                if (isAdded()) {
//                    requireActivity().runOnUiThread(() -> {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            if (!jsonObject.isNull("errors")) {
//                                Iterator<String> keys = (jsonObject.getJSONObject("errors").keys());
//
//                                while (keys.hasNext()) {
//                                    String key = keys.next();
//                                    for (int i = 0; i < jsonObject.getJSONObject("errors").getJSONArray(key).length(); i++) {
//                                        switch (key) {
//                                            case "encryption":
//                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.encryptionIncludeLayout.selectSpinner, binding.encryptionErrorLayout.getRoot(), binding.encryptionErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
//                                                break;
//                                            case "description":
//                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.descriptionIncludeLayout.inputEditText, binding.descriptionIncludeLayout.getRoot(), binding.descriptionErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
//                                                break;
//                                        }
//                                    }
//                                }
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    });
//                }
//            }
//        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}