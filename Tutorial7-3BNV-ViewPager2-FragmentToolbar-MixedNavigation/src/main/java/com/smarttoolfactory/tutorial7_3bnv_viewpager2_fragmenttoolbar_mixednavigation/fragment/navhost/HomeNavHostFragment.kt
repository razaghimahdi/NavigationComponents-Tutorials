package com.smarttoolfactory.tutorial7_3bnv_viewpager2_fragmenttoolbar_mixednavigation.fragment.navhost

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.smarttoolfactory.tutorial7_3bnv_viewpager2_fragmenttoolbar_mixednavigation.R
import com.smarttoolfactory.tutorial7_3bnv_viewpager2_fragmenttoolbar_mixednavigation.databinding.FragmentNavhostNotificationBinding
import com.smarttoolfactory.tutorial7_3bnv_viewpager2_fragmenttoolbar_mixednavigation.fragment.blankfragment.BaseDataBindingFragment
import com.smarttoolfactory.tutorial7_3bnv_viewpager2_fragmenttoolbar_mixednavigation.util.Event
import com.smarttoolfactory.tutorial7_3bnv_viewpager2_fragmenttoolbar_mixednavigation.viewmodel.NavControllerViewModel


class HomeNavHostFragment : BaseDataBindingFragment<FragmentNavhostNotificationBinding>() {
    override fun getLayoutRes(): Int = R.layout.fragment_navhost_home

    private val navControllerViewModel by activityViewModels<NavControllerViewModel>()

    private var navController: NavController? = null

    private val nestedNavHostFragmentId = R.id.nested_nav_host_fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nestedNavHostFragment =
            childFragmentManager.findFragmentById(nestedNavHostFragmentId) as? NavHostFragment
        navController = nestedNavHostFragment?.navController

    }

    override fun onResume() {
        super.onResume()
        // Set this navController as ViewModel's navController
        navControllerViewModel.currentNavController.value = Event(navController)
    }

    override fun onDestroyView() {
        navController = null
        navControllerViewModel.currentNavController.value = Event(null)
        super.onDestroyView()
    }


//    override fun onResume() {
//        super.onResume()
//
//        // Set this navController as ViewModel's navController
//        navController?.let {
//            navControllerViewModel.currentNavController.value = Event(it)
//        }
//    }
//
//    override fun onDestroyView() {
//        navController = null
//        super.onDestroyView()
//    }
}