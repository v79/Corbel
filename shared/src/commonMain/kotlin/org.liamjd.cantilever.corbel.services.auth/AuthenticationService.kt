package org.liamjd.cantilever.corbel.services.auth

import org.liamjd.cantilever.corbel.models.SubmitUser

/**
 * Handle authentication and authorization requests
 */
interface AuthenticationService {

   /**
    * Initiate a login call
    * @return an authorization code or token
    */
   suspend fun login(user: SubmitUser): String?

   /**
    * Initiate a logout call
    */
   suspend fun logout()
}