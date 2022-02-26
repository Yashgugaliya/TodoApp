# TodoApp 📃
<p align="center" width="100%">
    <img src="https://user-images.githubusercontent.com/47205113/155833951-cdcfa64a-c446-42f3-88a9-c5f713903598.png">
</p>
<p align="center" width="100%">
    <img src="https://user-images.githubusercontent.com/47205113/155833958-807afd44-ecf3-4c9a-b7c4-d33ba925ed02.png">
</p>

**TodoApp** is a sample  Todo  Android application 📱 built to demonstrate use of *Modern Android development* tools. Dedicated to all Android Developers with ❤️. 

***You can Install and test latest TodoApp from below 👇***

[![ToDoApp](https://img.shields.io/badge/TodoApp📃-APK-red.svg?style=for-the-badge&logo=android)](https://github.com/Yashgugaliya/TodoApp/blob/master/TodoApp.apk)


## About
It simply stores **ToDo list** data in persistence storage (i.e. SQLite Database). 
- This makes it offline capable 😃. 
- Clean and Simple Material UI.
- It supports dark theme too 🌗.

*Dummy API is used in this app. JSON response is statically hosted [here](https://patilshreyas.github.io/DummyFoodiumApi/api/posts/)*.

## Built With 🛠
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more..
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) - A cold asynchronous data stream that sequentially emits values and completes normally or with an exception.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes. 
  - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
  - [Room](https://developer.android.com/topic/libraries/architecture/room) - SQLite object mapping library.
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.

# Package Structure
    
    com.example.todoapp     # Root Package
    
    ├── data                # For data handling.
    │   ├── helper          # Helper class     
    │   └── local           # Local Persistence Database. Room (SQLite) database
    |
    ├── model               # Model classes
    ├── repository          # Single source of data.           
    |
    ├── ui                  # Activity/View layer
    │   └── fragments       # Fragments layer
    |  
    ├── utils               # Utility Classes / Kotlin extensions  
    └── viewmodel           # ViewHolder for RecyclerView
  


## Architecture
This app uses [***MVVM (Model View View-Model)***](https://developer.android.com/jetpack/docs/guide#recommended-app-arch) architecture.

![](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png)

