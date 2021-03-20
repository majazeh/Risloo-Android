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
import com.majazeh.risloo.Views.Adapters.Recycler.PsychologistsAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.ReferencesAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.Samples2Adapter;
import com.majazeh.risloo.Views.Adapters.Recycler.Sessions2Adapter;
import com.majazeh.risloo.databinding.FragmentCaseBinding;

public class CaseFragment extends Fragment {

    // Binding
    private FragmentCaseBinding binding;

    // Adapters
    private PsychologistsAdapter psychologistsAdapter;
    private ReferencesAdapter referencesAdapter;
    private Sessions2Adapter sessions2Adapter;
    private Samples2Adapter samples2Adapter;

    // Objects
    private RecyclerView.ItemDecoration itemDecoration, itemDecoration2;
    private LinearLayoutManager psychologistsLayoutManager, referencesLayoutManager, sessionsLayoutManager, samplesLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentCaseBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        ((MainActivity) requireActivity()).handler.postDelayed(() -> {
            binding.psychologistsShimmerLayout.getRoot().setVisibility(View.GONE);
            binding.psychologistsSingleLayout.getRoot().setVisibility(View.VISIBLE);
            binding.referencesShimmerLayout.getRoot().setVisibility(View.GONE);
            binding.referencesSingleLayout.getRoot().setVisibility(View.VISIBLE);
            binding.sessionsShimmerLayout.getRoot().setVisibility(View.GONE);
            binding.sessionsHeaderLayout.getRoot().setVisibility(View.VISIBLE);
            binding.sessionsSingleLayout.getRoot().setVisibility(View.VISIBLE);
            binding.samplesShimmerLayout.getRoot().setVisibility(View.GONE);
            binding.samplesHeaderLayout.getRoot().setVisibility(View.VISIBLE);
            binding.samplesSingleLayout.getRoot().setVisibility(View.VISIBLE);
        }, 2000);

        return binding.getRoot();
    }

    private void initializer() {
        psychologistsAdapter = new PsychologistsAdapter(requireActivity());
        referencesAdapter = new ReferencesAdapter(requireActivity());
        sessions2Adapter = new Sessions2Adapter(requireActivity());
        samples2Adapter = new Samples2Adapter(requireActivity());

        itemDecoration = new ItemDecorateRecyclerView("verticalLayout", (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._4sdp), (int) getResources().getDimension(R.dimen._12sdp));
        itemDecoration2 = new ItemDecorateRecyclerView("verticalLayout", 0, 0, 0, 0);
        psychologistsLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        referencesLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        sessionsLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        samplesLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);

        binding.psychologistsHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.CaseFragmentPsychologistsHeader));
        binding.referencesHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.CaseFragmentReferencesHeader));
        binding.sessionsHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.CaseFragmentSessionsHeader));
        binding.samplesHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.CaseFragmentSamplesHeader));

        InitManager.imgResTint(requireActivity(), binding.referencesAddImageView.getRoot(), R.drawable.ic_plus_light, R.color.Green700);
        InitManager.imgResTint(requireActivity(), binding.sessionsAddImageView.getRoot(), R.drawable.ic_plus_light, R.color.Green700);
        InitManager.imgResTint(requireActivity(), binding.samplesAddImageView.getRoot(), R.drawable.ic_plus_light, R.color.Green700);

        InitManager.recyclerView(binding.psychologistsSingleLayout.recyclerView, itemDecoration, psychologistsLayoutManager);
        InitManager.recyclerView(binding.referencesSingleLayout.recyclerView, itemDecoration, referencesLayoutManager);
        InitManager.recyclerView(binding.sessionsSingleLayout.recyclerView, itemDecoration2, sessionsLayoutManager);
        InitManager.recyclerView(binding.samplesSingleLayout.recyclerView, itemDecoration2, samplesLayoutManager);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.referencesAddImageView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_green700_ripple_green300);
            binding.sessionsAddImageView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_green700_ripple_green300);
            binding.samplesAddImageView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_green700_ripple_green300);
        } else {
            binding.referencesAddImageView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_green700);
            binding.sessionsAddImageView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_green700);
            binding.samplesAddImageView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_green700);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.referencesAddImageView.getRoot().setOnClickListener(v -> {
            binding.referencesAddImageView.getRoot().setClickable(false);

            // TODO: Place Code Here
        });

        binding.sessionsAddImageView.getRoot().setOnClickListener(v -> {
            binding.sessionsAddImageView.getRoot().setClickable(false);

            ((MainActivity) requireActivity()).navigator(R.id.createSessionFragment);
        });

        binding.samplesAddImageView.getRoot().setOnClickListener(v -> {
            binding.samplesAddImageView.getRoot().setClickable(false);

            ((MainActivity) requireActivity()).navigator(R.id.createSampleFragment);
        });
    }

    private void setData() {
        binding.serialTextView.setText("RS966666DK");
        binding.descriptionTextView.setText("ﻟﻮرم اﯾﭙﺴﻮم متن ﺳﺎﺧﺘﮕﯽ ﺑﺎ ﺗﻮﻟﯿﺪ ﺳﺎدﮔﯽ ﻧﺎﻣﻔﻬﻮم از ﺻﻨﻌﺖ ﭼﺎپ، و ﺑﺎ اﺳﺘﻔﺎده از ﻃﺮاﺣﺎن ﮔﺮاﻓﯿﮏ اﺳﺖ، ﭼﺎﭘﮕﺮﻫﺎ و ﻣﺘﻮن ﺑﻠﮑﻪ روزنامه و مجله در ستون و سطر آنچنان که لازم است، و برای شرایط فعلی تکنولوژی مورد نیاز، و کاربرهای متنوع با هدف بهبود ابزراهای کاربردی می\u200Cباشد، کتابهای زیادی در شصت و سه درصد گذشته حال و آینده، شناخت فراوان جامعه و متخصصان را می\u200Cطلبد.");
        binding.dateTextView.setText("شنبه 11 بهمن 99 ساعت 16:00");

//        psychologistsAdapter.setPsychology(null);
//        referencesAdapter.setReference(null);
//        sessions2Adapter.setSession(null);
//        samples2Adapter.setSample(null);
        binding.psychologistsSingleLayout.recyclerView.setAdapter(psychologistsAdapter);
        binding.referencesSingleLayout.recyclerView.setAdapter(referencesAdapter);
        binding.sessionsSingleLayout.recyclerView.setAdapter(sessions2Adapter);
        binding.samplesSingleLayout.recyclerView.setAdapter(samples2Adapter);

        String dataSize = "15";
        binding.psychologistsHeaderIncludeLayout.countTextView.setText("(" + dataSize + ")");
        binding.referencesHeaderIncludeLayout.countTextView.setText("(" + dataSize + ")");
        binding.sessionsHeaderIncludeLayout.countTextView.setText("(" + dataSize + ")");
        binding.samplesHeaderIncludeLayout.countTextView.setText("(" + dataSize + ")");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}