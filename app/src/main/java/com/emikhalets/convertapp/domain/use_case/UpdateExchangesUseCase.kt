package com.emikhalets.convertapp.domain.use_case

import com.emikhalets.convert.data.Repository
import com.emikhalets.convertapp.domain.model.ExchangeModel
import javax.inject.Inject

class UpdateExchangesUseCase @Inject constructor(
    private val repository: Repository,
) {

    suspend operator fun invoke(list: List<ExchangeModel>) {
        repository.updateExchanges(list)
    }
}
