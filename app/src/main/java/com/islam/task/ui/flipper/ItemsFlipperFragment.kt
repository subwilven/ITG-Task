package com.islam.task.ui.flipper

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.islam.task.R
import com.islam.task.pojo.Item
import com.islam.task.project_base.base.fragments.BaseSuperFragment
import com.islam.task.ui.marvel_Details.MarvelDetailViewModel
import kotlinx.android.synthetic.main.fragment_flipper.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import java.lang.Math.abs

class ItemsFlipperFragment : BaseSuperFragment<MarvelDetailViewModel>() {
    override var fragmentTag = "ItemsFlipperFragment"
    lateinit var mAdapter: ItemsFlipperAdapter

    companion object {
        fun newInstance(items: ArrayList<Item>, selectedItemIndex: Int): ItemsFlipperFragment {
            val fragment = ItemsFlipperFragment()
            val bundle = Bundle()
            bundle.putParcelableArrayList("items", items)
            bundle.putInt("selectedItemIndex", selectedItemIndex)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onLaunch() {
        initContentView(R.layout.fragment_flipper)
        initViewModel(this, MarvelDetailViewModel::class.java)
    }

    override fun onViewCreated(view: View, viewModel: MarvelDetailViewModel, instance: Bundle?) {
        mAdapter = ItemsFlipperAdapter()
        val viewPager = createViewPager(mAdapter)
        viewPager.offscreenPageLimit = 1

// Add a PageTransformer that translates the next and previous items horizontally
// towards the center of the screen, which makes them visible
        val nextItemVisiblePx = 24
        val currentItemHorizontalMarginPx = 24
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            // Next line scales the item's height. You can remove it if you don't want this effect
            page.scaleY = 1 - (0.25f * abs(position))
            // If you want a fading effect uncomment the next line:
            // page.alpha = 0.25f + (1 - abs(position))
        }
        viewPager.setPageTransformer(pageTransformer)

        val itemDecoration = HorizontalMarginItemDecoration(context!!)
        viewPager.addItemDecoration(itemDecoration)

        val items = arguments?.getParcelableArrayList<Item>("items")
        mAdapter.setData((items as List<Item>).toMutableList())

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                delay(100)
            }
            viewPager.currentItem = arguments?.getInt("selectedItemIndex", 0)!!

        }


        iv_close.setOnClickListener { activity?.onBackPressed() }
    }

    override fun setUpObservers() {
    }

    class HorizontalMarginItemDecoration(context: Context) :
            RecyclerView.ItemDecoration() {

        private val horizontalMarginInPx: Int = 24

        override fun getItemOffsets(
                outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
        ) {
            outRect.right = horizontalMarginInPx
            outRect.left = horizontalMarginInPx
        }

    }
}