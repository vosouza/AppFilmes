package com.vosouza.appfilmes.data.repository

interface LoginRepository{
    fun login(password: Int, userName: String): Boolean
}