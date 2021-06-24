import com.example.valorant_agents.networking.ApiResponse
import com.example.valorant_agents.networking.BrandError
import com.example.valorant_agents.networking.RemoteError
import com.example.valorant_agents.networking.RemoteResponse
import retrofit2.Response

/**
 * Converts the retrofit [Response] that hold [ApiResponse] into [RemoteResponse].
 *
 * Custom implementation to convert [ApiResponse] into [RemoteResponse].
 * This implementation depends on the structure of [ApiResponse] model.
 *
 * @param T api response data type, and remote response body type.
 * @param E remote response error type.
 */
fun <T, E> ApiResponse<T>.toRemoteResponse(
    remoteError: (RemoteError) -> E,
    brandError: (BrandError) -> E
): RemoteResponse<T, E> {
    val body = this.data
    val st = this.status ?.let { it } ?: 500
    val success = st in 200..299
    val error = this.error

    return if (success) {
        if (body == null)
            RemoteResponse.createError(remoteError(RemoteError.general(Exception("ApiResponse body is null!"))))
        else
            RemoteResponse.createSuccess(body)
    } else {
        if (error == null)
            RemoteResponse.createError(remoteError(RemoteError.general(Exception("ApiResponse error is null, and result =$status"))))
        else
            RemoteResponse.createError(brandError(error))
    }
}

/**
 * Converts the retrofit [response] that holds [ApiResponse] into [RemoteResponse],
 * and safely map the exceptions to [RemoteResponse.Error].
 *
 * This is a custom implementation depends on [ApiResponse] structure.
 *
 * @param T retrofit body type, and remote response body type.
 * @param E remote response error type.
 */
fun <T, E> safeApiResponseCall(
    response: Response<ApiResponse<T>>,
    remoteError: (RemoteError) -> E,
    brandError: (BrandError) -> E
): RemoteResponse<T, E> {
    return safeApiCall(
        response = response,
        bodyToRemote = {
            it.toRemoteResponse(remoteError, brandError)
        },
        remoteError = remoteError
    )
}

/**
 * Converts the retrofit [Response] into [RemoteResponse].
 *
 * @param T retrofit body type.
 * @param R remote response body type.
 * @param E remote response error type.
 *
 * Note that this method will return ERROR remote response if retrofit response body was null.
 */
fun <T, R, E> Response<T>.toRemoteResponse(
    bodyToRemote: (T) -> RemoteResponse<R, E>,
    remoteError: (error: RemoteError) -> E
): RemoteResponse<R, E> {
    return if (isSuccessful) {
        val body = body()
        if (body == null) {
            RemoteResponse.createError(remoteError(RemoteError.general(Exception("empty response"))))
        } else {
            bodyToRemote(body)
        }
    } else {
        RemoteResponse.createError(remoteError(RemoteError.Failed(code())))
    }
}


/**
 * Converts the retrofit [response] into [RemoteResponse] by [Response.toRemoteResponse],
 * and wrap it inside a try catch to safely map the exception to [RemoteResponse.Error].
 *
 * @param T retrofit body type.
 * @param R remote response body type.
 * @param E remote response error type.
 *
 * Note that this method will return [RemoteResponse.Error] if retrofit response body was null.
 */
fun <T, R, E> safeApiCall(
    response: Response<T>,
    bodyToRemote: (T) -> RemoteResponse<R, E>,
    remoteError: (RemoteError) -> E
): RemoteResponse<R, E> {
    return try {
        response.toRemoteResponse(bodyToRemote, remoteError)
    } catch (e: Exception) {
        RemoteResponse.createError(remoteError(RemoteError.General(e)))
    }
}

/**
 * Converts the retrofit [response] that holds [ApiResponse] into [RemoteResponse],
 * and safely map the exceptions to [RemoteResponse.Error].
 *
 * This is a custom implementation depends on [ApiResponse] structure.
 *
 * Note that the desired error type is defined as [Exception].
 *
 * @param T retrofit body type, and remote response body type.
 *
 * @return [RemoteResponse] with [T] body, and error of type [Exception].
 */
fun <T> safeApiResponseCall(
    response: Response<ApiResponse<T>>
): RemoteResponse<T, Exception> {
    return safeApiResponseCall(
        response = response,
        remoteError = {
            it.asException()
        },
        brandError = {
            Exception(it.toString())
        }
    )
}