package com.challenge.theScore.framework

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.challenge.theScore.core.data.TeamsModel
import com.challenge.theScore.framework.di.ApplicationModule
import com.challenge.theScore.framework.di.DaggerAppComponent
import com.challenge.theScore.framework.domain.TeamsApiService
import kotlinx.coroutines.*
import javax.inject.Inject

class TeamListViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var teamApi: TeamsApiService

    init {
        DaggerAppComponent.builder().applicationModule(ApplicationModule(getApplication())).build()
            .inject(this)
    }

    var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception: ${throwable.localizedMessage}")
    }

    private val coroutineScope = CoroutineScope(Dispatchers.IO + exceptionHandler)

    var teamsList = MutableLiveData<List<TeamsModel>>()
    var loading = MutableLiveData<Boolean>()
    var teamLoadError = MutableLiveData<Boolean>()
    var teamLoadErrorMessage = MutableLiveData<String>()

    fun getTeamList() {
        fetchTeamList()
    }

    private fun fetchTeamList() {
        loading.value = true
        job = coroutineScope.launch {
            val response = teamApi.getTeamsData()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    teamsList.postValue(response.body())
                    teamLoadError.postValue(false)
                    loading.postValue(false)
                } else {
                    onError("Error: ${response.message()}")
                }
            }
        }
    }

    private fun onError(message: String) {
        teamLoadErrorMessage.postValue(message)
        teamLoadError.postValue(true)
        loading.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    /**
     * Function to Sort the list
     */
    fun sortList(type: String) {
        var list: List<TeamsModel>? = null
        job = coroutineScope.launch {
            when {
                type.equals("Names", true) -> {
                    list = sortByNames()
                }
                type.equals("wins", true) -> {
                    list = sortByWins()
                }
                type.equals("losses", true) -> {
                    list = sortByLosses()
                }
            }
            withContext(Dispatchers.Main) {
                teamsList.postValue(list)
            }
        }
    }

    /**
     * Sort by TeamNames
     */
    private fun sortByNames(): List<TeamsModel> {
        return teamsList.value?.sortedWith(compareBy(TeamsModel::fullName))!!
    }

    /**
     * Sort by Wins
     */
    private fun sortByWins(): List<TeamsModel> {
        return teamsList.value?.sortedWith(compareByDescending(TeamsModel::wins))!!
    }

    /**
     * Sort by Losses
     */
    private fun sortByLosses(): List<TeamsModel> {
        return teamsList.value?.sortedWith(compareByDescending(TeamsModel::losses))!!
    }
}