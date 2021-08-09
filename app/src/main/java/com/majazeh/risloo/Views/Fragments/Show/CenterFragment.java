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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;

import com.majazeh.risloo.NavigationMainDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Managers.ToastManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.RoomsAdapter;
import com.majazeh.risloo.databinding.FragmentCenterBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
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

    // Models
    private CenterModel centerModel;

    // Objects
    private Handler handler;
    private HashMap data, header;

    // Vars
    private String type = "counseling_center";
    private boolean isLoading = true, userSelect = false, userScroll = false;

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

        InitManager.txtTextColor(binding.requestTextView.getRoot(), getResources().getString(R.string.CenterFragmentRequest), getResources().getColor(R.color.White));
        InitManager.imgResTint(requireActivity(), binding.addImageView.getRoot(), R.drawable.ic_plus_light, R.color.White);
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.roomsSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.addImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_green600_ripple_white);
            binding.requestTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_green600_ripple_green800);
        } else {
            binding.addImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_green600);
            binding.requestTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_green600);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        ClickManager.onDelayedClickListener(() -> {
            if (binding.avatarIncludeLayout.charTextView.getVisibility() == View.GONE) {
                try {
                    IntentManager.display(requireActivity(), binding.nameTextView.getText().toString(), centerModel.getDetail().getJSONArray("avatar").getJSONObject(2).getString("url"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).widget(binding.avatarIncludeLayout.avatarCircleImageView);

        binding.menuSpinner.selectSpinner.setOnTouchListener((v, event) -> {
            binding.menuSpinner.selectSpinner.setSelection(binding.menuSpinner.selectSpinner.getAdapter().getCount());
            userSelect = true;
            return false;
        });

        binding.menuSpinner.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    String pos = parent.getItemAtPosition(position).toString();

                    switch (pos) {
                        case "اعضاء": {
                            NavDirections action = NavigationMainDirections.actionGlobalCenterUsersFragment(centerModel);
                            ((MainActivity) requireActivity()).navController.navigate(action);
                        } break;
                        case "برنامه درمانی": {
                            NavDirections action = NavigationMainDirections.actionGlobalCenterSchedulesFragment(centerModel);
                            ((MainActivity) requireActivity()).navController.navigate(action);
                        } break;
                        case "پروفایل من": {
                            NavDirections action = NavigationMainDirections.actionGlobalReferenceFragment(type, null, centerModel);
                            ((MainActivity) requireActivity()).navController.navigate(action);
                        } break;
                        case "ویرایش": {
                            NavDirections action = NavigationMainDirections.actionGlobalEditCenterFragment(centerModel);
                            ((MainActivity) requireActivity()).navController.navigate(action);
                        } break;
                        case "محل برگزاری": {
                            NavDirections action = NavigationMainDirections.actionGlobalCenterPlatformsFragment(centerModel);
                            ((MainActivity) requireActivity()).navController.navigate(action);
                        } break;
                    }

                    parent.setSelection(parent.getAdapter().getCount());

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
                            ToastManager.showSuccesToast(requireActivity(), getResources().getString(R.string.ToastRequestSucces));
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
        }).widget(binding.requestTextView.getRoot());

        binding.searchIncludeLayout.editText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.searchIncludeLayout.editText.hasFocus())
                ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.searchIncludeLayout.editText);
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

        binding.getRoot().setOnTouchListener((v, event) -> {
            userScroll = true;
            return false;
        });

        binding.getRoot().setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (userScroll && !isLoading && !v.canScrollVertically(1)) {
                userScroll = false;
                isLoading = true;

                if (data.containsKey("page"))
                    data.put("page", ((int) data.get("page")) + 1);
                else
                    data.put("page", 1);

                if (binding.roomsSingleLayout.progressBar.getVisibility() == View.GONE)
                    binding.roomsSingleLayout.progressBar.setVisibility(View.VISIBLE);

                getData();
            }
        });

        ClickManager.onClickListener(() -> {
            NavDirections action = NavigationMainDirections.actionGlobalCreateRoomFragment("center", null, centerModel);
            ((MainActivity) requireActivity()).navController.navigate(action);
        }).widget(binding.addImageView.getRoot());
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

            if (model.getManager().getName() != null && !model.getManager().getName().equals("")) {
                binding.ownerTextView.setText(model.getManager().getName());
                binding.ownerGroup.setVisibility(View.VISIBLE);
            } else {
                binding.ownerGroup.setVisibility(View.GONE);
            }

            if (model.getDetail().has("description") && !model.getDetail().isNull("description") && !model.getDetail().getString("description").equals("")) {
                binding.descriptionTextView.setText(model.getDetail().getString("description"));
                binding.descriptionGroup.setVisibility(View.VISIBLE);
            } else {
                binding.descriptionGroup.setVisibility(View.GONE);
            }

            if (model.getDetail().has("phone_numbers") && !model.getDetail().isNull("phone_numbers") && model.getDetail().getJSONArray("phone_numbers").length() != 0) {
                JSONArray phones = model.getDetail().getJSONArray("phone_numbers");

                binding.mobileTextView.setText("");
                for (int i = 0; i < phones.length(); i++) {
                    binding.mobileTextView.append(phones.get(i).toString());
                    if (i != phones.length() - 1) {
                        binding.mobileTextView.append("  -  ");
                    }
                }

                binding.mobileGroup.setVisibility(View.VISIBLE);
            } else {
                binding.mobileGroup.setVisibility(View.GONE);
            }

            if (model.getDetail().has("avatar") && !model.getDetail().isNull("avatar") && model.getDetail().getJSONArray("avatar").length() != 0) {
                binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
                Picasso.get().load(model.getDetail().getJSONArray("avatar").getJSONObject(2).getString("url")).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
            } else {
                binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
                binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(binding.nameTextView.getText().toString()));

                Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
            }

            setAcceptation(model);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setAcceptation(CenterModel model) {
        if (model.getAcceptation() != null) {
            if (!model.getAcceptation().getKicked_at().equals("")) {
                setStatus("kicked");
            } else {
                if (model.getAcceptation().getAccepted_at() != 0)
                    switch (model.getAcceptation().getPosition()) {
                        case "manager":
                        case "operator":
                        case "psychologist":
                        case "client":
                            setStatus(model.getAcceptation().getPosition());
                            break;
                        default:
                            setStatus("accepted");
                            break;
                    }
                else
                    setStatus("awaiting");
            }
        } else {
            setStatus("request");
        }
    }

    private void setStatus(String status) {
        if (status.equals("request")) {
            binding.requestTextView.getRoot().setVisibility(View.VISIBLE);

            binding.statusTextView.setVisibility(View.GONE);
            binding.statusTextView.setText("");
        } else {
            binding.requestTextView.getRoot().setVisibility(View.GONE);

            binding.statusTextView.setVisibility(View.VISIBLE);
            binding.statusTextView.setText(SelectionManager.getCenterStatus(requireActivity(), "fa", status));
        }

        setDropdown(status);
    }

    private void setDropdown(String status) {
        ArrayList<String> items = new ArrayList<>();

        items.add(requireActivity().getResources().getString(R.string.CenterFragmentUsers));
        items.add(requireActivity().getResources().getString(R.string.CenterFragmentSchedules));

        if (!status.equals("request"))
            items.add(requireActivity().getResources().getString(R.string.CenterFragmentProfile));

        items.add(requireActivity().getResources().getString(R.string.CenterFragmentEdit));
        items.add(requireActivity().getResources().getString(R.string.CenterFragmentPlatforms));

        items.add("");

        InitManager.actionCustomSpinner(requireActivity(), binding.menuSpinner.selectSpinner, items);
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

                            List items = new List();
                            for (int i = 0; i < ((JSONObject) object).getJSONArray("data").length(); i++) {
                                items.add(new RoomModel(((JSONObject) object).getJSONArray("data").getJSONObject(i)));
                            }

                            if (Objects.equals(data.get("page"), 1))
                                roomsAdapter.clearItems();

                            if (!items.data().isEmpty()) {
                                roomsAdapter.setItems(items.data());
                                binding.roomsSingleLayout.recyclerView.setAdapter(roomsAdapter);

                                binding.roomsSingleLayout.emptyView.setVisibility(View.GONE);
                            } else if (roomsAdapter.getItemCount() == 0) {
                                binding.roomsSingleLayout.emptyView.setVisibility(View.VISIBLE);

                                if (binding.searchIncludeLayout.progressBar.getVisibility() == View.VISIBLE)
                                    binding.roomsSingleLayout.emptyView.setText(getResources().getString(R.string.AppSearchEmpty));
                                else
                                    binding.roomsSingleLayout.emptyView.setText(getResources().getString(R.string.RoomsAdapterEmpty));
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