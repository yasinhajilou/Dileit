package com.example.dileit.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.dileit.model.entity.Leitner
import com.example.dileit.model.entity.WordHistory
import com.example.dileit.model.entity.WordReviewHistory
import com.example.dileit.model.repository.InternalRepository
import io.reactivex.CompletableObserver
import io.reactivex.MaybeObserver
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

class InternalViewModel(application: Application) : AndroidViewModel(application) {
    private val mRepository: InternalRepository = InternalRepository(application)
    private val TAG = InternalViewModel::class.java.simpleName

    //observe for inserted item id
    private val mAddedLeitnerItemId = MutableLiveData<Long>()

    //observe for updated item status
    private val mUpdatedItemStatus = MutableLiveData<Boolean>()

    //observe for deleted item status
    private val mDeletedLeitnerItemStatus = MutableLiveData<Int>()

    //observe for existed item result
    private val mExistedItem = MutableLiveData<Boolean>()

    //observing cards by their state
    private val mBoxState = MutableLiveData<Int>()
    val learnedCardByBox = Transformations.switchMap(mBoxState) { input -> LiveDataReactiveStreams.fromPublisher(mRepository.getCardByState(input!!)) }

    //observing delete history res
    private val mDeletedHistoryStatus = MutableLiveData<Boolean>()
    fun setBoxState(state: Int) {
        mBoxState.value = state
    }

    //insert data
    fun insertWordReviewedHistory(wordReviewHistory: WordReviewHistory?) {
        mRepository.insetWordReviewedHistory(wordReviewHistory).subscribe()
    }

    fun insertWordHistory(wordHistory: WordHistory?) {
        mRepository.insertWordHistory(wordHistory).subscribe()
    }

    fun insertLeitnerItem(leitner: Leitner?) {
        mRepository.insertLeitnerItem(leitner)
                .subscribe(object : SingleObserver<Long> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onSuccess(aLong: Long) {
                        mAddedLeitnerItemId.postValue(aLong)
                    }

                    override fun onError(e: Throwable) {
                        mAddedLeitnerItemId.postValue(-1L)
                    }
                })
    }

    //update data
    fun updateLeitnerItem(leitner: Leitner?) {
        mRepository.updateLeitnerItem(leitner)
                .subscribe(object : CompletableObserver {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onComplete() {
                        mUpdatedItemStatus.postValue(true)
                    }

                    override fun onError(e: Throwable) {
                        mUpdatedItemStatus.postValue(false)
                    }
                })
    }

    val addedLeitnerItemId: LiveData<Long>
        get() = mAddedLeitnerItemId

    //getData
    val deletedHistoryResult: LiveData<Boolean>
        get() = mDeletedHistoryStatus

    fun getExistedLeitner(word: String?) {
        mRepository.getExistingLeitner(word)
                .subscribe(object : MaybeObserver<Leitner?> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onSuccess(t: Leitner) {
                        mExistedItem.postValue(true)
                    }

                    override fun onError(e: Throwable) {}
                    override fun onComplete() {
                        mExistedItem.postValue(false)
                    }
                })
    }

    val booleanExistedCard: LiveData<Boolean>
        get() = mExistedItem
    val allWordHistory: LiveData<List<WordHistory>>
        get() = LiveDataReactiveStreams.fromPublisher(mRepository.allWordHistory)
    val updateItemStatus: LiveData<Boolean>
        get() = mUpdatedItemStatus
    val deletedLeitnerItemStatus: LiveData<Int>
        get() = mDeletedLeitnerItemStatus

    fun getLeitnerInfoByWord(word: String?): LiveData<Leitner> {
        return LiveDataReactiveStreams.fromPublisher(mRepository.getLeitnerInfoByWord(word))
    }

    fun getCardsByState(state: Int): LiveData<List<Leitner>> {
        return LiveDataReactiveStreams.fromPublisher(mRepository.getCardByState(state))
    }

    fun getLeitnerCardById(id: Int): LiveData<Leitner> {
        return LiveDataReactiveStreams.fromPublisher(mRepository.getLeitnerCardById(id))
    }

    val allLeitnerItems: LiveData<List<Leitner>>
        get() = LiveDataReactiveStreams.fromPublisher(mRepository.allLeitnerItems)
    val todayList: LiveData<List<Leitner>>
        get() = LiveDataReactiveStreams.fromPublisher(mRepository.todayList)
    val todayListSize: LiveData<Int>
        get() = LiveDataReactiveStreams.fromPublisher(mRepository.todayListSize)
    val newCardsCount: LiveData<Int>
        get() = LiveDataReactiveStreams.fromPublisher(mRepository.newCardsCount)
    val learnedCardsCount: LiveData<Int>
        get() = LiveDataReactiveStreams.fromPublisher(mRepository.learnedCardsCount)
    val reviewingCardCount: LiveData<Int>
        get() = LiveDataReactiveStreams.fromPublisher(mRepository.reviewingCardCount)
    val allLeitnerCardCount: LiveData<Int>
        get() = LiveDataReactiveStreams.fromPublisher(mRepository.allCardCount)

    //Delete data
    fun deleteLeitnerItem(leitner: Leitner?) {
        mRepository.deleteLeitnerItem(leitner)
                .subscribe(object : SingleObserver<Int> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onSuccess(integer: Int) {
                        mDeletedLeitnerItemStatus.postValue(integer)
                    }

                    override fun onError(e: Throwable) {
                        mDeletedLeitnerItemStatus.postValue(-1)
                    }
                })
    }

    fun deleteAllHistory() {
        mRepository.deleteAllHistory().subscribe(object : CompletableObserver {
            override fun onSubscribe(d: Disposable) {}
            override fun onComplete() {
                mDeletedHistoryStatus.postValue(true)
            }

            override fun onError(e: Throwable) {
                mDeletedHistoryStatus.postValue(false)
            }
        })
    }

}