package com.example.dileit.model

import com.squareup.moshi.Json

class Contributor(@field:Json(name = "login") val username: String,
                  @field:Json(name = "avatar_url") val profileUrl: String
)