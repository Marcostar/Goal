
import com.sagycorp.myutd.data.FavTeam
import com.sagycorp.myutd.data.PlayerList
import com.sagycorp.myutd.data.Teams
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface SportsAPIService {

    @GET("searchteams.php")
    fun getFavTeam(
        @Query("t") tag: String
    ): Deferred<Teams>

    @GET("lookup_all_players.php")
    fun getAllPlayers(
        @Query("id") tag: Long
    ): Deferred<PlayerList>

    @GET("lookupteam.php")
    fun getTeamInfo(
        @Query("id") tag: Long
    ): Deferred<Teams>
}