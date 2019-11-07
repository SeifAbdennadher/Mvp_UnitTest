package com.example.movieapp.movie_list;

import com.example.movieapp.model.Movie;

import junit.framework.TestCase;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;

public class MovieListPresenterTest extends TestCase {

    @Mock
    private MovieListContract.View mView;
    @Mock
    private MovieListContract.Model mModel;

    @Captor
    private ArgumentCaptor<MovieListContract.Model.OnFinishedListener> mCallbackArgumentCaptor;

    private MovieListContract.Presenter mPresenter;

    public void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);
        mPresenter = new MovieListPresenter(mView);
        mPresenter.setMovieListModel(mModel);
    }

    // testGetMoreData Success

    public void testGetMoreData_Invalid_Success() {
        mPresenter.getMoreData(anyInt());
        final List<Movie> movies = mockMovieList();
        verify(mModel).getMovieList(mCallbackArgumentCaptor.capture(), anyInt());
        verify(mView).showProgress();
        mCallbackArgumentCaptor.getValue().onFinished(movies);
        verify(mView).hideProgress();
        verify(mView).onResponseFailure(new TimeoutException());
    }

    public void testGetMoreData_Valid_Success() {
        mPresenter.getMoreData(anyInt());
        final List<Movie> movies = mockMovieList();
        verify(mModel).getMovieList(mCallbackArgumentCaptor.capture(), anyInt());
        verify(mView).showProgress();
        mCallbackArgumentCaptor.getValue().onFinished(movies);
        verify(mView).hideProgress();
        verify(mView).setDataToRecyclerView(movies);
    }

    // testGetMoreData Failure

    public void testGetMoreData_Invalid_Failure() {
        mPresenter.getMoreData(1000);
        verify(mModel).getMovieList(mCallbackArgumentCaptor.capture(), anyInt());
        verify(mView).showProgress();
        TimeoutException timeoutException = new TimeoutException();
        mCallbackArgumentCaptor.getValue().onFailure(timeoutException);
        verify(mView).hideProgress();
        verify(mView).setDataToRecyclerView(mockMovieList());
    }

    public void testGetMoreData_Valid_Failure() {
        mPresenter.getMoreData(1000);
        verify(mModel).getMovieList(mCallbackArgumentCaptor.capture(), anyInt());
        verify(mView).showProgress();
        TimeoutException timeoutException = new TimeoutException();
        mCallbackArgumentCaptor.getValue().onFailure(timeoutException);
        verify(mView).hideProgress();
        verify(mView).onResponseFailure(timeoutException);
    }

    private List<Movie> mockMovieList() {
        return Arrays.asList(
                new Movie(475557, "Joker", "2019-10-02", 8.5f, "/udDclJoHjfjb8Ekgsd4FDteOkCU.jpg", "During the 1980s, a failed stand-up comedian is driven insane.", "/n6bUvigpRFqSwmPp1m2YADdbRBc.jpg", null, null, null, null),
                new Movie(290859, "Terminator", "2019-11-01", 6.6f, "/vqzNJRH4YyquRiWxCCOH0aXggHI.jpg", "More than two decades have passed since Sarah Connor prevented Judgment Day.", "/rtf4vjjLZLalpOzDUi0Qd2GTUqq.jpg", null, null, null, null),
                new Movie(420809, "Maleficent: Mistress of Evil", "2019-10-18", 7.2f, "/tBuabjEqxzoUBHfbyNbd8ulgy5j.jpg", "Maleficent and her goddaughter Aurora begin to question the complex family.", "/skvI4rYFrKXS73BJxWGH54Omlvv.jpg", null, null, null, null),
                new Movie(568012, "One Piece: Stampede", "2019-10-24", 7.5f, "/4E2lyUGLEr3yH4q6kJxPkQUhX7n.jpg", "One Piece: Stampede is a stand-alone film that celebrates the anime's 20th Anniversary.", "/iGnCzXEx0cFlUbpyAMeHwHWhPhx.jpg", null, null, null, null),
                new Movie(384018, "Fast & Furious Presents: Hobbs & Shaw", "2019-08-02", 6.5f, "/kvpNZAQow5es1tSY6XW2jAZuPPG.jpg", "Ever since US Diplomatic Security Service Agent Hobbs and lawless outcast Shaw first faced off.", "/qAhedRxRYWZAgZ8O8pHIl6QHdD7.jpg", null, null, null, null)
        );
    }
}