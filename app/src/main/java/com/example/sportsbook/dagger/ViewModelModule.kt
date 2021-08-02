package com.example.sportsbook.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sportsbook.main.bets.BetsViewModel
import com.example.sportsbook.main.MainViewModel
import com.example.sportsbook.sample.SampleViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun mainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BetsViewModel::class)
    internal abstract fun betsViewModel(viewModel: BetsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SampleViewModel::class)
    internal abstract fun sampleViewModel(viewModel: SampleViewModel): ViewModel

    //Add more ViewModels here
}