package com.majazeh.risloo.views.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.utils.managers.ToastManager;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.ParamsManager;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.fragments.main.create.FragmentCreateCenter;
import com.majazeh.risloo.views.fragments.main.tab.FragmentCreateScheduleTabPayment;
import com.majazeh.risloo.views.fragments.main.tab.FragmentCreateScheduleTabSession;
import com.majazeh.risloo.views.fragments.main.tab.FragmentCreateSessionTabPayment;
import com.majazeh.risloo.views.fragments.main.tab.FragmentCreateSessionTabSession;
import com.majazeh.risloo.views.fragments.main.tab.FragmentEditCenterTabDetail;
import com.majazeh.risloo.databinding.DialogSelectedBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class DialogSelected extends AppCompatDialogFragment {

    // Binding
    private DialogSelectedBinding binding;

    // Fragments
    private Fragment current, child, payment;

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
        dialog.getWindow().setAttributes(ParamsManager.matchWrapContent(dialog));

        setCancelable(true);

        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = DialogSelectedBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setDialog();

        setRecyclerView();

        return binding.getRoot();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        binding.inputEditText.getText().clear();
    }

    private void initializer() {
        current = ((ActivityMain) requireActivity()).fragmont.getCurrent();
        child = ((ActivityMain) requireActivity()).fragmont.getChild();
        payment = ((ActivityMain) requireActivity()).fragmont.getPayment();

        InitManager.unfixedVerticalRecyclerView(requireActivity(), binding.listRecyclerView, getResources().getDimension(R.dimen._16sdp), 0, getResources().getDimension(R.dimen._2sdp), 0);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.inputEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.inputEditText);
            return false;
        });

        CustomClickView.onDelayedListener(() -> {
            String value = binding.inputEditText.getText().toString().trim();

            if (!value.equals("")) {
                try {
                    TypeModel model;

                    if (method.equals("axises"))
                        model =  new TypeModel(new JSONObject().put("id", value).put("amount", "0"));
                    else
                        model = new TypeModel(new JSONObject().put("id", value));

                    addItem(model);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                dismiss();
            }
        }).widget(binding.entryButton);
    }

    private void setDialog() {
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
    }

    private void setRecyclerView() {
        if (current instanceof FragmentCreateCenter) {
            if (method.equals("phones"))
                binding.listRecyclerView.setAdapter(((FragmentCreateCenter) current).phonesAdapter);
        }

        if (child instanceof FragmentCreateScheduleTabSession) {
            if (method.equals("axises"))
                binding.listRecyclerView.setAdapter(((FragmentCreateScheduleTabSession) child).axisesAdapter);
        }

        if (child instanceof FragmentCreateSessionTabSession) {
            if (method.equals("axises"))
                binding.listRecyclerView.setAdapter(((FragmentCreateSessionTabSession) child).axisesAdapter);
        }

        if (child instanceof FragmentEditCenterTabDetail) {
            if (method.equals("phones"))
                binding.listRecyclerView.setAdapter(((FragmentEditCenterTabDetail) child).phonesAdapter);
        }

        calculateCount();
    }

    private void addItem(TypeModel item) {
        try {
            if (current instanceof FragmentCreateCenter) {
                if (method.equals("phones"))
                    if (!((FragmentCreateCenter) current).phonesAdapter.getIds().contains(item.object.getString("id")))
                        ((FragmentCreateCenter) current).phonesAdapter.addItem(item);
                    else
                        ToastManager.showDefaultToast(requireActivity(), requireActivity().getResources().getString(R.string.ToastHadSelectedPhone));
            }

            if (child instanceof FragmentCreateScheduleTabSession) {
                if (method.equals("axises"))
                    if (!((FragmentCreateScheduleTabSession) child).axisesAdapter.getIds().contains(item.object.getString("id"))) {
                        ((FragmentCreateScheduleTabSession) child).axisesAdapter.addItem(item);

                        addPayment(item);
                    } else
                        ToastManager.showDefaultToast(requireActivity(), requireActivity().getResources().getString(R.string.ToastHadSelectedAxis));
            }

            if (child instanceof FragmentCreateSessionTabSession) {
                if (method.equals("axises"))
                    if (!((FragmentCreateSessionTabSession) child).axisesAdapter.getIds().contains(item.object.getString("id"))) {
                        ((FragmentCreateSessionTabSession) child).axisesAdapter.addItem(item);

                        addPayment(item);
                    } else
                        ToastManager.showDefaultToast(requireActivity(), requireActivity().getResources().getString(R.string.ToastHadSelectedAxis));
            }

            if (child instanceof FragmentEditCenterTabDetail) {
                if (method.equals("phones"))
                    if (!((FragmentEditCenterTabDetail) child).phonesAdapter.getIds().contains(item.object.getString("id")))
                        ((FragmentEditCenterTabDetail) child).phonesAdapter.addItem(item);
                    else
                        ToastManager.showDefaultToast(requireActivity(), requireActivity().getResources().getString(R.string.ToastHadSelectedPhone));
            }

            calculateCount();

            ((ActivityMain) requireActivity()).inputon.clear(((ActivityMain) requireActivity()).inputon.editText);
            binding.inputEditText.getText().clear();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addPayment(TypeModel item) {
        if (payment instanceof FragmentCreateScheduleTabPayment)
            ((FragmentCreateScheduleTabPayment) payment).axisAdapter.addItem(item);

        if (payment instanceof FragmentCreateSessionTabPayment)
            ((FragmentCreateSessionTabPayment) payment).axisAdapter.addItem(item);
    }

    public void calculateCount() {
        if (Objects.requireNonNull(binding.listRecyclerView.getAdapter()).getItemCount() != 0) {
            binding.countTextView.setVisibility(View.VISIBLE);
            binding.countTextView.setText(StringManager.bracing(binding.listRecyclerView.getAdapter().getItemCount()));
        } else {
            binding.countTextView.setVisibility(View.GONE);
            binding.countTextView.setText("");
        }
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