package com.majazeh.risloo.Views.Fragments.Edit;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.BitmapManager;
import com.majazeh.risloo.Utils.Managers.FileManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.FragmentEditAvatarBinding;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditAvatarFragment extends Fragment {

    // Binding
    private FragmentEditAvatarBinding binding;

    // Objects
    public Bitmap avatarBitmap;

    // Widgets
    private CircleImageView avatarCircleImageView;
    private TextView charTextView;
    private TextView avatarGuideTextView;
    private TextView editAvatarTextView;

    // Vars
    public String avatarPath = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentEditAvatarBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        avatarCircleImageView = binding.fragmentEditAvatarCircleImageView.componentAvatar82sdpCircleImageView;
        charTextView = binding.fragmentEditAvatarCircleImageView.componentAvatar82sdpTextView;

        avatarGuideTextView = binding.fragmentEditAvatarGuideConstraintLayout.componentGuideTextTextView;
        avatarGuideTextView.setText(getResources().getString(R.string.EditAvatarFragmentHint));

        editAvatarTextView = binding.fragmentEditAvatarButtonTextView.componentButtonRectangle32sdp;
        editAvatarTextView.setText(getResources().getString(R.string.EditAvatarFragmentButton));
        editAvatarTextView.setTextColor(getResources().getColor(R.color.White));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            editAvatarTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        } else {
            editAvatarTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_blue500);
        }
    }

    private void listener() {
        avatarCircleImageView.setOnClickListener(v -> {
            avatarCircleImageView.setClickable(false);
            ((MainActivity) requireActivity()).handler.postDelayed(() -> avatarCircleImageView.setClickable(true), 300);

            ((MainActivity) requireActivity()).imageDialog.show(getActivity().getSupportFragmentManager(), "imageBottomSheet");
        });

        editAvatarTextView.setOnClickListener(v -> {
            editAvatarTextView.setClickable(false);
            ((MainActivity) requireActivity()).handler.postDelayed(() -> editAvatarTextView.setClickable(true), 300);

            if (avatarBitmap == null) {
                Toast.makeText(getActivity(), "exception", Toast.LENGTH_SHORT).show();
            } else {
                doWork();
            }
        });
    }

    private void setData() {
        if (((MainActivity) requireActivity()).singleton.getAvatar().equals("")) {
            charTextView.setVisibility(View.VISIBLE);
                if (((MainActivity) requireActivity()).singleton.getName().equals(""))
                charTextView.setText(StringManager.firstChars(getResources().getString(R.string.MainToolbar)));
            else
                charTextView.setText(StringManager.firstChars(((MainActivity) requireActivity()).singleton.getName()));

            Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(avatarCircleImageView);
        } else {
            charTextView.setVisibility(View.GONE);

            Picasso.get().load(((MainActivity) requireActivity()).singleton.getAvatar()).placeholder(R.color.Gray50).into(avatarCircleImageView);
        }
    }

    private void doWork() {
        FileManager.writeBitmapToCache(getActivity(), BitmapManager.modifyOrientation(avatarBitmap, avatarPath), "image");

        // TODO : Call Work Method
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}