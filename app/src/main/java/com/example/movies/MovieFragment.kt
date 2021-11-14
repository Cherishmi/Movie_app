package com.example.movies

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.Adapter.MovieAdapter


class MovieFragment : Fragment() {
    private lateinit var popularMovies: RecyclerView
    private lateinit var popularMoviesAdapter: MovieAdapter
    private lateinit var popularMoviesLayoutMgr: LinearLayoutManager

    private var popularMoviesPage = 1

    private lateinit var topRatedMovies: RecyclerView
    private lateinit var topRatedMoviesAdapter: MovieAdapter
    private lateinit var topRatedMoviesLayoutMgr: LinearLayoutManager

    private var topRatedMoviesPage = 1

    private lateinit var trendingMovies: RecyclerView
    private lateinit var trendingMoviesAdapter: MovieAdapter
    private lateinit var trendingMoviesLayoutMgr: LinearLayoutManager

    private var trendingMoviesPage = 1



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_movie, container, false)

        popularMovies = view.findViewById(R.id.popular_movies)
        popularMoviesLayoutMgr = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        popularMovies.layoutManager = popularMoviesLayoutMgr
        popularMoviesAdapter = MovieAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        popularMovies.adapter = popularMoviesAdapter

        topRatedMovies = view.findViewById(R.id.top_rated_movies)
        topRatedMoviesLayoutMgr = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        topRatedMovies.layoutManager = topRatedMoviesLayoutMgr
        topRatedMoviesAdapter = MovieAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        topRatedMovies.adapter = topRatedMoviesAdapter

        trendingMovies = view.findViewById(R.id.trending_movies)
        trendingMoviesLayoutMgr = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        trendingMovies.layoutManager = trendingMoviesLayoutMgr
        trendingMoviesAdapter = MovieAdapter(mutableListOf()){ movie -> showMovieDetails(movie) }
        trendingMovies.adapter = trendingMoviesAdapter

        getPopularMovies()
        getTopRatedMovies()
        getTrendingMovies()

        return view



    }

    //Popular movies--------------------------------------------

    private fun getPopularMovies() {
        MovieRepo.getPopularMovies(
            popularMoviesPage,
            ::onPopularMoviesFetched,
            ::onError
        )
    }
    private fun onPopularMoviesFetched(movies: List<Movie>) {
        popularMoviesAdapter.appendMovie(movies)
        attachPopularMoviesOnScrollListener()
    }

    private fun onError() {
        Toast.makeText(activity, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }
    private fun attachPopularMoviesOnScrollListener() {
        popularMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = popularMoviesLayoutMgr.itemCount
                val visibleItemCount = popularMoviesLayoutMgr.childCount
                val firstVisibleItem = popularMoviesLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    popularMovies.removeOnScrollListener(this)
                    popularMoviesPage++
                    getPopularMovies()
                }
            }
        })
    }

    //Top rated movies-----------------------------------------------------------------------

    private fun getTopRatedMovies() {
        MovieRepo.getTopRatedMovies(
            topRatedMoviesPage,
            ::onTopRatedMoviesFetched,
            ::onError
        )
    }

    private fun attachTopRatedMoviesOnScrollListener() {
        topRatedMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = topRatedMoviesLayoutMgr.itemCount
                val visibleItemCount = topRatedMoviesLayoutMgr.childCount
                val firstVisibleItem = topRatedMoviesLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    topRatedMovies.removeOnScrollListener(this)
                    topRatedMoviesPage++
                    getTopRatedMovies()
                }
            }
        })
    }

    private fun onTopRatedMoviesFetched(movies: List<Movie>) {
        topRatedMoviesAdapter.appendMovie(movies)
        attachTopRatedMoviesOnScrollListener()
    }

    //Trending movies---------------------------------------------------

    private fun getTrendingMovies() {
        MovieRepo.getTrendingMovies(
            trendingMoviesPage,
            ::onTrendingMoviesFetched,
            ::onError
        )
    }

    private fun attachTrendingdMoviesOnScrollListener() {
        trendingMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = trendingMoviesLayoutMgr.itemCount
                val visibleItemCount = trendingMoviesLayoutMgr.childCount
                val firstVisibleItem = trendingMoviesLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    trendingMovies.removeOnScrollListener(this)
                    trendingMoviesPage++
                    getTrendingMovies()
                }
            }
        })
    }

    private fun onTrendingMoviesFetched(movies: List<Movie>) {
        trendingMoviesAdapter.appendMovie(movies)
        attachTrendingdMoviesOnScrollListener()
    }


    private fun showMovieDetails(movie: Movie) {
        val intent = Intent(activity, MovieDetails::class.java)
        intent.putExtra(MOVIE_ID, movie.id)
        intent.putExtra(MOVIE_BACKDROP, movie.backdropPath)
        intent.putExtra(MOVIE_POSTER, movie.posterPath)
        intent.putExtra(MOVIE_TITLE, movie.title)
        intent.putExtra(MOVIE_RATING, movie.rating)
        intent.putExtra(MOVIE_RELEASE_DATE, movie.releaseDate)
        intent.putExtra(MOVIE_OVERVIEW, movie.overview)
        startActivity(intent)
    }



}