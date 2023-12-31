package com.emikhalets.convertapp.domain.use_case

import com.emikhalets.convertapp.core.common.extensions.logd
import com.emikhalets.convertapp.data.Repository
import com.emikhalets.convertapp.domain.model.ExchangeModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetExchangesUseCase @Inject constructor(
    private val repository: Repository,
) {

    operator fun invoke(): Flow<List<ExchangeModel>> {
        logd("GetExchangesUseCase")
        return repository.getExchanges()
    }
}
