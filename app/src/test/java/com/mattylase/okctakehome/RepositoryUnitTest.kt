package com.mattylase.okctakehome

import com.mattylase.okctakehome.extras.Constants
import com.mattylase.okctakehome.repository.Repository
import com.mattylase.okctakehome.repository.model.Match
import com.mattylase.okctakehome.repository.model.MatchesResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.mockk.*
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule

class RepositoryUnitTest : KoinTest {

    private val httpClient = mockk<HttpClient>(relaxed = true)

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(
            module {
                single { httpClient }
            }
        )
    }

    lateinit var repo: Repository

    @Before
    fun setup() {
        repo = spyk(Repository(), recordPrivateCalls = true)
    }

    @Test
    fun `verify defaultRequestConfig is constructed properly`() {
        val builder = HttpRequestBuilder()
        repo.defaultRequestConfig(builder)
        assertEquals(Constants.matchesHost, builder.host)
        assertEquals(Constants.matchesPath, builder.url.encodedPath)
    }
}