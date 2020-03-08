package com.challenge.theScore.framework.di

import com.challenge.theScore.framework.TeamListViewModel
import dagger.Component

@Component(modules = [ApplicationModule::class, ApiModule::class])
interface AppComponent {
    fun inject(viewModel: TeamListViewModel)
}