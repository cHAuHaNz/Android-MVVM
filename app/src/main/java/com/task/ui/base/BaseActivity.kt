package com.task.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 * Created by Amandeep Chauhan
 */

abstract class BaseActivity<T: ViewBinding> : AppCompatActivity() {

    protected lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = initViewBinding()
        setContentView(binding.root)
        initViewBinding()
        observeViewModel()
    }

    protected abstract fun initViewBinding(): T
    abstract fun observeViewModel()

}
