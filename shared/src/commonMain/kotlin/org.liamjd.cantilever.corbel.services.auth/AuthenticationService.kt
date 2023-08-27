package org.liamjd.cantilever.corbel.services.auth

import org.liamjd.cantilever.corbel.models.SubmitUser

interface AuthenticationService {

    fun login(user: SubmitUser): String?
}