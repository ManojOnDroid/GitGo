package com.example.gitgo.components.constants

object ApiConstants {
     const val CLIENT_ID = "Ov23lix5zzw2NHEFmjWP"
     const val CLIENT_SECRET = "17836cd9e0a896c0d74080239f813e0560711f8c"
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
