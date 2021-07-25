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
import androidx.recyclerview.widget.GridLayoutManager;
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
    ---------- Spinner Normal Codes ----------
    */

    public static void normalAdapterSpinner(Activity activity, Spinner spinner, int arrayRes) {
        ArrayList<String> arrayList = new ArrayList<>();
        Collections.addAll(arrayList, activity.getResources().getStringArray(arrayRes));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, R.layout.spinner_item_background_adapter, arrayList) {

            private TextView dropdownTextView;

            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup viewGroup) {
                return super.getView(position, convertView, viewGroup);
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup viewGroup) {
                View view = LayoutInflater.from(activity).inflate(R.layout.spinner_item_dropdown_adapter, viewGroup, false);

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
                    if (spinner.getSelectedItemPosition() == position)
                        view.setBackgroundResource(R.drawable.draw_rec_solid_gray100_ripple_gray300);
                    else
                        view.setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);
                } else {
                    if (spinner.getSelectedItemPosition() == position)
                        view.setBackgroundResource(R.drawable.draw_rec_solid_gray100);
                    else
                        view.setBackgroundResource(R.drawable.draw_rec_solid_white);
                }
            }

            private void setData(int position) {
                dropdownTextView.setText(arrayList.get(position));

                if (spinner.getSelectedItemPosition() == position)
                    dropdownTextView.setTextColor(activity.getResources().getColor(R.color.Blue700));
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
    }

    public static void normal12sspSpinner(Activity activity, Spinner spinner, int arrayRes) {
        ArrayList<String> arrayList = new ArrayList<>();
        Collections.addAll(arrayList, activity.getResources().getStringArray(arrayRes));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, R.layout.spinner_item_background_12ssp, arrayList) {

            private TextView dropdownTextView;

            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup viewGroup) {
                return super.getView(position, convertView, viewGroup);
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup viewGroup) {
                View view = LayoutInflater.from(activity).inflate(R.layout.spinner_item_dropdown_12ssp, viewGroup, false);

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
                    if (spinner.getSelectedItemPosition() == position)
                        view.setBackgroundResource(R.drawable.draw_rec_solid_gray100_ripple_gray300);
                    else
                        view.setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);
                } else {
                    if (spinner.getSelectedItemPosition() == position)
                        view.setBackgroundResource(R.drawable.draw_rec_solid_gray100);
                    else
                        view.setBackgroundResource(R.drawable.draw_rec_solid_white);
                }
            }

            private void setData(int position) {
                dropdownTextView.setText(arrayList.get(position));

                if (spinner.getSelectedItemPosition() == position)
                    dropdownTextView.setTextColor(activity.getResources().getColor(R.color.Blue700));
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
    }

    public static void normal10sspSpinner(Activity activity, Spinner spinner, ArrayList<String> arrayList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, R.layout.spinner_item_background_10ssp, arrayList) {

            private TextView dropdownTextView;

            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup viewGroup) {
                return super.getView(position, convertView, viewGroup);
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup viewGroup) {
                View view = LayoutInflater.from(activity).inflate(R.layout.spinner_item_dropdown_10ssp, viewGroup, false);

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
                    if (spinner.getSelectedItemPosition() == position)
                        view.setBackgroundResource(R.drawable.draw_rec_solid_gray100_ripple_gray300);
                    else
                        view.setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);
                } else {
                    if (spinner.getSelectedItemPosition() == position)
                        view.setBackgroundResource(R.drawable.draw_rec_solid_gray100);
                    else
                        view.setBackgroundResource(R.drawable.draw_rec_solid_white);
                }
            }

            private void setData(int position) {
                dropdownTextView.setText(arrayList.get(position));

                if (spinner.getSelectedItemPosition() == position)
                    dropdownTextView.setTextColor(activity.getResources().getColor(R.color.Blue700));
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
    }

    public static void normal12sspSpinner(Activity activity, Spinner spinner, ArrayList<String> arrayList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, R.layout.spinner_item_background_12ssp, arrayList) {

            private TextView dropdownTextView;

            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup viewGroup) {
                return super.getView(position, convertView, viewGroup);
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup viewGroup) {
                View view = LayoutInflater.from(activity).inflate(R.layout.spinner_item_dropdown_12ssp, viewGroup, false);

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
                    if (spinner.getSelectedItemPosition() == position)
                        view.setBackgroundResource(R.drawable.draw_rec_solid_gray100_ripple_gray300);
                    else
                        view.setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);
                } else {
                    if (spinner.getSelectedItemPosition() == position)
                        view.setBackgroundResource(R.drawable.draw_rec_solid_gray100);
                    else
                        view.setBackgroundResource(R.drawable.draw_rec_solid_white);
                }
            }

            private void setData(int position) {
                dropdownTextView.setText(arrayList.get(position));

                if (spinner.getSelectedItemPosition() == position)
                    dropdownTextView.setTextColor(activity.getResources().getColor(R.color.Blue700));
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
    }

     /*
    ---------- Spinner Custom Codes ----------
    */

    public static void profileCustomSpinner(Activity activity, Spinner spinner, ArrayList<String> arrayList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, R.layout.spinner_item_background_empty, arrayList) {

            private TextView dropdownTextView;

            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup viewGroup) {
                return super.getView(position, convertView, viewGroup);
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup viewGroup) {
                View view = LayoutInflater.from(activity).inflate(R.layout.spinner_item_dropdown_profile, viewGroup, false);

                initializer(view);

                detector(view);

                setData(position);

                return view;
            }

            private void initializer(View view) {
                dropdownTextView = view.findViewById(R.id.dropdown_textView);
            }

            private void detector(View view) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
                    view.setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);
                else
                    view.setBackgroundResource(R.drawable.draw_rec_solid_white);
            }

            private void setData(int position) {
                dropdownTextView.setText(arrayList.get(position));
            }

            @Override
            public int getCount() {
                return super.getCount() - 1;
            }

        };

        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getCount());
    }

    public static void toolbarCustomSpinner(Activity activity, Spinner spinner, ArrayList<String> arrayList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, R.layout.spinner_item_background_empty, arrayList) {

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
                dropdownTextView = view.findViewById(R.id.dropdown_textView);
            }

            private void detector(View view, int position) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    if (arrayList.get(position).equals("خروج"))
                        view.setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_red300);
                    else
                        view.setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);
                } else {
                    view.setBackgroundResource(R.drawable.draw_rec_solid_white);
                }
            }

            private void setData(int position) {
                dropdownTextView.setText(arrayList.get(position));

                if (dropdownTextView.getText().toString().equals("خروج"))
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
    }

    public static void actionCustomSpinner(Activity activity, Spinner spinner, ArrayList<String> arrayList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, R.layout.spinner_item_background_empty, arrayList) {

            private TextView dropdownTextView;
            private ImageView dropdownImageView;

            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup viewGroup) {
                return super.getView(position, convertView, viewGroup);
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup viewGroup) {
                View view = LayoutInflater.from(activity).inflate(R.layout.spinner_item_dropdown_action, viewGroup, false);

                initializer(view);

                detector(view);

                setData(position);

                return view;
            }

            private void initializer(View view) {
                dropdownTextView = view.findViewById(R.id.dropdown_textView);
                dropdownImageView = view.findViewById(R.id.dropdown_imageView);
            }

            private void detector(View view) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
                    view.setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);
                else
                    view.setBackgroundResource(R.drawable.draw_rec_solid_white);
            }

            private void setData(int position) {
                dropdownTextView.setText(arrayList.get(position));

                switch (arrayList.get(position)) {
                    case "اعضاء":
                        dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_users_light, null));
                        break;
                    case "برنامه درمانی":
                        dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_calendar_alt_light, null));
                        break;
                    case "تعریف برنامه درمانی":
                        dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_calendar_plus_light, null));
                        break;
                    case "پروفایل من":
                        dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_user_crown_light, null));
                        break;
                    case "ویرایش":
                        dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_edit_light, null));
                        break;
                    case "ورود به کاربری":
                        dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_user_cog_light, null));
                        break;
                    case "محل برگزاری":
                        dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_map_marker_alt_light, null));
                        break;
                    case "برچسب\u200Cهای مهم":
                        dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_tags_light, null));
                        break;
                    case "پذیرفتن":
                        dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_check_light, null));
                        break;
                    case "تعلیق":
                        dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_times_light, null));
                        break;
                    case "ساختن اتاق درمان":
                        dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_plus_light, null));
                        break;
                    case "اتاق درمان":
                        dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_building_light, null));
                        break;
                    case "لینک ثبت نام":
                        dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_link_light, null));
                        break;
                    case "کپی کردن لینک":
                        dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_copy_light, null));
                        break;
                    default:
                        if (dropdownTextView.getText().toString().contains("989")) {
                            dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_mobile_light, null));
                        }

                        if (dropdownTextView.getText().toString().contains("@")) {
                            dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_envelope_light, null));
                        }

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
    }

    public static void testCustomSpinner(Activity activity, Spinner spinner, ArrayList<String> arrayList, ArrayList<Boolean> arrayList2) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, R.layout.spinner_item_background_10ssp, arrayList) {

            private TextView dropdownTextView;
            private ImageView dropdownImageView;

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
                dropdownImageView = view.findViewById(R.id.dropdown_imageView);
            }

            private void detector(View view, int position) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    if (spinner.getSelectedItemPosition() == position)
                        view.setBackgroundResource(R.drawable.draw_rec_solid_gray100_ripple_gray300);
                    else
                        view.setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);
                } else {
                    if (spinner.getSelectedItemPosition() == position)
                        view.setBackgroundResource(R.drawable.draw_rec_solid_gray100);
                    else
                        view.setBackgroundResource(R.drawable.draw_rec_solid_white);
                }
            }

            private void setData(int position) {
                dropdownTextView.setText(arrayList.get(position));

                if (spinner.getSelectedItemPosition() == position)
                    dropdownTextView.setTextColor(activity.getResources().getColor(R.color.Blue700));
                else
                    dropdownTextView.setTextColor(activity.getResources().getColor(R.color.Gray600));

                if (arrayList2.get(position))
                    dropdownImageView.setVisibility(View.VISIBLE);
                else
                    dropdownImageView.setVisibility(View.GONE);
            }

            @Override
            public int getCount() {
                return super.getCount() - 1;
            }

        };

        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getCount());
    }

    /*
    ---------- RecyclerView Codes ----------
    */

    public static void fixedGridRecyclerView(Activity activity, RecyclerView recyclerView, float marginTop, float marginBottom, float marginInner, float marginSide) {
        recyclerView.addItemDecoration(new ItemDecorateRecyclerView("gridLayout", (int) marginTop, (int) marginBottom, (int) marginInner, (int) marginSide));
        recyclerView.setLayoutManager(new GridLayoutManager(activity, 2, LinearLayoutManager.VERTICAL, false));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
    }

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