package me.shrikanthravi.india.covid19app.ui.home

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.thunder413.datetimeutils.DateTimeStyle
import com.github.thunder413.datetimeutils.DateTimeUtils
import com.google.gson.Gson
import com.jjoe64.graphview.GridLabelRenderer
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import me.shrikanthravi.india.covid19app.R
import me.shrikanthravi.india.covid19app.data.local.Resource
import me.shrikanthravi.india.covid19app.data.model.Statewise
import me.shrikanthravi.india.covid19app.data.model.Stats
import me.shrikanthravi.india.covid19app.databinding.ActivityHomeBinding
import me.shrikanthravi.india.covid19app.ui.helpline.HelplineActivity
import me.shrikanthravi.india.covid19app.ui.precaution.PrecautionActivity
import me.shrikanthravi.india.covid19app.ui.state.StateViewActivity
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModel()
    private val gson: Gson by inject()
    lateinit var adapter: StateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        setListeners()
        setObserver()
        viewModel.getStats()
    }

    private fun initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        supportActionBar?.hide()
        initGraph()
    }

    private fun setListeners() {
        binding.imageRefresh.setOnClickListener {
            viewModel.getStats()
        }
        binding.cardVideo1.setOnClickListener {
            openYoutubeLink("8c_UJwLq8PI")
        }
        binding.cardVideo2.setOnClickListener {
            openYoutubeLink("SCgCzYAHusA")
        }
        binding.cardPrecaution.setOnClickListener {
            startActivity(Intent(applicationContext, PrecautionActivity::class.java))
        }
        binding.cardHelpline.setOnClickListener {
            startActivity(Intent(applicationContext, HelplineActivity::class.java))
        }
        binding.cardLink.setOnClickListener {

        }
        binding.layoutError.setOnClickListener {
            viewModel.getStats()
        }
    }

    private fun setObserver() {
        viewModel.performFetchStatsStatus.observe(this, Observer {
                handleStateView(it)
        })
    }

    lateinit var stats: Stats
    private fun updateUI() {
        if (!stats.statewise.isNullOrEmpty()) {
            var newConfirmed =
                stats.statewise[0].confirmed.toInt() - stats.casesTimeSeries[stats.casesTimeSeries.size - 1].totalconfirmed.toInt()
            var newRecovered =
                stats.statewise[0].recovered.toInt() - stats.casesTimeSeries[stats.casesTimeSeries.size - 1].totalrecovered.toInt()
            var newDeceased =
                stats.statewise[0].deaths.toInt() - stats.casesTimeSeries[stats.casesTimeSeries.size - 1].totaldeceased.toInt()
            if (newConfirmed >= 0) {
                binding.textConfirmedTitle.text = "CONFIRMED  [+" + newConfirmed + "]"
            } else {
                binding.textConfirmedTitle.text = "CONFIRMED  [+0]"
            }
            if (newRecovered >= 0) {
                binding.textRecoveredTitle.text = "RECOVERED  [+" + newRecovered + "]"
            } else {
                binding.textRecoveredTitle.text = "RECOVERED  [+0]"
            }
            if (newDeceased >= 0) {
                binding.textDeceasedTitle.text = "DECEASED  [+" + newDeceased + "]"
            } else {
                binding.textDeceasedTitle.text = "DECEASED  [+0]"
            }
            binding.textConfirmedValue.text = stats.statewise[0].confirmed
            binding.textActiveValue.text = stats.statewise[0].active
            binding.textRecoveredValue.text = stats.statewise[0].recovered
            binding.textDeceasedValue.text = stats.statewise[0].deaths
            val updateDate = DateTimeUtils.formatDate(stats.statewise[0].lastupdatedtime)
            val timeAgo = DateTimeUtils.getTimeAgo(this, updateDate, DateTimeStyle.AGO_SHORT_STRING)
            binding.textUpdateTime.text = timeAgo
            binding.textUpdateTimeMain.text = stats.statewise[0].lastupdatedtime
            val stateWiseStats = ArrayList<Statewise>()
            for (i in 1 until stats.statewise.size) {
                stateWiseStats.add(stats.statewise[i])
            }
            stateWiseStats.sortByDescending { it.confirmed.toInt() }
            println("statewise count " + stateWiseStats.size)
            adapter = StateAdapter(stateWiseStats, this, object : StateAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    val intent = Intent(applicationContext, StateViewActivity::class.java)
                    val data = gson.toJson(stateWiseStats[position])
                    intent.putExtra("stats", data)
                    startActivity(intent)
                }
            })
            binding.recyclerStates.layoutManager = LinearLayoutManager(this)
            binding.recyclerStates.adapter = adapter
            updateGraphUI()
        }
    }

    private fun initGraph() {
        binding.graphConfirmed.gridLabelRenderer.gridStyle = GridLabelRenderer.GridStyle.NONE
        binding.graphActive.gridLabelRenderer.gridStyle = GridLabelRenderer.GridStyle.NONE
        binding.graphRecovered.gridLabelRenderer.gridStyle = GridLabelRenderer.GridStyle.NONE
        binding.graphDeceased.gridLabelRenderer.gridStyle = GridLabelRenderer.GridStyle.NONE
        binding.graphConfirmed.gridLabelRenderer.isHorizontalLabelsVisible = false
        binding.graphActive.gridLabelRenderer.isHorizontalLabelsVisible = false
        binding.graphRecovered.gridLabelRenderer.isHorizontalLabelsVisible = false
        binding.graphDeceased.gridLabelRenderer.isHorizontalLabelsVisible = false
        binding.graphConfirmed.gridLabelRenderer.isVerticalLabelsVisible = false
        binding.graphActive.gridLabelRenderer.isVerticalLabelsVisible = false
        binding.graphRecovered.gridLabelRenderer.isVerticalLabelsVisible = false
        binding.graphDeceased.gridLabelRenderer.isVerticalLabelsVisible = false
    }

    private fun updateGraphUI() {
        binding.graphConfirmed.viewport.setMaxY(stats.statewise[0].confirmed.toDouble() + 1000)
        binding.graphRecovered.viewport.setMaxY(stats.statewise[0].confirmed.toDouble())
        binding.graphDeceased.viewport.setMaxY(stats.statewise[0].confirmed.toDouble())

        binding.graphConfirmed.viewport.isYAxisBoundsManual = true
        binding.graphRecovered.viewport.isYAxisBoundsManual = true
        binding.graphDeceased.viewport.isYAxisBoundsManual = true

        binding.graphConfirmed.viewport.isScalable = false
        binding.graphRecovered.viewport.isScalable = false
        binding.graphDeceased.viewport.isScalable = false

        var confirmedSeries = ArrayList<DataPoint>()
        var recoveredSeries = ArrayList<DataPoint>()
        var deceasedSeries = ArrayList<DataPoint>()

        for (i in stats.casesTimeSeries.indices) {
            confirmedSeries.add(
                DataPoint(
                    i.toDouble(),
                    stats.casesTimeSeries[i].totalconfirmed.toDouble()
                )
            )
            recoveredSeries.add(
                DataPoint(
                    i.toDouble(),
                    stats.casesTimeSeries[i].totalrecovered.toDouble()
                )
            )
            deceasedSeries.add(
                DataPoint(
                    i.toDouble(),
                    stats.casesTimeSeries[i].totaldeceased.toDouble()
                )
            )
        }

        val confirmedLineGraphSeries = LineGraphSeries<DataPoint>(confirmedSeries.toTypedArray())
        val recoveredLineGraphSeries = LineGraphSeries(recoveredSeries.toTypedArray())
        val deceasedLineGraphSeries = LineGraphSeries(deceasedSeries.toTypedArray())

        confirmedLineGraphSeries.color =
            ContextCompat.getColor(applicationContext, R.color.redLineColor)
        recoveredLineGraphSeries.color =
            ContextCompat.getColor(applicationContext, R.color.greenLineColor)
        deceasedLineGraphSeries.color =
            ContextCompat.getColor(applicationContext, R.color.grayLineColor)

        confirmedLineGraphSeries.setAnimated(true)
        recoveredLineGraphSeries.setAnimated(true)
        deceasedLineGraphSeries.setAnimated(true)

        binding.graphConfirmed.removeAllSeries()
        binding.graphRecovered.removeAllSeries()
        binding.graphDeceased.removeAllSeries()

        binding.graphConfirmed.addSeries(confirmedLineGraphSeries)
        binding.graphRecovered.addSeries(recoveredLineGraphSeries)
        binding.graphDeceased.addSeries(deceasedLineGraphSeries)
    }

    fun openYoutubeLink(youtubeID: String) {
        val intentApp = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + youtubeID))
        val intentBrowser =
            Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + youtubeID))
        try {
            startActivity(intentApp)
        } catch (ex: ActivityNotFoundException) {
            startActivity(intentBrowser)
        }
    }

    private fun handleStateView(resource: Resource<Stats>){
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
                    stats = resource.data
                    updateUI()
                }
            }

            Resource.Status.OFFLINE_ERROR -> {
                binding.layoutLoading.visibility = View.GONE
                binding.layoutError.visibility = View.VISIBLE
                binding.textErrorMessage.text = "No Internet Connection!\nTap to retry"
            }
            Resource.Status.ERROR -> {
                binding.layoutLoading.visibility = View.GONE
                binding.layoutError.visibility = View.VISIBLE
                binding.textErrorMessage.text = "Something went wrong!\nTap to retry!"
            }
        }
    }
}
