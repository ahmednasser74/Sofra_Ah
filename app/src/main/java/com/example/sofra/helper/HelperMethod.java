package com.example.sofra.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.sofra.R;
import com.example.sofra.ui.activity.BaseActivity;
import com.example.sofra.ui.activity.SplashCycleActivity;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.album.AlbumFile;

import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class HelperMethod {

    private static ProgressDialog checkDialog;
    public static AlertDialog alertDialog;
    private String path;
    BaseActivity baseActivity;

    public static void replace(Fragment fragment, FragmentManager supportFragmentManager, int id, TextView Tool_Bar_Title, String title) {
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        transaction.replace(id, fragment);
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
        if (Tool_Bar_Title != null) {
            Tool_Bar_Title.setText(title);
        }
    }

    public static void replaceFragmentWithAnimation(FragmentManager getChildFragmentManager, int id, Fragment fragment, String fromWhere) {
        FragmentTransaction transaction = getChildFragmentManager.beginTransaction();

        if (fromWhere == "l") {
//            android.R.anim.slide_in_left
            transaction.setCustomAnimations(R.anim.slide_in_left,
                    R.anim.slide_out_right);
        }
        if (fromWhere == "r") {
            transaction.setCustomAnimations(R.anim.enter_from_right,
                    R.anim.exit_to_left);
        }
        if (fromWhere == "t") {
            transaction.setCustomAnimations(R.anim.slide_out_down,
                    R.anim.slide_in_down);
        }
        if (fromWhere == "b") {
            transaction.setCustomAnimations(R.anim.slide_in_up,
                    R.anim.slide_out_up);
        }
//        if(fromWhere=="rr"){
//            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.slide_in_left, R.anim.slide_out_right);}
//        if(fromWhere=="t"){
//            transaction.setCustomAnimations(R.anim.slide_in_down, R.anim.slide_out_up);}
//        if(fromWhere=="b"){
//            transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_down);}
        transaction.replace(id, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
  public void dialogLogOut() {
        Dialog dialog = new Dialog(baseActivity);
        LayoutInflater inflater = (LayoutInflater) baseActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.dialog_sign_out, null);
        dialog.setContentView(v);

        Button button = (Button) dialog.findViewById(R.id.item_sign_out_dialog_btn_yes);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(baseActivity, SplashCycleActivity.class);
                baseActivity.startActivity(intent);
                baseActivity.finish();
                Toast.makeText(baseActivity, "You Have LogOut", Toast.LENGTH_SHORT).show();
            }
        });}
        
    public static void initImage(Activity activity, Action<ArrayList<AlbumFile>> action) {
        Album.initialize(AlbumConfig.newBuilder(activity)
                .setAlbumLoader(new MediaLoader())
                .build());
        Album.image(activity) // Image selection.
                .singleChoice()
                .camera(true)
                .columnCount(3)
                .onResult(action)
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                    }
                })
                .start();
    }

    public static void showCalender(Context context, String title, final TextView text_view_data, final DateTxt data1) {
        DatePickerDialog mDatePicker = new DatePickerDialog(context, AlertDialog.THEME_HOLO_DARK, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {
                DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
                DecimalFormat mFormat = new DecimalFormat("00", symbols);
                String data = selectedYear + "-" + mFormat.format(Double.valueOf((selectedMonth + 1))) + "-" + mFormat.format(Double.valueOf(selectedDay));
                data1.setDate_txt(data);
                data1.setDay(mFormat.format(Double.valueOf(selectedDay)));
                data1.setMonth(mFormat.format(Double.valueOf(selectedMonth + 1)));
                data1.setYear(String.valueOf(selectedYear));
                text_view_data.setText(data);
            }
        }, Integer.parseInt(data1.getYear()), Integer.parseInt(data1.getMonth()) - 1, Integer.parseInt(data1.getDay()));
        mDatePicker.setTitle(title);
        mDatePicker.show();
    }

    public static void disappearKeypad(Activity activity, View v) {
        try {
            if (v != null) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        } catch (Exception e) {
        }
    }

    public static void showProgressDialog(Activity activity, String title) {
        try {
            checkDialog = new ProgressDialog(activity);
            checkDialog.setMessage(String.valueOf(title));
            checkDialog.show();
            checkDialog.setContentView(R.layout.dialog_progress);
            Objects.requireNonNull(checkDialog.getWindow()).setBackgroundDrawableResource(R.color.albumTransparent);
            checkDialog.setIndeterminate(false);
            checkDialog.setCancelable(false);
        } catch (Exception e) {
        }
    }

    public static void onLoadImageFromUrl(ImageView imageView, String URl, Context context) {
        Glide.with(context)
                .load(URl)
                .into(imageView);
    }

    public static void onLoadImageFromUrl(CircleImageView circleImageView, String URl, Context context) {
        Glide.with(context)
                .load(URl)
                .into(circleImageView);
    }


    public static void dismissProgressDialog() {
        try {
            checkDialog.dismiss();

        } catch (Exception e) {

        }
    }

    public static MultipartBody.Part convertFileToMultipart(String pathImageFile, String Key) {
        if (pathImageFile != null) {
            File file = new File(pathImageFile);
            RequestBody reqFileselect = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part Imagebody = MultipartBody.Part.createFormData(Key, file.getName(), reqFileselect);
            return Imagebody;
        } else {
            return null;
        }
    }

    public static RequestBody convertToRequestBody(String part) {
        try {
            if (!part.equals("")) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), part);
                return requestBody;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

}

/**
 * public class HelperMethod {
 * private static ProgressDialog checkDialog;
 * public static AlertDialog alertDialog;
 * ​
 * public static void replaceFragment(FragmentManager fragmentManager, int id, Fragment fragment) {
 * FragmentTransaction transaction = fragmentManager.beginTransaction();
 * transaction.replace(id, fragment);
 * transaction.addToBackStack(null);
 * transaction.commit();
 * }
 * ​
 * public static void showCalender(Context context, String title, final TextView text_view_data, final DateTxt data1) {
 * DatePickerDialog mDatePicker = new DatePickerDialog(context, AlertDialog.THEME_HOLO_DARK, new DatePickerDialog.OnDateSetListener() {
 * public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {
 * DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
 * DecimalFormat mFormat = new DecimalFormat("00", symbols);
 * String data = selectedYear + "-" + mFormat.format(Double.valueOf((selectedMonth + 1))) + "-" + mFormat.format(Double.valueOf(selectedDay));
 * data1.setDate_txt(data);
 * data1.setDay(mFormat.format(Double.valueOf(selectedDay)));
 * data1.setMonth(mFormat.format(Double.valueOf(selectedMonth + 1)));
 * data1.setYear(String.valueOf(selectedYear));
 * text_view_data.setText(data);
 * }
 * }, Integer.parseInt(data1.getYear()), Integer.parseInt(data1.getMonth()) - 1, Integer.parseInt(data1.getDay()));
 * mDatePicker.setTitle(title);
 * mDatePicker.show();
 * }
 * ​
 * public static Date convertDateString(String date) {
 * try {
 * SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
 * ​
 * Date parse = format.parse(date);
 * ​
 * return parse;
 * ​
 * } catch (ParseException e) {
 * e.printStackTrace();
 * return null;
 * }
 * }
 * ​
 * public static DateTxt convertStringToDateTxtModel(String date) {
 * try {
 * Date date1 = convertDateString(date);
 * String day = (String) DateFormat.format("dd", date1); // 20
 * String monthNumber = (String) DateFormat.format("MM", date1); // 06
 * String year = (String) DateFormat.format("yyyy", date1); // 2013
 * ​
 * return new DateTxt(day, monthNumber, year, date);
 * ​
 * } catch (Exception e) {
 * return null;
 * }
 * }
 * //
 * public static void onLoadImageFromUrl(ImageView imageView, String URl, Context context) {
 * Glide.with(context)
 * .load(URl)
 * .into(imageView);
 * }
 * ​
 * public static void showProgressDialog(Activity activity, String title) {
 * try {
 * ​
 * checkDialog = new ProgressDialog(activity);
 * checkDialog.setMessage(title);
 * checkDialog.setIndeterminate(false);
 * checkDialog.setCancelable(false);
 * ​
 * checkDialog.show();
 * ​
 * } catch (Exception e) {
 * ​
 * }
 * }
 * ​
 * public static void dismissProgressDialog() {
 * try {
 * ​
 * checkDialog.dismiss();
 * ​
 * } catch (Exception e) {
 * ​
 * }
 * }
 * ​
 * public static void disappearKeypad(Activity activity, View v) {
 * try {
 * if (v != null) {
 * InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
 * imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
 * }
 * } catch (Exception e) {
 * ​
 * }
 * }
 * ​
 * public static void changeLang(Context context, String lang) {
 * Resources res = context.getResources();
 * // Change locale settings in the app.
 * DisplayMetrics dm = res.getDisplayMetrics();
 * android.content.res.Configuration conf = res.getConfiguration();
 * conf.setLocale(new Locale(lang)); // API 17+ only.
 * // Use conf.locale = new Locale(...) if targeting lower versions
 * res.updateConfiguration(conf, dm);
 * }
 * ​
 * public static void htmlReader(TextView textView, String s) {
 * if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
 * textView.setText(Html.fromHtml(s, Html.FROM_HTML_MODE_COMPACT));
 * } else {
 * textView.setText(Html.fromHtml(s));
 * }
 * }
 * ​
 * public static void onPermission(Activity activity) {
 * String[] perms = {
 * Manifest.permission.ACCESS_FINE_LOCATION,
 * Manifest.permission.READ_CONTACTS,
 * Manifest.permission.READ_EXTERNAL_STORAGE,
 * Manifest.permission.WRITE_EXTERNAL_STORAGE,
 * Manifest.permission.READ_PHONE_STATE,
 * Manifest.permission.CALL_PHONE};
 * ​
 * ActivityCompat.requestPermissions(activity,
 * perms,
 * 100);
 * ​
 * }
 * ​
 * }
 */