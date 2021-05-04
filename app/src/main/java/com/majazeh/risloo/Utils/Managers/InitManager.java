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
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;

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

    public static void spinner(Activity activity, Spinner spinner, int arrayRes, String dimension) {
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
            case "toolbar": {
                ArrayList<String> list = new ArrayList<>();
                Collections.addAll(list, activity.getResources().getStringArray(arrayRes));
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, R.layout.spinner_item_background_toolbar, list) {

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
                            if (position == 4) {
                                view.setBackgroundResource(R.drawable.draw_rec_solid_gray50_ripple_red300);
                            } else {
                                view.setBackgroundResource(R.drawable.draw_rec_solid_gray50_ripple_gray300);
                            }
                        }
                    }

                    private void setData(int position) {
                        dropdownTextView.setText(list.get(position));

                        if (position == 4) {
                            dropdownTextView.setTextColor(activity.getResources().getColor(R.color.Red500));
                        } else {
                            dropdownTextView.setTextColor(activity.getResources().getColor(R.color.Gray600));
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

    public static void recyclerView(RecyclerView recyclerView, RecyclerView.ItemDecoration itemDecoration, LinearLayoutManager layoutManager) {
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
    }

    public static void unfixedRecyclerView(RecyclerView recyclerView, RecyclerView.ItemDecoration itemDecoration, LinearLayoutManager layoutManager) {
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);
    }

}