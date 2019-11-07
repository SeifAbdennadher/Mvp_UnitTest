package com.example.movieapp.movie_filter;

import junit.framework.TestCase;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class MovieFilterPresenterTest extends TestCase {

    @Mock
    private MovieFilterContract.View mView;

    private MovieFilterContract.Presenter mPresenter;

    public void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);

        mPresenter = new MovieFilterPresenter(mView);
    }

    public void testOnValidateClicked_Invalid() {
        mPresenter.onFromDateSelected("2019-11-08");
        mPresenter.onToDateSelected("2019-11-21");
        mPresenter.onValidateClicked();

        verify(mView).showErrorSnackbar("Please select from and to dates.");
    }

    public void testOnValidateClicked_Valid() {
        mPresenter.onFromDateSelected("2019-11-08");
        mPresenter.onValidateClicked();

        verify(mView).showErrorSnackbar("Please select from and to dates.");
    }
}