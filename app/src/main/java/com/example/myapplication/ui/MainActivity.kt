package com.example.myapplication.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.model.Person
import com.example.myapplication.tracker.MyItemDetailsLookup
import com.example.myapplication.tracker.PersonKeyProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mainAdapter = MainAdapter()
    private lateinit var selectionTracker: SelectionTracker<Long>

    private val selectedListPerson = mutableListOf<Person>()

    private val listPerson = listOf(
        Person(1, "Alice", "555-0111"),
        Person(2, "Bob", "555-0119"),
        Person(3, "Carol", "555-0141"),
        Person(4, "Dan", "555-0155"),
        Person(5, "Eric", "555-0180"),
        Person(6, "Craig", "555-0145")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupView()
        populateAdapter()
    }

    private fun populateAdapter(){
        mainAdapter.addItemList(listPerson)
        mainAdapter.selectionTracker = selectionTracker
    }

    private fun setupView(){
        with(recyclerMain){
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mainAdapter
            setHasFixedSize(true)
        }

        setupTracker()

        buttonMain.setOnClickListener {
            getSelectedItems()
        }

        buttonMain2.setOnClickListener {
            selectionTracker.setItemsSelected(selectedListPerson.map { it.id }, true)
            Log.i("TAG", selectedListPerson.toString())
        }
    }

    private fun setupTracker() = with(recyclerMain){
        selectionTracker = SelectionTracker.Builder(
            "selection-1",
            recyclerMain,
            PersonKeyProvider(listPerson),
            MyItemDetailsLookup(this),
            StorageStrategy.createLongStorage())
            .withSelectionPredicate(object : SelectionTracker.SelectionPredicate<Long>() {
                override fun canSelectMultiple(): Boolean {
                    return true
                }
                override fun canSetStateForKey(key: Long, nextState: Boolean): Boolean {
                    if (nextState && selectionTracker.selection.size() >= 2) {
                        return false
                    }
                    return true
                }
                override fun canSetStateAtPosition(position: Int, nextState: Boolean): Boolean {
                    return true
                }})
            .build()
    }

    private fun getSelectedItems(){
        selectedListPerson.clear()

        selectionTracker.selection.forEach {
            val person = listPerson.single { p -> p.id == it }
            selectedListPerson.add(person)
        }

        selectionTracker.clearSelection()
    }
}
