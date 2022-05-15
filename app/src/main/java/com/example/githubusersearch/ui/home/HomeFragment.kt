package com.example.githubusersearch.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.githubusersearch.R
import com.example.githubusersearch.data.preference.PreferenceProvider
import com.example.githubusersearch.ui.interfaces.IProgressBarActivity
import com.example.githubusersearch.ui.interfaces.IProgressBarFragment
import com.example.githubusersearch.ui.interfaces.ISearchListenerActivity
import com.example.githubusersearch.utils.NetworkUtils
import com.example.githubusersearch.utils.hideKeyboard
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(),
    IProgressBarFragment {

    private val prefs by lazy { PreferenceProvider(requireContext()) }
    private lateinit var searchQuery: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initSearchListener()
        initNetworkChangesListener()
    }

    /** SearchView btn action */
    private fun initSearchListener() {
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && query.isNotEmpty()) {
                    val action = HomeFragmentDirections.actionSearch(query.trim())
                    Navigation.findNavController(search_view).navigate(action)
                    hideKeyboard()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    /** If network connection is missing UI will suggest to use cashed search.
     *  If app is running for the first time and there isn't cashed data,
     *  UI will suggest to check network connection. */
    private fun initNetworkChangesListener() {
        NetworkUtils.getNetworkLiveData(requireContext())
            .observe(viewLifecycleOwner, Observer { isConnected ->
                if (isConnected) {
                    offline_container.visibility = View.GONE
                    search_view.visibility = View.VISIBLE
                } else {
                    offline_container.visibility = View.VISIBLE
                    search_view.visibility = View.GONE
                    searchQuery = prefs.getSearchQuery()
                    if (searchQuery.isNotEmpty()) {
                        message_offline.visibility = View.GONE
                        message_offline_suggest.visibility = View.VISIBLE
                        last_search_container.visibility = View.VISIBLE
                        last_search_tv.text = searchQuery
                        last_search_container.setOnClickListener {
                            val action = HomeFragmentDirections.actionSearch(searchQuery)
                            Navigation.findNavController(search_view).navigate(action)
                        }
                    } else {
                        message_offline.visibility = View.VISIBLE
                        message_offline_suggest.visibility = View.GONE
                        last_search_container.visibility = View.GONE
                    }
                }
            })
    }

    override fun onResume() {
        super.onResume()
        hideProgressBar()
        (requireActivity() as ISearchListenerActivity).showSearchView(false)
    }

    // Show or hide activity's progressbar
    override fun showProgressBar() = (requireActivity() as IProgressBarActivity).show()
    override fun hideProgressBar() = (requireActivity() as IProgressBarActivity).hide()
}