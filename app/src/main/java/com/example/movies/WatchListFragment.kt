package com.example.movies


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.movies.Adapter.MovieAdapter
import com.example.movies.Adapter.WatchListAdapter


class WatchListFragment : Fragment() {

    private val db: AppDatabase by lazy {
        Room.databaseBuilder(
            requireActivity().applicationContext,
            AppDatabase::class.java,
            "mymovies.db"
        ).allowMainThreadQueries().build()
    }


    private lateinit var watchList: RecyclerView
    private lateinit var watchListAdapter: WatchListAdapter




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_watch_list, container, false)
        watchList = view.findViewById(R.id.watchlist)
        watchList.setHasFixedSize(true)
        watchList.layoutManager = LinearLayoutManager(context )
        watchListAdapter = WatchListAdapter(listOf())


        watchList.adapter = watchListAdapter


        getWatchList()


        return view
    }




    private fun getWatchList() {
        val movies = db.movieDao.getAll()
        val watchList = mutableListOf<WatchList>()
        watchList.addAll(
            movies.map { movie ->
                WatchList(
                    movie.id,
                    movie.title,
                    movie.overview,
                    movie.posterPath,
                    movie.backdropPath,
                    movie.rating,
                    movie.releaseDate,
                    WatchlistType.MovieType
                )

            }
        )

        watchListAdapter.updateItems(watchList)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        if (!hidden) {
            getWatchList()
        }
    }

    override fun onResume() {
        super.onResume()
        getWatchList()
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