package com.me.presentation.weather.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.me.domain.weather.entity.WeatherEntity
import com.me.presentation.R
import com.me.presentation.base.adapter.DataBindingAdapter


class WeatherLogListAdapter : DataBindingAdapter<WeatherEntity>(
    DiffCallback()
) {

    class DiffCallback : DiffUtil.ItemCallback<WeatherEntity>() {
        override fun areItemsTheSame(oldItem: WeatherEntity, newItem: WeatherEntity): Boolean =
            oldItem.dateCreated == newItem.dateCreated

        override fun areContentsTheSame(oldItem: WeatherEntity, newItem: WeatherEntity): Boolean =
            oldItem == newItem
    }

    override fun getItemViewType(position: Int) = R.layout.weather_log_list_item
}