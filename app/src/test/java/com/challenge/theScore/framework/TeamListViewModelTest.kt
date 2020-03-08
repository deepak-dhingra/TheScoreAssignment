package com.challenge.theScore.framework

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.challenge.theScore.core.data.Player
import com.challenge.theScore.core.data.TeamsModel
import com.challenge.theScore.framework.domain.TeamsApiService
import com.challenge.theScore.testHelper.observeOnce
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import retrofit2.Response

class TeamListViewModelTest {
    @Mock
    lateinit var teamApi: TeamsApiService

    private lateinit var application: Application

    @InjectMocks
    private lateinit var teamListViewModel: TeamListViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        application = Mockito.mock(Application::class.java)
        teamListViewModel = TeamListViewModel(application)
        teamApi = Mockito.mock(TeamsApiService::class.java)
    }

    @Test
    fun testTeamList() {
        runBlocking {
            Mockito.`when`(teamApi.getTeamsData()).thenReturn(getMockData())
            teamListViewModel.getTeamList()
        }
        teamListViewModel.teamsList.observeOnce {
            assertNotNull(it)
            assert(it.size == 3)
        }

        teamListViewModel.teamLoadError.observeOnce {
            assertFalse(it)
        }
    }


    @Test
    fun testTeamListError() {
        runBlocking {
            Mockito.`when`(teamApi.getTeamsData()).thenReturn(null)
            teamListViewModel.getTeamList()
        }

        teamListViewModel.teamsList.observeOnce {
            assertNull(it)
        }
        teamListViewModel.teamLoadError.observeOnce {
            assertTrue(it)
        }
    }

    @Test
    fun testSortByName() {
        runBlocking {
            Mockito.`when`(teamApi.getTeamsData()).thenReturn(getMockData())
            teamListViewModel.getTeamList()
            teamListViewModel.sortList("Names")
        }
        teamListViewModel.teamsList.observeOnce {
            assert(it[0].fullName == "A")
            assert(it[1].fullName == "B")
            assert(it[2].fullName == "C")
        }
    }


    @Test
    fun tesSortByWins() {
        runBlocking {
            Mockito.`when`(teamApi.getTeamsData()).thenReturn(getMockData())
            teamListViewModel.getTeamList()
            teamListViewModel.sortList("wins")
        }
        teamListViewModel.teamsList.observeOnce {
            assert(it[0].wins == "3")
            assert(it[1].wins == "2")
            assert(it[2].wins == "1")
        }
    }


    @Test
    fun tesSortByLosses() {
        runBlocking {
            Mockito.`when`(teamApi.getTeamsData()).thenReturn(getMockData())
            teamListViewModel.getTeamList()
            teamListViewModel.sortList("losses")
        }
        teamListViewModel.teamsList.observeOnce {
            assert(it[0].losses == "3")
            assert(it[1].losses == "2")
            assert(it[2].losses == "1")
        }
    }

    private fun getMockData(): Response<List<TeamsModel>> {
        return Response.success(
            listOf(
                TeamsModel(
                    "3", "2", "C", 10,
                    listOf(Player(1, "FirstName", "LastName", "Position", 10))
                ),
                TeamsModel(
                    "2", "1", "B", 10,
                    listOf(Player(1, "FirstName", "LastName", "Position", 10))
                ),
                TeamsModel(
                    "1", "3", "A", 10,
                    listOf(Player(1, "FirstName", "LastName", "Position", 10))
                )
            )
        )
    }
}