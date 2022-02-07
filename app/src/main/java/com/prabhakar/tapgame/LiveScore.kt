package com.prabhakar.tapgame

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class LiveScore {
    var mediatorLiveData = MediatorLiveData<Int>()
    private var score = 0

    private var liveDataOne = MutableLiveData<Int>()

    fun incrementByOne() {
        score++
        liveDataOne.value = score

    }

    fun getScore(): MutableLiveData<Int> {
        mediatorLiveData.addSource(liveDataOne, Observer {
            mediatorLiveData.value = it
        })
        return mediatorLiveData
    }

    fun resetScore() {
        score = 0
        liveDataOne.value = score
    }
}