package com.vosouza.appfilmes.data.repository

interface LoginRepository{
    fun login(password: String, userName: String): Boolean
}