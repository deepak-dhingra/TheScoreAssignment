package com.challenge.theScore.framework.di

import android.app.Application
import com.challenge.theScore.framework.domain.ApiClient
import com.challenge.theScore.framework.domain.TeamsApiService
import dagger.Module
import dagger.Provides

@Module
class ApiModule {
    @Provides
    fun provideTeamsApi(application: Application): TeamsApiService {
        return ApiClient.getInstance(application).getClient().create(TeamsApiService::class.java)
    }
}