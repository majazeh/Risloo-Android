package com.majazeh.risloo.Views.Dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Entities.Model;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.ParamsManager;
import com.majazeh.risloo.Utils.Widgets.ItemDecorateRecyclerView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.SearchableAdapter;
import com.majazeh.risloo.Views.Fragments.Create.CreateCaseFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateCaseUserFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateCenterFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateCenterUserFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateRoomFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateSampleFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditCenterDetailFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditCenterFragment;
import com.majazeh.risloo.databinding.DialogSearchableBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class SearchableDialog extends AppCompatDialogFragment {

    // Binding
    private DialogSearchableBinding binding;

    // Adapters
    private SearchableAdapter searchableAdapter;

    // Objects
    private RecyclerView.ItemDecoration itemDecoration;
    private LinearLayoutManager layoutManager;
    private Handler handler;

    // Vars
    private String method;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(requireActivity(), R.style.DialogTheme);

        DialogSearchableBinding binding = DialogSearchableBinding.inflate(LayoutInflater.from(requireActivity()));

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(binding.getRoot());
        dialog.setCancelable(true);
        dialog.getWindow().setAttributes(ParamsManager.applyWrapContent(dialog));

        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = DialogSearchableBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        detector();

        setRecyclerView();

        return binding.getRoot();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        clearEditText();
    }

    private void initializer() {
        searchableAdapter = new SearchableAdapter(requireActivity());

        itemDecoration = new ItemDecorateRecyclerView("verticalLayout", 0, 0, (int) getResources().getDimension(R.dimen._2sdp), 0);

        layoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);

        handler = new Handler();

        switch (method) {
            case "scales":
                binding.titleTextView.setText(getResources().getString(R.string.DialogScaleTitle));
                binding.inputEditText.setHint(getResources().getString(R.string.DialogScaleHint));
                binding.entryButton.setText(getResources().getString(R.string.DialogScaleEntry));
                break;
            case "references":
                binding.titleTextView.setText(getResources().getString(R.string.DialogReferenceTitle));
                binding.inputEditText.setHint(getResources().getString(R.string.DialogReferenceHint));
                binding.entryButton.setText(getResources().getString(R.string.DialogReferenceEntry));
                break;
            case "managers":
                binding.titleTextView.setText(getResources().getString(R.string.DialogManagerTitle));
                binding.inputEditText.setHint(getResources().getString(R.string.DialogManagerHint));
                binding.entryButton.setVisibility(View.GONE);
                break;
            case "rooms":
                binding.titleTextView.setText(getResources().getString(R.string.DialogRoomTitle));
                binding.inputEditText.setHint(getResources().getString(R.string.DialogRoomHint));
                binding.entryButton.setVisibility(View.GONE);
                break;
            case "cases":
                binding.titleTextView.setText(getResources().getString(R.string.DialogCaseTitle));
                binding.inputEditText.setHint(getResources().getString(R.string.DialogCaseHint));
                binding.entryButton.setVisibility(View.GONE);
                break;
            case "sessions":
                binding.titleTextView.setText(getResources().getString(R.string.DialogSessionTitle));
                binding.inputEditText.setHint(getResources().getString(R.string.DialogSessionHint));
                binding.entryButton.setVisibility(View.GONE);
                break;
            case "psychologies":
                binding.titleTextView.setText(getResources().getString(R.string.DialogPsychologyTitle));
                binding.inputEditText.setHint(getResources().getString(R.string.DialogPsychologyHint));
                binding.entryButton.setVisibility(View.GONE);
                break;
        }

        InitManager.unfixedRecyclerView(binding.listRecyclerView, itemDecoration, layoutManager);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.entryButton.setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.inputEditText);
                }
            }
            return false;
        });

        binding.inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(() -> {
                    // TODO : Place Code Here
                }, 750);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ClickManager.onDelayedClickListener(() -> {
            dismiss();
        }).widget(binding.entryButton);
    }

    private void setRecyclerView() {
        ArrayList<Model> values = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", String.valueOf(i));
                jsonObject.put("title", "عنوان" + " " + i);
                jsonObject.put("subtitle", "زیرنویس" + " " + i);

                Model model = new Model(jsonObject);

                values.add(model);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        switch (Objects.requireNonNull(((MainActivity) requireActivity()).navController.getCurrentDestination()).getId()) {
            case R.id.createCaseFragment:
                CreateCaseFragment createCaseFragment = (CreateCaseFragment) ((MainActivity) requireActivity()).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                if (createCaseFragment != null) {
                    switch (method) {
                        case "references":
                            searchableAdapter.setItems(values, method);
                            binding.listRecyclerView.setAdapter(searchableAdapter);
                            break;
                        case "rooms":
                            searchableAdapter.setItems(values, method);
                            binding.listRecyclerView.setAdapter(searchableAdapter);
                            break;
                    }
                }
                break;
            case R.id.createCaseUserFragment:
                CreateCaseUserFragment createCaseUserFragment = (CreateCaseUserFragment) ((MainActivity) requireActivity()).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                if (createCaseUserFragment != null) {
                    if (method.equals("references")) {
                        searchableAdapter.setItems(values, method);
                        binding.listRecyclerView.setAdapter(searchableAdapter);
                    }
                }
                break;
            case R.id.createCenterFragment:
                CreateCenterFragment createCenterFragment = (CreateCenterFragment) ((MainActivity) requireActivity()).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                if (createCenterFragment != null) {
                    if (method.equals("managers")) {
                        searchableAdapter.setItems(values, method);
                        binding.listRecyclerView.setAdapter(searchableAdapter);
                    }
                }
                break;
            case R.id.createCenterUserFragment:
                CreateCenterUserFragment createCenterUserFragment = (CreateCenterUserFragment) ((MainActivity) requireActivity()).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                if (createCenterUserFragment != null) {
                    if (method.equals("rooms")) {
                        searchableAdapter.setItems(values, method);
                        binding.listRecyclerView.setAdapter(searchableAdapter);
                    }
                }
                break;
            case R.id.createRoomFragment:
                CreateRoomFragment createRoomFragment = (CreateRoomFragment) ((MainActivity) requireActivity()).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                if (createRoomFragment != null) {
                    if (method.equals("psychologies")) {
                        searchableAdapter.setItems(values, method);
                        binding.listRecyclerView.setAdapter(searchableAdapter);
                    }
                }
                break;
            case R.id.createSampleFragment:
                CreateSampleFragment createSampleFragment = (CreateSampleFragment) ((MainActivity) requireActivity()).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                if (createSampleFragment != null) {
                    switch (method) {
                        case "scales":
                            searchableAdapter.setItems(values, method);
                            binding.listRecyclerView.setAdapter(searchableAdapter);
                            break;
                        case "references":
                            searchableAdapter.setItems(values, method);
                            binding.listRecyclerView.setAdapter(searchableAdapter);
                            break;
                        case "rooms":
                            searchableAdapter.setItems(values, method);
                            binding.listRecyclerView.setAdapter(searchableAdapter);
                            break;
                        case "cases":
                            searchableAdapter.setItems(values, method);
                            binding.listRecyclerView.setAdapter(searchableAdapter);
                            break;
                        case "sessions":
                            searchableAdapter.setItems(values, method);
                            binding.listRecyclerView.setAdapter(searchableAdapter);
                            break;
                    }
                }
                break;
            case R.id.editCenterFragment:
                EditCenterFragment editCenterFragment = (EditCenterFragment) ((MainActivity) requireActivity()).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                if (editCenterFragment != null) {
                    EditCenterDetailFragment editCenterDetailFragment = (EditCenterDetailFragment) editCenterFragment.adapter.getRegisteredFragment(0);

                    if (method.equals("managers")) {
                        searchableAdapter.setItems(values, method);
                        binding.listRecyclerView.setAdapter(searchableAdapter);
                    }
                }
                break;
        }
    }

    private void clearEditText() {
        binding.inputEditText.getText().clear();
    }

    public void setData(String method) {
        this.method = method;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}