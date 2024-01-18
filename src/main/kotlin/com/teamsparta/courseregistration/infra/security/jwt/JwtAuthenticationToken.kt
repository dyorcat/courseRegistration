package com.teamsparta.courseregistration.infra.security.jwt

import com.teamsparta.courseregistration.infra.security.UserPrincipal
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.web.authentication.WebAuthenticationDetails

class JwtAuthenticationToken(
    private val principal: UserPrincipal,
    details: WebAuthenticationDetails
): AbstractAuthenticationToken(principal.authorities) {

    init {
        super.setAuthenticated(true)
        super.setDetails((details))
    }

    override fun getCredentials() = null

    override fun getPrincipal() = principal

    override fun isAuthenticated(): Boolean {
        return true
    }

}