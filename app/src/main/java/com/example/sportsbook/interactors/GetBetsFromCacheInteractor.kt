package com.example.sportsbook.interactors

import com.example.sportsbook.persistence.BetsCache
import javax.inject.Inject

class GetBetsFromCacheInteractor @Inject constructor(
    private val cache: BetsCache
) {


}