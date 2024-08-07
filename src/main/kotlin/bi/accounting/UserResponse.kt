package bi.accounting

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class UserResponse(
    val id: Long,
) {
    val username: String? = null
    val email: String? = null
    val isEnabled: Boolean? = null
    val isAccountExpired: Boolean? = null
    val isAccountLocked: Boolean? = null
    val isPasswordExpired: Boolean? = null
    var authorities: List<String>? = null
    var tenantName: String? = null
    var tenantCode: String? = null
    var dateCreated: String? = null
    var dateUpdated: String? = null
    var isPrimary: Boolean? = null
}
