package com.example.myapplication.tracker

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import com.example.myapplication.model.Person

class ItemDetail(private val person: Person, private val adapterPosition: Int) : ItemDetailsLookup.ItemDetails<Long>() {

    override fun getSelectionKey() = person.id

    override fun getPosition() = adapterPosition

    override fun inSelectionHotspot(e: MotionEvent) = true
}