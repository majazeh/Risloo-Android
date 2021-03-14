package com.majazeh.risloo.Views.Fragments.Create;

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
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditAvatarFragment extends Fragment {

    // Vars
    public String avatarPath = "";

    // Objects
    public Bitmap avatarBitmap;

    // Widgets
    private CircleImageView avatarCircleImageView;
    private TextView charTextView;
    private TextView avatarGuideTextView;
    private TextView editAvatarTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_avatar, viewGroup, false);

        initializer(view);

        detector();

        listener();

        setData();

        return view;
    }

    private void initializer(View view) {
        avatarCircleImageView = view.findViewById(R.id.component_avatar_82sdp_circleImageView);

        charTextView = view.findViewById(R.id.component_avatar_82sdp_textView);

        avatarGuideTextView = view.findViewById(R.id.component_guide_text_textView);
        avatarGuideTextView.setText(getResources().getString(R.string.EditAvatarFragmentHint));

        editAvatarTextView = view.findViewById(R.id.fragment_edit_avatar_button_textView);
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
            ((MainActivity) getActivity()).handler.postDelayed(() -> avatarCircleImageView.setClickable(true), 300);

            ((MainActivity) getActivity()).imageDialog.show(getActivity().getSupportFragmentManager(), "imageBottomSheet");
        });

        editAvatarTextView.setOnClickListener(v -> {
            editAvatarTextView.setClickable(false);
            ((MainActivity) getActivity()).handler.postDelayed(() -> editAvatarTextView.setClickable(true), 300);

            if (avatarBitmap == null) {
                Toast.makeText(getActivity(), "exception", Toast.LENGTH_SHORT).show();
            } else {
                doWork();
            }
        });
    }

    private void setData() {
        if (((MainActivity) getActivity()).singleton.getAvatar().equals("")) {
            charTextView.setVisibility(View.VISIBLE);
                if (((MainActivity) getActivity()).singleton.getName().equals(""))
                charTextView.setText(StringManager.firstChars(getResources().getString(R.string.MainToolbar)));
            else
                charTextView.setText(StringManager.firstChars(((MainActivity) getActivity()).singleton.getName()));

            Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(avatarCircleImageView);
        } else {
            charTextView.setVisibility(View.GONE);

            Picasso.get().load(((MainActivity) getActivity()).singleton.getAvatar()).placeholder(R.color.Gray50).into(avatarCircleImageView);
        }
    }

    private void doWork() {
        FileManager.writeBitmapToCache(getActivity(), BitmapManager.modifyOrientation(avatarBitmap, avatarPath), "image");

        // TODO : Call Work Method
    }

}