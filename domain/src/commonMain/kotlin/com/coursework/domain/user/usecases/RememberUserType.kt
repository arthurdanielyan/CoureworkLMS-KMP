package com.coursework.domain.user.usecases

import com.coursework.domain.CurrentUserTypeMock
import com.coursework.domain.user.UserRepository

// The screens still read the role from CurrentUserTypeMock, so a login fills it with the real one.
internal suspend fun UserRepository.rememberUserType(): Result<Unit> {
    return getUserType().map { userType ->
        CurrentUserTypeMock.userType = userType
    }
}
