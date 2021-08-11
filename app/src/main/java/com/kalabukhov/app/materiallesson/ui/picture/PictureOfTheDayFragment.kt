package com.kalabukhov.app.materiallesson.ui.picture

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import coil.api.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import com.kalabukhov.app.materiallesson.R
import com.kalabukhov.app.materiallesson.ui.MainActivity
import com.kalabukhov.app.materiallesson.ui.api.ApiActivity
import com.kalabukhov.app.materiallesson.ui.api.collapsing_bar.CollapsingToolbarLayout
import com.kalabukhov.app.materiallesson.ui.chips.ChipsFragment
import com.kalabukhov.app.materiallesson.ui.themesForApp
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.main_fragment.*

var choiceDate: Long = 0
class PictureOfTheDayFragment : Fragment() {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProviders.of(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getData()
            .observe(this@PictureOfTheDayFragment, { renderData(it) })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))
//        bottomSheetBehavior.addBottomSheetCallback(object :
//            BottomSheetBehavior.BottomSheetCallback() {
//            override fun onStateChanged(bottomSheet: View, newState: Int) {
//                when (newState) {
//                    BottomSheetBehavior.STATE_DRAGGING -> TODO("not implemented")
//                    BottomSheetBehavior.STATE_COLLAPSED -> TODO("not implemented")
//                    BottomSheetBehavior.STATE_EXPANDED -> TODO("not implemented")
//                    BottomSheetBehavior.STATE_HALF_EXPANDED -> TODO("not implemented")
//                    BottomSheetBehavior.STATE_HIDDEN -> TODO("not implemented")
//                    BottomSheetBehavior.STATE_SETTLING -> TODO("not implemented")
//                }
//            }
//
//            override fun onSlide(bottomSheet: View, slideOffset: Float) {
//                TODO("not implemented")
//            }
//        })
        input_layout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${input_edit_text.text.toString()}")
            })
        }
        chipGroup.setOnCheckedChangeListener{group,checkedId:Int ->
            val chip: Chip? = view.findViewById(checkedId)
            chip?.let {
                changeDays(it.text.toString())
            }
        }
       setBottomAppBar(view)
    }

    private fun changeDays(day: String) {
        if (day == "toDay") {
            choiceDate = 0
            viewModel.getData()
                .observe(this@PictureOfTheDayFragment, { renderData(it) })
        }
        if (day == "1 last day") {
            choiceDate = 1
            viewModel.getData()
                .observe(this@PictureOfTheDayFragment, { renderData(it) })
        }
        if (day == "2 last day") {
            choiceDate = 2
            viewModel.getData()
                .observe(this@PictureOfTheDayFragment, { renderData(it) })
        }
        if (day == "3 last day") {
            choiceDate = 3
            viewModel.getData()
                .observe(this@PictureOfTheDayFragment, { renderData(it) })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
            R.id.app_bar_settings -> activity
                ?.supportFragmentManager
                ?.beginTransaction()
                ?.add(R.id.container, ChipsFragment())
                ?.addToBackStack(null)?.commit()
            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(it.supportFragmentManager, "tag")
                }
            }
            R.id.app_bar_lesson3 -> {
                activity?.let { startActivity(Intent(it, ApiActivity::class.java)) }
            }
            R.id.app_bar_lesson4 -> {
                activity?.let { startActivity(Intent(it, CollapsingToolbarLayout::class.java)) }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                val tittle = serverResponseData.title
                val textAll = serverResponseData.explanation
                if (url.isNullOrEmpty()) {
                    //showError("Сообщение, что ссылка пустая")
                    toast("Link is empty")
                } else {
                    //showSuccess()
                    bottom_sheet_description_header.text = tittle
                    bottom_sheet_description.text = textAll
                    image_view.load(url) {
                        lifecycle(this@PictureOfTheDayFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
                    }
                }
            }
            is PictureOfTheDayData.Loading -> {
                //showLoading()
            }
            is PictureOfTheDayData.Error -> {
                //showError(data.error.message)
                toast(data.error.message)
            }
        }
    }

    private fun setBottomAppBar(view: View) {
        (requireContext() as MainActivity).setSupportActionBar(view.findViewById(R.id.bottom_app_bar))
        setHasOptionsMenu(true)
        fab.setOnClickListener {
            if (isMain) {
                isMain = false
                bottom_app_bar.navigationIcon = null
                bottom_app_bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                fab.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_back_fab))
                bottom_app_bar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
            } else {
                isMain = true
                bottom_app_bar.navigationIcon =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_hamburger_menu_bottom_bar)
                bottom_app_bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                fab.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_plus_fab))
                bottom_app_bar.replaceMenu(R.menu.menu_bottom_bar)
            }
        }
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
        private var isMain = true
    }
}
