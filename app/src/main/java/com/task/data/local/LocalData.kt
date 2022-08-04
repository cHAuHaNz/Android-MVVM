package com.task.data.local

import android.content.Context
import com.task.SharedPrefKeys
import com.task.data.Resource
import com.task.data.dto.login.LoginRequest
import com.task.data.dto.login.LoginResponse
import com.task.data.error.PASS_WORD_ERROR
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

/**
 * Created by Amandeep Chauhan
 */
class LocalData @Inject constructor(val context: Context) {

    fun doLogin(loginRequest: LoginRequest): Resource<LoginResponse> {
        if (loginRequest == LoginRequest("chauhan@simplifiedbytes.in", "chauhan")) {
            return Resource.Success(LoginResponse("123", "Amandeep", "Chauhan",
                    "Sample Street", "69", "100001", "Chandigarh",
                    "India", "chauhan@simplifiedbytes.in"))
        }
        return Resource.DataError(PASS_WORD_ERROR)
    }

    fun getCachedFavourites(): Resource<Set<String>> {
        return runBlocking { Resource.Success(DataStore.getStringSet(SharedPrefKeys.FAVOURITES_KEY, setOf()).first()) }
    }

    fun isFavourite(id: String): Resource<Boolean> {
        return runBlocking {
            val cache = DataStore.getStringSet(SharedPrefKeys.FAVOURITES_KEY, setOf()).first()
            return@runBlocking Resource.Success(cache.contains(id))
        }
    }

    fun cacheFavourites(ids: Set<String>): Resource<Boolean> {
        return runBlocking {
            DataStore.saveStringSet(SharedPrefKeys.FAVOURITES_KEY, ids)
            return@runBlocking Resource.Success(true)
        }
    }

    fun removeFromFavourites(id: String): Resource<Boolean> {
        return runBlocking {
            val set = DataStore.getStringSet(SharedPrefKeys.FAVOURITES_KEY, mutableSetOf()).first().toMutableSet()
            if (set.contains(id)) {
                set.remove(id)
            }
            runBlocking { DataStore.saveStringSet(SharedPrefKeys.FAVOURITES_KEY, set) }
            return@runBlocking Resource.Success(true)
        }
    }
}