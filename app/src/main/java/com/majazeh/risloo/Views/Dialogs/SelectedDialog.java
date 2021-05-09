package com.majazeh.risloo.Views.Dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

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
import com.majazeh.risloo.Views.Fragments.Create.CreateCenterFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateScheduleFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSchedulePaymentFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionPaymentFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditCenterDetailFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditCenterFragment;
import com.majazeh.risloo.databinding.DialogSelectedBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class SelectedDialog extends AppCompatDialogFragment {

    // Binding
    private DialogSelectedBinding binding;

    // Objects
    private RecyclerView.ItemDecoration itemDecoration;
    private LinearLayoutManager layoutManager;

    // Vars
    private String method;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(requireActivity(), R.style.DialogTheme);

        DialogSelectedBinding binding = DialogSelectedBinding.inflate(LayoutInflater.from(requireActivity()));

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
        binding = DialogSelectedBinding.inflate(inflater, viewGroup, false);

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
        itemDecoration = new ItemDecorateRecyclerView("verticalLayout", 0, 0, (int) getResources().getDimension(R.dimen._2sdp), 0);

        layoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);

        switch (method) {
            case "phones":
                binding.titleTextView.setText(getResources().getString(R.string.DialogPhoneTitle));
                binding.inputEditText.setHint(getResources().getString(R.string.DialogPhoneHint));
                binding.inputEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                binding.entryButton.setText(getResources().getString(R.string.DialogPhoneEntry));
                break;
            case "axises":
                binding.titleTextView.setText(getResources().getString(R.string.DialogAxisTitle));
                binding.inputEditText.setHint(getResources().getString(R.string.DialogAxisHint));
                binding.inputEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                binding.entryButton.setText(getResources().getString(R.string.DialogAxisEntry));
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

        ClickManager.onDelayedClickListener(() -> {
            if (binding.inputEditText.length() != 0) {
                try {
                    String value = binding.inputEditText.getText().toString().trim();
                    Model item;

                    if (method.equals("axises"))
                        item = new Model(new JSONObject().put("id", "").put("title", value));
                    else
                        item = new Model(new JSONObject().put("id", value).put("title", value));

                    switch (Objects.requireNonNull(((MainActivity) requireActivity()).navController.getCurrentDestination()).getId()) {
                        case R.id.createCenterFragment:
                            CreateCenterFragment createCenterFragment = (CreateCenterFragment) ((MainActivity) requireActivity()).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                            if (createCenterFragment != null) {
                                if (method.equals("phones")) {
                                    if (!createCenterFragment.phonesAdapter.getIds().contains(value))
                                        createCenterFragment.phonesAdapter.addItem(item);
                                    else
                                        Toast.makeText(requireActivity(), "exception", Toast.LENGTH_SHORT).show();
                                }
                            }
                            break;
                        case R.id.createScheduleFragment:
                            CreateScheduleFragment createScheduleFragment = (CreateScheduleFragment) ((MainActivity) requireActivity()).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                            if (createScheduleFragment != null) {
                                CreateScheduleSessionFragment createScheduleSessionFragment = (CreateScheduleSessionFragment) createScheduleFragment.adapter.hashMap.get(createScheduleFragment.binding.viewPager.getRoot().getCurrentItem());
                                CreateSchedulePaymentFragment createSchedulePaymentFragment = (CreateSchedulePaymentFragment) createScheduleFragment.adapter.hashMap.get(3);

                                if (method.equals("axises")) {
                                    if (!createScheduleSessionFragment.axisesAdapter.getIds().contains(value)) {
                                        createScheduleSessionFragment.axisesAdapter.addItem(item);
                                        createSchedulePaymentFragment.axisPaymentsAdapter.addItem(item);
                                    } else {
                                        Toast.makeText(requireActivity(), "exception", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            break;
                        case R.id.createSessionFragment:
                            CreateSessionFragment createSessionFragment = (CreateSessionFragment) ((MainActivity) requireActivity()).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                            if (createSessionFragment != null) {
                                CreateSessionSessionFragment createSessionSessionFragment = (CreateSessionSessionFragment) createSessionFragment.adapter.hashMap.get(createSessionFragment.binding.viewPager.getRoot().getCurrentItem());
                                CreateSessionPaymentFragment createSessionPaymentFragment = (CreateSessionPaymentFragment) createSessionFragment.adapter.hashMap.get(2);

                                if (method.equals("axises")) {
                                    if (!createSessionSessionFragment.axisesAdapter.getIds().contains(value)) {
                                        createSessionSessionFragment.axisesAdapter.addItem(item);
                                        createSessionPaymentFragment.axisPaymentsAdapter.addItem(item);
                                    } else {
                                        Toast.makeText(requireActivity(), "exception", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            break;
                        case R.id.editCenterFragment:
                            EditCenterFragment editCenterFragment = (EditCenterFragment) ((MainActivity) requireActivity()).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                            if (editCenterFragment != null) {
                                EditCenterDetailFragment editCenterDetailFragment = (EditCenterDetailFragment) editCenterFragment.adapter.hashMap.get(editCenterFragment.binding.viewPager.getRoot().getCurrentItem());

                                if (method.equals("phones")) {
                                    if (!editCenterDetailFragment.phonesAdapter.getIds().contains(value))
                                        editCenterDetailFragment.phonesAdapter.addItem(item);
                                    else
                                        Toast.makeText(requireActivity(), "exception", Toast.LENGTH_SHORT).show();
                                }
                            }
                            break;
                    }

                    ((MainActivity) requireActivity()).controlEditText.clear(requireActivity(), ((MainActivity) requireActivity()).controlEditText.input());
                    clearEditText();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                dismiss();
            }
        }).widget(binding.entryButton);
    }

    private void setRecyclerView() {
        switch (Objects.requireNonNull(((MainActivity) requireActivity()).navController.getCurrentDestination()).getId()) {
            case R.id.createCenterFragment:
                CreateCenterFragment createCenterFragment = (CreateCenterFragment) ((MainActivity) requireActivity()).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                if (createCenterFragment != null) {
                    if (method.equals("phones")) {
                        binding.listRecyclerView.setAdapter(createCenterFragment.phonesAdapter);
                    }
                }
                break;
            case R.id.createScheduleFragment:
                CreateScheduleFragment createScheduleFragment = (CreateScheduleFragment) ((MainActivity) requireActivity()).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                if (createScheduleFragment != null) {
                    CreateScheduleSessionFragment createScheduleSessionFragment = (CreateScheduleSessionFragment) createScheduleFragment.adapter.hashMap.get(createScheduleFragment.binding.viewPager.getRoot().getCurrentItem());

                    if (method.equals("axises")) {
                        binding.listRecyclerView.setAdapter(createScheduleSessionFragment.axisesAdapter);
                    }
                }
                break;
            case R.id.createSessionFragment:
                CreateSessionFragment createSessionFragment = (CreateSessionFragment) ((MainActivity) requireActivity()).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                if (createSessionFragment != null) {
                    CreateSessionSessionFragment createSessionSessionFragment = (CreateSessionSessionFragment) createSessionFragment.adapter.hashMap.get(createSessionFragment.binding.viewPager.getRoot().getCurrentItem());

                    if (method.equals("axises")) {
                        binding.listRecyclerView.setAdapter(createSessionSessionFragment.axisesAdapter);
                    }
                }
                break;
            case R.id.editCenterFragment:
                EditCenterFragment editCenterFragment = (EditCenterFragment) ((MainActivity) requireActivity()).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                if (editCenterFragment != null) {
                    EditCenterDetailFragment editCenterDetailFragment = (EditCenterDetailFragment) editCenterFragment.adapter.hashMap.get(editCenterFragment.binding.viewPager.getRoot().getCurrentItem());

                    if (method.equals("phones")) {
                        binding.listRecyclerView.setAdapter(editCenterDetailFragment.phonesAdapter);
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