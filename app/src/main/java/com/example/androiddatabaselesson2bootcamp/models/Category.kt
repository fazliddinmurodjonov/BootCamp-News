package com.example.androiddatabaselesson2bootcamp.models

import java.io.Serializable

data class Category(val title: String, val article: ArrayList<BSWType>): Serializable
