package com.example.dogedex.doglist.data.api

import com.example.dogedex.doglist.domain.ApiResponse
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.UnknownHostException

data class ErrorApiResponse(
    @SerializedName("message") val message: String
)

suspend fun <T> makeNetworkCall(
    call: suspend () -> T
): ApiResponse<T> = withContext(Dispatchers.IO) {
    try {
        ApiResponse.Success(call())
    } catch (e: UnknownHostException) {
        ApiResponse.Error("Sin conexión a internet. Por favor verifica tu red.")
    } catch (e: HttpException) {
        val errorMessage = parseErrorMessage(e)
        ApiResponse.Error(errorMessage)
    } catch (e: Exception) {
        ApiResponse.Error(e.localizedMessage ?: "Ocurrió un error inesperado al conectar")
    }
}

private fun parseErrorMessage(httpException: HttpException): String {
    return try {
        val errorBody = httpException.response()?.errorBody()?.string()
        if (!errorBody.isNullOrEmpty()) {
            val errorResponse = Gson().fromJson(errorBody, ErrorApiResponse::class.java)
            errorResponse.message
        } else {
            "Error en el servidor (Código: ${httpException.code()})"
        }
    } catch (e: Exception) {
        "Error en el servidor (Código: ${httpException.code()})"
    }
}
