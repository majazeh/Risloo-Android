package com.majazeh.risloo.Views.Fragments.Create;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Entities.Model;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Widgets.ItemDecorateRecyclerView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.SelectedAdapter;
import com.majazeh.risloo.Views.Dialogs.SearchableDialog;
import com.majazeh.risloo.databinding.FragmentCreateCaseBinding;

import org.json.JSONException;

import java.util.ArrayList;

public class CreateCaseFragment extends Fragment {

    // Binding
    private FragmentCreateCaseBinding binding;

    // Adapters
    public SelectedAdapter referencesAdapter;

    // Dialogs
    private SearchableDialog roomsDialog, referencesDialog;

    // Objects
    private RecyclerView.ItemDecoration itemDecoration;
    private LinearLayoutManager referencesLayoutManager;

    // Vars
    private ArrayList<Model> references = new ArrayList<>();
    public String roomId = "", roomName = "", centerName = "", situation = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateCaseBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        referencesAdapter = new SelectedAdapter(requireActivity());

        roomsDialog = new SearchableDialog();
        referencesDialog = new SearchableDialog();

        itemDecoration = new ItemDecorateRecyclerView("verticalLayout", 0, 0, (int) getResources().getDimension(R.dimen._2sdp), 0);

        referencesLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);

        binding.roomIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCaseFragmentRoomHeader));
        binding.referenceIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCaseFragmentReferenceHeader));
        binding.situationIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCaseFragmentSituationHeader));

        InitManager.unfixedRecyclerView(binding.referenceIncludeLayout.selectRecyclerView, itemDecoration, referencesLayoutManager);

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

        binding.situationIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.situationIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.situationIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        ClickManager.onDelayedClickListener(() -> {
            if (roomId.equals("")) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.roomIncludeLayout.selectContainer, binding.roomErrorLayout.errorImageView, binding.roomErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }
            if (binding.referenceIncludeLayout.selectRecyclerView.getChildCount() == 0) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.referenceIncludeLayout.selectRecyclerView, binding.referenceErrorLayout.errorImageView, binding.referenceErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }
            if (binding.situationIncludeLayout.inputEditText.length() == 0) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.situationIncludeLayout.inputEditText, binding.situationErrorLayout.errorImageView, binding.situationErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }

            if (!roomId.equals("") && binding.referenceIncludeLayout.selectRecyclerView.getChildCount() != 0 && binding.situationIncludeLayout.inputEditText.length() != 0) {
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.roomIncludeLayout.selectContainer, binding.roomErrorLayout.errorImageView, binding.roomErrorLayout.errorTextView);
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.referenceIncludeLayout.selectRecyclerView, binding.referenceErrorLayout.errorImageView, binding.referenceErrorLayout.errorTextView);
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.situationIncludeLayout.inputEditText, binding.situationErrorLayout.errorImageView, binding.situationErrorLayout.errorTextView);

                doWork();
            }
        }).widget(binding.createTextView.getRoot());
    }

    private void setData() {
        if (!((MainActivity) requireActivity()).singleton.getAddress().equals("")) {
            roomId = ((MainActivity) requireActivity()).singleton.getAddress();
            roomName = ((MainActivity) requireActivity()).singleton.getAddress();
            binding.roomIncludeLayout.primaryTextView.setText(roomName);
        }
        if (!((MainActivity) requireActivity()).singleton.getAddress().equals("")) {
            centerName = ((MainActivity) requireActivity()).singleton.getAddress();
            binding.roomIncludeLayout.secondaryTextView.setText(centerName);
        }

//        if (extras.getString("references") != null) {
//            try {
//                JSONArray jsonArray = new JSONArray(extras.getString("references"));
//
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
//                    Model model = new Model(jsonObject);
//
//                    references.add(model);
//                }
//
//                setRecyclerView(references, "references");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        } else {
        setRecyclerView(references, new ArrayList<>(), "references");
//        }

        if (!((MainActivity) requireActivity()).singleton.getAddress().equals("")) {
            situation = ((MainActivity) requireActivity()).singleton.getAddress();
            binding.situationIncludeLayout.inputEditText.setText(situation);
        }
    }

    private void setRecyclerView(ArrayList<Model> items, ArrayList<String> ids, String method) {
        if (method.equals("references")) {
            referencesAdapter.setItems(items, ids, method, binding.referenceIncludeLayout.countTextView);
            binding.referenceIncludeLayout.selectRecyclerView.setAdapter(referencesAdapter);
        }
    }

    public void responseDialog(String method, Model item) {
        try {
            switch (method) {
                case "rooms":
                    if (!roomId.equals(item.get("id").toString())) {
                        roomId = item.get("id").toString();
                        roomName = item.get("title").toString();
                        centerName = item.get("subtitle").toString();

                        binding.roomIncludeLayout.primaryTextView.setText(roomName);
                        binding.roomIncludeLayout.secondaryTextView.setText(centerName);
                    } else if (roomId.equals(item.get("id").toString())) {
                        roomId = "";
                        roomName = "";
                        centerName = "";

                        binding.roomIncludeLayout.primaryTextView.setText("");
                        binding.roomIncludeLayout.secondaryTextView.setText("");
                    }

                    roomsDialog.dismiss();
                    break;
                case "references":
                    int position = referencesAdapter.getIds().indexOf(item.get("id").toString());

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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void doWork() {
        situation = binding.situationIncludeLayout.inputEditText.getText().toString().trim();

        // TODO : Call Work Method
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}