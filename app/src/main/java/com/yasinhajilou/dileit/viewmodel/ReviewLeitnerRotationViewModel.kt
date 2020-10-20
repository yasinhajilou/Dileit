package com.yasinhajilou.dileit.viewmodel

import androidx.lifecycle.ViewModel
import com.yasinhajilou.dileit.model.entity.Leitner

class ReviewLeitnerRotationViewModel : ViewModel() {
    var isViewStartUp: Boolean = true
    var newWordCounter: Int = 0
    var currentPosition: Int = 0
    var cardSize: Int = 0
    var filteredList: List<Leitner>? = null
}