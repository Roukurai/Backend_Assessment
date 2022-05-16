package com.example.backendassessment

data class ResponseItem(
    var tags:List<String>,
    var owner:ItemOwner,
    var is_answered:Boolean,
    var view_count:Int,
    var answer_count:Int,
    var score:Int,
    var last_activity_date:Int,
    var creation_date:Int,
    var question_id:Int,
    var content_license:String,
    var link:String,
    var title:String
)