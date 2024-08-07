package bi.accounting
import io.micronaut.function.aws.MicronautRequestHandler
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import io.micronaut.json.JsonMapper;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.serde.ObjectMapper
import jakarta.inject.Inject
import reactor.core.publisher.Mono

class FunctionRequestHandler : MicronautRequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent>() {
    @Inject
    lateinit var userClient: UserClient

    @Inject
    lateinit var objectMapper: ObjectMapper

    override fun execute(input: APIGatewayProxyRequestEvent): APIGatewayProxyResponseEvent {
        try {
            return  userClient.getUser(input.headers["Authorization"])
                .flatMap { userResponse ->
                    Mono.just(objectMapper.writeValueAsString(AuthorizeResponse(
                        isAuthorized = true,
                        context = mapOf("auth" to UserResponse(id = userResponse.id))
                    )
                    ))
                        .map { response ->
                            val responseEvent = APIGatewayProxyResponseEvent()
                            responseEvent.statusCode = 200
                            responseEvent.body = response
                            responseEvent }
                }
                .block()
                ?: run {
                    val response = APIGatewayProxyResponseEvent()
                    response.statusCode = 500
                    response.body = objectMapper.writeValueAsString(AuthorizeResponse(
                        isAuthorized = false, context = null)
                    )
                    response
                }
        }catch (e: HttpClientResponseException) {
            val response = APIGatewayProxyResponseEvent()
            response.statusCode = 400
            response.body = objectMapper.writeValueAsString(AuthorizeResponse(
                isAuthorized = false, context = null)
            )
            return response
        }
    }
}
