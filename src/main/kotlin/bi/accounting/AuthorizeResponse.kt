package bi.accounting

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class AuthorizeResponse(
    val isAuthorized: Boolean,
    val context: Map<String, UserResponse>?
)
