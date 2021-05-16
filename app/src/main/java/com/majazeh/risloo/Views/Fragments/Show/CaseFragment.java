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
    private LinearLayoutManager psychologistsLayoutManager, referencesLayoutManager, sessions2LayoutManager, samples2LayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentCaseBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

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
        sessions2LayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        samples2LayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);

        binding.psychologistsHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.PsychologistsAdapterHeader));
        binding.referencesHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.ReferencesAdapterHeader));
        binding.sessionsHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.Sessions2AdapterHeader));
        binding.samplesHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.Samples2AdapterHeader));

        InitManager.imgResTint(requireActivity(), binding.referencesAddImageView.getRoot(), R.drawable.ic_plus_light, R.color.Green700);
        InitManager.imgResTint(requireActivity(), binding.sessionsAddImageView.getRoot(), R.drawable.ic_plus_light, R.color.Green700);
        InitManager.imgResTint(requireActivity(), binding.samplesAddImageView.getRoot(), R.drawable.ic_plus_light, R.color.Green700);

        InitManager.recyclerView(binding.psychologistsSingleLayout.recyclerView, itemDecoration, psychologistsLayoutManager);
        InitManager.recyclerView(binding.referencesSingleLayout.recyclerView, itemDecoration, referencesLayoutManager);
        InitManager.recyclerView(binding.sessionsSingleLayout.recyclerView, itemDecoration2, sessions2LayoutManager);
        InitManager.recyclerView(binding.samplesSingleLayout.recyclerView, itemDecoration2, samples2LayoutManager);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.referencesAddImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_green700_ripple_green300);
            binding.sessionsAddImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_green700_ripple_green300);
            binding.samplesAddImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_green700_ripple_green300);
        } else {
            binding.referencesAddImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_green700);
            binding.sessionsAddImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_green700);
            binding.samplesAddImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_green700);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        ClickManager.onClickListener(() -> ((MainActivity) requireActivity()).navigator(R.id.createCaseUserFragment)).widget(binding.referencesAddImageView.getRoot());

        ClickManager.onClickListener(() -> ((MainActivity) requireActivity()).navigator(R.id.createSessionFragment)).widget(binding.sessionsAddImageView.getRoot());

        ClickManager.onClickListener(() -> ((MainActivity) requireActivity()).navigator(R.id.createSampleFragment)).widget(binding.samplesAddImageView.getRoot());
    }

    private void setData() {
        // Todo : Place Code Here

        binding.serialTextView.setText("RS966666DK");
        binding.descriptionTextView.setText("ﻟﻮرم اﯾﭙﺴﻮم متن ﺳﺎﺧﺘﮕﯽ ﺑﺎ ﺗﻮﻟﯿﺪ ﺳﺎدﮔﯽ ﻧﺎﻣﻔﻬﻮم از ﺻﻨﻌﺖ ﭼﺎپ، و ﺑﺎ اﺳﺘﻔﺎده از ﻃﺮاﺣﺎن ﮔﺮاﻓﯿﮏ اﺳﺖ، ﭼﺎﭘﮕﺮﻫﺎ و ﻣﺘﻮن ﺑﻠﮑﻪ روزنامه و مجله در ستون و سطر آنچنان که لازم است، و برای شرایط فعلی تکنولوژی مورد نیاز، و کاربرهای متنوع با هدف بهبود ابزراهای کاربردی می\u200Cباشد، کتابهای زیادی در شصت و سه درصد گذشته حال و آینده، شناخت فراوان جامعه و متخصصان را می\u200Cطلبد.");
        binding.dateTextView.setText("شنبه 11 بهمن 99 ساعت 16:00");

//        psychologistsAdapter.setPsychologists(null);
//        referencesAdapter.setReferences(null);
//        sessions2Adapter.setSessions(null);
//        samples2Adapter.setSamples(null);
        binding.psychologistsSingleLayout.recyclerView.setAdapter(psychologistsAdapter);
        binding.referencesSingleLayout.recyclerView.setAdapter(referencesAdapter);
        binding.sessionsSingleLayout.recyclerView.setAdapter(sessions2Adapter);
        binding.samplesSingleLayout.recyclerView.setAdapter(samples2Adapter);

        binding.psychologistsHeaderIncludeLayout.countTextView.setText("(" + psychologistsAdapter.getItemCount() + ")");
        binding.referencesHeaderIncludeLayout.countTextView.setText("(" + referencesAdapter.getItemCount() + ")");
        binding.sessionsHeaderIncludeLayout.countTextView.setText("(" + sessions2Adapter.getItemCount() + ")");
        binding.samplesHeaderIncludeLayout.countTextView.setText("(" + samples2Adapter.getItemCount() + ")");

        new Handler().postDelayed(() -> {
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
        }, 1000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}