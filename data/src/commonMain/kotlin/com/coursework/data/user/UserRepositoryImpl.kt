package com.coursework.data.user

import com.coursework.data.user.mapper.UserTypeMapper
import com.coursework.domain.user.UserRepository
import com.coursework.domain.user.model.UserType
import com.coursework.utils.runSuspendCatching

internal class UserRepositoryImpl(
    private val userApi: UserApi,
    private val userTypeMapper: UserTypeMapper,
) : UserRepository {

    override suspend fun getUserType(): Result<UserType> {
        return runSuspendCatching {
            userTypeMapper(userApi.getMe().role)
        }
    }
}
