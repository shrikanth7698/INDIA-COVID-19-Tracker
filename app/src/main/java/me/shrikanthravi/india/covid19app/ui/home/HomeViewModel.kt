package me.shrikanthravi.india.covid19app.ui.home

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.shrikanthravi.india.covid19app.data.local.Resource
import me.shrikanthravi.india.covid19app.data.model.Stats
import me.shrikanthravi.india.covid19app.data.retrofit.CustomAppRepository
import java.net.UnknownHostException


class HomeViewModel(private val productRepository: CustomAppRepository) : ViewModel() {

    //Fetch total stats
    private val performFetchStats = MutableLiveData<Resource<Stats>>()
    val performFetchStatsStatus: LiveData<Resource<Stats>>
        get() = performFetchStats

    fun getStats() {
        viewModelScope.launch {
            try {
                performFetchStats.value = Resource.loading()
                val response = productRepository.getStats()
                performFetchStats.value = Resource.success(response)
            } catch (e: Exception) {
                println("fetch stats failed ${e.message}")
                if (e is UnknownHostException) {
                    performFetchStats.value = Resource.offlineError()
                } else {
                    performFetchStats.value = Resource.error(e)
                }
            }
        }
    }

}