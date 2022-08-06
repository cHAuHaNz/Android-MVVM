package com.task.ui.component.home.home

import android.view.View
import androidx.fragment.app.viewModels
import com.task.R
import com.task.databinding.FragmentHomeBinding
import com.task.ui.base.BaseFragment

/**
 * Created by Amandeep Chauhan
 */
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel by viewModels<HomeViewModel>()

    override fun initViewBinding(view: View) = FragmentHomeBinding.bind(view)

    override fun setupViews() {
        viewModel.text.observe(viewLifecycleOwner) {
            binding.textHome.text = it
        }
    }

    override fun observeViewModel() {
    }

}