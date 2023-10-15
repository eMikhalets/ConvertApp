package com.emikhalets.convertapp.domain.use_case

import com.emikhalets.convertapp.data.Repository
import com.emikhalets.convertapp.domain.AppResult
import com.emikhalets.convertapp.domain.StringValue
import com.emikhalets.convertapp.domain.model.ExchangeModel
import javax.inject.Inject

class UpdateExchangesUseCase @Inject constructor(
    private val repository: Repository,
) {

    suspend operator fun invoke(list: List<ExchangeModel>): Result {
        return when (val result = repository.updateExchanges(list)) {
            is AppResult.Failure -> Result.Failure(result.error)
            is AppResult.Success -> Result.Success
        }
    }

    sealed interface Result {

        object Success : Result
        class Failure(val error: StringValue) : Result
    }
}
