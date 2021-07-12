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
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.ParamsManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Create.CreateCenterFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSchedulePaymentFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionPaymentFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditCenterDetailFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionPaymentFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionSessionFragment;
import com.majazeh.risloo.databinding.DialogSelectedBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class SelectedDialog extends AppCompatDialogFragment {

    // Binding
    private DialogSelectedBinding binding;

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
        binding.inputEditText.getText().clear();
    }

    private void initializer() {
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

        InitManager.unfixedVerticalRecyclerView(requireActivity(), binding.listRecyclerView, getResources().getDimension(R.dimen._16sdp), 0, getResources().getDimension(R.dimen._2sdp), 0);
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
            String text = binding.inputEditText.getText().toString().trim();

            if (!text.equals("")) {
                refreshList(createItem(text));
            } else
                dismiss();
        }).widget(binding.entryButton);
    }

    private void setRecyclerView() {
        Fragment current = ((MainActivity) requireActivity()).fragmont.getCurrent();
        Fragment child = ((MainActivity) requireActivity()).fragmont.getChild();

        if (current instanceof CreateCenterFragment) {
            if (method.equals("phones"))
                binding.listRecyclerView.setAdapter(((CreateCenterFragment) current).phonesAdapter);
        }

        if (child instanceof CreateScheduleSessionFragment) {
            if (method.equals("axises"))
                binding.listRecyclerView.setAdapter(((CreateScheduleSessionFragment) child).axisesAdapter);
        }

        if (child instanceof CreateSessionSessionFragment) {
            if (method.equals("axises"))
                binding.listRecyclerView.setAdapter(((CreateSessionSessionFragment) child).axisesAdapter);
        }

        if (child instanceof EditCenterDetailFragment) {
            if (method.equals("phones"))
                binding.listRecyclerView.setAdapter(((EditCenterDetailFragment) child).phonesAdapter);
        }

        if (child instanceof EditSessionSessionFragment) {
            if (method.equals("axises"))
                binding.listRecyclerView.setAdapter(((EditSessionSessionFragment) child).axisesAdapter);
        }

        calculateCount();
    }

    private TypeModel createItem(String text) {
        try {
            if (method.equals("axises"))
                return new TypeModel(new JSONObject().put("id", "").put("title", text));
            else
                return new TypeModel(new JSONObject().put("id", text).put("title", text));

        } catch (JSONException e) {
            e.printStackTrace();
        } return null;
    }

    private void addPayment(TypeModel item) {
        Fragment payment = ((MainActivity) requireActivity()).fragmont.getPayment();

        if (payment instanceof CreateSchedulePaymentFragment)
            ((CreateSchedulePaymentFragment) payment).axisAdapter.addItem(item);

        if (payment instanceof CreateSessionPaymentFragment)
            ((CreateSessionPaymentFragment) payment).axisAdapter.addItem(item);

        if (payment instanceof EditSessionPaymentFragment)
            ((EditSessionPaymentFragment) payment).axisAdapter.addItem(item);
    }

    private void refreshList(TypeModel item) {
        Fragment current = ((MainActivity) requireActivity()).fragmont.getCurrent();
        Fragment child = ((MainActivity) requireActivity()).fragmont.getChild();

        try {
            if (current instanceof CreateCenterFragment) {
                if (method.equals("phones"))
                    if (!((CreateCenterFragment) current).phonesAdapter.getIds().contains(item.object.getString("id")))
                        ((CreateCenterFragment) current).phonesAdapter.addItem(item);
                    else
                        Toast.makeText(requireActivity(), "موجود هست", Toast.LENGTH_SHORT).show();
            }

            if (child instanceof CreateScheduleSessionFragment) {
                if (method.equals("axises"))
                    if (!((CreateScheduleSessionFragment) child).axisesAdapter.getIds().contains(item.object.getString("id"))) {
                        ((CreateScheduleSessionFragment) child).axisesAdapter.addItem(item);

                        addPayment(item);
                    } else
                        Toast.makeText(requireActivity(), "موجود هست", Toast.LENGTH_SHORT).show();
            }

            if (child instanceof CreateSessionSessionFragment) {
                if (method.equals("axises"))
                    if (!((CreateSessionSessionFragment) child).axisesAdapter.getIds().contains(item.object.getString("id"))) {
                        ((CreateSessionSessionFragment) child).axisesAdapter.addItem(item);

                        addPayment(item);
                    } else
                        Toast.makeText(requireActivity(), "موجود هست", Toast.LENGTH_SHORT).show();
            }

            if (child instanceof EditCenterDetailFragment) {
                if (method.equals("phones"))
                    if (!((EditCenterDetailFragment) child).phonesAdapter.getIds().contains(item.object.getString("id")))
                        ((EditCenterDetailFragment) child).phonesAdapter.addItem(item);
                    else
                        Toast.makeText(requireActivity(), "موجود هست", Toast.LENGTH_SHORT).show();
            }

            if (child instanceof EditSessionSessionFragment) {
                if (method.equals("axises"))
                    if (!((EditSessionSessionFragment) child).axisesAdapter.getIds().contains(item.object.getString("id"))) {
                        ((EditSessionSessionFragment) child).axisesAdapter.addItem(item);

                        addPayment(item);
                    } else
                        Toast.makeText(requireActivity(), "موجود هست", Toast.LENGTH_SHORT).show();
            }

            calculateCount();

            ((MainActivity) requireActivity()).controlEditText.clear(requireActivity(), ((MainActivity) requireActivity()).controlEditText.input());
            binding.inputEditText.getText().clear();
        } catch (JSONException e) {
            e.printStackTrace();
        }
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