package com.challenge.theScore.framework.domain

import com.challenge.theScore.core.data.TeamsModel
import retrofit2.Response
import retrofit2.http.GET

interface TeamsApiService {
    @GET("input.json")
    suspend fun getTeamsData(): Response<List<TeamsModel>>
}