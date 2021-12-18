# RandomUsers
A simple android application that shows random users and their details like name, avatar, location, etc.

## App Walhthrough
- At first launch, the data is fetched from a remote server when there is an internet connection. This is saved in a database, then, populated on the first screen (user list screen) with a few details.
- When a user is clicked, the second screen is shown (user details) with more details on the user.
- Subsequently, the data is not re-fetched from remote server except the database is `EMPTY`. Although, this is a random display of users but this is to avoid several calls on the endpoint for now.

## Code Structure
This is a single module app which uses the MVVM architectural design pattern with some other third-party libraries and Jetpack components.

### Libraries Used
1. Android Support Libraries
2. Dagger-Hilt for Dependency Injection
3. Retrofit for making network calls
4. OkHttp for Network Logging Interceptor
5. Moshi modern JSON library for Kotlin and Java
6. Room for local data persistence
7. Navigation Component for navigating through the app
8. Kotlin Coroutines with Flow for repository layer and LiveData for ViewModel and View Layers
9. Mockito for running local unit tests
10. MockWebServer to test HTTP/HTTPS calls

### Testing
Testing is done with the JUnit4 testing framework, and with Google Truth for making assertions. Mockk is used to provide mocks in some of the tests. The UI test is done using Espresso Recorder.

### If I had more time, I should
- Fix issues with the Android Studio JDK dependencies on MacBook M1.
- Improve running Unit test and its coverage
- Write more and running UI tests
- Improve the UI/UX
- Implement the data loading/display using pagination
