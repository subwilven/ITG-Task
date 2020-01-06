package com.islam.task.ui.marvel_Details

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.islam.task.R
import com.islam.task.pojo.Item
import com.islam.task.pojo.Marvel
import com.islam.task.project_base.base.fragments.BaseSuperFragment
import com.islam.task.project_base.utils.ImageHandler.loadImage
import com.islam.task.ui.flipper.ItemsFlipperFragment
import kotlinx.android.synthetic.main.fragment_marvel_details.*


class MarvelDetailFragment : BaseSuperFragment<MarvelDetailViewModel>() {
    override var fragmentTag = "MarvelDetailFragment"
    lateinit var comicsAdapter: ItemsAdapter
    lateinit var seriesAdapter: ItemsAdapter
    lateinit var eventsAdapter: ItemsAdapter
    lateinit var storiesAdapter: ItemsAdapter

    companion object {
        fun newInstance(marvel: Marvel): MarvelDetailFragment {
            val fragment = MarvelDetailFragment()
            val bundle = Bundle()
            bundle.putSerializable("marvel", marvel)
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun onLaunch() {
        initContentView(R.layout.fragment_marvel_details)
        initToolbar(0, true)
        initViewModel(this, MarvelDetailViewModel::class.java)
    }

    override fun onViewCreated(view: View, viewModel: MarvelDetailViewModel, instance: Bundle?) {
        markScreenAsCompleted()
        val marvel = arguments?.get("marvel") as Marvel
        iv_marvel_thumb.loadImage(marvel.thumbnail.path + "." + marvel.thumbnail.extension)

        tv_marvel_name.text = marvel.name
        tv_marvel_descrption.text = marvel.description

        comicsAdapter = ItemsAdapter(viewModel)
        createRecyclerView(comicsAdapter,
                recyclerViewId = R.id.rv_comics,
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false))

        storiesAdapter = ItemsAdapter(viewModel)
        createRecyclerView(storiesAdapter,
                recyclerViewId = R.id.rv_stories,
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false))

        seriesAdapter = ItemsAdapter(viewModel)
        createRecyclerView(seriesAdapter,
                recyclerViewId = R.id.rv_series,
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false))

        eventsAdapter = ItemsAdapter(viewModel)
        createRecyclerView(eventsAdapter,
                recyclerViewId = R.id.rv_events,
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false))

        mViewModel.loadComics(R.id.rv_comics, marvel.id.toString())
        mViewModel.loadEvents(R.id.rv_events, marvel.id.toString())
        mViewModel.loadSeries(R.id.rv_series, marvel.id.toString())
        mViewModel.loadStories(R.id.rv_stories, marvel.id.toString())

    }

    override fun setUpObservers() {
        mViewModel.comics.observe(viewLifecycleOwner, Observer {
            comicsAdapter.setData(it.toMutableList())
        })

        mViewModel.events.observe(viewLifecycleOwner, Observer {
            eventsAdapter.setData(it.toMutableList())
        })

        mViewModel.series.observe(viewLifecycleOwner, Observer {
            seriesAdapter.setData(it.toMutableList())
        })

        mViewModel.stories.observe(viewLifecycleOwner, Observer {
            storiesAdapter.setData(it.toMutableList())
        })

        mViewModel.showImageFlipper.observes(viewLifecycleOwner, Observer {
            navigate(ItemsFlipperFragment.newInstance(it.second as ArrayList<Item>,it.first),addToBackStack = true)
        })
    }


}