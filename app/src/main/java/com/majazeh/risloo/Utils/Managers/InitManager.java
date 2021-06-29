package com.majazeh.risloo.Utils.Managers;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Widgets.ItemDecorateRecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class InitManager {

    public static void txtTextColor(TextView txt, String txtValue, int txtColor) {
        txt.setText(txtValue);
        txt.setTextColor(txtColor);
    }

    public static void imgResTint(Activity activity, ImageView img, int imgRes, int imgColor) {
        img.setImageResource(imgRes);
        ImageViewCompat.setImageTintList(img, AppCompatResources.getColorStateList(activity, imgColor));
    }

    public static void imgResTintRotate(Activity activity, ImageView img, int imgRes, int imgColor, int degree) {
        img.setImageResource(imgRes);
        ImageViewCompat.setImageTintList(img, AppCompatResources.getColorStateList(activity, imgColor));
        img.setRotation(img.getRotation() + degree);
    }

    /*
    ---------- Spinner Codes ----------
    */

    public static void fixedSpinner(Activity activity, Spinner spinner, int arrayRes, String dimension) {
        switch (dimension) {
            case "main": {
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity, arrayRes, R.layout.spinner_item_background_main);
                adapter.setDropDownViewResource(R.layout.spinner_item_dropdown_main);
                spinner.setAdapter(adapter);
                break;
            }
            case "test": {
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity, arrayRes, R.layout.spinner_item_background_test);
                adapter.setDropDownViewResource(R.layout.spinner_item_dropdown_test);
                spinner.setAdapter(adapter);
                break;
            }
            case "adapter": {
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity, arrayRes, R.layout.spinner_item_background_adapter);
                adapter.setDropDownViewResource(R.layout.spinner_item_dropdown_adapter);
                spinner.setAdapter(adapter);
                break;
            }
            case "adapter2": {
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity, arrayRes, R.layout.spinner_item_background_adapter2);
                adapter.setDropDownViewResource(R.layout.spinner_item_dropdown_adapter);
                spinner.setAdapter(adapter);
                break;
            }
        }
    }

    public static void unfixedSpinner(Activity activity, Spinner spinner, ArrayList<String> arrayList, String dimension) {
        switch (dimension) {
            case "prerequisite": {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, R.layout.spinner_item_background_prerequisite, arrayList) {

                    private TextView dropdownTextView;

                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup viewGroup) {
                        return super.getView(position, convertView, viewGroup);
                    }

                    @Override
                    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup viewGroup) {
                        View view = LayoutInflater.from(activity).inflate(R.layout.spinner_item_dropdown_prerequisite, viewGroup, false);

                        initializer(view);

                        detector(view, position);

                        setData(position);

                        return view;
                    }

                    private void initializer(View view) {
                        dropdownTextView = view.findViewById(R.id.dropdown_textView);
                    }

                    private void detector(View view, int position) {
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                            view.setBackgroundResource(R.drawable.draw_rec_solid_gray50_ripple_gray300);
                        }
                    }

                    private void setData(int position) {
                        dropdownTextView.setText(arrayList.get(position));
                        dropdownTextView.setTextColor(activity.getResources().getColor(R.color.Gray600));
                    }

                    @Override
                    public int getCount() {
                        return super.getCount() - 1;
                    }

                };

                spinner.setAdapter(adapter);
                spinner.setSelection(adapter.getCount());
            } break;
            case "test": {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, R.layout.spinner_item_background_test, arrayList) {

                    private TextView dropdownTextView;

                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup viewGroup) {
                        return super.getView(position, convertView, viewGroup);
                    }

                    @Override
                    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup viewGroup) {
                        View view = LayoutInflater.from(activity).inflate(R.layout.spinner_item_dropdown_test, viewGroup, false);

                        initializer(view);

                        detector(view, position);

                        setData(position);

                        return view;
                    }

                    private void initializer(View view) {
                        dropdownTextView = view.findViewById(R.id.dropdown_textView);
                    }

                    private void detector(View view, int position) {
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                            view.setBackgroundResource(R.drawable.draw_rec_solid_gray50_ripple_gray300);
                        }
                    }

                    private void setData(int position) {
                        dropdownTextView.setText(arrayList.get(position));
                        dropdownTextView.setTextColor(activity.getResources().getColor(R.color.Gray600));
                    }

                    @Override
                    public int getCount() {
                        return super.getCount() - 1;
                    }

                };

                spinner.setAdapter(adapter);
                spinner.setSelection(adapter.getCount());
            } break;
        }
    }

    public static void fixedCustomSpinner(Activity activity, Spinner spinner, int arrayRes, String dimension) {
        switch (dimension) {
            case "toolbar": {
                ArrayList<String> list = new ArrayList<>();
                Collections.addAll(list, activity.getResources().getStringArray(arrayRes));
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, R.layout.spinner_item_background_empty, list) {

                    private TextView dropdownTextView;

                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup viewGroup) {
                        return super.getView(position, convertView, viewGroup);
                    }

                    @Override
                    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup viewGroup) {
                        View view = LayoutInflater.from(activity).inflate(R.layout.spinner_item_dropdown_toolbar, viewGroup, false);

                        initializer(view);

                        detector(view, position);

                        setData(position);

                        return view;
                    }

                    private void initializer(View view) {
                        dropdownTextView = view.findViewById(R.id.textView);
                    }

                    private void detector(View view, int position) {
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                            if (position == getCount() - 1)
                                view.setBackgroundResource(R.drawable.draw_rec_solid_gray50_ripple_red300);
                            else
                                view.setBackgroundResource(R.drawable.draw_rec_solid_gray50_ripple_gray300);
                        }
                    }

                    private void setData(int position) {
                        dropdownTextView.setText(list.get(position));

                        if (position == getCount() - 1)
                            dropdownTextView.setTextColor(activity.getResources().getColor(R.color.Red500));
                        else
                            dropdownTextView.setTextColor(activity.getResources().getColor(R.color.Gray600));
                    }

                    @Override
                    public int getCount() {
                        return super.getCount() - 1;
                    }

                };

                spinner.setAdapter(adapter);
                spinner.setSelection(adapter.getCount());
                break;
            }
            case "bulkSamples": {
                ArrayList<String> list = new ArrayList<>();
                Collections.addAll(list, activity.getResources().getStringArray(arrayRes));
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, R.layout.spinner_item_background_empty, list) {

                    private TextView dropdownTextView;
                    private ImageView dropdownImageView;

                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup viewGroup) {
                        return super.getView(position, convertView, viewGroup);
                    }

                    @Override
                    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup viewGroup) {
                        View view = LayoutInflater.from(activity).inflate(R.layout.spinner_item_dropdown_recycler, viewGroup, false);

                        initializer(view);

                        detector(view, position);

                        setData(position);

                        return view;
                    }

                    private void initializer(View view) {
                        dropdownTextView = view.findViewById(R.id.item_textView);
                        dropdownImageView = view.findViewById(R.id.item_imageView);
                    }

                    private void detector(View view, int position) {
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                            if (position == getCount() - 1)
                                view.setBackgroundResource(R.drawable.draw_rec_solid_gray50_ripple_gray300);
                            else
                                view.setBackgroundResource(R.drawable.draw_rec_solid_gray50_ripple_blue300);
                        }
                    }

                    private void setData(int position) {
                        dropdownTextView.setText(list.get(position));

                        switch (dropdownTextView.getText().toString()) {
                            case "لینک ثبت نام":
                                dropdownTextView.setTextColor(activity.getResources().getColor(R.color.Blue600));

                                dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_link_light, null));
                                ImageViewCompat.setImageTintList(dropdownImageView, AppCompatResources.getColorStateList(activity, R.color.Blue600));
                                break;
                            case "کپی کردن لینک":
                                dropdownTextView.setTextColor(activity.getResources().getColor(R.color.Blue600));

                                dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_copy_light, null));
                                ImageViewCompat.setImageTintList(dropdownImageView, AppCompatResources.getColorStateList(activity, R.color.Blue600));
                                break;
                            case "ویرایش نمونه":
                                dropdownTextView.setTextColor(activity.getResources().getColor(R.color.Gray600));

                                dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_edit_light, null));
                                ImageViewCompat.setImageTintList(dropdownImageView, AppCompatResources.getColorStateList(activity, R.color.Gray500));
                                break;
                        }
                    }

                    @Override
                    public int getCount() {
                        return super.getCount() - 1;
                    }

                };

                spinner.setAdapter(adapter);
                spinner.setSelection(adapter.getCount());
                break;
            }
        }
    }

    public static void unfixedCustomSpinner(Activity activity, Spinner spinner, ArrayList<String> arrayList, String dimension) {
        switch (dimension) {
            case "room":
            case "center": {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, R.layout.spinner_item_background_empty, arrayList) {

                    private TextView dropdownTextView;
                    private ImageView dropdownImageView;

                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup viewGroup) {
                        return super.getView(position, convertView, viewGroup);
                    }

                    @Override
                    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup viewGroup) {
                        View view = LayoutInflater.from(activity).inflate(R.layout.spinner_item_dropdown_recycler, viewGroup, false);

                        initializer(view);

                        detector(view, position);

                        setData(position);

                        return view;
                    }

                    private void initializer(View view) {
                        dropdownTextView = view.findViewById(R.id.item_textView);
                        dropdownImageView = view.findViewById(R.id.item_imageView);
                    }

                    private void detector(View view, int position) {
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
                            view.setBackgroundResource(R.drawable.draw_rec_solid_gray50_ripple_gray300);
                    }

                    private void setData(int position) {
                        dropdownTextView.setText(arrayList.get(position));

                        switch (arrayList.get(position)) {
                            case "اعضاء":
                                dropdownTextView.setTextColor(activity.getResources().getColor(R.color.Gray600));

                                dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_users_light, null));
                                ImageViewCompat.setImageTintList(dropdownImageView, AppCompatResources.getColorStateList(activity, R.color.Gray500));
                                break;
                            case "برنامه درمانی":
                                dropdownTextView.setTextColor(activity.getResources().getColor(R.color.Gray600));

                                dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_calendar_alt_light, null));
                                ImageViewCompat.setImageTintList(dropdownImageView, AppCompatResources.getColorStateList(activity, R.color.Gray500));
                                break;
                            case "پروفایل من":
                                dropdownTextView.setTextColor(activity.getResources().getColor(R.color.Gray600));

                                dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_user_crown_light, null));
                                ImageViewCompat.setImageTintList(dropdownImageView, AppCompatResources.getColorStateList(activity, R.color.Gray500));
                                break;
                            case "ویرایش":
                                dropdownTextView.setTextColor(activity.getResources().getColor(R.color.Gray600));

                                dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_edit_light, null));
                                ImageViewCompat.setImageTintList(dropdownImageView, AppCompatResources.getColorStateList(activity, R.color.Gray500));
                                break;
                        }
                    }

                    @Override
                    public int getCount() {
                        return super.getCount() - 1;
                    }

                };

                spinner.setAdapter(adapter);
                spinner.setSelection(adapter.getCount());
                break;
            }
            case "users": {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, R.layout.spinner_item_background_empty, arrayList) {

                    private TextView dropdownTextView;
                    private ImageView dropdownImageView;

                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup viewGroup) {
                        return super.getView(position, convertView, viewGroup);
                    }

                    @Override
                    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup viewGroup) {
                        View view = LayoutInflater.from(activity).inflate(R.layout.spinner_item_dropdown_recycler, viewGroup, false);

                        initializer(view);

                        detector(view, position);

                        setData(position);

                        return view;
                    }

                    private void initializer(View view) {
                        dropdownTextView = view.findViewById(R.id.item_textView);
                        dropdownImageView = view.findViewById(R.id.item_imageView);
                    }

                    private void detector(View view, int position) {
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
                            switch (arrayList.get(position)) {
                                case "ورود به کاربری":
                                    view.setBackgroundResource(R.drawable.draw_rec_solid_gray50_ripple_blue300);
                                    break;
                                default:
                                    view.setBackgroundResource(R.drawable.draw_rec_solid_gray50_ripple_gray300);
                                    break;
                            }
                    }

                    private void setData(int position) {
                        dropdownTextView.setText(arrayList.get(position));

                        if (dropdownTextView.getText().toString().contains("989")) {
                            dropdownTextView.setTextColor(activity.getResources().getColor(R.color.Gray600));

                            dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_mobile_light, null));
                            ImageViewCompat.setImageTintList(dropdownImageView, AppCompatResources.getColorStateList(activity, R.color.Gray500));
                        } else if (dropdownTextView.getText().toString().contains("@")) {
                            dropdownTextView.setTextColor(activity.getResources().getColor(R.color.Gray600));

                            dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_envelope_light, null));
                            ImageViewCompat.setImageTintList(dropdownImageView, AppCompatResources.getColorStateList(activity, R.color.Gray500));
                        } else if (dropdownTextView.getText().toString().equals("ورود به کاربری")) {
                            dropdownTextView.setTextColor(activity.getResources().getColor(R.color.Blue600));

                            dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_user_cog_light, null));
                            ImageViewCompat.setImageTintList(dropdownImageView, AppCompatResources.getColorStateList(activity, R.color.Blue600));
                        } else if (dropdownTextView.getText().toString().equals("ویرایش کاربر")) {
                            dropdownTextView.setTextColor(activity.getResources().getColor(R.color.Gray600));

                            dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_edit_light, null));
                            ImageViewCompat.setImageTintList(dropdownImageView, AppCompatResources.getColorStateList(activity, R.color.Gray500));
                        }
                    }

                    @Override
                    public int getCount() {
                        return super.getCount() - 1;
                    }

                };

                spinner.setAdapter(adapter);
                spinner.setSelection(adapter.getCount());
                break;
            }
            case "centerUsers":
            case "roomUsers": {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, R.layout.spinner_item_background_empty, arrayList) {

                    private TextView dropdownTextView;
                    private ImageView dropdownImageView;

                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup viewGroup) {
                        return super.getView(position, convertView, viewGroup);
                    }

                    @Override
                    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup viewGroup) {
                        View view = LayoutInflater.from(activity).inflate(R.layout.spinner_item_dropdown_recycler, viewGroup, false);

                        initializer(view);

                        detector(view, position);

                        setData(position);

                        return view;
                    }

                    private void initializer(View view) {
                        dropdownTextView = view.findViewById(R.id.item_textView);
                        dropdownImageView = view.findViewById(R.id.item_imageView);
                    }

                    private void detector(View view, int position) {
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                            switch (arrayList.get(position)) {
                                case "پذیرفتن":
                                case "ساختن اتاق درمان":
                                    view.setBackgroundResource(R.drawable.draw_rec_solid_gray50_ripple_green300);
                                    break;
                                case "تعلیق":
                                    view.setBackgroundResource(R.drawable.draw_rec_solid_gray50_ripple_red300);
                                    break;
                                case "اتاق درمان":
                                case "ویرایش کاربر":
                                    view.setBackgroundResource(R.drawable.draw_rec_solid_gray50_ripple_gray300);
                                    break;
                                case "ورود به کاربری":
                                    view.setBackgroundResource(R.drawable.draw_rec_solid_gray50_ripple_blue300);
                                    break;
                            }
                        }
                    }

                    private void setData(int position) {
                        dropdownTextView.setText(arrayList.get(position));

                        switch (arrayList.get(position)) {
                            case "پذیرفتن":
                                dropdownTextView.setTextColor(activity.getResources().getColor(R.color.Green600));

                                dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_check_light, null));
                                ImageViewCompat.setImageTintList(dropdownImageView, AppCompatResources.getColorStateList(activity, R.color.Green600));
                                break;
                            case "تعلیق":
                                dropdownTextView.setTextColor(activity.getResources().getColor(R.color.Red600));

                                dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_times_light, null));
                                ImageViewCompat.setImageTintList(dropdownImageView, AppCompatResources.getColorStateList(activity, R.color.Red600));
                                break;
                            case "ساختن اتاق درمان":
                                dropdownTextView.setTextColor(activity.getResources().getColor(R.color.Green600));

                                dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_plus_light, null));
                                ImageViewCompat.setImageTintList(dropdownImageView, AppCompatResources.getColorStateList(activity, R.color.Green600));
                                break;
                            case "اتاق درمان":
                                dropdownTextView.setTextColor(activity.getResources().getColor(R.color.Gray500));

                                dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_building_light, null));
                                ImageViewCompat.setImageTintList(dropdownImageView, AppCompatResources.getColorStateList(activity, R.color.Gray500));
                                break;
                            case "ویرایش کاربر":
                                dropdownTextView.setTextColor(activity.getResources().getColor(R.color.Gray600));

                                dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_edit_light, null));
                                ImageViewCompat.setImageTintList(dropdownImageView, AppCompatResources.getColorStateList(activity, R.color.Gray500));
                                break;
                            case "ورود به کاربری":
                                dropdownTextView.setTextColor(activity.getResources().getColor(R.color.Blue600));

                                dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_user_cog_light, null));
                                ImageViewCompat.setImageTintList(dropdownImageView, AppCompatResources.getColorStateList(activity, R.color.Blue600));
                                break;
                        }
                    }

                    @Override
                    public int getCount() {
                        return super.getCount() - 1;
                    }

                };

                spinner.setAdapter(adapter);
                spinner.setSelection(adapter.getCount());
                break;
            }
            case "profiles": {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, R.layout.spinner_item_background_empty, arrayList) {

                    private TextView dropdownTextView;

                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup viewGroup) {
                        return super.getView(position, convertView, viewGroup);
                    }

                    @Override
                    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup viewGroup) {
                        View view = LayoutInflater.from(activity).inflate(R.layout.spinner_item_dropdown_main, viewGroup, false);

                        initializer(view);

                        detector(view, position);

                        setData(position);

                        return view;
                    }

                    private void initializer(View view) {
                        dropdownTextView = view.findViewById(R.id.item_textView);
                    }

                    private void detector(View view, int position) {
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                            view.setBackgroundResource(R.drawable.draw_rec_solid_gray50_ripple_gray300);
                        }
                    }

                    private void setData(int position) {
                        dropdownTextView.setText(arrayList.get(position));
                        dropdownTextView.setTextColor(activity.getResources().getColor(R.color.Gray600));
                    }

                    @Override
                    public int getCount() {
                        return super.getCount() - 1;
                    }

                };

                spinner.setAdapter(adapter);
                spinner.setSelection(adapter.getCount());
                break;
            }
        }
    }

    /*
    ---------- RecyclerView Codes ----------
    */

    public static void fixedHorizontalRecyclerView(Activity activity, RecyclerView recyclerView, float marginTop, float marginBottom, float marginInner, float marginSide) {
        recyclerView.addItemDecoration(new ItemDecorateRecyclerView("horizontalLayout", (int) marginTop, (int) marginBottom, (int) marginInner, (int) marginSide));
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
    }

    public static void fixedVerticalRecyclerView(Activity activity, RecyclerView recyclerView, float marginTop, float marginBottom, float marginInner, float marginSide) {
        recyclerView.addItemDecoration(new ItemDecorateRecyclerView("verticalLayout", (int) marginTop, (int) marginBottom, (int) marginInner, (int) marginSide));
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
    }

    public static void unfixedVerticalRecyclerView(Activity activity, RecyclerView recyclerView, float marginTop, float marginBottom, float marginInner, float marginSide) {
        recyclerView.addItemDecoration(new ItemDecorateRecyclerView("verticalLayout", (int) marginTop, (int) marginBottom, (int) marginInner, (int) marginSide));
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);
    }

}