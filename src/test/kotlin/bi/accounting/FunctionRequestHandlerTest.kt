package bi.accounting
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import io.micronaut.http.annotation.Header
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FunctionRequestHandlerTest {

    @Test
    fun testSuccessHandler() {
        val handler = FunctionRequestHandler()
        val request = APIGatewayProxyRequestEvent()
        request.httpMethod = "GET"
        request.path = "/"
        // update token to succeed
        request.headers = mapOf("Authorization" to "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMyIsIm5iZiI6MTcwMTg0NjgxNywicm9sZXMiOltdLCJpc3MiOiJhYmktdXNlcnMiLCJleHAiOjE3MDE4NTA0MTcsImlhdCI6MTcwMTg0NjgxN30.dnQJ2WOl9xvrDjlYaP4WvZFnMlGpW-j0dSAacaWfS7A")
        val response = handler.execute(request)
        assertEquals(200, response.statusCode.toInt())
        println(response.body)
        handler.close()
    }

    @Test
    fun testFailHandler() {
        val handler = FunctionRequestHandler()
        val request = APIGatewayProxyRequestEvent()
        request.httpMethod = "GET"
        request.path = "/"
        request.headers = mapOf("Authorization" to "Bearer eyJhbGciOiJIUzIJ9.eyJzdWIiOiJ1c2VyMyIsIm5iZiI6MTcwMTgwMDUyMCwicm9sZXMiOltdLCJpc3MiOiJhYmktdXNlcnMiLCJleHAiOjE3MDE4MDQxMjAsImlhdCI6MTcwMTgwMDUyMH0.rWNPUxG5waplYd_7HO1SeMMebHLaQYXqUiACyAjXRns")
        val response = handler.execute(request)
        assertEquals(400, response.statusCode.toInt())
        handler.close()
    }
}
