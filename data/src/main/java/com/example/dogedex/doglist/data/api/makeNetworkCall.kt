package com.example.dogedex.doglist.data.api

import com.example.dogedex.doglist.domain.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.UnknownHostException

suspend fun <T> makeNetworkCall(
    call: suspend () -> T
): ApiResponse<T> = withContext(Dispatchers.IO) {
    try {
        ApiResponse.Success(call())
    } catch (e: UnknownHostException) {
        ApiResponse.Error("Sin conexión a internet. Por favor verifica tu red.")
    } catch (e: HttpException) {
        ApiResponse.Error("Error en el servidor (Código: ${e.code()})")
    } catch (e: Exception) {
        ApiResponse.Error(e.localizedMessage ?: "Ocurrió un error inesperado al conectar")
    }
}
