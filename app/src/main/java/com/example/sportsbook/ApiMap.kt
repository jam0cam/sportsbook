package com.example.sportsbook

import android.util.Log
import com.example.sportsbook.extensions.switchIfNull
import com.example.sportsbook.interactors.FetchBetsInteractor
import com.example.sportsbook.main.BetParser
import com.example.sportsbook.main.Category
import com.example.sportsbook.main.Category.*
import com.example.sportsbook.main.DailyBet
import io.reactivex.Maybe
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

class ApiMap @Inject constructor(
    service: ApiService,
    private val parser: BetParser
) {
    val cookie = "ruibj=live%20betting; ruibj=default; JSESSIONID=SBK5~F686116CA8A3D4A4387628AB2FA058FF; timeZoneId=US/Eastern; oddsformat=AMERICAN; defaultbetamount=0; autoAcceptChange=NO; showCashoutConf=YES; JSESSIONID=SBK5~F686116CA8A3D4A4387628AB2FA058FF; SBK_JSESSIONID=SBK5~F686116CA8A3D4A4387628AB2FA058FF; timeZoneId=US/Eastern; oddsformat=AMERICAN; defaultbetamount=0; autoAcceptChange=NO; showCashoutConf=YES; ruitimeoutCookie=false; _ga=GA1.2.1207703326.1561983414; CCA_JSESSIONID=B7B7702D28662AEAFFEFE9FA78C4ECAB; everlogged=true; has_js=1; CCAREFINERY=CCA2|XoTJg; visid_incap_2286874=G1u9mI5ZQM2k4KNYZl2PnMDOpV4AAAAAQUIPAAAAAABnJgekzseGGFzP+nyoR5xG; CCA_JSESSIONID=B7B7702D28662AEAFFEFE9FA78C4ECAB; everlogged=true; _gid=GA1.2.1966506178.1610737008; incap_ses_8217_2286874=6HxXTntAdyNGHuXdC6YIcrLrJ2AAAAAAyCxC7Tf6iW5e7ZhK/DNENQ==; CASTGC=TGT-1613230438902-d5aM7FCONBHMlNyH5gRF37ujIcrgxW; CCAREFINERY=CCA1|YCfxU"
    val map1 = mapOf(
        NCAAF to service.getNcaaFootballLines(cookie).getData(NCAAF),
        NFL to service.getNflLines(cookie).getData(NFL),
        NCAAB to service.getNcaaBasketball(cookie).getData(NCAAB),
        ESPORTS to service.getEsports(cookie).getData(ESPORTS),
        TABLE_TENNIS to service.getTableTennis(cookie).getData(TABLE_TENNIS),
        NBA_PRESEASON to service.getNbaPreseason(cookie).getData(NBA_PRESEASON),
        NBA to service.getNba(cookie).getData(NBA),
        UFC to service.getUfc(cookie).getData(UFC),
        BOXING to service.getBoxing(cookie).getData(BOXING),
        TENNIS to service.getTennis(cookie).getData(TENNIS),
        SOCCER to service.getSoccer(cookie).getData(SOCCER),
    )

    /**
     * Takes the HTML api response and converts it into a list of [DailyBet]
     */
    private fun Single<Response<String>>.getData(category: Category): Maybe<List<DailyBet>> {
        return this
            .flatMapMaybe { response  ->
                Log.d(FetchBetsInteractor.TAG, "Response received for ${category.value}")
                response.body()
                    ?.let { parseAndFilterResponse(category, it) }
                    ?.let { Maybe.just(it) }
                    .switchIfNull { Maybe.just(emptyList()) }
            }
            .onErrorResumeNext (Maybe.just(emptyList()))
            .doOnError {
                Log.d(FetchBetsInteractor.TAG, "${it.message}")
            }
    }

    /**
     * parses HTML to [DailyBet] and filters out bets we don't care about.
     */
    private fun parseAndFilterResponse(category: Category, response: String): List<DailyBet> {
        var result = parser.parse(category, response)

        //remove all the bets that are not worth betting
        result.forEach {
            it.bets.apply {
                removeAll { it.odds.toIntOrNull() == null }
                removeAll { it.odds.toInt() > -1000 }
            }
        }

        //remove all the days that has no bets
        return result.filter { it.bets.isNotEmpty() }
    }

}