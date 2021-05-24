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
import android.widget.Toast;

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
import com.majazeh.risloo.Views.Adapters.Recycler.RoomsAdapter;
import com.majazeh.risloo.databinding.FragmentCenterBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class CenterFragment extends Fragment {

    // Binding
    private FragmentCenterBinding binding;

    // Adapters
    private RoomsAdapter roomsAdapter;

    // Objects
    private RecyclerView.ItemDecoration itemDecoration;
    private LinearLayoutManager layoutManager;
    private Handler handler;

    // Vars
    private HashMap data, header;
    private String id = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCenterBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        roomsAdapter = new RoomsAdapter(requireActivity());

        itemDecoration = new ItemDecorateRecyclerView("verticalLayout", (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._4sdp), (int) getResources().getDimension(R.dimen._12sdp));

        layoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);

        handler = new Handler();

        data = new HashMap<>();
        data.put("id", id);
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        InitManager.imgResTint(requireActivity(), binding.editImageView.getRoot(), R.drawable.ic_edit_light, R.color.Gray500);
        InitManager.imgResTint(requireActivity(), binding.profileImageView.getRoot(), R.drawable.ic_user_crown_light, R.color.Blue600);
        InitManager.imgResTint(requireActivity(), binding.schedulesImageView.getRoot(), R.drawable.ic_user_clock_light, R.color.Blue600);
        InitManager.imgResTint(requireActivity(), binding.usersImageView.getRoot(), R.drawable.ic_users_light, R.color.Blue600);

        binding.headerIncludeLayout.titleTextView.setText(getResources().getString(R.string.RoomsAdapterHeader));

        InitManager.imgResTint(requireActivity(), binding.addRoomImageView.getRoot(), R.drawable.ic_plus_light, R.color.Green700);
        InitManager.imgResTint(requireActivity(), binding.addScheduleImageView.getRoot(), R.drawable.ic_calendar_plus_light, R.color.Green700);
        InitManager.recyclerView(binding.roomsSingleLayout.recyclerView, itemDecoration, layoutManager);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.editImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_gray500_ripple_gray300);
            binding.profileImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_blue600_ripple_blue300);
            binding.schedulesImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_blue600_ripple_blue300);
            binding.usersImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_blue600_ripple_blue300);

            binding.addRoomImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_green700_ripple_green300);
            binding.addScheduleImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_green700_ripple_green300);
        } else {
            binding.editImageView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_gray500);
            binding.profileImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_blue600);
            binding.schedulesImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_blue600);
            binding.usersImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_blue600);

            binding.addRoomImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_green700);
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

        ClickManager.onClickListener(() -> {
            Bundle extras = new Bundle();
            extras.putString("id", id);
            if (getArguments().getString("address") != null)
                extras.putString("address", getArguments().getString("address"));

            if (getArguments().getString("manager_name") != null)
                extras.putString("manager_name", getArguments().getString("manager_name"));

            if (getArguments().getString("manager_id") != null)
                extras.putString("manager_id", getArguments().getString("manager_id"));

            if (getArguments().getString("phone_numbers") != null)
                extras.putString("phone_numbers", getArguments().getString("phone_numbers"));

            if (getArguments().getString("title") != null)
                extras.putString("title", getArguments().getString("title"));


            if (getArguments().getString("description") != null)
                extras.putString("description", getArguments().getString("description"));


            if (getArguments().getString("type") != null)
                extras.putString("type", getArguments().getString("type"));


            if (getArguments().getString("avatar") != null && !getArguments().getString("avatar").equals(""))
                extras.putString("avatar", getArguments().getString("avatar"));


            ((MainActivity) requireActivity()).navigator(R.id.editCenterFragment, extras);
        }).widget(binding.editImageView.getRoot());

        ClickManager.onClickListener(() -> {
            Bundle extras = new Bundle();
            extras.putString("id", id);

            ((MainActivity) requireActivity()).navigator(R.id.userFragment, extras);
        }).widget(binding.profileImageView.getRoot());

        ClickManager.onClickListener(() -> {
            Bundle extras = new Bundle();
            extras.putString("id", id);

            ((MainActivity) requireActivity()).navigator(R.id.centerSchedulesFragment, extras);
        }).widget(binding.schedulesImageView.getRoot());

        ClickManager.onClickListener(() -> {
            Bundle extras = new Bundle();
            extras.putString("id", id);

            ((MainActivity) requireActivity()).navigator(R.id.centerUsersFragment, extras);
        }).widget(binding.usersImageView.getRoot());

        ClickManager.onDelayedClickListener(() -> {
            ((MainActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");

            Center.request(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            // sdfnslkdafnksnfklasklfnlksfn

                            ((MainActivity) requireActivity()).loadingDialog.dismiss();
                            Toast.makeText(requireActivity(), requireActivity().getResources().getString(R.string.AppChanged), Toast.LENGTH_SHORT).show();
                        });
                    }
                }

                @Override
                public void onFailure(String response) {
                    // Place Code if Needed
                }
            });
        }).widget(binding.statusTextView.getRoot());

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
                    binding.searchIncludeLayout.progressBar.setVisibility(View.VISIBLE);
                    data.put("q", String.valueOf(s));
                    setData();
                }, 750);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ClickManager.onClickListener(() -> {
            Bundle extras = new Bundle();
            extras.putString("id", id);

            ((MainActivity) requireActivity()).navigator(R.id.createRoomFragment, extras);
        }).widget(binding.addRoomImageView.getRoot());

        ClickManager.onClickListener(() -> {
            Bundle extras = new Bundle();
            extras.putString("id", id);

            ((MainActivity) requireActivity()).navigator(R.id.createScheduleFragment, extras);
        }).widget(binding.addScheduleImageView.getRoot());
    }

    private void setData() {
        if (!((MainActivity) requireActivity()).singleton.getType().equals("admin")) {
            binding.editImageView.getRoot().setVisibility(View.GONE);
            binding.profileImageView.getRoot().setVisibility(View.GONE);
            binding.usersImageView.getRoot().setVisibility(View.GONE);
        }

        if (getArguments() != null) {
            if (getArguments().getString("id") != null) {
                id = getArguments().getString("id");
                data.put("id", id);
            }

            if (getArguments().getString("status") != null) {
                setStatus(getArguments().getString("status"));
            }

            if (getArguments().getString("title") != null && !getArguments().getString("title").equals("")) {
                binding.nameTextView.setVisibility(View.VISIBLE);
                binding.nameTextView.setText(getArguments().getString("title"));
            } else {
                binding.nameTextView.setVisibility(View.GONE);
            }

            if (getArguments().getString("description") != null && !getArguments().getString("description").equals("")) {
                binding.descriptionTextView.setVisibility(View.VISIBLE);
                binding.descriptionTextView.setText(getArguments().getString("description"));
            } else {
                binding.descriptionTextView.setVisibility(View.GONE);
            }

            if (getArguments().getString("avatar") != null && !getArguments().getString("avatar").equals("")) {
                binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
                Picasso.get().load(getArguments().getString("avatar")).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
            } else {
                binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
                binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(binding.nameTextView.getText().toString()));

                Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
            }

            if (getArguments().getString("manager_name") != null && !getArguments().getString("manager_name").equals("")) {
                binding.ownerTextView.setVisibility(View.VISIBLE);
                binding.ownerTextView.setText(getArguments().getString("manager_name"));
            } else {
                binding.ownerTextView.setVisibility(View.GONE);
            }

            if (getArguments().getString("phone_numbers") != null && !getArguments().getString("phone_numbers").equals("")) {
                binding.mobileGroup.setVisibility(View.VISIBLE);
                binding.mobileTextView.setText(getArguments().getString("phone_numbers"));
            } else {
                binding.mobileGroup.setVisibility(View.GONE);
            }
        }

        Center.showDashboard(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        JSONObject jsonObject = (JSONObject) object;

                        try {
                            CenterModel centerModel = new CenterModel(jsonObject.getJSONObject("center"));

                            setAcceptation(centerModel);

                            if (!centerModel.getDetail().isNull("title")) {
                                binding.nameTextView.setVisibility(View.VISIBLE);
                                binding.nameTextView.setText(centerModel.getDetail().getString("title"));
                            } else {
                                binding.nameTextView.setVisibility(View.GONE);
                            }

                            if (!centerModel.getDetail().isNull("description")) {
                                binding.descriptionTextView.setVisibility(View.VISIBLE);
                                binding.descriptionTextView.setText(centerModel.getDetail().getString("description"));
                            } else {
                                binding.descriptionTextView.setVisibility(View.GONE);
                            }

                            if (!centerModel.getDetail().isNull("avatar") && centerModel.getDetail().getJSONArray("avatar").length() != 0) {
                                binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
                                Picasso.get().load(centerModel.getDetail().getJSONArray("avatar").getJSONObject(1).getString("url")).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
                            } else {
                                binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
                                binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(binding.nameTextView.getText().toString()));

                                Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
                            }

                            if (centerModel.getManager().getName() != null) {
                                binding.ownerTextView.setVisibility(View.VISIBLE);
                                binding.ownerTextView.setText(centerModel.getManager().getName());
                            } else {
                                binding.ownerTextView.setVisibility(View.GONE);
                            }

                            if (!centerModel.getDetail().isNull("phone_numbers") && centerModel.getDetail().getJSONArray("phone_numbers").length() != 0) {
                                binding.mobileGroup.setVisibility(View.VISIBLE);
                                binding.mobileTextView.setText(centerModel.getDetail().getJSONArray("phone_numbers").get(0).toString());
                            } else {
                                binding.mobileGroup.setVisibility(View.GONE);
                            }

                            List rooms = new List();
                            for (int i = 0; i < jsonObject.getJSONArray("data").length(); i++) {
                                rooms.add(new RoomModel(jsonObject.getJSONArray("data").getJSONObject(i)));
                            }

                            if (!rooms.data().isEmpty()) {
                                roomsAdapter.setRooms(rooms.data());

                                binding.roomsSingleLayout.recyclerView.setAdapter(roomsAdapter);
                                binding.headerIncludeLayout.countTextView.setText("(" + roomsAdapter.getItemCount() + ")");

                                binding.roomsSingleLayout.textView.setVisibility(View.GONE);
                            } else {
                                binding.roomsSingleLayout.textView.setVisibility(View.VISIBLE);
                            }
                            binding.roomsSingleLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.roomsShimmerLayout.getRoot().setVisibility(View.GONE);
                            binding.roomsShimmerLayout.getRoot().stopShimmer();

                            if (binding.searchIncludeLayout.progressBar.getVisibility() == View.VISIBLE) {
                                binding.searchIncludeLayout.progressBar.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        binding.roomsSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.roomsShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.roomsShimmerLayout.getRoot().stopShimmer();

                        if (binding.searchIncludeLayout.progressBar.getVisibility() == View.VISIBLE) {
                            binding.searchIncludeLayout.progressBar.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }

    private void setStatus(String status) {
        switch (status) {
            case "request":
                binding.statusTextView.getRoot().setEnabled(true);

                binding.statusTextView.getRoot().setText(getResources().getString(R.string.CenterFragmentStatusRequest));
                binding.statusTextView.getRoot().setTextColor(getResources().getColor(R.color.White));

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
                    binding.statusTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_green600_ripple_green800);
                else
                    binding.statusTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_green600);
                break;
            case "owner":
                binding.statusTextView.getRoot().setEnabled(false);

                binding.statusTextView.getRoot().setText(getResources().getString(R.string.CenterFragmentStatusOwner));
                binding.statusTextView.getRoot().setTextColor(getResources().getColor(R.color.Gray500));

                binding.statusTextView.getRoot().setBackgroundResource(android.R.color.transparent);
                break;
            case "client":
                binding.statusTextView.getRoot().setEnabled(false);

                binding.statusTextView.getRoot().setText(getResources().getString(R.string.CenterFragmentStatusClient));
                binding.statusTextView.getRoot().setTextColor(getResources().getColor(R.color.Gray500));

                binding.statusTextView.getRoot().setBackgroundResource(android.R.color.transparent);
                break;
            case "accepted":
                binding.statusTextView.getRoot().setEnabled(false);

                binding.statusTextView.getRoot().setText(getResources().getString(R.string.CenterFragmentStatusAccepted));
                binding.statusTextView.getRoot().setTextColor(getResources().getColor(R.color.Gray500));

                binding.statusTextView.getRoot().setBackgroundResource(android.R.color.transparent);
                break;
            case "awaiting":
                binding.statusTextView.getRoot().setEnabled(false);

                binding.statusTextView.getRoot().setText(getResources().getString(R.string.CenterFragmentStatusAwaiting));
                binding.statusTextView.getRoot().setTextColor(getResources().getColor(R.color.Gray500));

                binding.statusTextView.getRoot().setBackgroundResource(android.R.color.transparent);
                break;
            case "kicked":
                binding.statusTextView.getRoot().setEnabled(false);

                binding.statusTextView.getRoot().setText(getResources().getString(R.string.CenterFragmentStatusKicked));
                binding.statusTextView.getRoot().setTextColor(getResources().getColor(R.color.Gray500));

                binding.statusTextView.getRoot().setBackgroundResource(android.R.color.transparent);
                break;
        }
    }

    private void setAcceptation(CenterModel model) {
        if (model.getAcceptation() != null) {
            if (model.getAcceptation().getPosition().equals("manager")) {
                setStatus("owner");
            } else if (model.getAcceptation().getPosition().equals("client")) {
                setStatus("client");
            } else {
                if (model.getAcceptation().getKicked_at() != null) {
                    setStatus("kicked");
                } else {
                    if (model.getAcceptation().getAccepted_at() == 0) {
                        setStatus("accepted");
                    } else {
                        setStatus("awaiting");
                    }
                }
            }
        } else {
            setStatus("request");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        handler.removeCallbacksAndMessages(null);
    }

}