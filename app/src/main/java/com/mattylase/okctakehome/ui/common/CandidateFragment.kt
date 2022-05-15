package com.mattylase.okctakehome.ui.common

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mattylase.okctakehome.R
import com.mattylase.okctakehome.extras.logTag
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * CandidateFragments are used as the basic building blocks of our application due to the similarity
 * in the UI spec for Special Blend & Match %. This base class does our "boilerplate" initialization
 * for the RecyclerView and its associated classes
 */
abstract class CandidateFragment : Fragment() {

    lateinit var candidateAdapter: CandidateAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var loadingView: View
    lateinit var errorView: View
    val viewModel by sharedViewModel<TakehomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_candidate, container, false)
        loadingView = root.findViewById(R.id.loadingView)
        errorView = root.findViewById(R.id.loadingFailedView)

        candidateAdapter = CandidateAdapter()
        root.findViewById<RecyclerView>(R.id.recyclerView).also {
            it.adapter = candidateAdapter
            recyclerView = it

            val width = resources.getInteger(R.integer.grid_span_count)
            it.layoutManager = GridLayoutManager(context, width)
            it.addItemDecoration(
                GridItemDecoration(
                    resources.getDimension(R.dimen.grid_item_spacing),
                    gridWidth = width
                )
            )
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listenForUpdates()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val count = resources.getInteger(R.integer.grid_span_count)
        (recyclerView.layoutManager as GridLayoutManager).spanCount = count
        (recyclerView.getItemDecorationAt(0) as GridItemDecoration).gridWidth = count
        recyclerView.invalidateItemDecorations()
    }

    protected fun showLoadingView() {
        recyclerView.visibility = View.INVISIBLE
        errorView.visibility = View.GONE
        loadingView.visibility = View.VISIBLE
        AnimationUtils.loadAnimation(context, R.anim.heart_beat).also {
            loadingView.startAnimation(it)
        }
    }

    protected fun showRecyclerView() {
        recyclerView.visibility = View.VISIBLE
        errorView.visibility = View.GONE
        loadingView.clearAnimation()
        loadingView.visibility = View.INVISIBLE
    }

    protected fun showErrorView() {
        recyclerView.visibility = View.INVISIBLE
        errorView.visibility = View.VISIBLE
        loadingView.clearAnimation()
        loadingView.visibility = View.INVISIBLE
    }

    abstract fun listenForUpdates()

}
