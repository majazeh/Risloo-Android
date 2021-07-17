package com.majazeh.risloo.Views.Fragments.Create;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayoutMediator;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Tab.CreateScheduleAdapter;
import com.majazeh.risloo.databinding.FragmentCreateScheduleBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class CreateScheduleFragment extends Fragment {

    // Binding
    public FragmentCreateScheduleBinding binding;

    // Adapters
    public CreateScheduleAdapter adapter;

    // Objects
    private TabLayoutMediator tabLayoutMediator;

    // Fragments
    private Fragment time, reference, session, payment;

    // Vars
    private String[] tabs;
    private HashMap data, header;
    public RoomModel roomModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateScheduleBinding.inflate(inflater, viewGroup, false);

        initializer();

        setArgs();

        return binding.getRoot();
    }

    private void initializer() {
        adapter = new CreateScheduleAdapter(requireActivity());

        tabs = getResources().getStringArray(R.array.CreateScheduleTabs);
        tabLayoutMediator = new TabLayoutMediator(binding.tabLayout.getRoot(), binding.viewPager.getRoot(), (tab, position) -> tab.setText(tabs[position]));

        time = ((MainActivity) requireActivity()).fragmont.getTime();
        reference = ((MainActivity) requireActivity()).fragmont.getReference();
        session = ((MainActivity) requireActivity()).fragmont.getSession();
        payment = ((MainActivity) requireActivity()).fragmont.getPayment();

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());
    }

    private void setArgs() {
        String type = CreateScheduleFragmentArgs.fromBundle(getArguments()).getType();
        TypeModel typeModel = CreateScheduleFragmentArgs.fromBundle(getArguments()).getTypeModel();

        if (typeModel != null) {
            if (type.equals("room")) {
                roomModel = (RoomModel) CreateScheduleFragmentArgs.fromBundle(getArguments()).getTypeModel();
                setData(roomModel);
            }
        }

        binding.viewPager.getRoot().setAdapter(adapter);
        tabLayoutMediator.attach();
    }

    private void setData(RoomModel model) {
        if (model.getRoomId() != null && !model.getRoomId().equals("")) {
            data.put("id", model.getRoomId());
        }
    }

    public void doWork() {
        ((MainActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");

        Center.createSchedule(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        ((MainActivity) requireActivity()).loadingDialog.dismiss();
                        Toast.makeText(requireActivity(), requireActivity().getResources().getString(R.string.AppAdded), Toast.LENGTH_SHORT).show();
                    });
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (!jsonObject.isNull("errors")) {
                                Iterator<String> keys = (jsonObject.getJSONObject("errors").keys());

                                while (keys.hasNext()) {
                                    String key = keys.next();
                                    for (int i = 0; i < jsonObject.getJSONObject("errors").getJSONArray(key).length(); i++) {
                                        switch (key) {
                                            case "":
                                                break;
                                        }
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
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