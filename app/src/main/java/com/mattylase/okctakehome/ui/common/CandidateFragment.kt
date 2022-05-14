package com.mattylase.okctakehome.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mattylase.okctakehome.R
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

abstract class CandidateFragment : Fragment() {

    lateinit var candidateAdapter: CandidateAdapter
    val viewModel by sharedViewModel<TakehomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_candidate, container, false)
        candidateAdapter = CandidateAdapter()
        root.findViewById<RecyclerView>(R.id.recyclerView).also {
            it.adapter = candidateAdapter

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

    abstract fun listenForUpdates()

}
