package com.example.backendassessment

data class QueryResponse(var items:List<ResponseItem>, var has_more:Boolean, var quota_max:Int, var quota_remaining:Int)
