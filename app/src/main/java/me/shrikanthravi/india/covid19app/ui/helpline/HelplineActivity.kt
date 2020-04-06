package me.shrikanthravi.india.covid19app.ui.helpline

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.thunder413.datetimeutils.DateTimeStyle
import com.github.thunder413.datetimeutils.DateTimeUtils
import me.shrikanthravi.india.covid19app.R
import me.shrikanthravi.india.covid19app.data.local.Resource
import me.shrikanthravi.india.covid19app.data.model.DistrictData
import me.shrikanthravi.india.covid19app.data.model.Statewise
import me.shrikanthravi.india.covid19app.databinding.ActivityHelplineBinding
import me.shrikanthravi.india.covid19app.databinding.ActivityPrecautionBinding
import me.shrikanthravi.india.covid19app.ui.home.HomeViewModel
import me.shrikanthravi.india.covid19app.ui.home.StateAdapter
import me.shrikanthravi.india.covid19app.ui.state.DistrictAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class HelplineActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHelplineBinding
    private val viewModel: HelplineViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        setListeners()
        setObserver()
        viewModel.getHelplineNumbers()
    }

    private fun initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_helpline)
        supportActionBar?.hide()
    }

    private fun setListeners() {
        binding.imageBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setObserver(){
        viewModel.performFetchNumbersStatus.observe(this, Observer {
            if (it != null) {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        if (it.data != null) {
                            helplineList.clear()
                            helplineList.addAll(it.data)
                            updateUI()
                        }
                    }
                    Resource.Status.OFFLINE_ERROR -> {

                    }
                    Resource.Status.ERROR -> {

                    }
                    Resource.Status.LOADING -> {

                    }
                }
            }
        })
    }

    var helplineList: ArrayList<Helpline> = ArrayList()
    lateinit var adapter: HelplineAdapter
    private fun updateUI() {
        println("numbers count " + helplineList.size)
        adapter = HelplineAdapter(helplineList, this, object : HelplineAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:"+helplineList[position].phoneNumbers[0])
                startActivity(intent)
            }
        })
        binding.recyclerHelpline.layoutManager = LinearLayoutManager(this)
        binding.recyclerHelpline.adapter = adapter

    }
}
