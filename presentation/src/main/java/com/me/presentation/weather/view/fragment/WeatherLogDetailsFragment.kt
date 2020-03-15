package com.me.presentation.weather.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.me.domain.weather.entity.WeatherEntity
import com.me.presentation.R
import com.me.presentation.base.model.Resource
import com.me.presentation.base.viewmodel.ViewModelFactory
import com.me.presentation.databinding.FragmentWeatherLogDetailsBinding
import com.me.presentation.weather.model.WeatherModel
import com.me.presentation.weather.view.viewmodel.WeatherLogViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class WeatherLogDetailsFragment : Fragment() {


    @Inject
    lateinit var vmFactory: ViewModelFactory<WeatherLogViewModel>

    lateinit var weatherLogDetailsBinding: FragmentWeatherLogDetailsBinding

    val weatherModel = WeatherModel()

    private val vm by lazy {
        ViewModelProviders.of(this, vmFactory)
            .get(WeatherLogViewModel::class.java)
    }

    private val dateCreated by lazy {
        arguments?.let { WeatherLogDetailsFragmentArgs.fromBundle(it).WEATHERLOGCREATIONDATEKEY }
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        weatherLogDetailsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_weather_log_details,
            container,
            false
        )
        weatherLogDetailsBinding.weatherModel = weatherModel

        return weatherLogDetailsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            dateCreated?.let { vm.getCachedWeatherLog(it) }
        }


        vm.weatherLog.observe(this, Observer { updateWeatherLog(it) })
    }


    private fun updateWeatherLog(resource: Resource<WeatherEntity>?) {
        resource?.let {
            it.data?.let { weatherEntity ->
                weatherModel.country.set(weatherEntity.country)
                weatherModel.dateCreated.set(weatherEntity.dateCreated)
                weatherModel.dt.set(weatherEntity.dt)
                weatherModel.name.set(weatherEntity.name)
                weatherModel.temp.set(weatherEntity.temp)
                weatherModel.description.set(weatherEntity.description)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_weather_log_details, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_delete -> {
            dateCreated?.let {
                vm.deleteWeatherLog(it)
            }
            NavHostFragment.findNavController(this).popBackStack()
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    companion object {
        fun navigateTo(from: Fragment, dateCreated: Long) {
            val directions = WeatherLogListFragmentDirections.actionLogListToLogDetails(dateCreated)
            NavHostFragment.findNavController(from).navigate(directions)
        }
    }
}