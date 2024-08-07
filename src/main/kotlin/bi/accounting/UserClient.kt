package bi.accounting

import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.client.annotation.Client
import reactor.core.publisher.Mono

@Client(id = "user")
interface UserClient {

    @Get("/users")
    fun getUser(@Header("Authorization") token: String?): Mono<UserResponse>
}