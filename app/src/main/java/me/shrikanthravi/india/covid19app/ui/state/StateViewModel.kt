package me.shrikanthravi.india.covid19app.ui.state

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.shrikanthravi.india.covid19app.data.local.Resource
import me.shrikanthravi.india.covid19app.data.model.StateDistrictWise
import me.shrikanthravi.india.covid19app.data.model.Stats
import me.shrikanthravi.india.covid19app.data.retrofit.CustomAppRepository
import me.shrikanthravi.india.covid19app.data.retrofit.NoConnectivityException


class StateViewModel(private val productRepository: CustomAppRepository) : ViewModel() {

    //Fetch district stats
    private val performFetchStats = MutableLiveData<Resource<StateDistrictWise>>()
    val performFetchStatsStatus: LiveData<Resource<StateDistrictWise>>
        get() = performFetchStats

    fun getStats(state: String) {
        viewModelScope.launch {
            try {
                performFetchStats.value = Resource.loading()
                val response = productRepository.getDistrictStats()
                var found = false
                for(i in response){
                    if(i.state==state){
                        found = true
                        performFetchStats.value = Resource.success(i)
                        break
                    }
                }
                if(!found){
                    performFetchStats.value = Resource.error(null)
                }
            } catch (e: Exception) {
                println("fetch district stats failed ${e.message}")
                if (e is NoConnectivityException) {
                    performFetchStats.value = Resource.offlineError()
                } else {
                    performFetchStats.value = Resource.error(e)
                }
            }
        }
    }

}