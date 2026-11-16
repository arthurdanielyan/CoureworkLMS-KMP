package com.coursework.domain.user

import com.coursework.domain.user.model.UserType

interface UserRepository {

    suspend fun getUserType(): Result<UserType>
}
