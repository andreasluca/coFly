import com.andreas.fly.model.amadeus.AmadeusAuthenticationTokenResponse
import retrofit2.Call
import retrofit2.http.*

interface AmadeusSecurityApi {

    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("token")
    fun getAuthenticationToken(@Field("grant_type") grantType: String,
                               @Field("client_id") clientId: String,
                               @Field("client_secret") clientSecret: String): Call<AmadeusAuthenticationTokenResponse>
}
