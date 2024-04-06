package org.havry

import com.google.gson.Gson
import org.havry.entities.UpdateUserDTO
import org.havry.entities.User
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpRequest.BodyPublisher
import java.net.http.HttpResponse
import java.net.http.HttpResponse.BodyHandlers
import java.time.Duration
import java.util.UUID
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

@Suppress("unused")
class BlockClient(private val httpClient: HttpClient,
                  private val baseUrl: String,
                  private val apiKey: String) {

    private val gson = Gson()
    private val executor = Executors.newSingleThreadExecutor()

    fun getUser(uuid: UUID): CompletableFuture<User> {
        return CompletableFuture.supplyAsync({
            try {
                val resp = execute(
                    "/user?uuid=$uuid", "GET"
                )

                fromJson(resp.body(), User::class.java)
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }, executor)
    }

    fun getUser(username: String): CompletableFuture<User> {
        return CompletableFuture.supplyAsync({
            try {
                val resp = execute(
                    "/user?username=$username", "GET"
                )

                fromJson(resp.body(), User::class.java)
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }, executor)
    }

    fun createUser(user: User): CompletableFuture<Unit> {
        return CompletableFuture.supplyAsync({
            try {
                execute(
                    "/user",
                    "POST",
                    HttpRequest.BodyPublishers.ofString(toJson(user))
                )
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }, executor)
    }

    fun updateUser(dto: UpdateUserDTO): CompletableFuture<Unit> {
        return CompletableFuture.supplyAsync({
            try {
                execute(
                    "/user",
                    "PATCH",
                    HttpRequest.BodyPublishers.ofString(toJson(dto))
                )
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }, executor)
    }

    fun deleteUser(uuid: UUID): CompletableFuture<Unit> {
        return CompletableFuture.supplyAsync({
            try {
                execute(
                    "/user/$uuid", "DELETE"
                )
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }, executor)
    }

    private fun execute(endpoint: String, method: String, body: BodyPublisher = HttpRequest.BodyPublishers.noBody()): HttpResponse<String> {
        return httpClient.send(
            baseRequest(endpoint, method, body),
            BodyHandlers.ofString()
        )
    }

    private fun baseRequest(endpoint: String, method: String, body: BodyPublisher = HttpRequest.BodyPublishers.noBody()): HttpRequest {
        return HttpRequest.newBuilder()
            .uri(URI("$baseUrl/api$endpoint"))
            .header("Content-Type", "application/json; charset=utf-8")
            .header("X-Api-Key", apiKey)
            .timeout(Duration.ofSeconds(10))
            .method(method, body)
            .build()
    }

    private fun toJson(data: Any): String {
        return gson.toJson(data)
    }

    private fun <T> fromJson(raw: String, clazz: Class<T>): T {
        return gson.fromJson(raw, clazz)
    }
}