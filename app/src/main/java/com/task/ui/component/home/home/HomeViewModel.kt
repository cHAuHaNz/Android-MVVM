package com.task.ui.component.home.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.task.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Amandeep Chauhan
 */
@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}