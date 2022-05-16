package com.example.backendassessment

data class QueryToCache(val query:String?,val page:Int, val pageSize:Int, val queryItems:MutableList<ResponseItem>)
