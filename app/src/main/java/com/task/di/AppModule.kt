package com.task.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.task.BuildConfig
import com.task.data.db.DatabaseService
import com.task.data.local.LocalData
import com.task.data.local.SharedPrefs
import com.task.data.remote.moshiFactories.MyKotlinJsonAdapterFactory
import com.task.data.remote.moshiFactories.MyStandardJsonAdapters
import com.task.data.remote.service.RecipesService
import com.task.utils.NetworkConnectivity
import com.task.utils.NetworkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideLocalRepository(@ApplicationContext context: Context): LocalData {
        return LocalData(context)
    }

    @Provides
    @Singleton
    fun provideCoroutineContext(): CoroutineContext {
        return Dispatchers.IO
    }

    @Provides
    @Singleton
    fun provideSharedPrefs(): SharedPrefs = SharedPrefs

    /**
     * We need to write @Singleton on the provide method if we are create the instance inside this method
     * to make it singleton. Even if we have written @Singleton on the instance's class
     */
    @Provides
    @Singleton
    fun provideDatabaseService(@ApplicationContext appContext: Context): DatabaseService =
        Room.databaseBuilder(
            appContext, DatabaseService::class.java,
            "Sample.db"
        ).build()

    @Provides
    @Singleton
    fun provideNetworkConnectivity(@ApplicationContext context: Context): NetworkConnectivity {
        return NetworkManager(context)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val builder = OkHttpClient.Builder()
            /** Uncomment below interceptor if you need to send headers in all requests like Authorization or API keys */
            /*.addInterceptor(Interceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .header("Authorization", "value")
                    .method(original.method, original.body)
                    .build()
                chain.proceed(request)
            })*/
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.apply { level = HttpLoggingInterceptor.Level.BODY }
            builder.addInterceptor(loggingInterceptor)
        }
        return Retrofit.Builder()
            .baseUrl("https://simplifiedbytes.in/").client(builder.build())
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(MyKotlinJsonAdapterFactory())
                        .add(MyStandardJsonAdapters.FACTORY)
                        .build()
                )
            ).build()
    }

    @Provides
    @Singleton
    fun providesRecipesService(retrofit: Retrofit): RecipesService {
        return retrofit.create(RecipesService::class.java)
    }
}
