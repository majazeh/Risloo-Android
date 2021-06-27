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
import androidx.navigation.NavDirections;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class CenterFragment extends Fragment {

    // Binding
    private FragmentCenterBinding binding;

    // Adapters
    private RoomsAdapter roomsAdapter;

    // Objects
    private Handler handler;

    // Vars
    private HashMap data, header;
    private CenterModel centerModel;
    private String type = "counseling_center";
    private boolean isLoading = true, userSelect = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCenterBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setArgs();

        getData();

        return binding.getRoot();
    }

    private void initializer() {
        roomsAdapter = new RoomsAdapter(requireActivity());

        handler = new Handler();

        data = new HashMap<>();
        data.put("page", 1);
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.headerIncludeLayout.titleTextView.setText(getResources().getString(R.string.RoomsAdapterHeader));

        InitManager.imgResTint(requireActivity(), binding.addRoomImageView.getRoot(), R.drawable.ic_plus_light, R.color.White);
        InitManager.imgResTint(requireActivity(), binding.addScheduleImageView.getRoot(), R.drawable.ic_calendar_plus_light, R.color.Green700);
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.roomsSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.addRoomImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_green600_ripple_white);
            binding.addScheduleImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_green700_ripple_green300);
        } else {
            binding.addRoomImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_green600);
            binding.addScheduleImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_green700);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        ClickManager.onDelayedClickListener(() -> {
            if (binding.avatarIncludeLayout.charTextView.getVisibility() == View.GONE) {
                try {
                    IntentManager.display(requireActivity(), binding.nameTextView.getText().toString(), "", centerModel.getDetail().getJSONArray("avatar").getJSONObject(2).getString("url"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                    String item = parent.getItemAtPosition(position).toString();

                    switch (item) {
                        case "اعضاء": {
                            NavDirections action = CenterFragmentDirections.actionCenterFragmentToCenterUsersFragment(centerModel);
                            ((MainActivity) requireActivity()).navController.navigate(action);
                        } break;
                        case "برنامه درمانی": {
                            NavDirections action = CenterFragmentDirections.actionCenterFragmentToCenterSchedulesFragment(centerModel);
                            ((MainActivity) requireActivity()).navController.navigate(action);
                        } break;
                        case "پروفایل من": {
                            NavDirections action = CenterFragmentDirections.actionCenterFragmentToReferenceFragment(type, null, centerModel.getAcceptation().getId(), centerModel);
                            ((MainActivity) requireActivity()).navController.navigate(action);
                        } break;
                        case "ویرایش": {
                            NavDirections action = CenterFragmentDirections.actionCenterFragmentToEditCenterFragment(centerModel);
                            ((MainActivity) requireActivity()).navController.navigate(action);
                        } break;
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

            Center.request(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    centerModel = (CenterModel) object;

                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            setAcceptation(centerModel);

                            ((MainActivity) requireActivity()).loadingDialog.dismiss();
                            Toast.makeText(requireActivity(), requireActivity().getResources().getString(R.string.AppChanged), Toast.LENGTH_SHORT).show();
                        });
                    }
                }

                @Override
                public void onFailure(String response) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            // Place Code if Needed
                        });
                    }
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

                    if (binding.roomsSingleLayout.progressBar.getVisibility() == View.GONE)
                        binding.roomsSingleLayout.progressBar.setVisibility(View.VISIBLE);

                    getData();
                }
            }
        });

        ClickManager.onClickListener(() -> {
            NavDirections action = CenterFragmentDirections.actionCenterFragmentToCreateRoomFragment(centerModel);
            ((MainActivity) requireActivity()).navController.navigate(action);
        }).widget(binding.addRoomImageView.getRoot());

        ClickManager.onClickListener(() -> {
            NavDirections action = CenterFragmentDirections.actionCenterFragmentToCreateScheduleFragment(centerModel);
            ((MainActivity) requireActivity()).navController.navigate(action);
        }).widget(binding.addScheduleImageView.getRoot());
    }

    private void setArgs() {
        centerModel = (CenterModel) CenterFragmentArgs.fromBundle(getArguments()).getTypeModel();

        setData(centerModel);
    }

    private void setData(CenterModel model) {
        try {
            if (model.getCenterId() != null && !model.getCenterId().equals("")) {
                data.put("id", model.getCenterId());
            }

            if (model.getCenterType() != null && !model.getCenterType().equals("")) {
                type = model.getCenterType();
            }

            if (model.getDetail().has("title") && !model.getDetail().isNull("title") && !model.getDetail().getString("title").equals("")) {
                binding.nameTextView.setText(model.getDetail().getString("title"));
                binding.nameTextView.setVisibility(View.VISIBLE);
            } else {
                binding.nameTextView.setVisibility(View.GONE);
            }

            if (model.getDetail().has("description") && !model.getDetail().isNull("description") && !model.getDetail().getString("description").equals("")) {
                binding.descriptionTextView.setText(model.getDetail().getString("description"));
                binding.descriptionTextView.setVisibility(View.VISIBLE);
            } else {
                binding.descriptionTextView.setVisibility(View.GONE);
            }

            if (model.getManager().getName() != null && !model.getManager().getName().equals("")) {
                binding.ownerTextView.setText(model.getManager().getName());
                binding.ownerGroup.setVisibility(View.VISIBLE);
            } else {
                binding.ownerGroup.setVisibility(View.GONE);
            }

            if (model.getDetail().has("avatar") && !model.getDetail().isNull("avatar") && model.getDetail().getJSONArray("avatar").length() != 0) {
                binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
                Picasso.get().load(model.getDetail().getJSONArray("avatar").getJSONObject(2).getString("url")).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
            } else {
                binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
                binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(binding.nameTextView.getText().toString()));

                Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
            }

            if (model.getDetail().has("phone_numbers") && !model.getDetail().isNull("phone_numbers") && model.getDetail().getJSONArray("phone_numbers").length() != 0) {
                binding.mobileTextView.setText(model.getDetail().getJSONArray("phone_numbers").get(0).toString());
                binding.mobileGroup.setVisibility(View.VISIBLE);
            } else {
                binding.mobileGroup.setVisibility(View.GONE);
            }

            setAcceptation(model);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setAcceptation(CenterModel model) {
        if (model.getAcceptation() != null) {
            switch (model.getAcceptation().getPosition()) {
                case "manager":
                case "operator":
                case "psychologist":
                case "client":
                    setStatus(model.getAcceptation().getPosition());
                    break;
                default:
                    if (!model.getAcceptation().getKicked_at().equals("")) {
                        setStatus("kicked");
                    } else {
                        if (model.getAcceptation().getAccepted_at() != 0)
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

    private void setStatus(String status) {
        binding.statusTextView.getRoot().setText(SelectionManager.getCenterStatus(requireActivity(), "fa", status));

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

    private void setDropdown(String status) {
        ArrayList<String> menu = new ArrayList<>();

        if (((MainActivity) requireActivity()).singleton.getType().equals("admin")) {
            menu.add(requireActivity().getResources().getString(R.string.CenterFragmentUsers));
        }

        menu.add(requireActivity().getResources().getString(R.string.CenterFragmentSchedules));

        if (((MainActivity) requireActivity()).singleton.getType().equals("admin") && !status.equals("request"))
            menu.add(requireActivity().getResources().getString(R.string.CenterFragmentProfile));

        if (((MainActivity) requireActivity()).singleton.getType().equals("admin")) {
            menu.add(requireActivity().getResources().getString(R.string.CenterFragmentEdit));
        }

        menu.add("");

        InitManager.unfixedCustomSpinner(requireActivity(), binding.menuSpinner.selectSpinner, menu, "center");
    }

    private void getData() {
        Center.showDashboard(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        try {
                            centerModel = new CenterModel(((JSONObject) object).getJSONObject("center"));

                            setData(centerModel);

                            List rooms = new List();
                            for (int i = 0; i < ((JSONObject) object).getJSONArray("data").length(); i++) {
                                rooms.add(new RoomModel(((JSONObject) object).getJSONArray("data").getJSONObject(i)));
                            }

                            if (Objects.equals(data.get("page"), 1))
                                roomsAdapter.clearRooms();

                            if (!rooms.data().isEmpty()) {
                                roomsAdapter.setRooms(rooms.data());
                                binding.roomsSingleLayout.recyclerView.setAdapter(roomsAdapter);

                                binding.roomsSingleLayout.textView.setVisibility(View.GONE);
                            } else if (roomsAdapter.getItemCount() == 0) {
                                binding.roomsSingleLayout.textView.setVisibility(View.VISIBLE);
                            }
                            binding.headerIncludeLayout.countTextView.setText(StringManager.bracing(roomsAdapter.getItemCount()));

                            binding.roomsSingleLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.roomsShimmerLayout.getRoot().setVisibility(View.GONE);
                            binding.roomsShimmerLayout.getRoot().stopShimmer();

                            if (binding.roomsSingleLayout.progressBar.getVisibility() == View.VISIBLE)
                                binding.roomsSingleLayout.progressBar.setVisibility(View.GONE);
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
                        binding.roomsSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.roomsShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.roomsShimmerLayout.getRoot().stopShimmer();

                        if (binding.roomsSingleLayout.progressBar.getVisibility() == View.VISIBLE)
                            binding.roomsSingleLayout.progressBar.setVisibility(View.GONE);
                        if (binding.searchIncludeLayout.progressBar.getVisibility() == View.VISIBLE)
                            binding.searchIncludeLayout.progressBar.setVisibility(View.GONE);
                    });
                    isLoading = false;
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        handler.removeCallbacksAndMessages(null);
    }

}