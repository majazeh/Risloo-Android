package com.majazeh.risloo.views.fragments.main.index;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.managers.FileManager;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.views.adapters.recycler.main.Index.IndexDownloadAdapter;
import com.majazeh.risloo.databinding.FragmentDownloadsBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class FragmentDownloads extends Fragment {

    // Binding
    private FragmentDownloadsBinding binding;

    // Adapters
    private IndexDownloadAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentDownloadsBinding.inflate(inflater, viewGroup, false);

        initializer();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        adapter = new IndexDownloadAdapter(requireActivity());

        binding.headerIncludeLayout.titleTextView.setText(getResources().getString(R.string.DownloadsFragmentTitle));

        InitManager.rvVerticalFixedUnnested(requireActivity(), binding.indexSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
    }

    private void setData() {
        File[] files = FileManager.listFilesExternalStoragePublicPath(Environment.DIRECTORY_DOWNLOADS, "Risloo");

        if (files != null) {
            ArrayList<File> items = new ArrayList<>(Arrays.asList(files));

            adapter.setItems(items);
            binding.indexSingleLayout.recyclerView.setAdapter(adapter);

            binding.indexSingleLayout.emptyView.setVisibility(View.GONE);
        } else if (adapter.getItemCount() == 0) {
            binding.indexSingleLayout.recyclerView.setAdapter(null);

            binding.indexSingleLayout.emptyView.setVisibility(View.VISIBLE);
            binding.indexSingleLayout.emptyView.setText(getResources().getString(R.string.DownloadsFragmentEmpty));
        }

        binding.headerIncludeLayout.countTextView.setText(StringManager.bracing(adapter.getItemCount()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}