package org.liamjd.cantilever.corbel.services.auth

import org.liamjd.cantilever.corbel.models.SubmitUser

interface AuthenticationService {

    suspend fun login(user: SubmitUser)
}