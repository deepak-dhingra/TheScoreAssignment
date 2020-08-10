# MVVM, Dagger2, Kotlin

**Reference:**
* MVVM architecture pattern is followed for this assignment.
* Kotlin language is used in this app.
* Dagger 2 library is used for the Dependency Injection.
* Architecture components are used in this assignment like DataBinding, LiveData, ViewModels and Navigation Component.
* Retrofit library is used for fetching data and GSON for parsing JSON response.
* Unit Testing and UI testing is done using JUnit, Mockito and Espresso.


**Requirements Completed**

* Request Data
  * Request NBA teams data from input.json endpoint using networking framework of your choice. **DONE**
  * Implement Network caching to cache the response.**DONE**
* The Team List screen
  * Displays all of the teams data in alphabetical order **DONE**
  * Each team's full_name, wins, and losses must be displayed **DONE**
  * When the user clicks a team it should launch their team page **DONE**
  * Allow the user to sort the list by Alphabetical order. **DONE**
  * Bonus: Allow the user to sort the list by wins or losses **DONE**
* The Team Page screen
  * Displays information about a specific Team selected from the Team List **DONE**
  * The team's full_name, wins, and losses must be displayed **DONE**
  * The team's roster must be displayed with each Player's first_name, last_name, position, number **DONE**
* Unit Tests (You must write valid unit tests to test your code)
  * Write Unit Tests to test and verify the functionality and behaviour. **DONE**
* Bonus: Ui Tests
  * Write UI (Instrumentation/Espresso/Robolectric) Tests to test and verify UI. **DONE**
