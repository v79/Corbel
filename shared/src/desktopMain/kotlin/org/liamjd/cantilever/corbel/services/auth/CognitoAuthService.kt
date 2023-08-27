package org.liamjd.cantilever.corbel.services.auth

import org.liamjd.cantilever.corbel.models.SubmitUser
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminInitiateAuthRequest
import software.amazon.awssdk.services.cognitoidentityprovider.model.AuthFlowType
import software.amazon.awssdk.services.cognitoidentityprovider.model.InitiateAuthRequest

class CognitoAuthService : AuthenticationService {

    // TODO: Move these to a config file
    private val poolId = "eu-west-2_aSdFDvU0j"
    private val clientAppId = "6ijb6bg3hk22selq6rj2bb5rmq"
    private val fedPoolId = ""
    private val customDomain = "https://cantilever.auth.eu-west-2.amazoncognito.com"
    private val region="eu-west-2"

    private val cognitoClient: CognitoIdentityProviderClient = CognitoIdentityProviderClient.builder()
        .region(Region.EU_WEST_2)
        .credentialsProvider(ProfileCredentialsProvider.create())
        .build()

    override fun login(user: SubmitUser) {
        println("Attempting to log in with $user to cognito pool $poolId")
        val authParams = mapOf("USERNAME" to user.username, "PASSWORD" to user.password, "REFRESH_TOKEN" to "")


        val authRequest = AdminInitiateAuthRequest.builder()
            .clientId(clientAppId)
            .userPoolId(poolId)
            .authParameters(authParams)
            .authFlow(AuthFlowType.USER_SRP_AUTH)
            .build()

        val response = cognitoClient.adminInitiateAuth(authRequest)
        println("Result challenge is : ${response.challengeName()}")
    }



}