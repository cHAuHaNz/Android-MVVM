package com.task.ui.component.home.notifications

import android.view.View
import androidx.fragment.app.viewModels
import com.task.R
import com.task.databinding.FragmentNotificationsBinding
import com.task.ui.base.BaseFragment

/**
 * Created by Amandeep Chauhan
 */
class NotificationsFragment : BaseFragment<FragmentNotificationsBinding>(R.layout.fragment_notifications) {

    private val viewModel by viewModels<NotificationsViewModel>()

    override fun setupViews() {
    }

    override fun initViewBinding(view: View) = FragmentNotificationsBinding.bind(view)

    override fun observeViewModel() {
        viewModel.text.observe(viewLifecycleOwner) {
            binding.textNotifications.text = it
        }
    }
}