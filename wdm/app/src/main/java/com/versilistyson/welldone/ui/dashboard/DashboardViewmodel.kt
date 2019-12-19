package com.versilistyson.welldone.ui.dashboard

import android.app.Application
import androidx.lifecycle.*
import com.versilistyson.MyApplication
import com.versilistyson.welldone.data.remote.dto.SensorRecentResponse
import com.versilistyson.welldone.repository.DashboardRepository

class DashboardViewmodel(application: Application): AndroidViewModel(application) {

    private val dashboardRepository = DashboardRepository(application)

    val sensorLiveData = liveData {
        emit(dashboardRepository.fetchAllSensors())
    }

    private val _sensorStatusLiveData: MutableLiveData<MutableList<SensorRecentResponse>> by lazy{
        MutableLiveData<MutableList<SensorRecentResponse>>()
    }

    val sensorStatusLiveData : LiveData<MutableList<SensorRecentResponse>>
    get() = _sensorStatusLiveData

    fun addSensorStatus(sensorRecentResponse: SensorRecentResponse){
        sensorStatusLiveData.value?.let{
            it.add(sensorRecentResponse)
        }
    }
}