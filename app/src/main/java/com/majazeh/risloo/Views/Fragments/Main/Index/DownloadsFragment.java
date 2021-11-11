package com.majazeh.risloo.Views.Fragments.Main.Index;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.databinding.FragmentDownloadsBinding;

public class DownloadsFragment extends Fragment {

    // Binding
    private FragmentDownloadsBinding binding;

    // Adapters
//    private DownloadAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentDownloadsBinding.inflate(inflater, viewGroup, false);

        initializer();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
//        adapter = new DownloadAdapter(requireActivity());

        binding.headerIncludeLayout.titleTextView.setText(getResources().getString(R.string.DownloadsFragmentTitle));

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.indexSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
    }

    private void setData() {
        // TODO : Place Code When Needed
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}