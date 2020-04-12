package me.shrikanthravi.india.covid19app.ui.helpline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.shrikanthravi.india.covid19app.data.local.Resource
import me.shrikanthravi.india.covid19app.data.retrofit.CustomAppRepository
import java.net.UnknownHostException


class HelplineViewModel(private val productRepository: CustomAppRepository) : ViewModel() {

    //Fetch helpline number
    private val performFetchNumbers = MutableLiveData<Resource<ArrayList<Helpline>>>()
    val performFetchNumbersStatus: LiveData<Resource<ArrayList<Helpline>>>
        get() = performFetchNumbers

    fun getHelplineNumbers() {
        viewModelScope.launch {
            try {
                performFetchNumbers.value = Resource.loading()
                val response = populateHelplineData()
                performFetchNumbers.value = Resource.success(response)
            } catch (e: Exception) {
                println("fetch numbers failed ${e.message}")
                if (e is UnknownHostException) {
                    performFetchNumbers.value = Resource.offlineError()
                } else {
                    performFetchNumbers.value = Resource.error(e)
                }
            }
        }
    }


    private fun populateHelplineData(): ArrayList<Helpline> {
        var helpLines: ArrayList<Helpline> = ArrayList()
        var helpLine = Helpline("Andhra Pradesh", arrayOf("08662410978"))
        helpLines.add(helpLine)
        helpLine = Helpline("Arunachal Pradesh", arrayOf("9436055743"))
        helpLines.add(helpLine)
        helpLine = Helpline("Assam", arrayOf("6913347770"))
        helpLines.add(helpLine)
        helpLine = Helpline("Bihar", arrayOf("104"))
        helpLines.add(helpLine)
        helpLine = Helpline("Chhattisgarh", arrayOf("104"))
        helpLines.add(helpLine)
        helpLine = Helpline("Goa", arrayOf("104"))
        helpLines.add(helpLine)
        helpLine = Helpline("Gujarat", arrayOf("104"))
        helpLines.add(helpLine)
        helpLine = Helpline("Haryana", arrayOf("8558893911"))
        helpLines.add(helpLine)
        helpLine = Helpline("Himachal Pradesh", arrayOf("104"))
        helpLines.add(helpLine)
        helpLine = Helpline("Jharkhand", arrayOf("104"))
        helpLines.add(helpLine)
        helpLine = Helpline("Karnataka", arrayOf("104"))
        helpLines.add(helpLine)
        helpLine = Helpline("Kerala", arrayOf("04712552056"))
        helpLines.add(helpLine)
        helpLine = Helpline("Madhya Pradesh", arrayOf("104"))
        helpLines.add(helpLine)
        helpLine = Helpline("Maharashtra", arrayOf("02026127394"))
        helpLines.add(helpLine)
        helpLine = Helpline("Manipur", arrayOf("3852411668"))
        helpLines.add(helpLine)
        helpLine = Helpline("Meghalaya", arrayOf("108"))
        helpLines.add(helpLine)
        helpLine = Helpline("Mizoram", arrayOf("102"))
        helpLines.add(helpLine)
        helpLine = Helpline("Nagaland", arrayOf("7005539653"))
        helpLines.add(helpLine)
        helpLine = Helpline("Odisha", arrayOf("9439994859"))
        helpLines.add(helpLine)
        helpLine = Helpline("Punjab", arrayOf("104"))
        helpLines.add(helpLine)
        helpLine = Helpline("Rajasthan", arrayOf("01412225624"))
        helpLines.add(helpLine)
        helpLine = Helpline("Sikkim", arrayOf("104"))
        helpLines.add(helpLine)
        helpLine = Helpline("Tamil Nadu", arrayOf("04429510500"))
        helpLines.add(helpLine)
        helpLine = Helpline("Telangana", arrayOf("104"))
        helpLines.add(helpLine)
        helpLine = Helpline("Tripura", arrayOf("03812315879"))
        helpLines.add(helpLine)
        helpLine = Helpline("Uttarakhand", arrayOf("104"))
        helpLines.add(helpLine)
        helpLine = Helpline("Uttar Pradesh", arrayOf("18001805145"))
        helpLines.add(helpLine)
        helpLine = Helpline("West Bengal", arrayOf("1800313444222", "03323412600"))
        helpLines.add(helpLine)
        helpLine = Helpline("Andaman and Nicobar Islands", arrayOf("03192232102"))
        helpLines.add(helpLine)
        helpLine = Helpline("Chandigarh", arrayOf("9779558282"))
        helpLines.add(helpLine)
        helpLine = Helpline("Dadra and Nagar Haveli and Daman & Diu", arrayOf("104"))
        helpLines.add(helpLine)
        helpLine = Helpline("Delhi", arrayOf("01122307145"))
        helpLines.add(helpLine)
        helpLine = Helpline("Jammu & Kashmir", arrayOf("01912520982", "01942440283"))
        helpLines.add(helpLine)
        helpLine = Helpline("Ladakh", arrayOf("01912520982", "01942440283"))
        helpLines.add(helpLine)
        helpLine = Helpline("Lakshadweep", arrayOf("104"))
        helpLines.add(helpLine)
        helpLine = Helpline("Puducherry", arrayOf("104"))
        helpLines.add(helpLine)
        return helpLines
    }


}