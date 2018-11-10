package org.michaelbel.moviemade.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.snackbar.Snackbar;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.extensions.AndroidExtensions;
import org.michaelbel.moviemade.mvp.presenter.MoviePresenter;
import org.michaelbel.moviemade.mvp.view.MvpMovieView;
import org.michaelbel.moviemade.ui.activity.MovieActivity;
import org.michaelbel.moviemade.ui.view.EmptyView;
import org.michaelbel.moviemade.ui.view.RatingView;
import org.michaelbel.moxy.android.MvpAppCompatFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.VISIBLE;

@SuppressWarnings("all")
public class MovieFragment extends MvpAppCompatFragment implements MvpMovieView, View.OnClickListener {

    private boolean connectionError;
    private MovieActivity activity;
    private NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();

    private View view;

    @InjectPresenter
    public MoviePresenter presenter;

    @BindView(R.id.progress_bar)
    public ProgressBar progressBar;

    @BindView(R.id.empty_view)
    public EmptyView emptyView;

    @BindView(R.id.poster_image)
    public ImageView posterImage;

    @BindView(R.id.info_layout)
    public LinearLayout infoLayout;

    @BindView(R.id.rating_view)
    public RatingView ratingView;

    @BindView(R.id.rating_text)
    public TextView ratingText;

    @BindView(R.id.vote_count_text)
    public AppCompatTextView voteCountText;

    @BindView(R.id.date_layout)
    public LinearLayout releaseDateLayout;

    @BindView(R.id.release_date_icon)
    public ImageView releaseDateIcon;

    @BindView(R.id.release_date_text)
    public TextView releaseDateText;

    @BindView(R.id.runtime_icon)
    public ImageView runtimeIcon;

    @BindView(R.id.runtime_text)
    public TextView runtimeText;

    @BindView(R.id.lang_layout)
    public LinearLayout langLayout;

    @BindView(R.id.lang_icon)
    public ImageView langIcon;

    @BindView(R.id.lang_text)
    public TextView langText;

    @BindView(R.id.title_layout)
    public LinearLayout titleLayout;

    @BindView(R.id.title_text)
    public TextView titleText;

    @BindView(R.id.tagline_text)
    public TextView taglineText;

    @BindView(R.id.overview_text)
    public TextView overviewText;

    @BindView(R.id.watchlist_layout)
    public LinearLayout watchLayout;

    @BindView(R.id.watchlist_icon)
    public ImageView watchIcon;

    @BindView(R.id.watchlist_text)
    public AppCompatTextView watchText;

    @BindView(R.id.trailers_layout)
    public FrameLayout trailersLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MovieActivity) getActivity();
        activity.registerReceiver(networkChangeReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle args) {
        view = inflater.inflate(R.layout.fragment_movie, container, false);
        ButterKnife.bind(this, view);
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        runtimeText.setText(R.string.loading_status);
        runtimeIcon.setImageDrawable(AndroidExtensions.getIcon(activity, R.drawable.ic_clock, ContextCompat.getColor(activity, R.color.iconActive)));

        taglineText.setText(R.string.loading_tagline);

        trailersLayout.setOnClickListener(this);
        watchLayout.setOnClickListener(this);

        emptyView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activity.unregisterReceiver(networkChangeReceiver);
    }

    @Override
    public void setPoster(RequestOptions options, String posterPath) {
        Glide.with(activity).asBitmap()
             .load(posterPath)
             .apply(options)
             .into(new BitmapImageViewTarget(posterImage) {
                @Override
                public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                    super.onResourceReady(bitmap, transition);
                    Palette.from(bitmap).generate(palette -> posterImage.setBackgroundColor(ContextCompat.getColor(activity, R.color.primary)));
                }
            });
        posterImage.setVisibility(VISIBLE);
    }

    @Override
    public void setMovieTitle(String title) {
        titleText.setText(title);
    }

    @Override
    public void setOverview(String overview) {
        if (TextUtils.isEmpty(overview)) {
            overviewText.setText(R.string.no_overview);
            return;
        }

        overviewText.setText(overview);
    }

    @Override
    public void setVoteAverage(float voteAverage) {
        ratingView.setRating(voteAverage);
        ratingText.setText(String.valueOf(voteAverage));
    }

    @Override
    public void setVoteCount(int voteCount) {
        voteCountText.setText(String.valueOf(voteCount));
    }

    @Override
    public void setReleaseDate(String releaseDate) {
        if (TextUtils.isEmpty(releaseDate)) {
            infoLayout.removeView(releaseDateLayout);
            return;
        }

        releaseDateIcon.setImageDrawable(AndroidExtensions.getIcon(activity, R.drawable.ic_calendar, ContextCompat.getColor(activity, R.color.iconActive)));
        releaseDateText.setText(releaseDate);
    }

    @Override
    public void setOriginalLanguage(String originalLanguage) {
        if (TextUtils.isEmpty(originalLanguage)) {
            infoLayout.removeView(langLayout);
            return;
        }

        langIcon.setImageDrawable(AndroidExtensions.getIcon(activity, R.drawable.ic_earth, ContextCompat.getColor(activity, R.color.iconActive)));
        langText.setText(originalLanguage);
    }

    @Override
    public void setRuntime(String runtime) {
        if (runtime == null) {
            runtimeText.setText(R.string.unknown);
            return;
        }

        runtimeText.setText(runtime);
    }

    @Override
    public void setTagline(String tagline) {
        if (tagline == null || TextUtils.isEmpty(tagline)) {
            titleLayout.removeView(taglineText);
            return;
        }

        taglineText.setText(tagline);
    }

    @Override
    public void setWatching(boolean watch) {

    }

    @Override
    public void showConnectionError() {
        Snackbar.make(view, R.string.no_connection, Snackbar.LENGTH_SHORT).show();
        connectionError = true;
    }

    @Override
    public void showComplete() {
        connectionError = false;
    }

    @Override
    public void onClick(View v) {
        if (v == trailersLayout) {
            activity.startTrailers(activity.movie);
        } else if (v == watchLayout) {

        }
    }

    private class NetworkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;

            if (networkInfo != null && networkInfo.isConnected()) {
                if (connectionError) {
                    presenter.loadMovieDetails(activity.movie.id);
                }
            }
        }
    }

    /*@Override
    public void showMovie(Movie movie) {
        //buttonsLayout.setVisibility(VISIBLE);

        movieView.favoriteButtonVisibility(loaded ? View.VISIBLE : View.INVISIBLE);
        movieView.watchingButtonVisibility(loaded ? View.VISIBLE : View.INVISIBLE);
        movieView.setFavoriteButton();
        movieView.setWatchingButton();
        movieView.topLayoutLoaded();
        movieView.addStatus(movie.status);
        movieView.addBudget(movie.budget);
        movieView.addRevenue(movie.revenue);
        movieView.addCountries(AndroidUtils.formatCountries(movie.countries));
        if (movieView.getCompanies().isEmpty()) {
            movieView.addCompanies(movie.companies);
        }

        movieView.addGenres(movie.genres);

        movieView.addImdbpage(movie.imdbId);
        movieView.addHomepage(movie.homepage);
        movieView.addCollection(movie.belongsToCollection);*//*

        //presenter.loadCredits(movie.id);
        //presenter.loadTrailers(movie.id);
        //presenter.loadImages(movie.id);
        //presenter.loadKeywords(movie.id);

        //genres.clear();
        //genres.addAll(movie.genres);
        //movieView.getGenresView().setClickable(true);
    }

    @Override
    public void onWatchingButtonClick(View view) {
        *//*if (loadedMovie != null) {
            presenter.setMovieWatching(loadedMovie);
        } else if (extraMovieRealm != null) {
            presenter.setMovieWatching(extraMovieRealm);
        }*//*
    }

    @Override
    public void watchingButtonState(boolean state) {
        *//*watchButton.setChecked(state);*//*
    }

    @Override
    public void onMovieUrlClick(View view, int position) {
        *//*if (extraMovieRealm != null) {
            if (position == 1) {
                Browser.openUrl(activity, String.format(Locale.US, Url.TMDB_MOVIE, extraMovieRealm.id));
            } else if (position == 2) {
                Browser.openUrl(activity, String.format(Locale.US, Url.IMDB_MOVIE, extraMovieRealm.imdbId));
            } else if (position == 3) {
                Browser.openUrl(activity, extraMovieRealm.homepage);
            }
        } else if (position == 1) {
            Browser.openUrl(activity, String.format(Locale.US, Url.TMDB_MOVIE, extraMovie.id));
        } else if (position == 2) {
            Browser.openUrl(activity, String.format(Locale.US, Url.IMDB_MOVIE, loadedMovie.imdbId));
        } else if (position == 3) {
            Browser.openUrl(activity, loadedMovie.homepage);
        }*//*
    }

    @Override
    public void onPostersClick(View view) {
        *//*if (extraMovie != null) {
            Browser.openUrl(activity, String.format(Locale.US, Url.TMDB_MOVIE_POSTERS, extraMovie.id));
        } else if (extraMovieRealm != null) {
            Browser.openUrl(activity, String.format(Locale.US, Url.TMDB_MOVIE_POSTERS, extraMovieRealm.id));
        }*//*
    }

    @Override
    public void onBackdropsClick(View view) {
        *//*if (extraMovie != null) {
            Browser.openUrl(activity, String.format(Locale.US, Url.TMDB_MOVIE_BACKDROPS, extraMovie.id));
        } else if (extraMovieRealm != null) {
            Browser.openUrl(activity, String.format(Locale.US, Url.TMDB_MOVIE_BACKDROPS, extraMovieRealm.id));
        }*//*
    }

    @Override
    public void onPosterClick(View view) {
        *//*imageAnimator.enterSingle(true);*//*
    }*/
}