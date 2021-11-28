package com.majazeh.risloo.Views.Fragments.Main.Show;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.FileManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Adapters.Recycler.Main.Index.IndexDownloadAdapter;
import com.majazeh.risloo.databinding.FragmentFolderBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class FolderFragment extends Fragment {

    // Binding
    private FragmentFolderBinding binding;

    // Adapters
    private IndexDownloadAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentFolderBinding.inflate(inflater, viewGroup, false);

        initializer();

        setArgs();

        return binding.getRoot();
    }

    private void initializer() {
        adapter = new IndexDownloadAdapter(requireActivity());

        binding.headerIncludeLayout.titleTextView.setText(getResources().getString(R.string.FolderFragmentTitle));

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.indexSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
    }

    private void setArgs() {
        String folderName = FolderFragmentArgs.fromBundle(getArguments()).getFolderName();
        setData(folderName);
    }

    private void setData(String name) {
        File[] files = FileManager.listFilesExternalStoragePublicPath(Environment.DIRECTORY_DOWNLOADS, "Risloo" + File.separator + name);

        if (files != null) {
            ArrayList<File> items = new ArrayList<>(Arrays.asList(files));

            adapter.setItems(items);
            binding.indexSingleLayout.recyclerView.setAdapter(adapter);

            binding.indexSingleLayout.emptyView.setVisibility(View.GONE);
        } else if (adapter.getItemCount() == 0) {
            binding.indexSingleLayout.recyclerView.setAdapter(null);

            binding.indexSingleLayout.emptyView.setVisibility(View.VISIBLE);
            binding.indexSingleLayout.emptyView.setText(getResources().getString(R.string.FolderFragmentEmpty));
        }

        binding.headerIncludeLayout.countTextView.setText(StringManager.bracing(adapter.getItemCount()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}