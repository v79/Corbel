package org.liamjd.cantilever.corbel.services.auth

/**
 * Handle authentication and authorization requests
 */
interface AuthenticationService {

   /**
    * Initiate a login call
    * @return an authorization code
    */
   suspend fun login(): String?

   /**
    * Initiate a logout call
    */
   suspend fun logout()

   /**
    * Refresh or get an authentication token for the given code
    */
   suspend fun getToken(authCode: String): CognitoIDToken

   val authCode: String
}