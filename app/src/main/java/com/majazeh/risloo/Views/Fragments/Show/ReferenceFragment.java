package com.majazeh.risloo.Views.Fragments.Show;

import android.annotation.SuppressLint;
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
import com.majazeh.risloo.Views.Adapters.Recycler.Cases3Adapter;
import com.majazeh.risloo.Views.Adapters.Recycler.RoomsAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.Samples3Adapter;
import com.majazeh.risloo.databinding.FragmentReferenceBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ReferenceFragment extends Fragment {

    // Binding
    private FragmentReferenceBinding binding;

    // Adapters
    private RoomsAdapter roomsAdapter;
    private Cases3Adapter cases3Adapter;
    private Samples3Adapter samples3Adapter;

    // Objects
    private RecyclerView.ItemDecoration itemDecoration, itemDecoration2;
    private LinearLayoutManager roomsLayoutManager, cases3LayoutManager, samples3LayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentReferenceBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        roomsAdapter = new RoomsAdapter(requireActivity());
        cases3Adapter = new Cases3Adapter(requireActivity());
        samples3Adapter = new Samples3Adapter(requireActivity());

        itemDecoration = new ItemDecorateRecyclerView("verticalLayout", (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._4sdp), (int) getResources().getDimension(R.dimen._12sdp));
        itemDecoration2 = new ItemDecorateRecyclerView("verticalLayout", 0, 0, 0, 0);

        roomsLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        cases3LayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        samples3LayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);

        binding.roomsHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.RoomsAdapterHeader));
        binding.casesHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.Cases3AdapterHeader));
        binding.samplesHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.Samples3AdapterHeader));

        InitManager.recyclerView(binding.roomsSingleLayout.recyclerView, itemDecoration, roomsLayoutManager);
        InitManager.recyclerView(binding.casesSingleLayout.recyclerView, itemDecoration2, cases3LayoutManager);
        InitManager.recyclerView(binding.samplesSingleLayout.recyclerView, itemDecoration2, samples3LayoutManager);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        ClickManager.onDelayedClickListener(() -> {
            if (!((MainActivity) requireActivity()).singleton.getAvatar().equals("")) {
                IntentManager.display(requireActivity(), "", "", ((MainActivity) requireActivity()).singleton.getAvatar());
            }
        }).widget(binding.avatarIncludeLayout.avatarCircleImageView);
    }

    private void setData() {
        HashMap data = new HashMap();
        data.put("id", getArguments().getString("id"));
        data.put("userId", getArguments().getString("userId"));
        HashMap header = new HashMap();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());
        Center.user(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (((MainActivity) requireActivity()).singleton.getName().equals("")) {
                    binding.nameTextView.setText(getResources().getString(R.string.AppDefaultName));
                } else {
                    binding.nameTextView.setText(((MainActivity) requireActivity()).singleton.getName());
                }

                if (((MainActivity) requireActivity()).singleton.getStatus().equals("")) {
                    binding.statusTextView.setVisibility(View.GONE);
                } else {
                    binding.statusTextView.setText(((MainActivity) requireActivity()).singleton.getStatus());
                }

                if (((MainActivity) requireActivity()).singleton.getMobile().equals("")) {
                    binding.mobileGroup.setVisibility(View.GONE);
                } else {
                    binding.mobileGroup.setVisibility(View.VISIBLE);
                    binding.mobileTextView.setText(((MainActivity) requireActivity()).singleton.getMobile());
                }

                if (!((MainActivity) requireActivity()).singleton.getAvatar().equals("")) {
                    binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
                    Picasso.get().load(((MainActivity) requireActivity()).singleton.getAvatar()).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
                } else {
                    binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
                    binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(binding.nameTextView.getText().toString()));

                    Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
                }

//        roomsAdapter.setRooms(null);
//        cases3Adapter.setCases(null);
//        samples3Adapter.setSamples(null);
                binding.roomsSingleLayout.recyclerView.setAdapter(roomsAdapter);
                binding.casesSingleLayout.recyclerView.setAdapter(cases3Adapter);
                binding.samplesSingleLayout.recyclerView.setAdapter(samples3Adapter);

                binding.roomsHeaderIncludeLayout.countTextView.setText("(" + roomsAdapter.getItemCount() + ")");
                binding.casesHeaderIncludeLayout.countTextView.setText("(" + cases3Adapter.getItemCount() + ")");
                binding.samplesHeaderIncludeLayout.countTextView.setText("(" + samples3Adapter.getItemCount() + ")");

                    binding.roomsShimmerLayout.getRoot().setVisibility(View.GONE);
                    binding.roomsSingleLayout.getRoot().setVisibility(View.VISIBLE);

                    binding.casesShimmerLayout.getRoot().setVisibility(View.GONE);
                    binding.casesHeaderLayout.getRoot().setVisibility(View.VISIBLE);
                    binding.casesSingleLayout.getRoot().setVisibility(View.VISIBLE);

                    binding.samplesShimmerLayout.getRoot().setVisibility(View.GONE);
                    binding.samplesHeaderLayout.getRoot().setVisibility(View.VISIBLE);
                    binding.samplesSingleLayout.getRoot().setVisibility(View.VISIBLE);

            }

            @Override
            public void onFailure(String response) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}