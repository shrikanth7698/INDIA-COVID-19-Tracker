package me.shrikanthravi.india.covid19app.di

import me.shrikanthravi.india.covid19app.ui.helpline.HelplineViewModel
import me.shrikanthravi.india.covid19app.ui.home.HomeViewModel
import me.shrikanthravi.india.covid19app.ui.state.StateViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { StateViewModel(get()) }
    viewModel { HelplineViewModel(get()) }
}