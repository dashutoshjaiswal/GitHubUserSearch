package com.example.githubusersearch.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.githubusersearch.R
import com.example.githubusersearch.data.Status
import com.example.githubusersearch.databinding.FragmentDetailsBinding
import com.example.githubusersearch.ui.interfaces.IProgressBarActivity
import com.example.githubusersearch.ui.interfaces.IProgressBarFragment
import com.example.githubusersearch.ui.interfaces.ISearchListenerActivity
import com.example.githubusersearch.utils.toastL
import com.example.githubusersearch.utils.toastS
import kotlinx.android.synthetic.main.fragment_details.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class DetailsFragment : Fragment(), KodeinAware,
    IProgressBarFragment {

    // DI
    override val kodein by kodein()
    private val factory: DetailsViewModelFactory by instance()

    // Navigation
    private val args: DetailsFragmentArgs by navArgs()

    private lateinit var viewModel: DetailsViewModel
    private lateinit var binding: FragmentDetailsBinding
    private var name: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        name = args.name
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, factory).get(DetailsViewModel::class.java)

        binding.apply {
            lifecycleOwner = this@DetailsFragment
        }

        viewModel.getRepo(name)

        initObserver()
    }

    // Observe data & update UI
    private fun initObserver() {
        viewModel.user.observe(viewLifecycleOwner, Observer { status ->
            when (status) {
                is Status.Loading -> showProgressBar()
                is Status.Success -> {
                    binding.repo = status.data
                    hideProgressBar()
                    bindProjectLink(status.data.htmlUrl)
                }
                is Status.Error -> {
                    requireContext().toastS(status.errorMessage)
                    hideProgressBar()
                }
            }
        })
    }

    // Show or hide activity's progressbar
    override fun showProgressBar() = (requireActivity() as IProgressBarActivity).show()
    override fun hideProgressBar() = (requireActivity() as IProgressBarActivity).hide()

    override fun onResume() {
        super.onResume()
        (requireActivity() as ISearchListenerActivity).showSearchView(false)
    }

    private fun bindProjectLink(url: String?) {

        project_link_container.setOnClickListener {
            if (TextUtils.isEmpty(url)) {
                requireActivity().toastL(resources.getString(R.string.message_no_url))
            } else {
                val defaultBrowser =
                    Intent.makeMainSelectorActivity(
                        Intent.ACTION_MAIN,
                        Intent.CATEGORY_APP_BROWSER
                    )
                defaultBrowser.data = Uri.parse(url)
                startActivity(defaultBrowser)
            }
        }

    }

}