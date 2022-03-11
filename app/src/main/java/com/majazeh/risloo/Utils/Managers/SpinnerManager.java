package com.majazeh.risloo.utils.managers;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.majazeh.risloo.R;

import java.util.ArrayList;
import java.util.Collections;

public class SpinnerManager {

    // -------------------- Input AutoComplete

    public static void input12sspAutoComplete(Activity activity, AutoCompleteTextView act, ArrayList<String> arrayList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, R.layout.spinner_item_dropdown_12ssp, arrayList);
        act.setAdapter(adapter);
    }

    // -------------------- Input Spinner

    public static void input12sspSpinner(Activity activity, Spinner spinner, int arrayRes) {
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
                if (spinner.getSelectedItemPosition() == position)
                    view.setBackgroundResource(R.drawable.draw_rec_solid_coolgray100_ripple_coolgray300);
                else
                    view.setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_coolgray300);
            }

            private void setData(int position) {
                dropdownTextView.setText(arrayList.get(position));

                if (spinner.getSelectedItemPosition() == position)
                    dropdownTextView.setTextColor(activity.getResources().getColor(R.color.risloo500));
                else
                    dropdownTextView.setTextColor(activity.getResources().getColor(R.color.coolGray600));
            }

            @Override
            public int getCount() {
                return super.getCount() - 1;
            }

        };

        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getCount());
    }

    public static void input12sspSpinner(Activity activity, Spinner spinner, ArrayList<String> arrayList) {
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
                if (spinner.getSelectedItemPosition() == position)
                    view.setBackgroundResource(R.drawable.draw_rec_solid_coolgray100_ripple_coolgray300);
                else
                    view.setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_coolgray300);
            }

            private void setData(int position) {
                dropdownTextView.setText(arrayList.get(position));

                if (spinner.getSelectedItemPosition() == position)
                    dropdownTextView.setTextColor(activity.getResources().getColor(R.color.risloo500));
                else
                    dropdownTextView.setTextColor(activity.getResources().getColor(R.color.coolGray600));
            }

            @Override
            public int getCount() {
                return super.getCount() - 1;
            }

        };

        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getCount());
    }

    public static void input10sspSpinner(Activity activity, Spinner spinner, int arrayRes) {
        ArrayList<String> arrayList = new ArrayList<>();
        Collections.addAll(arrayList, activity.getResources().getStringArray(arrayRes));
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
                if (spinner.getSelectedItemPosition() == position)
                    view.setBackgroundResource(R.drawable.draw_rec_solid_coolgray100_ripple_coolgray300);
                else
                    view.setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_coolgray300);
            }

            private void setData(int position) {
                dropdownTextView.setText(arrayList.get(position));

                if (spinner.getSelectedItemPosition() == position)
                    dropdownTextView.setTextColor(activity.getResources().getColor(R.color.risloo500));
                else
                    dropdownTextView.setTextColor(activity.getResources().getColor(R.color.coolGray600));
            }

            @Override
            public int getCount() {
                return super.getCount() - 1;
            }

        };

        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getCount());
    }

    public static void input10sspSpinner(Activity activity, Spinner spinner, ArrayList<String> arrayList) {
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
                if (spinner.getSelectedItemPosition() == position)
                    view.setBackgroundResource(R.drawable.draw_rec_solid_coolgray100_ripple_coolgray300);
                else
                    view.setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_coolgray300);
            }

            private void setData(int position) {
                dropdownTextView.setText(arrayList.get(position));

                if (spinner.getSelectedItemPosition() == position)
                    dropdownTextView.setTextColor(activity.getResources().getColor(R.color.risloo500));
                else
                    dropdownTextView.setTextColor(activity.getResources().getColor(R.color.coolGray600));
            }

            @Override
            public int getCount() {
                return super.getCount() - 1;
            }

        };

        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getCount());
    }

    public static void input8sspSpinner(Activity activity, Spinner spinner, int arrayRes) {
        ArrayList<String> arrayList = new ArrayList<>();
        Collections.addAll(arrayList, activity.getResources().getStringArray(arrayRes));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, R.layout.spinner_item_background_8ssp, arrayList) {

            private TextView dropdownTextView;

            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup viewGroup) {
                return super.getView(position, convertView, viewGroup);
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup viewGroup) {
                View view = LayoutInflater.from(activity).inflate(R.layout.spinner_item_dropdown_8ssp, viewGroup, false);

                initializer(view);

                detector(view, position);

                setData(position);

                return view;
            }

            private void initializer(View view) {
                dropdownTextView = view.findViewById(R.id.dropdown_textView);
            }

            private void detector(View view, int position) {
                if (spinner.getSelectedItemPosition() == position)
                    view.setBackgroundResource(R.drawable.draw_rec_solid_coolgray100_ripple_coolgray300);
                else
                    view.setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_coolgray300);
            }

            private void setData(int position) {
                dropdownTextView.setText(arrayList.get(position));

                if (spinner.getSelectedItemPosition() == position)
                    dropdownTextView.setTextColor(activity.getResources().getColor(R.color.risloo500));
                else
                    dropdownTextView.setTextColor(activity.getResources().getColor(R.color.coolGray600));
            }

            @Override
            public int getCount() {
                return super.getCount() - 1;
            }

        };

        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getCount());
    }

    public static void input8sspSpinner(Activity activity, Spinner spinner, ArrayList<String> arrayList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, R.layout.spinner_item_background_8ssp, arrayList) {

            private TextView dropdownTextView;

            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup viewGroup) {
                return super.getView(position, convertView, viewGroup);
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup viewGroup) {
                View view = LayoutInflater.from(activity).inflate(R.layout.spinner_item_dropdown_8ssp, viewGroup, false);

                initializer(view);

                detector(view, position);

                setData(position);

                return view;
            }

            private void initializer(View view) {
                dropdownTextView = view.findViewById(R.id.dropdown_textView);
            }

            private void detector(View view, int position) {
                if (spinner.getSelectedItemPosition() == position)
                    view.setBackgroundResource(R.drawable.draw_rec_solid_coolgray100_ripple_coolgray300);
                else
                    view.setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_coolgray300);
            }

            private void setData(int position) {
                dropdownTextView.setText(arrayList.get(position));

                if (spinner.getSelectedItemPosition() == position)
                    dropdownTextView.setTextColor(activity.getResources().getColor(R.color.risloo500));
                else
                    dropdownTextView.setTextColor(activity.getResources().getColor(R.color.coolGray600));
            }

            @Override
            public int getCount() {
                return super.getCount() - 1;
            }

        };

        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getCount());
    }

    // -------------------- Input Custom Spinner

    public static void inputCustomTestSpinner(Activity activity, Spinner spinner, ArrayList<String> arrayList, ArrayList<Boolean> arrayList2) {
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
                if (spinner.getSelectedItemPosition() == position)
                    view.setBackgroundResource(R.drawable.draw_rec_solid_coolgray100_ripple_coolgray300);
                else
                    view.setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_coolgray300);
            }

            private void setData(int position) {
                dropdownTextView.setText(arrayList.get(position));

                if (spinner.getSelectedItemPosition() == position)
                    dropdownTextView.setTextColor(activity.getResources().getColor(R.color.risloo500));
                else
                    dropdownTextView.setTextColor(activity.getResources().getColor(R.color.coolGray600));

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

    public static void inputCustomTreasurySpinner(Activity activity, Spinner spinner, ArrayList<String> arrayList, ArrayList<String> arrayList2) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, R.layout.spinner_item_background_treasury, arrayList) {

            private TextView primaryTextView;
            private TextView secondaryTextView;

            private TextView dropdownPrimaryTextView;
            private TextView dropdownSecondaryTextView;

            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup viewGroup) {
                View view = LayoutInflater.from(activity).inflate(R.layout.spinner_item_background_treasury, null, false);

                initializer(view);

                setData(position);

                return view;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup viewGroup) {
                View view = LayoutInflater.from(activity).inflate(R.layout.spinner_item_dropdown_treasury, viewGroup, false);

                initializerDropDown(view);

                detectorDropDown(view, position);

                setDataDropDown(position);

                return view;
            }

            private void initializer(View view) {
                primaryTextView = view.findViewById(R.id.primary_textView);
                secondaryTextView = view.findViewById(R.id.secondary_textView);
            }

            private void initializerDropDown(View view) {
                dropdownPrimaryTextView = view.findViewById(R.id.primary_textView);
                dropdownSecondaryTextView = view.findViewById(R.id.secondary_textView);
            }

            private void detectorDropDown(View view, int position) {
                if (spinner.getSelectedItemPosition() == position)
                    view.setBackgroundResource(R.drawable.draw_rec_solid_coolgray100_ripple_coolgray300);
                else
                    view.setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_coolgray300);
            }

            private void setData(int position) {
                primaryTextView.setText(arrayList.get(position));
                secondaryTextView.setText(SpannableManager.spanForegroundColor(arrayList2.get(position), 12, arrayList2.get(position).length(), activity.getResources().getColor(R.color.emerald600)));
            }

            private void setDataDropDown(int position) {
                dropdownPrimaryTextView.setText(arrayList.get(position));
                dropdownSecondaryTextView.setText(SpannableManager.spanForegroundColor(arrayList2.get(position), 12, arrayList2.get(position).length(), activity.getResources().getColor(R.color.emerald600)));
            }

            @Override
            public int getCount() {
                return super.getCount() - 1;
            }

        };

        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getCount());
    }

    // -------------------- Select Spinner

    public static void selectBillSpinner(Activity activity, Spinner spinner, ArrayList<String> arrayList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, R.layout.spinner_item_background_empty, arrayList) {

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
                dropdownTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }

            private void detector(View view, int position) {
                if (arrayList.get(position).equals("پرداخت"))
                    view.setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_emerald300);
                else
                    view.setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_coolgray300);
            }

            private void setData(int position) {
                dropdownTextView.setText(arrayList.get(position));

                if (dropdownTextView.getText().toString().equals("پرداخت"))
                    dropdownTextView.setTextColor(activity.getResources().getColor(R.color.emerald600));
                else
                    dropdownTextView.setTextColor(activity.getResources().getColor(R.color.coolGray600));
            }

            @Override
            public int getCount() {
                return super.getCount() - 1;
            }

        };

        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getCount());
    }

    public static void selectProfileSpinner(Activity activity, Spinner spinner, ArrayList<String> arrayList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, R.layout.spinner_item_background_empty, arrayList) {

            private TextView dropdownTextView;

            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup viewGroup) {
                return super.getView(position, convertView, viewGroup);
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup viewGroup) {
                View view = LayoutInflater.from(activity).inflate(R.layout.spinner_item_dropdown_10ssp, viewGroup, false);

                initializer(view);

                detector(view);

                setData(position);

                return view;
            }

            private void initializer(View view) {
                dropdownTextView = view.findViewById(R.id.dropdown_textView);
                dropdownTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }

            private void detector(View view) {
                view.setBackgroundResource(R.drawable.draw_rec_solid_coolgray50_ripple_coolgray300);
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

    public static void selectToolbarSpinner(Activity activity, Spinner spinner, ArrayList<String> arrayList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, R.layout.spinner_item_background_empty, arrayList) {

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
                dropdownTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }

            private void detector(View view, int position) {
                if (arrayList.get(position).equals("خروج"))
                    view.setBackgroundResource(R.drawable.draw_rec_solid_coolgray50_ripple_coolred300);
                else
                    view.setBackgroundResource(R.drawable.draw_rec_solid_coolgray50_ripple_coolgray300);
            }

            private void setData(int position) {
                dropdownTextView.setText(arrayList.get(position));

                if (dropdownTextView.getText().toString().equals("خروج"))
                    dropdownTextView.setTextColor(activity.getResources().getColor(R.color.red600));
                else
                    dropdownTextView.setTextColor(activity.getResources().getColor(R.color.coolGray600));
            }

            @Override
            public int getCount() {
                return super.getCount() - 1;
            }

        };

        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getCount(), false);
    }

    // -------------------- Select Custom Spinner

    public static void selectCustomActionSpinner(Activity activity, Spinner spinner, ArrayList<String> arrayList) {
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
                view.setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_coolgray300);
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
                        dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_user_light, null));
                        break;
                    case "ویرایش":
                        dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_edit_light, null));
                        break;
                    case "ورود به کاربری":
                        dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_user_cog_light, null));
                        break;
                    case "محل برگزاری جلسات":
                        dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_map_marker_alt_light, null));
                        break;
                    case "اتاق\u200Cهای درمان":
                        dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_loveseat_light, null));
                        break;
                    case "حسابداری":
                        dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_calculator_light, null));
                        break;
                    case "برچسب\u200Cهای مهم":
                        dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_tags_light, null));
                        break;
                    case "پذیرفتن":
                    case "تأیید":
                        dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_check_light, null));
                        break;
                    case "تعلیق":
                    case "رد کردن":
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
                    case "دانلود فایل":
                        dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_arrow_to_bottom_light, null));
                        break;
                    case "دریافت پیوست":
                        dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_cloud_download_light, null));
                        break;
                    case "ارسال تکلیف":
                        dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_cloud_upload_light, null));
                        break;
                    case "گزارشات":
                        dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_clipboard_light, null));
                        break;
                    case "پیش\u200Cفاکتور و تسویه":
                        dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_file_invoice_light, null));
                        break;
                    default:
                        if (dropdownTextView.getText().toString().contains("989"))
                            dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_mobile_light, null));

                        if (dropdownTextView.getText().toString().contains("@"))
                            dropdownImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_envelope_light, null));

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

}