package com.majazeh.risloo.Views.Fragments.Edit;

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
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Widgets.ItemDecorateRecyclerView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.PhonesAdapter;
import com.majazeh.risloo.Views.Dialogs.PhoneDialog;
import com.majazeh.risloo.databinding.FragmentEditCenterDetailBinding;

import java.util.ArrayList;

public class EditCenterDetailFragment extends Fragment {

    // Binding
    public FragmentEditCenterDetailBinding binding;

    // Adapters
    public PhonesAdapter phonesAdapter;

    // Dialogs
    private PhoneDialog phoneDialog;

    // Objects
    private RecyclerView.ItemDecoration itemDecoration;
    private LinearLayoutManager layoutManager;

    // Vars
    private ArrayList<String> phones = new ArrayList<>();
    private String manager = "", name = "", address = "", description = "";

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
        phonesAdapter = new PhonesAdapter(requireActivity());

        phoneDialog = new PhoneDialog();

        itemDecoration = new ItemDecorateRecyclerView("verticalLayout", 0, 0, (int) getResources().getDimension(R.dimen._2sdp), 0);

        layoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);

        binding.managerIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditCenterDetailFragmentManagerHeader));
        binding.nameIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditCenterDetailFragmentNameHeader));
        binding.addressIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditCenterDetailFragmentAddressHeader));
        binding.phonesIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditCenterDetailFragmentPhonesHeader));
        binding.descriptionIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditCenterDetailFragmentDescriptionHeader));

        binding.addressIncludeLayout.inputEditText.setHint(getResources().getString(R.string.EditCenterDetailFragmentAddressHint));
        binding.descriptionIncludeLayout.inputEditText.setHint(getResources().getString(R.string.EditCenterDetailFragmentDescriptionHint));

        InitManager.unfixedRecyclerView(binding.phonesIncludeLayout.selectRecyclerView, itemDecoration, layoutManager);

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
            // TODO : Place Code Here
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
                phoneDialog.show(requireActivity().getSupportFragmentManager(), "phoneDialog");
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
            if (manager.equals("")) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.managerIncludeLayout.selectTextView, binding.managerIncludeLayout.errorImageView, binding.managerIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }
            if (binding.nameIncludeLayout.inputEditText.length() == 0) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.nameIncludeLayout.inputEditText, binding.nameIncludeLayout.errorImageView, binding.nameIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }
            if (binding.phonesIncludeLayout.selectRecyclerView.getChildCount() == 0) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.phonesIncludeLayout.selectRecyclerView, binding.phonesIncludeLayout.errorImageView, binding.phonesIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }

            if (!manager.equals("") && binding.nameIncludeLayout.inputEditText.length() != 0 && binding.phonesIncludeLayout.selectRecyclerView.getChildCount() != 0) {
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.managerIncludeLayout.selectTextView, binding.managerIncludeLayout.errorImageView, binding.managerIncludeLayout.errorTextView);
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.nameIncludeLayout.inputEditText, binding.nameIncludeLayout.errorImageView, binding.nameIncludeLayout.errorTextView);
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.phonesIncludeLayout.selectRecyclerView, binding.phonesIncludeLayout.errorImageView, binding.phonesIncludeLayout.errorTextView);

                doWork();
            }
        }).widget(binding.editTextView.getRoot());
    }

    private void setData() {
        if (!((MainActivity) requireActivity()).singleton.getManager().equals("")) {
            manager = ((MainActivity) requireActivity()).singleton.getManager();
            binding.managerIncludeLayout.selectTextView.setText(manager);
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
//                JSONArray phones = new JSONArray(extras.getString("phones"));
//
//                for (int i = 0; i < phones.length(); i++) {
//                    this.phones.add(phones.getString(i));
//                }
//
//                setRecyclerView();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        } else {
            setRecyclerView();
//        }

        if (!((MainActivity) requireActivity()).singleton.getDescription().equals("")) {
            description = ((MainActivity) requireActivity()).singleton.getDescription();
            binding.descriptionIncludeLayout.inputEditText.setText(description);
        }
    }

    private void setRecyclerView() {
        phonesAdapter.setPhones(phones);
        binding.phonesIncludeLayout.selectRecyclerView.setAdapter(phonesAdapter);
    }

    private void doWork() {
        name = binding.nameIncludeLayout.inputEditText.getText().toString().trim();
        address = binding.addressIncludeLayout.inputEditText.getText().toString().trim();
        description = binding.descriptionIncludeLayout.inputEditText.getText().toString().trim();

        phones = phonesAdapter.getPhones();

        // TODO : Call Work Method
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}