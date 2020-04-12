package me.shrikanthravi.india.covid19app.ui.state

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.thunder413.datetimeutils.DateTimeStyle
import com.github.thunder413.datetimeutils.DateTimeUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import me.shrikanthravi.india.covid19app.R
import me.shrikanthravi.india.covid19app.data.local.Resource
import me.shrikanthravi.india.covid19app.data.model.DistrictData
import me.shrikanthravi.india.covid19app.data.model.StateDistrictWise
import me.shrikanthravi.india.covid19app.data.model.Statewise
import me.shrikanthravi.india.covid19app.data.model.Stats
import me.shrikanthravi.india.covid19app.databinding.ActivityHomeBinding
import me.shrikanthravi.india.covid19app.databinding.ActivityStateViewBinding
import me.shrikanthravi.india.covid19app.ui.home.HomeViewModel
import me.shrikanthravi.india.covid19app.ui.home.StateAdapter
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.reflect.Type


class StateViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStateViewBinding
    private val viewModel: StateViewModel by viewModel()
    private val gson: Gson by inject()
    lateinit var adapter: DistrictAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        setObserver()
        setListeners()
        var data = intent.getStringExtra("stats")
        val type: Type = object : TypeToken<Statewise?>() {}.type
        stats = gson.fromJson(data,type)
        viewModel.getStats(stats.state)
    }

    private fun initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_state_view)
        supportActionBar?.hide()
    }

    private fun setListeners(){
        binding.imageBack.setOnClickListener {
            onBackPressed()
        }
        binding.layoutError.setOnClickListener {
            viewModel.getStats(stats.state)
        }
    }

    private fun setObserver() {
        viewModel.performFetchStatsStatus.observe(this, Observer {
            handleStateView(it)
        })
    }

    lateinit var stats: Statewise
    var districtList: ArrayList<DistrictData> = ArrayList()
    private fun updateUI() {
        if (stats!=null) {
            binding.textConfirmedValue.text = stats.confirmed
            binding.textActiveValue.text = stats.active
            binding.textRecoveredValue.text = stats.recovered
            binding.textDeceasedValue.text = stats.deaths
            binding.textState.text = stats.state
            val updateDate = DateTimeUtils.formatDate(stats.lastupdatedtime)
            val timeAgo = DateTimeUtils.getTimeAgo(this, updateDate, DateTimeStyle.AGO_SHORT_STRING)
            binding.textUpdateTime.text = timeAgo
            println("district count " + districtList.size)
            districtList.sortByDescending { it.confirmed }
            adapter = DistrictAdapter(districtList, this, object : DistrictAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {

                }
            })
            binding.recyclerDistrict.layoutManager = LinearLayoutManager(this)
            binding.recyclerDistrict.adapter = adapter
        }

    }

    private fun handleStateView(resource: Resource<StateDistrictWise>){
        when(resource.status){
            Resource.Status.LOADING -> {
                binding.layoutStates.visibility = View.VISIBLE
                binding.layoutStates.alpha = 1f
                binding.layoutLoading.visibility = View.VISIBLE
                binding.layoutError.visibility = View.GONE
            }
            Resource.Status.SUCCESS -> {
                binding.layoutStates.animate().alpha(0f).setDuration(1000).withEndAction {
                    binding.layoutStates.visibility = View.GONE
                }.start()
                if (resource.data != null) {
                    districtList.clear()
                    if(!resource.data.districtData.isNullOrEmpty()){
                        districtList.addAll(resource.data.districtData)
                    }
                    updateUI()
                }
            }

            Resource.Status.OFFLINE_ERROR -> {
                binding.layoutStates.visibility = View.VISIBLE
                binding.layoutStates.alpha = 1f
                binding.layoutLoading.visibility = View.GONE
                binding.layoutError.visibility = View.VISIBLE
                binding.textErrorMessage.text = "No Internet Connection!\nTap to retry"
            }
            Resource.Status.ERROR -> {
                binding.layoutStates.visibility = View.VISIBLE
                binding.layoutStates.alpha = 1f
                binding.layoutLoading.visibility = View.GONE
                binding.layoutError.visibility = View.VISIBLE
                if(!resource.message.isNullOrEmpty()){
                    binding.textErrorMessage.text = resource.message
                }else{
                    binding.textErrorMessage.text = "Something went wrong!\nTap to retry!"
                }

            }
        }
    }
}
