package com.example.myapplication.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.Person
import com.example.myapplication.tracker.ItemDetail
import kotlinx.android.synthetic.main.item_row.view.*

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    private var listPerson: List<Person> = arrayListOf()
    lateinit var selectionTracker: SelectionTracker<Long>

    init {
        setHasStableIds(true)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = listPerson[position]
        holder.bind(person, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listPerson.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun addItemList(listPerson: List<Person>){
        this.listPerson = listPerson
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        lateinit var itemDetail: ItemDetail

        fun bind(person: Person, position: Int) = with(view) {

            itemDetail = ItemDetail(person, position)

            isActivated = selectionTracker.isSelected(itemDetail.selectionKey)

            list_item_name.text = person.name
            list_item_phone.text = person.phone
        }
    }
}