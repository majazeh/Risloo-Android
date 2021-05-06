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
import com.majazeh.risloo.databinding.FragmentRoomUserBinding;
import com.squareup.picasso.Picasso;

public class RoomUserFragment extends Fragment {

    // Binding
    private FragmentRoomUserBinding binding;

    // Adapters
    private RoomsAdapter roomsAdapter;
    private Cases3Adapter cases3Adapter;
    private Samples3Adapter samples3Adapter;

    // Objects
    private RecyclerView.ItemDecoration itemDecoration, itemDecoration2;
    private LinearLayoutManager roomsLayoutManager, casesLayoutManager, samplesLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentRoomUserBinding.inflate(inflater, viewGroup, false);

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
        casesLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        samplesLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);

        binding.roomsHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.ReferenceFragmentRoomsHeader));
        binding.casesHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.ReferenceFragmentCasesHeader));
        binding.samplesHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.ReferenceFragmentSamplesHeader));

        InitManager.recyclerView(binding.roomsSingleLayout.recyclerView, itemDecoration, roomsLayoutManager);
        InitManager.recyclerView(binding.casesSingleLayout.recyclerView, itemDecoration2, casesLayoutManager);
        InitManager.recyclerView(binding.samplesSingleLayout.recyclerView, itemDecoration2, samplesLayoutManager);
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
            binding.mobileTextView.setVisibility(View.GONE);
            binding.mobileImageView.setVisibility(View.GONE);
        } else {
            binding.mobileTextView.setText(((MainActivity) requireActivity()).singleton.getMobile());
        }

        if (((MainActivity) requireActivity()).singleton.getAvatar().equals("")) {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
            binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(binding.nameTextView.getText().toString()));

            Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
        } else {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);

            Picasso.get().load(((MainActivity) requireActivity()).singleton.getAvatar()).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
        }

//        roomsAdapter.setRoom(null);
//        cases3Adapter.setCase(null);
//        samples3Adapter.setSample(null);
        binding.roomsSingleLayout.recyclerView.setAdapter(roomsAdapter);
        binding.casesSingleLayout.recyclerView.setAdapter(cases3Adapter);
        binding.samplesSingleLayout.recyclerView.setAdapter(samples3Adapter);

        String dataSize = "5";
        binding.roomsHeaderIncludeLayout.countTextView.setText("(" + dataSize + ")");
        binding.casesHeaderIncludeLayout.countTextView.setText("(" + dataSize + ")");
        binding.samplesHeaderIncludeLayout.countTextView.setText("(" + dataSize + ")");

        new Handler().postDelayed(() -> {
            binding.roomsShimmerLayout.getRoot().setVisibility(View.GONE);
            binding.roomsSingleLayout.getRoot().setVisibility(View.VISIBLE);

            binding.casesShimmerLayout.getRoot().setVisibility(View.GONE);
            binding.casesHeaderLayout.getRoot().setVisibility(View.VISIBLE);
            binding.casesSingleLayout.getRoot().setVisibility(View.VISIBLE);

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