package com.coursework.domain.user.usecases

import com.coursework.domain.CurrentUserTypeMock
import com.coursework.domain.user.model.UserType

class GetUserTypeUseCase {

    suspend operator fun invoke(): UserType {
        // TODO: Not yet implemented
        return CurrentUserTypeMock.userType
    }
}