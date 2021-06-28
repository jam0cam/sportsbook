package com.example.sportsbook.network

import android.util.Log
import com.example.sportsbook.extensions.switchIfNull
import com.example.sportsbook.interactors.FetchBetsInteractor
import com.example.sportsbook.main.BetParser
import com.example.sportsbook.main.Category
import com.example.sportsbook.main.Category.*
import com.example.sportsbook.main.DailyBet
import com.example.sportsbook.persistence.ErrorLogger
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

class ApiMap @Inject constructor(
    service: ApiService,
    private val parser: BetParser,
    private val errorLogger: ErrorLogger,
) {
    val cookie = "ruibj=live%20betting; ruibj=default; JSESSIONID=SBK5~F686116CA8A3D4A4387628AB2FA058FF; timeZoneId=US/Eastern; oddsformat=AMERICAN; defaultbetamount=0; autoAcceptChange=NO; showCashoutConf=YES; JSESSIONID=SBK5~F686116CA8A3D4A4387628AB2FA058FF; SBK_JSESSIONID=SBK5~F686116CA8A3D4A4387628AB2FA058FF; timeZoneId=US/Eastern; oddsformat=AMERICAN; defaultbetamount=0; autoAcceptChange=NO; showCashoutConf=YES; ruitimeoutCookie=false; _ga=GA1.2.1207703326.1561983414; CCA_JSESSIONID=B7B7702D28662AEAFFEFE9FA78C4ECAB; everlogged=true; has_js=1; CCAREFINERY=CCA2|XoTJg; visid_incap_2286874=G1u9mI5ZQM2k4KNYZl2PnMDOpV4AAAAAQUIPAAAAAABnJgekzseGGFzP+nyoR5xG; CCA_JSESSIONID=B7B7702D28662AEAFFEFE9FA78C4ECAB; everlogged=true; _gid=GA1.2.1966506178.1610737008; incap_ses_8217_2286874=6HxXTntAdyNGHuXdC6YIcrLrJ2AAAAAAyCxC7Tf6iW5e7ZhK/DNENQ==; CASTGC=TGT-1613230438902-d5aM7FCONBHMlNyH5gRF37ujIcrgxW; CCAREFINERY=CCA1|YCfxU"
    val map1 = mapOf(
        NCAAF to service.getNcaaFootballLines().getData(NCAAF).subscribeOn(Schedulers.io()),
        NFL to service.getNflLines().getData(NFL).subscribeOn(Schedulers.io()),
        NCAAB to service.getNcaaBasketball().getData(NCAAB).subscribeOn(Schedulers.io()),
        ESPORTS to service.getEsports().getData(ESPORTS).subscribeOn(Schedulers.io()),
        TABLE_TENNIS to service.getTableTennis().getData(TABLE_TENNIS).subscribeOn(Schedulers.io()),
        NBA_PRESEASON to service.getNbaPreseason().getData(NBA_PRESEASON).subscribeOn(Schedulers.io()),
        NBA to service.getNba().getData(NBA).subscribeOn(Schedulers.io()),
        UFC to service.getUfc().getData(UFC).subscribeOn(Schedulers.io()),
        BOXING to service.getBoxing().getData(BOXING).subscribeOn(Schedulers.io()),
        TENNIS to service.getTennis().getData(TENNIS).subscribeOn(Schedulers.io()),
        WIMBLEDON to service.getWimbledon().getData(WIMBLEDON).subscribeOn(Schedulers.io()),
        SOCCER to service.getSoccer().getData(SOCCER).subscribeOn(Schedulers.io()),
    )

    /**
     * Takes the HTML api response and converts it into a list of [DailyBet]
     */
    private fun Single<Response<String>>.getData(category: Category): Maybe<List<DailyBet>> {
        return this
            .flatMapMaybe { response  ->
                Log.d(FetchBetsInteractor.TAG, "Response received for ${category.value}")
                response.body()
                    ?.let {
                        if(it.contains("username", true) && it.contains("password", true)) {
                            errorLogger.log("Login requested for $category")
                        }
                        parseAndFilterResponse(category, it)
                    }
                    ?.let { Maybe.just(it) }
                    .switchIfNull {
                        errorLogger.log("empty response for $category")
                        Maybe.just(emptyList())
                    }
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