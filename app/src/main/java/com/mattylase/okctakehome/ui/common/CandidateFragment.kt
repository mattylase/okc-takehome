package com.mattylase.okctakehome.ui.common

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mattylase.okctakehome.R
import com.mattylase.okctakehome.extras.gone
import com.mattylase.okctakehome.extras.invisible
import com.mattylase.okctakehome.extras.logTag
import com.mattylase.okctakehome.extras.visible
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * CandidateFragments are used as the basic building blocks of our application due to the similarity
 * in the UI spec for Special Blend & Match %. This base class does our "boilerplate" initialization
 * for the RecyclerView and its associated classes
 */
abstract class CandidateFragment : Fragment() {

    val viewModel by sharedViewModel<TakehomeViewModel>()
    var candidateAdapter: CandidateAdapter? = null
    private var recyclerView: RecyclerView? = null
    private var loadingView: View? = null
    private var errorView: View? = null

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

    @SuppressLint("NotifyDataSetChanged")
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val count = resources.getInteger(R.integer.grid_span_count)

        recyclerView?.let {
            (it.layoutManager as GridLayoutManager).spanCount = count
            (it.getItemDecorationAt(0) as GridItemDecoration).gridWidth = count
            it.invalidateItemDecorations()
        }

        // Given the user expects some slight delay on a rotation as-is, using this to give Glide a
        // bump and to recalculate the image view bounds, since the cell size is dynamic
        candidateAdapter?.notifyDataSetChanged()
    }

    // Given our structure and not transitioning fragments/activities, I don't think
    // these will leak, but futureproofing it anyway
    override fun onDestroyView() {
        super.onDestroyView()
        recyclerView = null
        errorView = null
        loadingView = null
        candidateAdapter = null
    }

    protected fun showLoadingView() {
        recyclerView.invisible()
        errorView.gone()
        loadingView.visible()
        AnimationUtils.loadAnimation(context, R.anim.heart_beat).also {
            loadingView?.startAnimation(it)
        }
    }

    protected fun showRecyclerView() {
        recyclerView.visible()
        errorView.gone()
        loadingView?.clearAnimation()
        loadingView.invisible()
    }

    protected fun showErrorView() {
        recyclerView.invisible()
        errorView.visible()
        loadingView?.clearAnimation()
        loadingView.invisible()
    }

    abstract fun listenForUpdates()
}
