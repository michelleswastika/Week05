package com.vp.week4retrofit.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.vp.week4retrofit.adapter.ListCompanyAdapter
import com.vp.week4retrofit.adapter.ListCountryAdapter
import com.vp.week4retrofit.adapter.ListGenreAdapter
import com.vp.week4retrofit.adapter.ListLanguageAdapter
import com.vp.week4retrofit.databinding.ActivityMovieDetailBinding
import com.vp.week4retrofit.helper.Const
import com.vp.week4retrofit.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetail : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var genreAdapter: ListGenreAdapter
    private lateinit var languageAdapter: ListLanguageAdapter
    private lateinit var countryAdapter: ListCountryAdapter
    private lateinit var companyAdapter: ListCompanyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieId = intent.getIntExtra("movie_id", 0)
//        Toast.makeText(applicationContext, "Movie ID: ${movieId}", Toast.LENGTH_SHORT).show()

        moviesViewModel = ViewModelProvider(this)[MoviesViewModel::class.java]
        moviesViewModel.getMovieDetail(Const.API_KEY, movieId)
        moviesViewModel.movieDetails.observe(this, Observer {
            response->
            binding.tvTitleMovieDetail.apply {
                text = response.title
            }

            binding.tvOverview.apply {
                text = response.overview
            }

            genreAdapter = ListGenreAdapter(response.genres)
            binding.rvGenre.apply {
                layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
                adapter = genreAdapter
            }

            languageAdapter = ListLanguageAdapter(response.spoken_languages)
            binding.rvLanguage.apply {
                layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
                adapter = languageAdapter
            }

            countryAdapter = ListCountryAdapter(response.production_countries)
            binding.rvCountry.apply {
                layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
                adapter = countryAdapter
            }

            companyAdapter = ListCompanyAdapter(response.production_companies)
            binding.rvCompany.apply {
                layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
                adapter = companyAdapter
            }

            Glide.with(applicationContext).load(Const.IMG_URL + response.backdrop_path)
                .into(binding.imgPosterMovieDetail)
        })
    }
}