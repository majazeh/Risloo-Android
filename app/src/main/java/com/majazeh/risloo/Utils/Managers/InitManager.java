package com.majazeh.risloo.Utils.Managers;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

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

                    @Override
                    public View getView(int position, View convertView, ViewGroup viewGroup) {
                        View view = super.getView(position, convertView, viewGroup);
                        return view;
                    }

                    @Override
                    public int getCount() {
                        return super.getCount() - 1;
                    }

                };
                adapter.setDropDownViewResource(R.layout.spinner_item_dropdown_toolbar);
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