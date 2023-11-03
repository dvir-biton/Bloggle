package com.fylora.blog.data.client

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BlogClientManager @Inject constructor(private val blogClient: BlogClient) {
    private val _responseFlow = MutableSharedFlow<Response>()
    val responseFlow = _responseFlow.asSharedFlow()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            blogClient.getResponse().collect { response ->
                _responseFlow.emit(response)
            }
        }
    }
}
