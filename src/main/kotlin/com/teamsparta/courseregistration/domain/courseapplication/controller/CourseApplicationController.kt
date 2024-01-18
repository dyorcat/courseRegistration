package com.teamsparta.courseregistration.domain.courseapplication.controller

import com.teamsparta.courseregistration.domain.course.dto.UpdateCourseRequest
import com.teamsparta.courseregistration.domain.course.service.CourseService
import com.teamsparta.courseregistration.domain.courseapplication.dto.ApplyCourseRequest
import com.teamsparta.courseregistration.domain.courseapplication.dto.CourseApplicationResponse
import com.teamsparta.courseregistration.domain.courseapplication.dto.UpdateApplicationStatusRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RequestMapping("/course/{courseId}/applications")
@RestController
class CourseApplicationController(
    private val courseService: CourseService
) {

    @GetMapping
    fun getApplicationList(@PathVariable courseId: Long): ResponseEntity<List<CourseApplicationResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getCourseApplicationList(courseId))
    }

    @GetMapping("/{applicationId}")
    fun getApplication(
        @PathVariable courseId: Long,
        @PathVariable applicationId: Long
    ): ResponseEntity<CourseApplicationResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getCourseApplication(courseId, applicationId))
    }

    @PostMapping
    fun applyCourse(
        @PathVariable courseId: Long, @RequestBody applyCourseRequest: ApplyCourseRequest
    ): ResponseEntity<CourseApplicationResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.applyCourse(courseId, applyCourseRequest))
    }

    @PatchMapping("/{applicationId}")
    fun updateApplicationStatus(
        @PathVariable courseId: Long,
        @PathVariable applicationId: Long,
        @RequestBody updateApplicationStatusRequest: UpdateApplicationStatusRequest
    ): ResponseEntity<CourseApplicationResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.updateCourseApplicationStatus(courseId, applicationId, updateApplicationStatusRequest))
    }
}

