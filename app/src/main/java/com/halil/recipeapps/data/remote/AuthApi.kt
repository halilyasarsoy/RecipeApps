package com.halil.recipeapps.data.remote

import com.halil.recipeapps.data.model.User
import retrofit2.http.POST
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Field
import retrofit2.Response
//
//interface AuthApi {
//    @FormUrlEncoded
//    @POST("login")
//    suspend fun login(
//        @Field("username") username: String,
//        @Field("password") password: String
//    ): Response<User>
//}