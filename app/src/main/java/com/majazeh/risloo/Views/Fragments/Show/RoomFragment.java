package com.majazeh.risloo.Views.Fragments.Show;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Widgets.ItemDecorateRecyclerView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.Cases2Adapter;
import com.majazeh.risloo.databinding.FragmentRoomBinding;
import com.squareup.picasso.Picasso;

public class RoomFragment extends Fragment {

    // Binding
    private FragmentRoomBinding binding;

    // Adapters
    private Cases2Adapter cases2Adapter;

    // Objects
    private RecyclerView.ItemDecoration itemDecoration;
    private LinearLayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentRoomBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        ((MainActivity) requireActivity()).handler.postDelayed(() -> {
            binding.casesShimmerLayout.getRoot().setVisibility(View.GONE);
            binding.casesSingleLayout.getRoot().setVisibility(View.VISIBLE);
        }, 2000);

        return binding.getRoot();
    }

    private void initializer() {
        cases2Adapter = new Cases2Adapter(requireActivity());

        itemDecoration = new ItemDecorateRecyclerView("verticalLayout", (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._6sdp), (int) getResources().getDimension(R.dimen._12sdp));
        layoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);

        InitManager.imgResTint(requireActivity(), binding.usersImageView.getRoot(), R.drawable.ic_users_light, R.color.Blue600);

        binding.headerIncludeLayout.titleTextView.setText(getResources().getString(R.string.RoomFragmentCasesHeader));

        InitManager.imgResTint(requireActivity(), binding.addImageView.getRoot(), R.drawable.ic_plus_light, R.color.Green700);
        InitManager.recyclerView(binding.casesSingleLayout.recyclerView, itemDecoration, layoutManager);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.usersImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_blue600_ripple_blue300);

            binding.addImageView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_green700_ripple_green300);
        } else {
            binding.usersImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_blue600);

            binding.addImageView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_green700);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.avatarIncludeLayout.avatarCircleImageView.setOnClickListener(v -> {
            binding.avatarIncludeLayout.avatarCircleImageView.setClickable(false);
            ((MainActivity) requireActivity()).handler.postDelayed(() -> binding.avatarIncludeLayout.avatarCircleImageView.setClickable(true), 300);

            if (!((MainActivity) requireActivity()).singleton.getAvatar().equals("")) {
                IntentManager.display(requireActivity(), "", "", ((MainActivity) requireActivity()).singleton.getAvatar());
            }
        });

        binding.usersImageView.getRoot().setOnClickListener(v -> {
            binding.usersImageView.getRoot().setClickable(false);
            ((MainActivity) requireActivity()).handler.postDelayed(() -> binding.usersImageView.getRoot().setClickable(true), 300);

            // TODO : Call Work Method
        });

        binding.searchIncludeLayout.editText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.searchIncludeLayout.editText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.searchIncludeLayout.editText);
                }
            }
            return false;
        });

        binding.searchIncludeLayout.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((MainActivity) requireActivity()).handler.removeCallbacksAndMessages(null);
                ((MainActivity) requireActivity()).handler.postDelayed(() -> {
                    // TODO : Place Code Here
                }, 750);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.addImageView.getRoot().setOnClickListener(v -> {
            binding.addImageView.getRoot().setClickable(false);
            ((MainActivity) requireActivity()).handler.postDelayed(() -> binding.addImageView.getRoot().setClickable(true), 300);

            ((MainActivity) requireActivity()).navigator(R.id.createCaseFragment);
        });
    }

    private void setData() {
        if (((MainActivity) requireActivity()).singleton.getName().equals("")) {
            binding.nameTextView.setText(getResources().getString(R.string.MainToolbar));
        } else {
            binding.nameTextView.setText(((MainActivity) requireActivity()).singleton.getName());
        }

        if (((MainActivity) requireActivity()).singleton.getAvatar().equals("")) {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
            binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(binding.nameTextView.getText().toString()));

            Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
        } else {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);

            Picasso.get().load(((MainActivity) requireActivity()).singleton.getAvatar()).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
        }

//        cases2Adapter.setCase(null);
        binding.casesSingleLayout.recyclerView.setAdapter(cases2Adapter);

        String dataSize = "15";
        binding.headerIncludeLayout.countTextView.setText("(" + dataSize + ")");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}