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
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.SelectedAdapter;
import com.majazeh.risloo.Views.Dialogs.SearchableDialog;
import com.majazeh.risloo.databinding.FragmentCreateRoomUserBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Room;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class CreateRoomUserFragment extends Fragment {

    // Binding
    private FragmentCreateRoomUserBinding binding;

    // Adapters
    public SelectedAdapter referencesAdapter;

    // Dialogs
    private SearchableDialog referencesDialog;

    // Vars
    private HashMap data, header;
    public String roomId = "", centerId = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateRoomUserBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setExtra();

        return binding.getRoot();
    }

    private void initializer() {
        referencesAdapter = new SelectedAdapter(requireActivity());

        referencesDialog = new SearchableDialog();

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.referenceIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateRoomUserFragmentReferenceHeader));

        InitManager.unfixedVerticalRecyclerView(requireActivity(), binding.referenceIncludeLayout.selectRecyclerView, 0, 0, getResources().getDimension(R.dimen._2sdp), 0);

        InitManager.txtTextColor(binding.createTextView.getRoot(), getResources().getString(R.string.CreateRoomUserFragmentButton), getResources().getColor(R.color.White));
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
        binding.referenceIncludeLayout.selectRecyclerView.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                referencesDialog.show(requireActivity().getSupportFragmentManager(), "referencesDialog");
                referencesDialog.setData("references");
            }
            return false;
        });

        ClickManager.onDelayedClickListener(() -> {
            if (binding.referenceIncludeLayout.selectRecyclerView.getChildCount() == 0) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.referenceIncludeLayout.selectRecyclerView, binding.referenceErrorLayout.getRoot(), binding.referenceErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.referenceIncludeLayout.selectRecyclerView, binding.referenceErrorLayout.getRoot(), binding.referenceErrorLayout.errorTextView);
                doWork();
            }
        }).widget(binding.createTextView.getRoot());
    }

    private void setExtra() {
        if (getArguments() != null) {
            if (getArguments().getString("id") != null && !getArguments().getString("id").equals("")) {
                roomId = getArguments().getString("id");
                data.put("id", roomId);
            }

            if (getArguments().getString("center_id") != null && !getArguments().getString("center_id").equals("")) {
                centerId = getArguments().getString("center_id");
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
        switch (method) {
            case "references":
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

                break;
        }
    }

    private void doWork() {
        ((MainActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");

        data.put("user_id", referencesAdapter.getIds());

        Room.createUser(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        Bundle extras = new Bundle();
                        extras.putString("id", roomId);

                        ((MainActivity) requireActivity()).loadingDialog.dismiss();
                        Toast.makeText(requireActivity(), requireActivity().getResources().getString(R.string.AppAdded), Toast.LENGTH_SHORT).show();
//                        ((MainActivity) requireActivity()).navigator(R.id.roomUsersFragment, extras);
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
                                        if (key.equals("user_id")) {
                                            ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.referenceIncludeLayout.selectRecyclerView, binding.referenceErrorLayout.getRoot(), binding.referenceErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
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