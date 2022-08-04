# [Android-MVVM](https://github.com/cHAuHaNz/Android-MVVM)


[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-android--best--practices-brightgreen.svg?style=flat)](https://android-arsenal.com/details/3/4975)  [![kotlin](https://img.shields.io/badge/Kotlin-1.7.xxx-brightgreen.svg)](https://kotlinlang.org/)  [![Room DB](https://img.shields.io/badge/Room%20DB-2.5.0.alpha02-brightgreen.svg)](https://developer.android.com/training/data-storage/room)  [![DataStore](https://img.shields.io/badge/DataStore-1.0.0-brightgreen.svg)](https://developer.android.com/topic/libraries/architecture/datastore)  [![coroutines](https://img.shields.io/badge/coroutines-asynchronous-red.svg)](https://kotlinlang.org/docs/reference/coroutines-overview.html)  [![Mockk](https://img.shields.io/badge/Mockk-testing-yellow.svg)](https://mockk.io/)      [![Junit5](https://img.shields.io/badge/Junit5-testing-yellowgreen.svg)](https://junit.org/junit5/)   [![Espresso](https://img.shields.io/badge/Espresso-testing-lightgrey.svg)](https://developer.android.com/training/testing/espresso/)  [![Hilt DI](https://img.shields.io/badge/Hilt-2.42-orange.svg)](https://dagger.dev/hilt/)  [![Kotlin-Android-Extensions](https://img.shields.io/badge/Kotlin--Android--Extensions-plugin-red.svg)](https://kotlinlang.org/docs/tutorials/android-plugin.html) [![MVVM](https://img.shields.io/badge/Clean--Code-MVVM-brightgreen.svg)](https://github.com/googlesamples/android-architecture)

![MVVM3](https://user-images.githubusercontent.com/1812129/68319232-446cf900-00be-11ea-92cf-cad817b2af2c.png)

Android-MVVM is a template of a client application architecture, proposed by John Gossman as an alternative to MVC and MVP patterns when using Data Binding technology. 
Its concept is to separate data presentation logic from business logic by moving it into particular class for a clear distinction.

**Why Promoting MVVM VS MVP:**
- ViewModel has Built in LifeCycleAwareness, on the other hand Presenter not, and you have to take this responsibility in your side.
- ViewModel doesn't have a reference for View, on the other hand Presenter still hold a reference for view, even if you made it as weak reference.
- ViewModel survive configuration changes, while it is your own responsibilities to survive the configuration changes in case of Presenter. (Saving and restoring the UI state)

**MVVM Best Practice:**
- Avoid references to Views in ViewModels.
- Instead of pushing data to the UI, let the UI observe changes to it.
- Distribute responsibilities, add a domain layer if needed.
- Add a data repository as the single-point entry to your data.
- Expose information about the state of your data using a wrapper or another LiveData.
- Consider edge cases, leaks and how long-running operations can affect the instances in your architecture.
- Don’t put logic in the ViewModel that is critical to saving clean state or related to data. Any call you make from a ViewModel can be the last one.


**What are Coroutines ?**
-------------------

 **Coroutine :**
Is light wight threads for asynchronous programming, Coroutines not only open the doors to
asynchronous programming, but also provide a wealth of other possibilities such as concurrency, actors, etc.

----------

**Coroutines VS RXJava**
-------------------
They're different tools with different strengths. Like a tank and a cannon, they have a lot of overlap but are more or less desirable under different circumstances.
- Coroutines Is light wight threads for asynchronous programming.
- RX-Kotlin/RX-Java is functional reactive programming, its core pattern relay on observer design pattern, 
  so you can use it to handle user interaction with UI while you still using coroutines as main core for background work.

**How does Coroutines concept work ?**
------------
 - Kotlin coroutine is a way of doing things asynchronously in a sequential manner. Creating a coroutine is a lot cheaper vs creating a thread.


**When I can choose Coroutines or RX-Kotlin to do some behaviour ?**
--------------------------
 - Coroutines : *When we have concurrent tasks, like you would fetch data from Remote connections , database, 
  any background processes, sure you can use RX in such cases too, but it looks like you use a tank to kill ant.*
 - RX-Kotlin : *When you would to handle stream of UI actions like : user scrolling, clicks, update UI upon some events .....ect .*

**What is the Coroutines benefits?**
-----------------------------

 - Writing an asynchronous code is sequential manner.
 - Costing of create coroutines are much cheaper to crate threads.
 - Don't be over engineered to use observable pattern, when no need to use it.
 - parent coroutine can automatically manage the life cycle of its child coroutines for you.


**What is Room DB?**
-----------------------------

The Room persistence library provides an abstraction layer over SQLite.
This library takes care most of complicated stuff that we previously had to do ourselves,
we will write much less boilerplate code to create tables and make database operations.
>   ### Sqlite in android is not that cool
>   - You need to write out a boilerplate code to convert between your java object and your sqlite object.
>   - It doesn't have compile time safety, if you building sqlite query and if you forgot to add comma, you going to get run time crash, that makes you very hard to test all those cases you put.
>   - When you are writing reactive application and you want to observe the databases changes to UI, sqlite doesn't facilitate to do that but Room is built to work with LiveData and RxJava for data observation.

[You can continue reading this article about Room DB here](https://gist.github.com/cHAuHaNz/e3acdf90b5d52bba025fe74df3ac2371)


**What is DataStore?**
-----------------------------
`DataStore` is a Jetpack data storage library that provides a safe and consistent way to store small amounts of data, such as preferences or application state. 
It’s based on Kotlin coroutines and Flow which enable asynchronous data storage. It aims to replace `SharedPreferences`, as it is thread-safe and non-blocking. 
It provides two different implementations: `Proto DataStore`, which stores typed objects (backed by protocol buffers) 
and `Preferences DataStore`, which stores key-value pairs.

>   ### DataStore vs SharedPreferences
>   Most likely you’ve used SharedPreferences in your apps. It is also likely that you’ve experienced issues with SharedPreferences 
>   that are hard to reproduce - seeing odd crashes in your analytics due to uncaught exceptions, blocking the UI thread when making 
>   calls or inconsistent, persisted data throughout your app. DataStore was built to address all of these issues.
>   
>   Let’s take a look at a direct comparison between SharedPreferences and DataStore:
> 
>   ![SharedPref vs DataStore](https://i.imgur.com/aKw00q8.png)

[You can continue reading this article about Room DB here](https://gist.github.com/cHAuHaNz/e3acdf90b5d52bba025fe74df3ac2371)


**Handle Retrofit with Coroutines**
-----------------------------

![Retrofit](https://i.imgur.com/2opItSZ.png) ![Kotlin Coroutines](https://i.imgur.com/pV2tInG.jpg)

 - Add Coroutines to your gradle file

>     // Add Coroutines
>     implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4'
>     implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4'
>     implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core-common:1.3.8'
>     // Add Retrofit2
>     implementation 'com.squareup.retrofit2:retrofit:2.9.0'
>     implementation 'com.squareup.retrofit2:converter-moshi:2.9.0'
>     implementation 'com.squareup.okhttp3:okhttp:5.0.0-alpha.5'


 - Make Retrofit Calls.


```kotlin
    @GET("/recipes.json")
    suspend fun fetchRecipes(): Response<List<RecipesItem>>
```

 - With ```async``` we create new coroutine and returns its future result as an implementation of [Deferred].
 - The coroutine builder called ```launch``` allow us to start a coroutine in background and keep working in the meantime.
 - so async will run in background then return its promised result to parent coroutine which
 created by launch.
 - when we get a result, it is up to us to do handle the result.



```kotlin
    viewModelScope.launch {
        recipesLiveDataPrivate.value = Resource.Loading()
        flow {
            emit(remoteRepository.fetchRecipes())
        }.collect {
            recipesLiveDataPrivate.value = it
        }
    }
  ```



**Keep your code clean according to MVVM**
-----------------------------
 - Yes, liveData is easy, powerful, but you should know how to use.
 - For livedata which will emit data stream, it has to be in your
   data layer, and don't inform those observables any thing else like
   in which thread those will consume, cause it is another
 - For livedata which will emit UI binding events, it has to be in your ViewModel Layer.
 - Observers in UI Consume and react to live data values and bind it.
   responsibility, and according to `Single responsibility principle`
  in `SOLID (object-oriented design)`, so don't break this concept by
   mixing the responsibilities .

  ![MVVM](https://i.imgur.com/9SVp1L1.png)


**Library reference resources:**
-----------------------------
- Hilt: https://developer.android.com/training/dependency-injection/hilt-android

- MVVM Architecture : https://developer.android.com/jetpack/guide

- Coroutines: https://developer.android.com/kotlin/coroutines

- Data Binding: https://developer.android.com/topic/libraries/data-binding

- View Binding: https://developer.android.com/topic/libraries/view-binding

- Leak Canary: https://square.github.io/leakcanary/

- Glide: https://github.com/bumptech/glide

- Retrofit: https://square.github.io/retrofit/

**Concept reference resources:**
-----------------------------
- RecyclerView Codelab: https://developer.android.com/codelabs/kotlin-android-training-recyclerview-fundamentals

- Repository Codelab: https://developer.android.com/codelabs/kotlin-android-training-repository

- Room and Coroutines Codelab: https://developer.android.com/codelabs/kotlin-android-training-room-database

- Hilt Codelab: https://developer.android.com/codelabs/android-hilt


## LICENSE
Copyright [2022] [Amandeep Chauhan]
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
