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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.ParamsManager;
import com.majazeh.risloo.Utils.Widgets.ItemDecorateRecyclerView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Create.CreateCenterFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateScheduleFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateSessionFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSchedulePaymentFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionPaymentFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditCenterDetailFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditCenterFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionPaymentFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionSessionFragment;
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
        itemDecoration = new ItemDecorateRecyclerView("verticalLayout", (int) getResources().getDimension(R.dimen._16sdp), 0, (int) getResources().getDimension(R.dimen._2sdp), 0);

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
                    TypeModel item;

                    if (method.equals("axises"))
                        item = new TypeModel(new JSONObject().put("id", "").put("title", value));
                    else
                        item = new TypeModel(new JSONObject().put("id", value).put("title", value));

                    Fragment fragment = ((MainActivity) requireActivity()).navHostFragment.getChildFragmentManager().getFragments().get(0);
                    if (fragment != null) {

                        if (fragment instanceof CreateCenterFragment) {
                            if (method.equals("phones"))
                                if (!((CreateCenterFragment) fragment).phonesAdapter.getIds().contains(value))
                                    ((CreateCenterFragment) fragment).phonesAdapter.addItem(item);
                                else
                                    Toast.makeText(requireActivity(), "exception", Toast.LENGTH_SHORT).show();

                        } else if (fragment instanceof CreateScheduleFragment) {
                            Fragment childFragment = ((CreateScheduleFragment) fragment).adapter.hashMap.get(((CreateScheduleFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
                            if (childFragment != null) {
                                if (method.equals("axises"))
                                    if (childFragment instanceof CreateScheduleSessionFragment)
                                        if (!((CreateScheduleSessionFragment) childFragment).axisesAdapter.getIds().contains(value)) {
                                            ((CreateScheduleSessionFragment) childFragment).axisesAdapter.addItem(item);

                                            CreateSchedulePaymentFragment paymentFragment = (CreateSchedulePaymentFragment) ((CreateScheduleFragment) fragment).adapter.hashMap.get(3);
                                            if (paymentFragment != null)
                                                paymentFragment.axisAdapter.addItem(item);
                                        } else
                                            Toast.makeText(requireActivity(), "exception", Toast.LENGTH_SHORT).show();
                            }

                        } else if (fragment instanceof CreateSessionFragment) {
                            Fragment childFragment = ((CreateSessionFragment) fragment).adapter.hashMap.get(((CreateSessionFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
                            if (childFragment != null) {
                                if (method.equals("axises"))
                                    if (childFragment instanceof CreateSessionSessionFragment)
                                        if (!((CreateSessionSessionFragment) childFragment).axisesAdapter.getIds().contains(value)) {
                                            ((CreateSessionSessionFragment) childFragment).axisesAdapter.addItem(item);

                                            CreateSessionPaymentFragment paymentFragment = (CreateSessionPaymentFragment) ((CreateSessionFragment) fragment).adapter.hashMap.get(2);
                                            if (paymentFragment != null)
                                                paymentFragment.axisAdapter.addItem(item);
                                        } else
                                            Toast.makeText(requireActivity(), "exception", Toast.LENGTH_SHORT).show();
                            }

                        } else if (fragment instanceof EditCenterFragment) {
                            Fragment childFragment = ((EditCenterFragment) fragment).adapter.hashMap.get(((EditCenterFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
                            if (childFragment != null) {
                                if (method.equals("phones"))
                                    if (childFragment instanceof EditCenterDetailFragment)
                                        if (!((EditCenterDetailFragment) childFragment).phonesAdapter.getIds().contains(value))
                                            ((EditCenterDetailFragment) childFragment).phonesAdapter.addItem(item);
                                        else
                                            Toast.makeText(requireActivity(), "exception", Toast.LENGTH_SHORT).show();
                            }

                        } else if (fragment instanceof EditSessionFragment) {
                            Fragment childFragment = ((EditSessionFragment) fragment).adapter.hashMap.get(((EditSessionFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
                            if (childFragment != null) {
                                if (method.equals("axises"))
                                    if (childFragment instanceof EditSessionSessionFragment)
                                        if (!((EditSessionSessionFragment) childFragment).axisesAdapter.getIds().contains(value)) {
                                            ((EditSessionSessionFragment) childFragment).axisesAdapter.addItem(item);

                                            EditSessionPaymentFragment paymentFragment = (EditSessionPaymentFragment) ((EditSessionFragment) fragment).adapter.hashMap.get(2);
                                            if (paymentFragment != null)
                                                paymentFragment.axisAdapter.addItem(item);
                                        } else
                                            Toast.makeText(requireActivity(), "exception", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    calculateCount();

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
        Fragment fragment = ((MainActivity) requireActivity()).navHostFragment.getChildFragmentManager().getFragments().get(0);
        if (fragment != null) {

            if (fragment instanceof CreateCenterFragment) {
                if (method.equals("phones"))
                    binding.listRecyclerView.setAdapter(((CreateCenterFragment) fragment).phonesAdapter);

            } else if (fragment instanceof CreateScheduleFragment) {
                Fragment childFragment = ((CreateScheduleFragment) fragment).adapter.hashMap.get(((CreateScheduleFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
                if (childFragment != null) {
                    if (method.equals("axises"))
                        if (childFragment instanceof CreateScheduleSessionFragment)
                            binding.listRecyclerView.setAdapter(((CreateScheduleSessionFragment) childFragment).axisesAdapter);
                }

            } else if (fragment instanceof CreateSessionFragment) {
                Fragment childFragment = ((CreateSessionFragment) fragment).adapter.hashMap.get(((CreateSessionFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
                if (childFragment != null) {
                    if (method.equals("axises"))
                        if (childFragment instanceof CreateSessionSessionFragment)
                            binding.listRecyclerView.setAdapter(((CreateSessionSessionFragment) childFragment).axisesAdapter);
                }

            } else if (fragment instanceof EditCenterFragment) {
                Fragment childFragment = ((EditCenterFragment) fragment).adapter.hashMap.get(((EditCenterFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
                if (childFragment != null) {
                    if (method.equals("phones"))
                        if (childFragment instanceof EditCenterDetailFragment)
                            binding.listRecyclerView.setAdapter(((EditCenterDetailFragment) childFragment).phonesAdapter);
                }

            } else if (fragment instanceof EditSessionFragment) {
                Fragment childFragment = ((EditSessionFragment) fragment).adapter.hashMap.get(((EditSessionFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
                if (childFragment != null) {
                    if (method.equals("axises"))
                        if (childFragment instanceof EditSessionSessionFragment)
                            binding.listRecyclerView.setAdapter(((EditSessionSessionFragment) childFragment).axisesAdapter);
                }
            }
        }
        calculateCount();
    }

    private void clearEditText() {
        binding.inputEditText.getText().clear();
    }

    public void setData(String method) {
        this.method = method;
    }

    public void calculateCount() {
        if (Objects.requireNonNull(binding.listRecyclerView.getAdapter()).getItemCount() != 0) {
            String count = "(" + binding.listRecyclerView.getAdapter().getItemCount() + ")";

            binding.countTextView.setVisibility(View.VISIBLE);
            binding.countTextView.setText(count);
        } else {
            binding.countTextView.setVisibility(View.GONE);
            binding.countTextView.setText("");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}