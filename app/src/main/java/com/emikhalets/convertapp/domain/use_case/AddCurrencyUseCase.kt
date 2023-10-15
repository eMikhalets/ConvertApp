package com.emikhalets.convertapp.domain.use_case

import com.emikhalets.convert.data.Repository
import javax.inject.Inject

class AddCurrencyUseCase @Inject constructor(
    private val repository: Repository,
) {

    suspend operator fun invoke(code: String) {
        return repository.insertCurrency(code)
    }
}
