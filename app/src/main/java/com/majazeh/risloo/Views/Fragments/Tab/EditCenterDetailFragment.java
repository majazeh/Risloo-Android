package com.majazeh.risloo.Views.Fragments.Tab;

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
import com.majazeh.risloo.Views.Dialogs.SelectedDialog;
import com.majazeh.risloo.databinding.FragmentEditCenterDetailBinding;

import org.json.JSONException;

import java.util.ArrayList;

public class EditCenterDetailFragment extends Fragment {

    // Binding
    private FragmentEditCenterDetailBinding binding;

    // Adapters
    public SelectedAdapter phonesAdapter;

    // Dialogs
    private SearchableDialog managersDialog;
    private SelectedDialog phonesDialog;

    // Objects
    private RecyclerView.ItemDecoration itemDecoration;
    private LinearLayoutManager phoneLayoutManager;

    // Vars
    private ArrayList<Model> phones = new ArrayList<>();
    public String managerId = "", managerName = "", name = "", address = "", description = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentEditCenterDetailBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        phonesAdapter = new SelectedAdapter(requireActivity());

        managersDialog = new SearchableDialog();
        phonesDialog = new SelectedDialog();

        itemDecoration = new ItemDecorateRecyclerView("verticalLayout", 0, 0, (int) getResources().getDimension(R.dimen._2sdp), 0);

        phoneLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);

        binding.managerIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditCenterDetailFragmentManagerHeader));
        binding.nameIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditCenterDetailFragmentNameHeader));
        binding.addressIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditCenterDetailFragmentAddressHeader));
        binding.phonesIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditCenterDetailFragmentPhonesHeader));
        binding.descriptionIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditCenterDetailFragmentDescriptionHeader));

        binding.addressIncludeLayout.inputEditText.setHint(getResources().getString(R.string.EditCenterDetailFragmentAddressHint));
        binding.descriptionIncludeLayout.inputEditText.setHint(getResources().getString(R.string.EditCenterDetailFragmentDescriptionHint));

        InitManager.unfixedRecyclerView(binding.phonesIncludeLayout.selectRecyclerView, itemDecoration, phoneLayoutManager);

        InitManager.txtTextColor(binding.editTextView.getRoot(), getResources().getString(R.string.EditCenterDetailFragmentButton), getResources().getColor(R.color.White));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.editTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        } else {
            binding.editTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        ClickManager.onDelayedClickListener(() -> {
            managersDialog.show(requireActivity().getSupportFragmentManager(), "managersDialog");
            managersDialog.setData("managers");
        }).widget(binding.managerIncludeLayout.selectTextView);

        binding.nameIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.nameIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.nameIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        binding.addressIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.addressIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.addressIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        binding.phonesIncludeLayout.selectRecyclerView.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                phonesDialog.show(requireActivity().getSupportFragmentManager(), "phonesDialog");
                phonesDialog.setData("phones");
            }
            return false;
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
            if (managerId.equals("")) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.managerIncludeLayout.selectTextView, null, null, getResources().getString(R.string.AppInputEmpty));
            }
            if (binding.nameIncludeLayout.inputEditText.length() == 0) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.nameIncludeLayout.inputEditText, null, null, getResources().getString(R.string.AppInputEmpty));
            }
            if (binding.phonesIncludeLayout.selectRecyclerView.getChildCount() == 0) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.phonesIncludeLayout.selectRecyclerView, null, null, getResources().getString(R.string.AppInputEmpty));
            }

            if (!managerId.equals("") && binding.nameIncludeLayout.inputEditText.length() != 0 && binding.phonesIncludeLayout.selectRecyclerView.getChildCount() != 0) {
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.managerIncludeLayout.selectTextView, null, null);
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.nameIncludeLayout.inputEditText, null, null);
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.phonesIncludeLayout.selectRecyclerView, null, null);

                doWork();
            }
        }).widget(binding.editTextView.getRoot());
    }

    private void setData() {
        if (!((MainActivity) requireActivity()).singleton.getManager().equals("")) {
            managerId = ((MainActivity) requireActivity()).singleton.getManager();
            managerName = ((MainActivity) requireActivity()).singleton.getManager();
            binding.managerIncludeLayout.selectTextView.setText(managerName);
        }
        if (!((MainActivity) requireActivity()).singleton.getName().equals("")) {
            name = ((MainActivity) requireActivity()).singleton.getName();
            binding.nameIncludeLayout.inputEditText.setText(name);
        }
        if (!((MainActivity) requireActivity()).singleton.getAddress().equals("")) {
            address = ((MainActivity) requireActivity()).singleton.getAddress();
            binding.addressIncludeLayout.inputEditText.setText(address);
        }

//        if (extras.getString("phones") != null) {
//            try {
//                JSONArray jsonArray = new JSONArray(extras.getString("phones"));
//
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
//                    Model model = new Model(jsonObject);
//
//                    phones.add(model);
//                }
//
//                setRecyclerView(phones, "phones");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        } else {
        setRecyclerView(phones, new ArrayList<>(),"phones");
//        }

        if (!((MainActivity) requireActivity()).singleton.getDescription().equals("")) {
            description = ((MainActivity) requireActivity()).singleton.getDescription();
            binding.descriptionIncludeLayout.inputEditText.setText(description);
        }
    }

    private void setRecyclerView(ArrayList<Model> items, ArrayList<String> ids, String method) {
        if (method.equals("phones")) {
            phonesAdapter.setItems(items, ids, method, binding.phonesIncludeLayout.countTextView);
            binding.phonesIncludeLayout.selectRecyclerView.setAdapter(phonesAdapter);
        }
    }

    public void responseDialog(String method, Model item) {
        try {
            switch (method) {
                case "managers":
                    if (!managerId.equals(item.get("id").toString())) {
                        managerId = item.get("id").toString();
                        managerName = item.get("title").toString();

                        binding.managerIncludeLayout.selectTextView.setText(managerName);
                    } else if (managerId.equals(item.get("id").toString())) {
                        managerId = "";
                        managerName = "";

                        binding.managerIncludeLayout.selectTextView.setText("");
                    }

                    managersDialog.dismiss();
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void doWork() {
        name = binding.nameIncludeLayout.inputEditText.getText().toString().trim();
        address = binding.addressIncludeLayout.inputEditText.getText().toString().trim();
        description = binding.descriptionIncludeLayout.inputEditText.getText().toString().trim();

        // TODO : Call Work Method
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}