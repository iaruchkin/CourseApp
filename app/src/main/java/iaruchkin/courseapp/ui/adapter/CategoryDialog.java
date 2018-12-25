package iaruchkin.courseapp.ui.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import iaruchkin.courseapp.data.NewsCategory;

public class CategoryDialog extends DialogFragment {

    private NewsCategory selectedCategory = NewsCategory.HOME;

    String items[] = NewsCategory.names();

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity())
                .setTitle("Choose category")
                .setItems(items, (dialog, item) -> {
                    selectedCategory = NewsCategory.valueOf(items[item]);
                    Log.d("Alert Dialog",selectedCategory.serverValue());
                });
        return adb.create();
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.d("LOG", "Dialog 2: onCancel");
    }

    @NonNull
    public NewsCategory getSelectedCategory() {
        return selectedCategory;
    }
}

//    public void setOnCategorySelectedListener(@Nullable OnCategorySelectedListener categorySelectedListener,
//                                              @NonNull Spinner spinner) {
//        this.categorySelectedListener = categorySelectedListener;
//        if (categorySelectedListener == null) {
//            spinner.setOnItemSelectedListener(null);
//            return;
//        }
//
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                final NewsCategory item = getItem(position);
//                CategoriesSpinnerAdapter.this.selectedCategory = item;
//                categorySelectedListener.onSelected(item);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }
//
//    public interface OnCategorySelectedListener {
//        void onSelected(@NonNull NewsCategory category);
//    }
//}