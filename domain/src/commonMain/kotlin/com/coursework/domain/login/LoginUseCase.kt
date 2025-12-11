package com.coursework.domain.login

import com.coursework.domain.CurrentUserTypeMock
import com.coursework.domain.user.model.UserType

class LoginUseCase {

    suspend operator fun invoke(userType: UserType): Result<Unit> {
        return runCatching {
            CurrentUserTypeMock.userType = userType
        }
    }
}