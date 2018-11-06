package com.example.stgoa.gistlib.auth.login.model

sealed class LoginUIModel {
    class ShowProgress(val show: Boolean) : LoginUIModel()
    class LoginFails(val error: Throwable) : LoginUIModel()
    object LoginSuccess : LoginUIModel()
}