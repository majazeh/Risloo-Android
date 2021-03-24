package com.majazeh.risloo.Views.Fragments.Show;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Widgets.ItemDecorateRecyclerView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.FormsAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.ProfilesAdapter;
import com.majazeh.risloo.databinding.FragmentSampleBinding;

public class SampleFragment extends Fragment {

    // Binding
    private FragmentSampleBinding binding;

    // Adapters
    private ProfilesAdapter profilesAdapter;
    private FormsAdapter formsGeneralAdapter, formsPrerequisiteAdapter, formsAnswerAdapter;

    // Objects
    private RecyclerView.ItemDecoration itemDecoration;
    private LinearLayoutManager profilesLayoutManager, formsGeneralLayoutManager, formsPrerequisiteLayoutManager, formsAnswerLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentSampleBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        ((MainActivity) requireActivity()).handler.postDelayed(() -> {
            binding.profilesShimmerLayout.getRoot().setVisibility(View.GONE);
            binding.profilesSingleLayout.getRoot().setVisibility(View.VISIBLE);
            binding.formsGeneralShimmerLayout.getRoot().setVisibility(View.GONE);
            binding.formsGeneralSingleLayout.getRoot().setVisibility(View.VISIBLE);
            binding.formsPrerequisiteShimmerLayout.getRoot().setVisibility(View.GONE);
            binding.formsPrerequisiteSingleLayout.getRoot().setVisibility(View.VISIBLE);
            binding.formsAnswerShimmerLayout.getRoot().setVisibility(View.GONE);
            binding.formsAnswerSingleLayout.getRoot().setVisibility(View.VISIBLE);
        }, 2000);

        return binding.getRoot();
    }

    private void initializer() {
        profilesAdapter = new ProfilesAdapter(requireActivity());
        formsGeneralAdapter = new FormsAdapter(requireActivity());
        formsPrerequisiteAdapter = new FormsAdapter(requireActivity());
        formsAnswerAdapter = new FormsAdapter(requireActivity());

        itemDecoration = new ItemDecorateRecyclerView("verticalLayout", (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._4sdp), (int) getResources().getDimension(R.dimen._12sdp));
        profilesLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        formsGeneralLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        formsPrerequisiteLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        formsAnswerLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);

        InitManager.txtTextColor(binding.primaryTextView.getRoot(), getResources().getString(R.string.SampleFragmentFill), getResources().getColor(R.color.Gray500));
        InitManager.txtTextColor(binding.secondaryTextView.getRoot(), getResources().getString(R.string.SampleFragmentScore), getResources().getColor(R.color.Gray500));

        binding.profilesHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.SampleFragmentProfileHeader));
        binding.formsHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.SampleFragmentFormHeader));

        InitManager.recyclerView(binding.profilesSingleLayout.recyclerView, itemDecoration, profilesLayoutManager);
        InitManager.recyclerView(binding.formsGeneralSingleLayout.recyclerView, itemDecoration, formsGeneralLayoutManager);
        InitManager.recyclerView(binding.formsPrerequisiteSingleLayout.recyclerView, itemDecoration, formsPrerequisiteLayoutManager);
        InitManager.recyclerView(binding.formsAnswerSingleLayout.recyclerView, itemDecoration, formsAnswerLayoutManager);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.primaryTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_gray500_ripple_gray300);
            binding.secondaryTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_gray500_ripple_gray300);
        } else {
            binding.primaryTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_gray500);
            binding.secondaryTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_gray500);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.primaryTextView.getRoot().setOnClickListener(v -> {
            binding.primaryTextView.getRoot().setClickable(false);
            ((MainActivity) requireActivity()).handler.postDelayed(() -> binding.primaryTextView.getRoot().setClickable(true), 300);

            // TODO : Call Work Method
        });

        binding.secondaryTextView.getRoot().setOnClickListener(v -> {
            binding.secondaryTextView.getRoot().setClickable(false);
            ((MainActivity) requireActivity()).handler.postDelayed(() -> binding.secondaryTextView.getRoot().setClickable(true), 300);

            // TODO : Call Work Method
        });

        binding.formsEditableImageView.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.formsEditableImageView.setTextColor(getResources().getColor(R.color.Gray900));

                formsGeneralAdapter.setEditable(true);
                formsPrerequisiteAdapter.setEditable(true);
                formsAnswerAdapter.setEditable(true);
            } else {
                binding.formsEditableImageView.setTextColor(getResources().getColor(R.color.Gray600));

                formsGeneralAdapter.setEditable(false);
                formsPrerequisiteAdapter.setEditable(false);
                formsAnswerAdapter.setEditable(false);
            }
        });
    }

    private void setData() {
        binding.nameTextView.setText("آزمون ریون");
        binding.referenceTextView.setText("دکتر مسعود جان\u200Cبزرگی");
        binding.statusTextView.setText("باز");

//        profilesAdapter.setProfile(null);
//        formsGeneralAdapter.setForm(null);
//        formsPrerequisiteAdapter.setForm(null);
//        formsAnswerAdapter.setForm(null);
        binding.profilesSingleLayout.recyclerView.setAdapter(profilesAdapter);
        binding.formsGeneralSingleLayout.recyclerView.setAdapter(formsGeneralAdapter);
        binding.formsPrerequisiteSingleLayout.recyclerView.setAdapter(formsPrerequisiteAdapter);
        binding.formsAnswerSingleLayout.recyclerView.setAdapter(formsAnswerAdapter);

        String dataSize = "15";
        binding.profilesHeaderIncludeLayout.countTextView.setText("(" + dataSize + ")");
        binding.formsHeaderIncludeLayout.countTextView.setText("(" + dataSize + ")");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}