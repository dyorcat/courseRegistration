package com.teamsparta.courseregistration.domain.lecture.controller

import com.teamsparta.courseregistration.domain.course.service.CourseService
import com.teamsparta.courseregistration.domain.lecture.dto.AddLectureRequest
import com.teamsparta.courseregistration.domain.lecture.dto.LectureResponse
import com.teamsparta.courseregistration.domain.lecture.dto.UpdateLectureRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/courses/{courseId}/lectures")
class LectureController(
    private val courseService: CourseService
) {

    @PreAuthorize("hasRole('TUTOR') or hasRole('STUDENT')")
    @GetMapping
    fun getLectureList(@PathVariable courseId: Long): ResponseEntity<List<LectureResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getLectureList(courseId))
    }

    @PreAuthorize("hasRole('TUTOR') or hasRole('STUDENT')")
    @GetMapping("/{lectureId}")
    fun getLecture(@PathVariable courseId: Long, @PathVariable lectureId: Long): ResponseEntity<LectureResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getLecture(courseId, lectureId))
    }

    @PreAuthorize("hasRole('TUTOR')")
    @PostMapping()
    fun addLecture(
        @PathVariable courseId: Long, @RequestBody addLectureRequest: AddLectureRequest
    ): ResponseEntity<LectureResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.addLecture(courseId,addLectureRequest))
    }

    @PreAuthorize("hasRole('TUTOR')")
    @PutMapping("/{lectureId}")
    fun updateLecture(
        @PathVariable courseId: Long,
        @PathVariable lectureId: Long,
        @RequestBody updateLectureRequest: UpdateLectureRequest
    ): ResponseEntity<LectureResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.updateLecture(courseId, lectureId, updateLectureRequest))
    }

    @PreAuthorize("hasRole('TUTOR')")
    @DeleteMapping("/{lectureId}")
    fun deleteLecture(
        @PathVariable courseId: Long,
        @PathVariable lectureId: Long
    ): ResponseEntity<Unit> {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }


}