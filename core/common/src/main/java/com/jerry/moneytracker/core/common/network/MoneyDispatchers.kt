package com.jerry.moneytracker.core.common.network

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val dispatcher: MoneyDispatchers)

enum class MoneyDispatchers {
    Default,
    IO,
}