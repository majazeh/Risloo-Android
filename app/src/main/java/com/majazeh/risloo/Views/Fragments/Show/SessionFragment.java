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
import com.majazeh.risloo.Views.Adapters.Recycler.PracticesAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.PsychologistsAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.ReferencesAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.Samples2Adapter;
import com.majazeh.risloo.databinding.FragmentSessionBinding;

public class SessionFragment extends Fragment {

    // Binding
    private FragmentSessionBinding binding;

    // Adapters
    private PsychologistsAdapter psychologistsAdapter;
    private ReferencesAdapter referencesAdapter;
    private PracticesAdapter practicesAdapter;
    private Samples2Adapter samples2Adapter;

    // Objects
    private RecyclerView.ItemDecoration itemDecoration, itemDecoration2;
    private LinearLayoutManager psychologistsLayoutManager, referencesLayoutManager, practicesLayoutManager, samplesLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentSessionBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        psychologistsAdapter = new PsychologistsAdapter(requireActivity());
        referencesAdapter = new ReferencesAdapter(requireActivity());
        practicesAdapter = new PracticesAdapter(requireActivity());
        samples2Adapter = new Samples2Adapter(requireActivity());

        itemDecoration = new ItemDecorateRecyclerView("verticalLayout", (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._4sdp), (int) getResources().getDimension(R.dimen._12sdp));
        itemDecoration2 = new ItemDecorateRecyclerView("verticalLayout", 0, 0, 0, 0);

        psychologistsLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        referencesLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        practicesLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        samplesLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);

        InitManager.txtTextColor(binding.editTextView.getRoot(), getResources().getString(R.string.SessionFragmentEdit), getResources().getColor(R.color.Gray500));
        InitManager.txtTextColor(binding.reportActionTextView.getRoot(), getResources().getString(R.string.SessionFragmentReportAdd), getResources().getColor(R.color.Green700));

        binding.reportHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.SessionFragmentReportHeader));
        binding.psychologistsHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.SessionFragmentPsychologistsHeader));
        binding.referencesHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.SessionFragmentReferencesHeader));
        binding.practicesHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.SessionFragmentPracticesHeader));
        binding.samplesHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.SessionFragmentSamplesHeader));

        InitManager.imgResTint(requireActivity(), binding.practicesAddImageView.getRoot(), R.drawable.ic_plus_light, R.color.Green700);
        InitManager.imgResTint(requireActivity(), binding.samplesAddImageView.getRoot(), R.drawable.ic_plus_light, R.color.Green700);

        InitManager.recyclerView(binding.psychologistsSingleLayout.recyclerView, itemDecoration, psychologistsLayoutManager);
        InitManager.recyclerView(binding.referencesSingleLayout.recyclerView, itemDecoration, referencesLayoutManager);
        InitManager.recyclerView(binding.practicesSingleLayout.recyclerView, itemDecoration2, practicesLayoutManager);
        InitManager.recyclerView(binding.samplesSingleLayout.recyclerView, itemDecoration2, samplesLayoutManager);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.editTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_gray500_ripple_gray300);
            binding.reportActionTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_green700_ripple_green300);

            binding.practicesAddImageView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_green700_ripple_green300);
            binding.samplesAddImageView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_green700_ripple_green300);
        } else {
            binding.editTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_gray500);
            binding.reportActionTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_green700);

            binding.practicesAddImageView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_green700);
            binding.samplesAddImageView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_green700);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.editTextView.getRoot().setOnClickListener(v -> {
            binding.editTextView.getRoot().setClickable(false);
            ((MainActivity) requireActivity()).handler.postDelayed(() -> binding.editTextView.getRoot().setClickable(true), 300);

            ((MainActivity) requireActivity()).navigator(R.id.editSessionFragment);
        });

        binding.reportActionTextView.getRoot().setOnClickListener(v -> {
            binding.reportActionTextView.getRoot().setClickable(false);
            ((MainActivity) requireActivity()).handler.postDelayed(() -> binding.reportActionTextView.getRoot().setClickable(true), 300);

            ((MainActivity) requireActivity()).navigator(R.id.createReportFragment);
        });

        binding.practicesAddImageView.getRoot().setOnClickListener(v -> {
            binding.practicesAddImageView.getRoot().setClickable(false);
            ((MainActivity) requireActivity()).handler.postDelayed(() -> binding.practicesAddImageView.getRoot().setClickable(true), 300);

            ((MainActivity) requireActivity()).navigator(R.id.createPracticeFragment);
        });

        binding.samplesAddImageView.getRoot().setOnClickListener(v -> {
            binding.samplesAddImageView.getRoot().setClickable(false);
            ((MainActivity) requireActivity()).handler.postDelayed(() -> binding.samplesAddImageView.getRoot().setClickable(true), 300);

            ((MainActivity) requireActivity()).navigator(R.id.createSampleFragment);
        });
    }

    private void setData() {
        binding.serialTextView.setText("SE966666D");
        binding.dateTextView.setText("شنبه 11 بهمن 99 ساعت 16:00");
        binding.durationTextView.setText("60 دقیقه");
        binding.statusTextView.setText("در انتظار تشکیل جلسه");

//        psychologistsAdapter.setPsychology(null);
//        referencesAdapter.setReference(null);
//        practicesAdapter.setPractice(null);
//        samples2Adapter.setSample(null);
        binding.psychologistsSingleLayout.recyclerView.setAdapter(psychologistsAdapter);
        binding.referencesSingleLayout.recyclerView.setAdapter(referencesAdapter);
        binding.practicesSingleLayout.recyclerView.setAdapter(practicesAdapter);
        binding.samplesSingleLayout.recyclerView.setAdapter(samples2Adapter);

        String dataSize = "5";
        binding.psychologistsHeaderIncludeLayout.countTextView.setText("(" + dataSize + ")");
        binding.referencesHeaderIncludeLayout.countTextView.setText("(" + dataSize + ")");
        binding.practicesHeaderIncludeLayout.countTextView.setText("(" + dataSize + ")");
        binding.samplesHeaderIncludeLayout.countTextView.setText("(" + dataSize + ")");

        ((MainActivity) requireActivity()).handler.postDelayed(() -> {
            binding.psychologistsShimmerLayout.getRoot().setVisibility(View.GONE);
            binding.psychologistsSingleLayout.getRoot().setVisibility(View.VISIBLE);

            binding.referencesShimmerLayout.getRoot().setVisibility(View.GONE);
            binding.referencesSingleLayout.getRoot().setVisibility(View.VISIBLE);

            binding.practicesShimmerLayout.getRoot().setVisibility(View.GONE);
            binding.practicesHeaderLayout.getRoot().setVisibility(View.VISIBLE);
            binding.practicesSingleLayout.getRoot().setVisibility(View.VISIBLE);

            binding.samplesShimmerLayout.getRoot().setVisibility(View.GONE);
            binding.samplesHeaderLayout.getRoot().setVisibility(View.VISIBLE);
            binding.samplesSingleLayout.getRoot().setVisibility(View.VISIBLE);
        }, 2000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        ((MainActivity) requireActivity()).handler.removeCallbacksAndMessages(null);
    }

}