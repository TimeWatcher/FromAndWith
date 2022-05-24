package com.althurdinok.fromandwith.data.repository.onborading

import com.althurdinok.fromandwith.data.OnBoardingData
import io.reactivex.Observable

interface OnBoardingRepository {
    suspend fun getOnBoardingDataset(): Observable<List<OnBoardingData>>
}