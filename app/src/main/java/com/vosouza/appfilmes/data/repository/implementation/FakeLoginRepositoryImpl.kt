package com.vosouza.appfilmes.data.repository.implementation

import com.vosouza.appfilmes.data.repository.LoginRepository
import javax.inject.Inject


class FakeLoginRepositoryImpl @Inject constructor() : LoginRepository {
    override fun login(password: Int, userName: String): Boolean {
        return password == 123 && userName == "user"
    }
}