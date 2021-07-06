package com.majazeh.risloo.Views.Fragments.Create;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Case;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.SelectedAdapter;
import com.majazeh.risloo.Views.Dialogs.SearchableDialog;
import com.majazeh.risloo.databinding.FragmentCreateCaseBinding;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class CreateCaseFragment extends Fragment {

    // Binding
    private FragmentCreateCaseBinding binding;

    // Adapters
    public SelectedAdapter referencesAdapter;

    // Dialogs
    private SearchableDialog roomsDialog, referencesDialog;

    // Vars
    private HashMap data, header;
    public String roomId = "", roomName = "", centerName = "", problem = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateCaseBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setExtra();

        return binding.getRoot();
    }

    private void initializer() {
        referencesAdapter = new SelectedAdapter(requireActivity());

        roomsDialog = new SearchableDialog();
        referencesDialog = new SearchableDialog();

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.roomIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCaseFragmentRoomHeader));
        binding.referenceIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCaseFragmentReferenceHeader));
        binding.problemIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCaseFragmentProblemHeader));

        InitManager.unfixedVerticalRecyclerView(requireActivity(), binding.referenceIncludeLayout.selectRecyclerView, 0, 0, getResources().getDimension(R.dimen._2sdp), 0);

        InitManager.txtTextColor(binding.createTextView.getRoot(), getResources().getString(R.string.CreateCenterFragmentButton), getResources().getColor(R.color.White));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.createTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        } else {
            binding.createTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        ClickManager.onDelayedClickListener(() -> {
            roomsDialog.show(requireActivity().getSupportFragmentManager(), "roomsDialog");
            roomsDialog.setData("rooms");
        }).widget(binding.roomIncludeLayout.selectContainer);

        binding.referenceIncludeLayout.selectRecyclerView.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                referencesDialog.show(requireActivity().getSupportFragmentManager(), "referencesDialog");
                referencesDialog.setData("references");
            }
            return false;
        });

        binding.problemIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.problemIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.problemIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        ClickManager.onDelayedClickListener(() -> {
            if (roomId.equals("")) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.roomIncludeLayout.selectContainer, binding.roomErrorLayout.getRoot(), binding.roomErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.roomIncludeLayout.selectContainer, binding.roomErrorLayout.getRoot(), binding.roomErrorLayout.errorTextView);
                doWork();
            }
        }).widget(binding.createTextView.getRoot());
    }

    private void setExtra() {
        if (getArguments() != null) {
            if (getArguments().getString("id") != null && !getArguments().getString("id").equals("")) {
                data.put("id", getArguments().getString("id"));
            }

            if (getArguments().getString("room_id") != null && !getArguments().getString("room_id").equals("") && getArguments().getString("room_name") != null && !getArguments().getString("room_name").equals("")) {
                roomId = getArguments().getString("room_id");
                roomName = getArguments().getString("room_name");
                binding.roomIncludeLayout.primaryTextView.setText(roomName);
            }

            if (getArguments().getString("center_name") != null && !getArguments().getString("center_name").equals("")) {
                centerName = getArguments().getString("center_name");
                binding.roomIncludeLayout.secondaryTextView.setText(centerName);
            }

            if (getArguments().getString("problem") != null && !getArguments().getString("problem").equals("")) {
                problem = getArguments().getString("problem");
                binding.problemIncludeLayout.inputEditText.setText(problem);
            }

            if (getArguments().getString("clients") != null && !getArguments().getString("clients").equals("")) {
                try {
                    JSONArray clients = new JSONArray(getArguments().getString("clients"));

                    ArrayList<TypeModel> references = new ArrayList<>();
                    ArrayList<String> ids = new ArrayList<>();

                    for (int i = 0; i < clients.length(); i++) {
                        TypeModel model = new TypeModel((JSONObject) clients.get(i));

                        references.add(model);
                        ids.add(model.object.getString("id"));
                    }

                    setRecyclerView(references, ids, "references");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                setRecyclerView(new ArrayList<>(), new ArrayList<>(), "references");
            }
        } else {
            setRecyclerView(new ArrayList<>(), new ArrayList<>(), "references");
        }
    }

    private void setRecyclerView(ArrayList<TypeModel> items, ArrayList<String> ids, String method) {
        if (method.equals("references")) {
            referencesAdapter.setItems(items, ids, method, binding.referenceIncludeLayout.countTextView);
            binding.referenceIncludeLayout.selectRecyclerView.setAdapter(referencesAdapter);
        }
    }

    public void responseDialog(String method, TypeModel item) {
        try {
            switch (method) {
                case "rooms": {
                    RoomModel model = (RoomModel) item;

                    if (!roomId.equals(model.getRoomId())) {
                        roomId = model.getRoomId();
                        roomName = model.getRoomManager().getName();
                        centerName = model.getRoomCenter().getDetail().getString("title");

                        binding.roomIncludeLayout.primaryTextView.setText(roomName);
                        binding.roomIncludeLayout.secondaryTextView.setText(centerName);
                    } else if (roomId.equals(model.getRoomId())) {
                        roomId = "";
                        roomName = "";
                        centerName = "";

                        binding.roomIncludeLayout.primaryTextView.setText("");
                        binding.roomIncludeLayout.secondaryTextView.setText("");
                    }

                    roomsDialog.dismiss();
                }
                break;
                case "references": {
                    UserModel model = (UserModel) item;

                    int position = referencesAdapter.getIds().indexOf(model.getId());

                    if (position == -1)
                        referencesAdapter.addItem(item);
                    else
                        referencesAdapter.removeItem(position);

                    if (referencesAdapter.getIds().size() != 0) {
                        binding.referenceIncludeLayout.countTextView.setVisibility(View.VISIBLE);
                        binding.referenceIncludeLayout.countTextView.setText("(" + referencesAdapter.getIds().size() + ")");
                    } else {
                        binding.referenceIncludeLayout.countTextView.setVisibility(View.GONE);
                        binding.referenceIncludeLayout.countTextView.setText("");
                    }
                }
                break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void doWork() {
        ((MainActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");

        problem = binding.problemIncludeLayout.inputEditText.getText().toString().trim();

        data.put("room_id", roomId);
        data.put("client_id", referencesAdapter.getIds());
        data.put("problem", problem);

        Case.create(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        ((MainActivity) requireActivity()).loadingDialog.dismiss();
                        Toast.makeText(requireActivity(), requireActivity().getResources().getString(R.string.AppAdded), Toast.LENGTH_SHORT).show();
//                        ((MainActivity) requireActivity()).navigator(R.id.casesFragment);
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
                                            case "room_id":
                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.roomIncludeLayout.selectContainer, binding.roomErrorLayout.getRoot(), binding.roomErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                                break;
                                            case "client_id":
                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.referenceIncludeLayout.selectRecyclerView, binding.referenceErrorLayout.getRoot(), binding.referenceErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                                break;
                                            case "problem":
                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.problemIncludeLayout.inputEditText, binding.problemErrorLayout.getRoot(), binding.problemErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
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