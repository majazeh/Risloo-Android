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
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Widgets.ItemDecorateRecyclerView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.Cases2Adapter;
import com.majazeh.risloo.databinding.FragmentRoomBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.Madule.Room;
import com.mre.ligheh.Model.TypeModel.CaseModel;

import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class RoomFragment extends Fragment {

    // Binding
    private FragmentRoomBinding binding;

    // Adapters
    private Cases2Adapter cases2Adapter;

    // Objects
    private RecyclerView.ItemDecoration itemDecoration;
    private LinearLayoutManager layoutManager;
    private Handler handler;
    private Bundle extras;

    // Vars
    private HashMap data, header;
    private boolean isLoading = true, userSelect = false;
    private String type = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentRoomBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setPermission();

        setExtra();

        getData();

        return binding.getRoot();
    }

    private void initializer() {
        cases2Adapter = new Cases2Adapter(requireActivity());

        itemDecoration = new ItemDecorateRecyclerView("verticalLayout", (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._4sdp), (int) getResources().getDimension(R.dimen._12sdp));

        layoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);

        handler = new Handler();

        extras = new Bundle();

        data = new HashMap<>();
        data.put("page", 1);
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.headerIncludeLayout.titleTextView.setText(getResources().getString(R.string.Cases2AdapterHeader));

        InitManager.imgResTint(requireActivity(), binding.addCaseImageView.getRoot(), R.drawable.ic_plus_light, R.color.White);
        InitManager.imgResTint(requireActivity(), binding.addScheduleImageView.getRoot(), R.drawable.ic_calendar_plus_light, R.color.Green700);
        InitManager.recyclerView(binding.casesSingleLayout.recyclerView, itemDecoration, layoutManager);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.addCaseImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_green600_ripple_white);
            binding.addScheduleImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_green700_ripple_green300);
        } else {
            binding.addCaseImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_green600);
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

        binding.menuSpinner.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        binding.menuSpinner.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    String pos = parent.getItemAtPosition(position).toString();

                    switch (pos) {
                        case "اعضاء":
                            if (type.equals("room"))
                                ((MainActivity) requireActivity()).navigator(R.id.roomUsersFragment, extras);
                            else
                                ((MainActivity) requireActivity()).navigator(R.id.centerUsersFragment, extras);
                            break;
                        case "برنامه درمانی":
                            if (type.equals("room"))
                                ((MainActivity) requireActivity()).navigator(R.id.roomSchedulesFragment, extras);
                            else
                                ((MainActivity) requireActivity()).navigator(R.id.centerSchedulesFragment, extras);
                            break;
                        case "پروفایل من":
                            ((MainActivity) requireActivity()).navigator(R.id.referenceFragment, extras);
                            break;
                        case "ویرایش":
                            ((MainActivity) requireActivity()).navigator(R.id.editCenterFragment, extras);
                            break;
                    }

                    binding.menuSpinner.selectSpinner.setSelection(binding.menuSpinner.selectSpinner.getAdapter().getCount());

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ClickManager.onDelayedClickListener(() -> {
            ((MainActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");

            Room.request(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    RoomModel model = (RoomModel) object;

                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            setAcceptation(model);

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
                    data.put("page", 1);
                    data.put("q", String.valueOf(s));

                    if (binding.searchIncludeLayout.progressBar.getVisibility() == View.GONE)
                        binding.searchIncludeLayout.progressBar.setVisibility(View.VISIBLE);

                    getData();
                }, 750);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.getRoot().setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (!isLoading) {
                if (!binding.getRoot().canScrollVertically(1)) {
                    isLoading = true;

                    if (data.containsKey("page"))
                        data.put("page", ((int) data.get("page")) + 1);
                    else
                        data.put("page", 1);

                    if (binding.casesSingleLayout.progressBar.getVisibility() == View.GONE)
                        binding.casesSingleLayout.progressBar.setVisibility(View.VISIBLE);

                    getData();
                }
            }
        });

        ClickManager.onClickListener(() -> ((MainActivity) requireActivity()).navigator(R.id.createCaseFragment, extras)).widget(binding.addCaseImageView.getRoot());

        ClickManager.onClickListener(() -> ((MainActivity) requireActivity()).navigator(R.id.createScheduleFragment, extras)).widget(binding.addScheduleImageView.getRoot());
    }

    private void setDropdown(String status) {
        ArrayList<String> menu = new ArrayList<>();

        if (((MainActivity) requireActivity()).singleton.getType().equals("admin")) {
            menu.add(requireActivity().getResources().getString(R.string.RoomFragmentUsers));
        }

        menu.add(requireActivity().getResources().getString(R.string.RoomFragmentSchedules));

        if (((MainActivity) requireActivity()).singleton.getType().equals("admin") && !type.equals("room")) {
            if (!status.equals("request"))
                menu.add(requireActivity().getResources().getString(R.string.RoomFragmentProfile));

            menu.add(requireActivity().getResources().getString(R.string.RoomFragmentEdit));
        }

        menu.add("");

        InitManager.customizedSpinner(requireActivity(), binding.menuSpinner.selectSpinner, menu, "room");
    }

    private void setPermission() {
        if (!((MainActivity) requireActivity()).singleton.getType().equals("admin")) {
            binding.addCaseImageView.getRoot().setVisibility(View.GONE);
            binding.addScheduleImageView.getRoot().setVisibility(View.GONE);
        }
    }

    private void setExtra() {
        if (getArguments() != null) {
            if (getArguments().getString("id") != null && !getArguments().getString("id").equals("")) {
                extras.putString("id", getArguments().getString("id"));
                data.put("id", getArguments().getString("id"));
            }

            if (getArguments().getString("type") != null && !getArguments().getString("type").equals("")) {
                type = getArguments().getString("type");
                extras.putString("type", type);
            }

            if (getArguments().getString("status") != null && !getArguments().getString("status").equals("")) {
                extras.putString("status", getArguments().getString("status"));

                if (!type.equals("room")) {
                    setStatus(getArguments().getString("status"));
                }
            } else {
                setDropdown("");
            }

            if (getArguments().getString("manager_id") != null && !getArguments().getString("manager_id").equals("") && getArguments().getString("manager_name") != null && !getArguments().getString("manager_name").equals("")) {
                extras.putString("manager_id", getArguments().getString("manager_id"));
                extras.putString("manager_name", getArguments().getString("manager_name"));

                if (type.equals("room")) {
                    binding.nameTextView.setText(getArguments().getString("manager_name"));
                    binding.nameTextView.setVisibility(View.VISIBLE);
                }
            }

            if (getArguments().getString("title") != null && !getArguments().getString("title").equals("")) {
                extras.putString("title", getArguments().getString("title"));

                if (!type.equals("room")) {
                    binding.nameTextView.setText(getArguments().getString("title"));
                    binding.nameTextView.setVisibility(View.VISIBLE);
                }
            } else {
                if (!type.equals("room"))
                    binding.nameTextView.setVisibility(View.GONE);
            }

            if (getArguments().getString("address") != null && !getArguments().getString("address").equals("")) {
                extras.putString("address", getArguments().getString("address"));
            }

            if (getArguments().getString("description") != null && !getArguments().getString("description").equals("")) {
                extras.putString("description", getArguments().getString("description"));

                if (!type.equals("room")) {
                    binding.descriptionTextView.setText(getArguments().getString("description"));
                    binding.descriptionTextView.setVisibility(View.VISIBLE);
                }
            } else {
                binding.descriptionTextView.setVisibility(View.GONE);
            }

            if (getArguments().getString("avatar") != null && !getArguments().getString("avatar").equals("")) {
                extras.putString("avatar", getArguments().getString("avatar"));
                binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
                Picasso.get().load(getArguments().getString("avatar")).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
            } else {
                binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
                binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(binding.nameTextView.getText().toString()));

                Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
            }

            if (getArguments().getString("phone_numbers") != null && !getArguments().getString("phone_numbers").equals("")) {
                try {
                    extras.putString("phone_numbers", getArguments().getString("phone_numbers"));

                    if (!getArguments().getString("type").equals("room")) {
                        binding.mobileTextView.setText(new JSONArray(getArguments().getString("phone_numbers")).getString(0));
                        binding.mobileGroup.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                binding.mobileGroup.setVisibility(View.GONE);
            }

            if (getArguments().getString("room_id") != null && !getArguments().getString("room_id").equals("")) {
                extras.putString("room_id", getArguments().getString("room_id"));
                extras.putString("room_name", getArguments().getString("room_name"));
                extras.putString("center_name", getArguments().getString("center_name"));
            }
        }
    }

    private void setData(RoomModel model) {
        try {
            if (!model.getRoomType().equals("room")) {
                setAcceptation(model);
            }

            if (model.getRoomManager().getUserId() != null && model.getRoomManager().getName() != null) {
                extras.putString("manager_id", model.getRoomManager().getUserId());
                extras.putString("manager_name", model.getRoomManager().getName());

                if (model.getRoomType().equals("room")) {
                    binding.nameTextView.setText(model.getRoomManager().getName());
                    binding.nameTextView.setVisibility(View.VISIBLE);
                }
            }

            if (model.getRoomCenter().getDetail().has("title") && !model.getRoomCenter().getDetail().isNull("title")) {
                extras.putString("title", model.getRoomCenter().getDetail().getString("title"));

                if (!model.getRoomType().equals("room")) {
                    binding.nameTextView.setText(model.getRoomCenter().getDetail().getString("title"));
                    binding.nameTextView.setVisibility(View.VISIBLE);
                }
            } else {
                if (!model.getRoomType().equals("room"))
                    binding.nameTextView.setVisibility(View.GONE);
            }

            if (model.getRoomCenter().getDetail().has("address") && !model.getRoomCenter().getDetail().isNull("address")) {
                extras.putString("address", model.getRoomCenter().getDetail().getString("address"));
            }

            if (model.getRoomCenter().getDetail().has("description") && !model.getRoomCenter().getDetail().isNull("description")) {
                extras.putString("description", model.getRoomCenter().getDetail().getString("description"));

                if (!model.getRoomType().equals("room")) {
                    binding.descriptionTextView.setText(model.getRoomCenter().getDetail().getString("description"));
                    binding.descriptionTextView.setVisibility(View.VISIBLE);
                }
            } else {
                binding.descriptionTextView.setVisibility(View.GONE);
            }

            if (model.getRoomCenter().getDetail().has("avatar") && !model.getRoomCenter().getDetail().isNull("avatar") && model.getRoomCenter().getDetail().getJSONArray("avatar").length() != 0) {
                extras.putString("avatar", model.getRoomCenter().getDetail().getJSONArray("avatar").getJSONObject(1).getString("url"));

                if (!model.getRoomType().equals("room")) {
                    binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
                    Picasso.get().load(model.getRoomCenter().getDetail().getJSONArray("avatar").getJSONObject(1).getString("url")).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
                }
            } else {
                binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
                binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(binding.nameTextView.getText().toString()));

                Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
            }

            if (model.getRoomCenter().getDetail().has("phone_numbers") && !model.getRoomCenter().getDetail().isNull("phone_numbers") && model.getRoomCenter().getDetail().getJSONArray("phone_numbers").length() != 0) {
                extras.putString("phone_numbers", model.getRoomCenter().getDetail().getJSONArray("phone_numbers").toString());

                if (!model.getRoomType().equals("room")) {
                    binding.mobileTextView.setText(model.getRoomCenter().getDetail().getJSONArray("phone_numbers").get(0).toString());
                    binding.mobileGroup.setVisibility(View.VISIBLE);
                }
            } else {
                binding.mobileGroup.setVisibility(View.GONE);
            }

            if (model.getRoomCenter().getCenterId() != null && !model.getRoomCenter().getCenterId().equals("")) {
                extras.putString("center_id", model.getRoomCenter().getCenterId());
            }

            if (model.getRoomAcceptation() != null && model.getRoomAcceptation().getId() != null) {
                extras.putString("user_id", model.getRoomAcceptation().getId());
            }

            if (model.getRoomAcceptation() != null && model.getRoomAcceptation().getName() != null) {
                extras.putString("user_name", model.getRoomAcceptation().getName());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getData() {
        Room.showDashboard(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        try {
                            RoomModel model = new RoomModel(((JSONObject) object).getJSONObject("room"));

                            setData(model);

                            List cases = new List();
                            for (int i = 0; i < ((JSONObject) object).getJSONArray("data").length(); i++) {
                                cases.add(new CaseModel(((JSONObject) object).getJSONArray("data").getJSONObject(i)));
                            }

                            if (Objects.equals(data.get("page"), 1))
                                cases2Adapter.clearCases();

                            if (!cases.data().isEmpty()) {
                                cases2Adapter.setCases(cases.data());
                                binding.casesSingleLayout.recyclerView.setAdapter(cases2Adapter);

                                binding.casesSingleLayout.textView.setVisibility(View.GONE);
                            } else if (cases2Adapter.getItemCount() == 0) {
                                binding.casesSingleLayout.textView.setVisibility(View.VISIBLE);
                            }
                            binding.headerIncludeLayout.countTextView.setText("(" + cases2Adapter.getItemCount() + ")");

                            binding.casesSingleLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.casesShimmerLayout.getRoot().setVisibility(View.GONE);
                            binding.casesShimmerLayout.getRoot().stopShimmer();

                            if (binding.casesSingleLayout.progressBar.getVisibility() == View.VISIBLE)
                                binding.casesSingleLayout.progressBar.setVisibility(View.GONE);
                            if (binding.searchIncludeLayout.progressBar.getVisibility() == View.VISIBLE)
                                binding.searchIncludeLayout.progressBar.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                    isLoading = false;
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        binding.casesSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.casesShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.casesShimmerLayout.getRoot().stopShimmer();

                        if (binding.casesSingleLayout.progressBar.getVisibility() == View.VISIBLE)
                            binding.casesSingleLayout.progressBar.setVisibility(View.GONE);
                        if (binding.searchIncludeLayout.progressBar.getVisibility() == View.VISIBLE)
                            binding.searchIncludeLayout.progressBar.setVisibility(View.GONE);
                    });
                    isLoading = false;
                }
            }
        });
    }

    private void setStatus(String status) {
        binding.statusTextView.getRoot().setText(SelectionManager.getRoomStatus(requireActivity(), "fa", status));

        switch (status) {
            case "request":
                binding.statusTextView.getRoot().setEnabled(true);
                binding.statusTextView.getRoot().setTextColor(getResources().getColor(R.color.White));

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
                    binding.statusTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_green600_ripple_green800);
                else
                    binding.statusTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_green600);
                break;
            default:
                binding.statusTextView.getRoot().setEnabled(false);
                binding.statusTextView.getRoot().setTextColor(getResources().getColor(R.color.Gray500));

                binding.statusTextView.getRoot().setBackgroundResource(android.R.color.transparent);
                break;
        }

        setDropdown(status);
    }

    private void setAcceptation(RoomModel model) {
        if (model.getRoomAcceptation() != null) {
            switch (model.getRoomAcceptation().getPosition()) {
                case "manager":
                case "operator":
                case "psychologist":
                case "client":
                    setStatus(model.getRoomAcceptation().getPosition());
                    break;
                default:
                    if (!model.getRoomAcceptation().getKicked_at().equals("")) {
                        setStatus("kicked");
                    } else {
                        if (model.getRoomAcceptation().getAccepted_at() != 0)
                            setStatus("accepted");
                        else
                            setStatus("awaiting");
                    }
                    break;
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