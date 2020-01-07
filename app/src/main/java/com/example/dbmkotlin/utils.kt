package com.example.dbmkotlin

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

fun Activity.toast(mensaje: String){
    Toast.makeText(this,mensaje, Toast.LENGTH_SHORT).show()
}
fun ViewGroup.inflate(layoutid: Int): View {
    return LayoutInflater.from(context).inflate(layoutid, this , false)
}