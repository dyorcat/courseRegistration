package com.teamsparta.courseregistration.domain.course.dto

data class CourseResponse (
    val id: Long,
    val title: String,
    val description: String?,
    val status: String,
    val maxApplicants: Int,
    val numApplicants: Int,

)
