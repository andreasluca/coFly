package com.build38.fly

class Constants {
    companion object {
        const val AMADEUS_BASE_URL = "https://test.api.amadeus.com/v1/"
        const val AMADEUS_SECURITY_BASE_URL = "${AMADEUS_BASE_URL}security/oauth2/"
        const val AMADEUS_SHOPPING_BASE_URL = "${AMADEUS_BASE_URL}shopping/"
        const val AMADEUS_AUTH_TOKEN_GRANT_TYPE = "client_credentials"
    }
}
