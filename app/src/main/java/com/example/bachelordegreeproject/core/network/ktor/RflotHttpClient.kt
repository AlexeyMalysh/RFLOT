package com.example.bachelordegreeproject.core.network.ktor

import com.example.bachelordegreeproject.di.ApiUrl
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration.Companion.seconds

@Singleton
class RflotHttpClient @Inject constructor(
    @ApiUrl private val baseUrl: String,
    private val engine: HttpClientEngineFactory<*>,
) {
    private val client by lazy {
        HttpClient(engine) {
            expectSuccess = true
            install(ContentNegotiation) {
                json(
                    Json {
                        isLenient = true
                        ignoreUnknownKeys = true
                        allowSpecialFloatingPointValues = true
                        useArrayPolymorphism = true
                    }
                )
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Timber.d("Response: $message")
                    }
                }
                level = LogLevel.ALL
            }
            install(HttpTimeout) {
                connectTimeoutMillis = 10.seconds.inWholeMilliseconds
                requestTimeoutMillis = 10.seconds.inWholeMilliseconds
                socketTimeoutMillis = 10.seconds.inWholeMilliseconds
            }
            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                url {
                    protocol = URLProtocol.HTTP
                    host = baseUrl
                }
            }
        }
    }
    operator fun invoke(): HttpClient = client
}
