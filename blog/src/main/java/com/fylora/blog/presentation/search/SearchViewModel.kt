package com.fylora.blog.presentation.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fylora.blog.data.client.BlogClient
import com.fylora.blog.data.client.BlogClientManager
import com.fylora.blog.data.client.Request
import com.fylora.blog.data.client.Response
import com.fylora.blog.data.model.Account
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val blogClient: BlogClient,
    private val blogClientManager: BlogClientManager,
): ViewModel() {

    var query = mutableStateOf("")
        private set

    var isHintVisible = mutableStateOf(true)
        private set

    var error = mutableStateOf("")
        private set

    var accounts = mutableStateOf(emptyList<Account>())
        private set

    init {
        viewModelScope.launch {
            blogClientManager.responseFlow
                .collect {
                    when(it) {
                        is Response.AccountsResponse -> {
                            accounts.value = it.account
                            println("search event: accounts received")
                        }
                        is Response.ErrorResponse -> {
                            error.value = it.error
                            accounts.value = emptyList()
                            println("search event: error received")
                        }
                        else -> println("search event: action failed?")
                    }
                }
        }
    }

    fun onEvent(event: SearchEvent) {
        when(event) {
            is SearchEvent.OnSearchChange -> {
                query.value = event.query
            }
            is SearchEvent.OnUserClick -> {
                viewModelScope.launch {
                    blogClient.sendRequest(
                        Request.GetAccount(
                            event.userId
                        )
                    )
                }
            }
            SearchEvent.OnSearch -> {
                error.value = ""
                viewModelScope.launch {
                    blogClient.sendRequest(
                        Request.SearchAccounts(
                            query.value,
                            SEARCH_AMOUNT
                        )
                    )
                }
            }
            is SearchEvent.OnFocusChange -> {
                isHintVisible.value = !event.focus.isFocused
                        && query.value.isBlank()
            }
        }
    }

    companion object {
        const val SEARCH_AMOUNT = 50
    }
}