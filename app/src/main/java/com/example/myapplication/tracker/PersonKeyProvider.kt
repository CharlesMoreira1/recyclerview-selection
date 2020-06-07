package com.example.myapplication.tracker

import androidx.recyclerview.selection.ItemKeyProvider
import com.example.myapplication.model.Person

class PersonKeyProvider(private val listPerson: List<Person>) : ItemKeyProvider<Long>(SCOPE_MAPPED) {

    override fun getKey(position: Int) = listPerson[position].id

    override fun getPosition(key: Long) = listPerson.indexOf(listPerson.single { person -> person.id == key })
}