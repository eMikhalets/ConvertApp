package com.emikhalets.convertapp.domain.use_case

import com.emikhalets.convertapp.data.Repository
import com.emikhalets.convertapp.domain.AppResult
import com.emikhalets.convertapp.domain.StringValue
import javax.inject.Inject

class DeleteCurrencyUseCase @Inject constructor(
    private val repository: Repository,
) {

    suspend operator fun invoke(code: String): Result {
        return when (val result = repository.deleteCurrency(code)) {
            is AppResult.Failure -> Result.Failure(result.error)
            is AppResult.Success -> Result.Success
        }
    }

    sealed interface Result {

        object Success : Result
        class Failure(val error: StringValue) : Result
    }
}
