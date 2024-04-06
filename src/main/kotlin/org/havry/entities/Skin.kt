package org.havry.entities

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class Skin(
    @SerializedName("texture_id") val textureId: String,
    @SerializedName("user_uuid") val userUUID: UUID,
    val value: String,
    val signature: String
)