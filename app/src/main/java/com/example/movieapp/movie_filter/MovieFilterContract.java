package com.example.movieapp.movie_filter;

public interface MovieFilterContract {

    interface View {
        void showClearAll();

        void hideClearAll();

        void updateFromTextView(String date);

        void updateToTextView(String date);

        void showErrorSnackbar(String message);

        void popBackWithDates(String fromDate, String toDate);
    }

    interface Presenter {
        void onFromDateSelected(String date);

        void onToDateSelected(String date);

        void onValidateClicked();

        void onClearAllClicked();
    }

}
