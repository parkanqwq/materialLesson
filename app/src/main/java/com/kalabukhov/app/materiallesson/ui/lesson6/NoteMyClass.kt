package com.kalabukhov.app.materiallesson.ui.lesson6

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.kalabukhov.app.materiallesson.R
import kotlinx.android.synthetic.main.activity_recycler.*
import kotlinx.android.synthetic.main.lesson3.*

class NoteMyClass  : AppCompatActivity() {

    private var isNewList = false
    private lateinit var itemTouchHelper: ItemTouchHelper
    private lateinit var adapter: AdapterForNote

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)

        val data = arrayListOf(
             Pair(Data(0, "Header"), false)
        )

        adapter = AdapterForNote(
            object : AdapterForNote.OnListItemClickListener {
                override fun onItemClick(data: Data) {
                    Toast.makeText(this@NoteMyClass, data.someText, Toast.LENGTH_SHORT).show()
                }
            },
            data,
            object : AdapterForNote.OnStartDragListener {
                override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                    itemTouchHelper.startDrag(viewHolder)
                }
            }
        )

        recyclerView.adapter = adapter
        recyclerActivityFAB.setOnClickListener { adapter.appendItem(this) }
        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(recyclerView)
        recyclerActivityDiffUtilFAB.setOnClickListener { changeAdapterData() }
        //changeAdapterData()
    }

    private fun changeAdapterData() {
        adapter.setItems(createItemList(isNewList).map { it })
        isNewList = !isNewList
    }

    private fun createItemList(instanceNumber: Boolean): List<Pair<Data, Boolean>> {
        return when (instanceNumber) {
            false -> listOf(
                Pair(Data(0, "Header"), false),
                Pair(Data(1, "Mars", ""), false),
                Pair(Data(2, "Mars", ""), false),
                Pair(Data(3, "Mars", ""), false),
                Pair(Data(4, "Mars", ""), false),
                Pair(Data(5, "Mars", ""), false),
                Pair(Data(6, "Mars", ""), false)
            )
            true -> listOf(
                Pair(Data(0, "Header"), false),
                Pair(Data(1, "Mar", ""), false),
                Pair(Data(2, "Jupiter", ""), false),
                Pair(Data(3, "Ma", ""), false),
                Pair(Data(4, "Neptune", ""), false),
                Pair(Data(5, "Saturn", ""), false),
                Pair(Data(6, "M", ""), false)
            )
        }
    }
}