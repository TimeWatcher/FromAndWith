package com.althurdinok.fromandwith.data.repository.onborading.impl

import com.althurdinok.fromandwith.R
import com.althurdinok.fromandwith.data.OnBoardingData
import com.althurdinok.fromandwith.data.repository.onborading.OnBoardingRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FakeOnBoardingRepository : OnBoardingRepository {
    override suspend fun getOnBoardingDataset(): Observable<List<OnBoardingData>> {
        return Observable.create<List<OnBoardingData>>() {
            it.onNext(
                listOf(
                    OnBoardingData(
                        R.drawable.landing_page_1,
                        "三人行，必有我师焉",
                        "向Ta学习，或者和Ta一起学习"
                    ),
                    OnBoardingData(
                        R.drawable.landing_page_2,
                        "教学相长，共同成长",
                        "分享自己的知识，收获他人的知识"
                    ),
                    OnBoardingData(
                        R.drawable.landing_page_3,
                        "学海无涯，同舟共济",
                        "学习的路上，从此不必独自前行"
                    )
                )
            )
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}