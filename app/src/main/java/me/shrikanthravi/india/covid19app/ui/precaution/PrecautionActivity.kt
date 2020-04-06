package me.shrikanthravi.india.covid19app.ui.precaution

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import me.shrikanthravi.india.covid19app.R
import me.shrikanthravi.india.covid19app.databinding.ActivityPrecautionBinding


class PrecautionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrecautionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        setListeners()
    }

    private fun initView(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_precaution)
        supportActionBar?.hide()
    }

    private fun setListeners(){
        binding.imageBack.setOnClickListener {
            onBackPressed()
        }
        binding.buttonDisinfectionGuide.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.cdc.gov/coronavirus/2019-ncov/prevent-getting-sick/cleaning-disinfection.html"))
            startActivity(intent)
        }
    }
}
