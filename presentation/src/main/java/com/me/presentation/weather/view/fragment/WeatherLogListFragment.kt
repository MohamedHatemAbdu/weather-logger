package com.me.presentation.weather.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.me.domain.weather.entity.WeatherEntity
import com.me.presentation.R
import com.me.presentation.base.extensions.startRefreshing
import com.me.presentation.base.extensions.stopRefreshing
import com.me.presentation.base.model.Constants
import com.me.presentation.base.model.Resource
import com.me.presentation.base.model.ResourceState
import com.me.presentation.base.viewmodel.ViewModelFactory
import com.me.presentation.weather.view.adapter.WeatherLogListAdapter
import com.me.presentation.weather.view.viewmodel.WeatherLogViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_weather_log_list.*

import javax.inject.Inject


class WeatherLogListFragment : Fragment() {


    private val aItemClick: (WeatherEntity) -> Unit =
        {
            WeatherLogDetailsFragment.navigateTo(this, it.dateCreated)
        }
    @Inject
    lateinit var adapter: WeatherLogListAdapter

    @Inject
    lateinit var vmFactory: ViewModelFactory<WeatherLogViewModel>

    private val vm by lazy {
        ViewModelProviders.of(this, vmFactory)
            .get(WeatherLogViewModel::class.java)
    }

    private val snackBar by lazy {
        Snackbar.make(
            srlWeatherLogList,
            getString(R.string.weather_log_list_no_cached_data),
            Snackbar.LENGTH_INDEFINITE
        )
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
        return inflater.inflate(R.layout.fragment_weather_log_list, container, false)

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_weather_log_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_save -> {
            vm.getRemoteWeatherLog(Constants.RIGA_LATVIA_CITY_ID)
            true
        }

        R.id.action_clear -> {
            vm.clearAll()
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            vm.getWeatherLogs()
        }

        adapter.itemClick = aItemClick
        rvWeatherLogList.adapter = adapter

        vm.weatherLogsList.observe(this, Observer { updateWeatherLogs(it) })
        vm.weatherLog.observe(this, Observer { updateLoadingState(it.state) })

        srlWeatherLogList.setOnRefreshListener {
            vm.getWeatherLogs()
        }

    }


    private fun updateWeatherLogs(resource: Resource<List<WeatherEntity>>?) {
        resource?.let {
            updateLoadingState(it.state)

            it.data?.let {
                if (it.isEmpty())
                    showSnackBar(getString(R.string.weather_log_list_no_cached_data))
                adapter.submitList(it)
            }
            it.message?.let { showSnackBar(it) }
        }
    }

    private fun updateLoadingState(state: ResourceState) {
        when (state) {
            ResourceState.LOADING -> srlWeatherLogList.startRefreshing()
            ResourceState.SUCCESS -> srlWeatherLogList.stopRefreshing()
            ResourceState.ERROR -> srlWeatherLogList.stopRefreshing()
        }
    }

    private fun showSnackBar(msg: String) {
        snackBar.setAction(getString(R.string.weather_log_list_snack_bar_action)) {
            snackBar.dismiss()
        }.setText(msg).show()
    }
}
