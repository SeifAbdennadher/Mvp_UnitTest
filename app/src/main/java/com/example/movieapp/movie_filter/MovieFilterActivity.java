/**
 * @file MovieFilterActivity.java
 * @brief This activity is responsible for handling the filter functionality for Movies.
 * @author Shrikant
 * @date 16/04/2018
 */

package com.example.movieapp.movie_filter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.movieapp.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import static com.example.movieapp.utils.Constants.KEY_RELEASE_FROM;
import static com.example.movieapp.utils.Constants.KEY_RELEASE_TO;

public class MovieFilterActivity extends AppCompatActivity implements MovieFilterContract.View {


    private TextView tvFromReleaseDate;
    private TextView tvToReleaseDate;
    private TextView tvClerAll;

    private RelativeLayout rlMainLayout;

    private MovieFilterContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_filter);
        mPresenter = new MovieFilterPresenter(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initUI();
        setListeners();

        Intent mIntent = getIntent();
        mPresenter.onFromDateSelected(mIntent.getStringExtra(KEY_RELEASE_FROM));
        mPresenter.onToDateSelected(mIntent.getStringExtra(KEY_RELEASE_TO));
    }

    /**
     * This method will intialize the UI components
     */
    private void initUI() {

        tvFromReleaseDate = findViewById(R.id.tv_from_date);
        tvToReleaseDate = findViewById(R.id.tv_to_date);
        tvClerAll = findViewById(R.id.tv_clear_all);
        rlMainLayout = findViewById(R.id.rl_main_layout);
    }

    /**
     * This method will handle the listeners for the UI components
     */
    private void setListeners() {

        tvFromReleaseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                                String date = year + "-" + String.format("%02d", (monthOfYear + 1)) + "-" + String.format("%02d", dayOfMonth);
                                mPresenter.onFromDateSelected(date);
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");

            }
        });


        tvToReleaseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                String date = year + "-" + String.format("%02d", (monthOfYear + 1)) + "-" + String.format("%02d", dayOfMonth);
                                mPresenter.onToDateSelected(date);
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        tvClerAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onClearAllClicked();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.filter_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_done:
                mPresenter.onValidateClicked();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void showClearAll() {
        tvClerAll.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideClearAll() {
        tvClerAll.setVisibility(View.GONE);
    }


    @Override
    public void updateFromTextView(String date) {
        tvFromReleaseDate.setText(date);
    }

    @Override
    public void updateToTextView(String date) {
        tvToReleaseDate.setText(date);
    }

    @Override
    public void showErrorSnackbar(String message) {
        Snackbar snackbar = Snackbar.make(rlMainLayout, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void popBackWithDates(String fromDate, String toDate) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(KEY_RELEASE_FROM, fromDate);
        returnIntent.putExtra(KEY_RELEASE_TO, toDate);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
