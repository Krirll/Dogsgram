package ru.krirll.dogsgram.data.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ru.krirll.dogsgram.domain.model.Dog

data class DogDto(

    @SerializedName("message")
    @Expose
    val urlList: List<String>
)
