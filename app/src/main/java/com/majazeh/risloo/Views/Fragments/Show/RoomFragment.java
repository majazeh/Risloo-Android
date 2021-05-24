package com.majazeh.risloo.Views.Fragments.Show;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Widgets.ItemDecorateRecyclerView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.Cases2Adapter;
import com.majazeh.risloo.databinding.FragmentRoomBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.Madule.Room;
import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class RoomFragment extends Fragment {

    // Binding
    private FragmentRoomBinding binding;

    // Adapters
    private Cases2Adapter cases2Adapter;

    // Objects
    private RecyclerView.ItemDecoration itemDecoration;
    private LinearLayoutManager layoutManager;
    private Handler handler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentRoomBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        cases2Adapter = new Cases2Adapter(requireActivity());

        itemDecoration = new ItemDecorateRecyclerView("verticalLayout", (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._4sdp), (int) getResources().getDimension(R.dimen._12sdp));

        layoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);

        handler = new Handler();

        InitManager.imgResTint(requireActivity(), binding.schedulesImageView.getRoot(), R.drawable.ic_user_clock_light, R.color.Blue600);
        InitManager.imgResTint(requireActivity(), binding.usersImageView.getRoot(), R.drawable.ic_users_light, R.color.Blue600);

        binding.headerIncludeLayout.titleTextView.setText(getResources().getString(R.string.Cases2AdapterHeader));

        InitManager.imgResTint(requireActivity(), binding.addCaseImageView.getRoot(), R.drawable.ic_plus_light, R.color.Green700);
        InitManager.imgResTint(requireActivity(), binding.addScheduleImageView.getRoot(), R.drawable.ic_calendar_plus_light, R.color.Green700);
        InitManager.recyclerView(binding.casesSingleLayout.recyclerView, itemDecoration, layoutManager);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.schedulesImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_blue600_ripple_blue300);
            binding.usersImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_blue600_ripple_blue300);

            binding.addCaseImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_green700_ripple_green300);
            binding.addScheduleImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_green700_ripple_green300);
        } else {
            binding.schedulesImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_blue600);
            binding.usersImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_blue600);

            binding.addCaseImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_green700);
            binding.addScheduleImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_green700);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        ClickManager.onDelayedClickListener(() -> {
            if (!((MainActivity) requireActivity()).singleton.getAvatar().equals("")) {
                IntentManager.display(requireActivity(), "", "", ((MainActivity) requireActivity()).singleton.getAvatar());
            }
        }).widget(binding.avatarIncludeLayout.avatarCircleImageView);

        ClickManager.onClickListener(() -> ((MainActivity) requireActivity()).navigator(R.id.roomSchedulesFragment)).widget(binding.schedulesImageView.getRoot());

        ClickManager.onClickListener(() -> ((MainActivity) requireActivity()).navigator(R.id.roomUsersFragment)).widget(binding.usersImageView.getRoot());

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
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(() -> {
                    // TODO : Place Code Here
                }, 750);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ClickManager.onClickListener(() -> ((MainActivity) requireActivity()).navigator(R.id.createCaseFragment)).widget(binding.addCaseImageView.getRoot());

        ClickManager.onClickListener(() -> ((MainActivity) requireActivity()).navigator(R.id.createScheduleFragment)).widget(binding.addScheduleImageView.getRoot());
    }

    private void setData() {
        HashMap data = new HashMap();
        HashMap header = new HashMap();
        Room.show(data, header, new Response() {
            @Override
            public void onOK(Object object) {

                try {
                    JSONObject jsonObject = (JSONObject) object;
                    RoomModel roomModel = new RoomModel(jsonObject.getJSONObject("room"));

                    List cases = new List();
                    for (int i = 0; i < jsonObject.getJSONArray("data").length(); i++) {
                        cases.add(new CaseModel(jsonObject.getJSONArray("data").getJSONObject(i)));
                    }

                    if (((MainActivity) requireActivity()).singleton.getName().equals("")) {
                        binding.nameTextView.setText(getResources().getString(R.string.AppDefaultName));
                    } else {
                        binding.nameTextView.setText(((MainActivity) requireActivity()).singleton.getName());
                    }

                    if (!((MainActivity) requireActivity()).singleton.getAvatar().equals("")) {
                        binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
                        Picasso.get().load(((MainActivity) requireActivity()).singleton.getAvatar()).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
                    } else {
                        binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
                        binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(binding.nameTextView.getText().toString()));

                        Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
                    }

                    if (!((MainActivity) requireActivity()).singleton.getEnter()) {
                        binding.schedulesImageView.getRoot().setVisibility(View.GONE);
                        binding.usersImageView.getRoot().setVisibility(View.GONE);
                    } else {
                        binding.schedulesImageView.getRoot().setVisibility(View.VISIBLE);
                        binding.usersImageView.getRoot().setVisibility(View.VISIBLE);
                    }

                    cases2Adapter.setCases(cases.data());

                    binding.casesSingleLayout.recyclerView.setAdapter(cases2Adapter);
                    binding.headerIncludeLayout.countTextView.setText("(" + cases2Adapter.getItemCount() + ")");

                    binding.casesShimmerLayout.getRoot().setVisibility(View.GONE);
                    binding.casesSingleLayout.getRoot().setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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