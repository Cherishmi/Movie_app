package com.example.movies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop


const val MOVIE_BACKDROP = "extra_movie_backdrop"
const val MOVIE_POSTER = "extra_movie_poster"
const val MOVIE_TITLE = "extra_movie_title"
const val MOVIE_RATING = "extra_movie_rating"
const val MOVIE_RELEASE_DATE = "extra_movie_release_date"
const val MOVIE_OVERVIEW = "extra_movie_overview"
const val MOVIE_ID = "extra_movie_id"

private var movieId = 0L
private var movieBackdrop = ""
private var moviePoster = ""
private var movieTitle = ""
private var movieRating = 0f
private var movieReleaseDate = ""
private var movieOverview = ""



class MovieDetails : AppCompatActivity() {

    private val db: AppDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "mymovies.db"
        ).allowMainThreadQueries().build()
    }



    private lateinit var backdrop: ImageView
    private lateinit var poster: ImageView
    private lateinit var title: TextView
    private lateinit var rating: RatingBar
    private lateinit var releaseDate: TextView
    private lateinit var overview: TextView
    private lateinit var addToWatchList: Button




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        backdrop = findViewById(R.id.movie_backdrop)
        poster = findViewById(R.id.movie_poster)
        title = findViewById(R.id.movie_title)
        rating = findViewById(R.id.movie_rating)
        releaseDate = findViewById(R.id.movie_release_date)
        overview = findViewById(R.id.movie_overview)
        addToWatchList = findViewById(R.id.add_to_fav)

        val extras = intent.extras

        if (extras != null) {
            populateDetails(extras)
        } else {
            finish()
        }
    }

    private fun populateDetails(extras: Bundle) {
        movieId = extras.getLong(MOVIE_ID)
        movieBackdrop = extras.getString(MOVIE_BACKDROP, "")
        moviePoster = extras.getString(MOVIE_POSTER, "")
        movieTitle = extras.getString(MOVIE_TITLE, "")
        movieRating = extras.getFloat(MOVIE_RATING, 0f)
        movieReleaseDate = extras.getString(MOVIE_RELEASE_DATE, "")
        movieOverview = extras.getString(MOVIE_OVERVIEW, "")
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w1280$movieBackdrop")
            .transform(CenterCrop())
            .into(backdrop)
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w342$moviePoster")
            .transform(CenterCrop())

            .into(poster)

        title.text = movieTitle
        rating.rating = movieRating / 2
        releaseDate.text = movieReleaseDate
        overview.text = movieOverview
        val movie = getMovie(movieId)
        if (movie == null) {
            addToWatchList.text = getString(R.string.add)
        } else {
            addToWatchList.text = getString(R.string.remove)
        }
    }

    private fun getMovie(id: Long): MovieEntity? {
        return db.movieDao.findById(id)
    }



    override fun onStart() {
        super.onStart()
        addToWatchList.setOnClickListener {
            if (getMovie(movieId) == null) {
                val entity = MovieEntity(
                    movieId,
                    movieTitle,
                    movieOverview,
                    moviePoster,
                    movieBackdrop,
                    movieRating,
                    movieReleaseDate
                )
                db.movieDao.insert(entity)
                addToWatchList.text = getString(R.string.remove)
            } else {
                db.movieDao.delete(movieId)
                addToWatchList.text = getString(R.string.add)
            }
        }
    }


}



