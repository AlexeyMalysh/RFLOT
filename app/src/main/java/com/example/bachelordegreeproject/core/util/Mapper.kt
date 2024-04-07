package com.example.bachelordegreeproject.core.util

interface Mapper<I, O> {
    fun map(input: I): O

    fun reverse(input: O): I = throw UnsupportedOperationException()
}