package com.example.bachelordegreeproject.core.util.constants

sealed class Result<out T>(val isSuccess: Boolean) {
    abstract val value: T?

    abstract fun <R> map(mapper: (T) -> R): Result<R>

    data object Loading : Result<Nothing>(isSuccess = false) {
        override val value: Nothing?
            get() = null

        override fun <R> map(mapper: (Nothing) -> R): Result<R> = throw NotImplementedError()

    }

    data class Success<T>(override val value: T) : Result<T>(isSuccess = true) {
        override fun <R> map(mapper: (T) -> R): Result<R> = Success(
            value = mapper.invoke(value)
        )

        override fun toString() = "Success - $value"
    }

    object Empty : Result<Nothing>(isSuccess = true) {
        override val value: Nothing?
            get() = null

        override fun <R> map(mapper: (Nothing) -> R): Result<R> = this

        override fun toString() = "Success"
    }

    data class Fail<out T>(
        val exception: Throwable? = null,
        val statusCode: Int? = null,
        val code: String? = null,
        val text: String? = null,
        val errorData: String? = null,
        override val value: T? = null,
    ) : Result<T>(isSuccess = false) {
        override fun <R> map(mapper: (T) -> R): Result<R> = Fail(
            exception, statusCode, code, text, errorData, value?.let(mapper)
        )

        override fun toString() = """
            ------------ HTTP ERROR ------------
            exception: $exception
            status: $statusCode
            message: $text
            ------------------------------------
        """.trimIndent()

        companion object
    }
}