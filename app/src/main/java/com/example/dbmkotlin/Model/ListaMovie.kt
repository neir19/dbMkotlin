package com.example.dbmkotlin.Model

data class ListaMovie(var lista: ArrayList<ResultsItem>) {
    public fun addItem(listt: ArrayList<ResultsItem>){
        for(ite: ResultsItem in listt){
            lista.add(ite)
        }

    }
}