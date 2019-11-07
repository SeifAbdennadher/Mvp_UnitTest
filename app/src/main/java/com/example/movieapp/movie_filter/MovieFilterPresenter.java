package com.example.movieapp.movie_filter;

public class MovieFilterPresenter implements MovieFilterContract.Presenter {

    private final MovieFilterContract.View mView;

    private String mFromDate = "";
    private String mToDate = "";

    public MovieFilterPresenter(MovieFilterContract.View view) {
        this.mView = view;
    }

    @Override
    public void onFromDateSelected(String date) {
        if (date != null && !date.isEmpty()) {
            mFromDate = date;
            mView.updateFromTextView(mFromDate);
            mView.showClearAll();
        }
    }

    @Override
    public void onToDateSelected(String date) {
        if (date != null && !date.isEmpty()) {
            mToDate = date;
            mView.updateToTextView(date);
            mView.showClearAll();
        }
    }

    @Override
    public void onValidateClicked() {
        if ((mFromDate.isEmpty() && !mToDate.isEmpty()) || (!mFromDate.isEmpty() && mToDate.isEmpty())) {
            mView.showErrorSnackbar("Please select from and to dates.");
        } else {
            mView.popBackWithDates(mFromDate, mToDate);
        }
    }

    @Override
    public void onClearAllClicked() {
        mFromDate = "";
        mToDate = "";
        mView.updateFromTextView("Select From");
        mView.updateToTextView("Select To");
        mView.hideClearAll();
    }
}
