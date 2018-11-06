package com.example.stgoa.gistlib.auth.services.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class AuthorizationRequest(
    @SerializedName("note") val note: String,
    @SerializedName("scopes") val scopes: Array<String>,
    @SerializedName("client_id") val clientId: String,
    @SerializedName("client_secret") val clientSecret: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AuthorizationRequest

        if (note != other.note) return false
        if (!Arrays.equals(scopes, other.scopes)) return false
        if (clientId != other.clientId) return false
        if (clientSecret != other.clientSecret) return false

        return true
    }

    override fun hashCode(): Int {
        var result = note.hashCode()
        result = 31 * result + Arrays.hashCode(scopes)
        result = 31 * result + clientId.hashCode()
        result = 31 * result + clientSecret.hashCode()
        return result
    }
}