package com.example.gitgo.components.constants

import com.example.gitgo.BuildConfig

object ApiConstants {
     const val CLIENT_ID = BuildConfig.GITHUB_CLIENT_ID
     const val CLIENT_SECRET = BuildConfig.GITHUB_CLIENT_SECRET
     const val REDIRECT_URI = "gitgo://callback"
     const val AUTH_URL = "https://github.com/login/oauth/authorize"
     fun getFullAuthUrl(): String {
          return AUTH_URL +
                  "?client_id=$CLIENT_ID" +
                  "&redirect_uri=$REDIRECT_URI" +
                  "&scope=repo,user,workflow" +
                  "&state=gitgo_secure_state_123"
     }
}
