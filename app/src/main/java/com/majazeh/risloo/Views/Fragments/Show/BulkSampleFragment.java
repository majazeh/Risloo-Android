package com.majazeh.risloo.Views.Fragments.Show;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
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
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Widgets.ItemDecorateRecyclerView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.ReferencesAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.Samples4Adapter;
import com.majazeh.risloo.Views.Adapters.Recycler.Scales2Adapter;
import com.majazeh.risloo.databinding.FragmentBulkSampleBinding;
import com.squareup.picasso.Picasso;

public class BulkSampleFragment extends Fragment {

    // Binding
    private FragmentBulkSampleBinding binding;

    // Adapters
    private ReferencesAdapter referencesAdapter;
    private Scales2Adapter scales2Adapter;
    private Samples4Adapter samples4Adapter;

    // Objects
    private RecyclerView.ItemDecoration itemDecoration, itemDecoration2;
    private LinearLayoutManager referencesLayoutManager, scalesLayoutManager, samplesLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentBulkSampleBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        referencesAdapter = new ReferencesAdapter(requireActivity());
        scales2Adapter = new Scales2Adapter(requireActivity());
        samples4Adapter = new Samples4Adapter(requireActivity());

        itemDecoration = new ItemDecorateRecyclerView("verticalLayout", (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._4sdp), (int) getResources().getDimension(R.dimen._12sdp));
        itemDecoration2 = new ItemDecorateRecyclerView("verticalLayout", 0, 0, 0, 0);

        referencesLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        scalesLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        samplesLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);

        InitManager.imgResTint(requireActivity(), binding.editTextView.getRoot(), R.drawable.ic_edit_light, R.color.Gray500);
        InitManager.imgResTint(requireActivity(), binding.linkTextView.buttonImageView, R.drawable.ic_copy_light, R.color.Gray500);

        InitManager.txtTextColor(binding.linkTextView.buttonTextView, getResources().getString(R.string.BulkSampleFragmentLink), getResources().getColor(R.color.Gray500));

        binding.referencesHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.BulkSampleFragmentReferencesHeader));
        binding.scalesHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.Scales2AdapterHeader));
        binding.samplesHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.Samples4AdapterHeader));

        InitManager.recyclerView(binding.referencesSingleLayout.recyclerView, itemDecoration, referencesLayoutManager);
        InitManager.recyclerView(binding.scalesSingleLayout.recyclerView, itemDecoration2, scalesLayoutManager);
        InitManager.recyclerView(binding.samplesSingleLayout.recyclerView, itemDecoration2, samplesLayoutManager);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.editTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_gray500_ripple_gray300);
        } else {
            binding.editTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_gray500);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        ClickManager.onDelayedClickListener(() -> {
            if (!((MainActivity) requireActivity()).singleton.getAvatar().equals("")) {
                IntentManager.display(requireActivity(), "", "", ((MainActivity) requireActivity()).singleton.getAvatar());
            }
        }).widget(binding.avatarsIncludeLayout.avatarCircleImageView);

        ClickManager.onDelayedClickListener(() -> {
            if (!((MainActivity) requireActivity()).singleton.getAvatar().equals("")) {
                IntentManager.display(requireActivity(), "", "", ((MainActivity) requireActivity()).singleton.getAvatar());
            }
        }).widget(binding.avatarsIncludeLayout.avatarSubCircleImageView);

        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(binding.editTextView.getRoot());

        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(binding.linkTextView.buttonImageView);

        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(binding.linkTextView.buttonTextView);
    }

    private void setData() {
        binding.serialTextView.setText("BS966666W");
        binding.centerTextView.setText("کلینیک شخصی");
        binding.psychologyTextView.setText("محمد رضا سالاری فر");
        binding.caseTextView.setText("بدون پرونده");
        binding.statusTextView.setText("باز");

        if (((MainActivity) requireActivity()).singleton.getAvatar().equals("")) {
            binding.avatarsIncludeLayout.charTextView.setVisibility(View.VISIBLE);
            binding.avatarsIncludeLayout.charSubTextView.setVisibility(View.VISIBLE);
            binding.avatarsIncludeLayout.charTextView.setText(StringManager.firstChars(binding.centerTextView.getText().toString()));
            binding.avatarsIncludeLayout.charSubTextView.setText(StringManager.firstChars(binding.psychologyTextView.getText().toString()));

            Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(binding.avatarsIncludeLayout.avatarCircleImageView);
            Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(binding.avatarsIncludeLayout.avatarSubCircleImageView);
        } else {
            binding.avatarsIncludeLayout.charTextView.setVisibility(View.GONE);
            binding.avatarsIncludeLayout.charSubTextView.setVisibility(View.GONE);

            Picasso.get().load(((MainActivity) requireActivity()).singleton.getAvatar()).placeholder(R.color.Gray50).into(binding.avatarsIncludeLayout.avatarCircleImageView);
            Picasso.get().load(((MainActivity) requireActivity()).singleton.getAvatar()).placeholder(R.color.Gray50).into(binding.avatarsIncludeLayout.avatarSubCircleImageView);
        }

//        referencesAdapter.setReference(null);
//        scales2Adapter.setScale(null);
//        samples4Adapter.setSample(null);
        binding.referencesSingleLayout.recyclerView.setAdapter(referencesAdapter);
        binding.scalesSingleLayout.recyclerView.setAdapter(scales2Adapter);
        binding.samplesSingleLayout.recyclerView.setAdapter(samples4Adapter);

        String dataSize = "5";
        binding.referencesHeaderIncludeLayout.countTextView.setText("(" + dataSize + ")");
        binding.scalesHeaderIncludeLayout.countTextView.setText("(" + dataSize + ")");
        binding.samplesHeaderIncludeLayout.countTextView.setText("(" + dataSize + ")");

        new Handler().postDelayed(() -> {
            binding.referencesShimmerLayout.getRoot().setVisibility(View.GONE);
            binding.referencesSingleLayout.getRoot().setVisibility(View.VISIBLE);

            binding.scalesShimmerLayout.getRoot().setVisibility(View.GONE);
            binding.scalesHeaderLayout.getRoot().setVisibility(View.VISIBLE);
            binding.scalesSingleLayout.getRoot().setVisibility(View.VISIBLE);

            binding.samplesShimmerLayout.getRoot().setVisibility(View.GONE);
            binding.samplesHeaderLayout.getRoot().setVisibility(View.VISIBLE);
            binding.samplesSingleLayout.getRoot().setVisibility(View.VISIBLE);
        }, 2000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}