package com.build38.fly

class Constants {
    companion object {
        const val AMADEUS_BASE_URL = "https://test.api.amadeus.com/v1/"
        const val AMADEUS_SECURITY_BASE_URL = "${AMADEUS_BASE_URL}security/oauth2/"
        const val AMADEUS_SHOPPING_BASE_URL = "${AMADEUS_BASE_URL}shopping/"
        const val AMADEUS_AUTH_TOKEN_GRANT_TYPE = "client_credentials"
        const val AMADEUS_AUTH_TOKEN_CLIENT_ID = "QYlNY5V2OWQ4R2E1JfDD76PGHetJvT7N"
        const val AMADEUS_AUTH_TOKEN_CLIENT_SECRET = "2AMwnKeBumrIcuX6"
    }
}
