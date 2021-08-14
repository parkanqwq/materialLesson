package com.kalabukhov.app.materiallesson.ui.api.collapsing_bar

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ActionBarOverlayLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.kalabukhov.app.materiallesson.R
import com.kalabukhov.app.materiallesson.R.animator.tab_btn_return
import kotlinx.android.synthetic.main.activity_collapsing_toolbar_layout.*

class CollapsingToolbarLayout : AppCompatActivity() {
    var show = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collapsing_toolbar_layout)

       // main_backdrop1.setOnClickListener { if (show) hideComponents() else showComponents()}

        var chack = false
        fab_btn_for_anim.setOnClickListener {
            if (!chack) {
                frameBackTem.visibility = View.VISIBLE
                fab_btn_for_anim.animate()
                    .rotation(225f)
                    .duration = 700L
                chipMenu.animate()
                    .x(250f)
                    .duration = 300L
                chipMenu2.animate()
                    .x(320f)
                    .duration = 500L
                chipMenu3.animate()
                    .x(350f)
                    .duration = 700L
                chack = true
            } else {
                frameBackTem.visibility = View.GONE
                fab_btn_for_anim.animate()
                    .rotation(0f)
                    .duration = 700L
                chipMenu.animate()
                    .x(1000f)
                    .duration = 300L
                chipMenu2.animate()
                    .x(1000f)
                    .duration = 500L
                chipMenu3.animate()
                    .x(1000f)
                    .duration = 700L
                chack = false
            }
        }
    }

//    private fun showComponents(){
//        show = true
//
//        val constraintSet = ConstraintSet()
//        constraintSet.clone(this, R.layout.activity_collapsing_toolbar_layout_end)
//
//        val transition = ChangeBounds()
//        transition.interpolator = AnticipateOvershootInterpolator(1f)
//        transition.duration = 1200
//
//        TransitionManager.beginDelayedTransition(transition_position, transition)
//        constraintSet.applyTo(transition_position)
//    }
//
//    private fun hideComponents(){
//        show = false
//
//        val constraintSet = ConstraintSet()
//        constraintSet.clone(this, R.layout.activity_collapsing_toolbar_layout)
//
//        val transition = ChangeBounds()
//        transition.interpolator = AnticipateOvershootInterpolator(1f)
//        transition.duration = 1200
//
//        TransitionManager.beginDelayedTransition(transition_position, transition)
//        constraintSet.applyTo(transition_position)
//    }
}