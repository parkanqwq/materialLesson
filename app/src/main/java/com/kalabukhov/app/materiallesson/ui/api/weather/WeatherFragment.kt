package com.kalabukhov.app.materiallesson.ui.api.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import coil.api.load
import com.kalabukhov.app.materiallesson.R
import com.kalabukhov.app.materiallesson.ui.api.mars.MarsViewModel
import com.kalabukhov.app.materiallesson.ui.picture.PictureOfTheDayData
import kotlinx.android.synthetic.main.fragment_mars.*
import kotlinx.android.synthetic.main.fragment_weather.*

class WeatherFragment : Fragment() {

    private val viewModel: WeatherViewModel by lazy {
        ViewModelProviders.of(this).get(WeatherViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getData()
            .observe(this@WeatherFragment, { renderData(it) })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                if (url.isNullOrEmpty()) {
                } else {
                    weatherId.load(url) {
                        lifecycle(this@WeatherFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
                    }
                }
            }
            is PictureOfTheDayData.Loading -> {
                //showLoading()
            }
            is PictureOfTheDayData.Error -> {
                //showError(data.error.message)
            }
        }
    }
}
