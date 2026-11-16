package com.coursework.data.user.mapper

import com.coursework.domain.user.model.UserType
import com.coursework.utils.Mapper

internal class UserTypeMapper : Mapper<String, UserType> {

    override fun map(from: String): UserType {
        return when (from) {
            "student" -> UserType.Student
            // The app has no admin screens yet, so an admin gets the teacher's.
            else -> UserType.Teacher
        }
    }
}
