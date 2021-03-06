package com.smarttoolfactory.tutorial6_5navigationui_viewpager2_fragmenttoolbar_mixednavigation.navhost

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.smarttoolfactory.tutorial6_5navigationui_viewpager2_fragmenttoolbar_mixednavigation.R
import com.smarttoolfactory.tutorial6_5navigationui_viewpager2_fragmenttoolbar_mixednavigation.blankfragment.BaseDataBindingFragment
import com.smarttoolfactory.tutorial6_5navigationui_viewpager2_fragmenttoolbar_mixednavigation.databinding.FragmentNavhostParentBinding
import com.smarttoolfactory.tutorial6_5navigationui_viewpager2_fragmenttoolbar_mixednavigation.viewmodel.AppbarViewModel


/**
 * Using [FragmentStateAdapter.registerFragmentTransactionCallback] with [FragmentStateAdapter] solves back navigation instead of using [OnBackPressedCallback.handleOnBackPressed] in every [NavHostFragment]
 * ### Should set app:defaultNavHost="true" for [NavHostFragment] for this to work
 */
class ParentNavHostFragment : BaseDataBindingFragment<FragmentNavhostParentBinding>() {
    override fun getLayoutRes(): Int = R.layout.fragment_navhost_parent

    private var navController: NavController? = null

    private val nestedNavHostFragmentId = R.id.nested_nav_host_fragment_parent

    private val appbarViewModel by activityViewModels<AppbarViewModel>()

//    private val viewModel:AppbarViewModel by navGraphViewModels(R.id.parent_dest)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val nestedNavHostFragment =
            childFragmentManager.findFragmentById(nestedNavHostFragmentId) as? NavHostFragment
        navController = nestedNavHostFragment?.navController

        // Listen on back press
        listenOnBackPressed()

        listenBackStack()

        val appBarConfig = AppBarConfiguration(navController!!.graph)
        dataBinding!!.toolbar.setupWithNavController(navController!!, appBarConfig)


        appbarViewModel.appbarParentVisibility.observe(viewLifecycleOwner, Observer { visible ->
            if (visible) {
                dataBinding!!.appbar.visibility = View.VISIBLE
            } else {
                dataBinding!!.appbar.visibility = View.GONE
            }
        })

    }


    private fun listenOnBackPressed() {
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    override fun onResume() {
        super.onResume()
        println("🏰 ${this.javaClass.simpleName} onResume()")
//        callback.isEnabled = true
    }

    override fun onPause() {
        super.onPause()
        println("🏰 ${this.javaClass.simpleName} onPause()")
//        callback.isEnabled = false
    }

    /**
     * This callback should be created with Disabled because on rotation ViewPager creates
     * NavHost fragments that are not on screen, destroys them afterwards but it might take
     * up to 5 seconds.
     *
     * ### Note: During that interval touching back button sometimes call incorrect [OnBackPressedCallback.handleOnBackPressed] instead of this one if callback is **ENABLED**
     */
//    val callback = object : OnBackPressedCallback(false) {
//
//        override fun handleOnBackPressed() {
//
//            // Check if it's the root of nested fragments in this navhosts
//            if (navController?.currentDestination?.id == navController?.graph?.startDestination) {
//
//                Toast.makeText(requireContext(), "🏂 AT START DESTINATION ", Toast.LENGTH_SHORT)
//                    .show()
//
//                /*
//                    Disable this callback because calls OnBackPressedDispatcher
//                     gets invoked  calls this callback  gets stuck in a loop
//                 */
//                isEnabled = false
//                requireActivity().onBackPressed()
//                isEnabled = true
//
//            } else if (isVisible) {
//                navController?.navigateUp()
//            }
//
//        }
//
//    }


    private fun listenBackStack() {

        // Get NavHostFragment
        val navHostFragment =
            childFragmentManager.findFragmentById(nestedNavHostFragmentId)
        // ChildFragmentManager of the current NavHostFragment
        val navHostChildFragmentManager = navHostFragment?.childFragmentManager

        navHostChildFragmentManager?.addOnBackStackChangedListener {

            val backStackEntryCount = navHostChildFragmentManager.backStackEntryCount
            val fragments = navHostChildFragmentManager.fragments
            val currentDestination = navController?.currentDestination

            fragments.forEach {

                println(
                    " 🏠 ${this.javaClass.simpleName} handleOnBackPressed() " +
                            "fragment: ${it.javaClass.simpleName} #${it.hashCode()}," +
                            "backStackEntryCount: $backStackEntryCount, " +
                            " isVisible: ${it.isVisible}, " +
                            " isVisible: ${it.isVisible}, " +
                            ", isResumed: ${it.isResumed}, " +
                            "currentDestination: ${currentDestination!!}, DEST ID: ${currentDestination.id}, " +
                            "startDestination: ${navController!!.graph.startDestination}"
                )
            }


            Toast.makeText(
                requireContext(),
                "🏠 ${this.javaClass.simpleName} ChildFragmentManager backStackEntryCount: $backStackEntryCount",
                Toast.LENGTH_SHORT
            ).show()
        }

    }


}