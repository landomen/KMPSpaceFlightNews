# Space Flight News
## Kotlin Multiplatform / Compose Multiplatform Sample Project

This is a Kotlin Multiplatform project targeting Android, iOS, Desktop. It uses the Compose Multiplatform  
framework to share the same UI between all three platforms by leveraging Compose.

The app itself is a simple news reader that fetches Space Flight related news from the https://spaceflightnewsapi.net/ API.  
The articles are displayed in a list, with the option to view details of each article. The user can also open the article on the  
original website or share it through the system share sheet.

This is a companion sample to my series of articles **Building a Space Flight News App with Compose Multiplatform for Android, iOS, and Desktop**: https://medium.com/@domen.lanisnik/2b93ad3c0271?source=friends_link&sk=6d05f40abea32570129c223d6bd46387.

***Note:** the repository currently only contains the bolded functionality from the list below. With each new release of the article series, the repository will be updated with new features.*

***Note:** the `main` branch contains the latest state of the sample app after the latest part of the article series is released. You can find the end code for each part under the `part-X` branches.*

The app demonstrates the following features:
- **building UI with Compose and Material3**,
- **sharing view models across platforms**,
- **adding dependency injection using Koin**,
- **integrating a network layer using Ktor**,
- **integrating a database layer using SQLDelight to support offline mode**,
- **displaying remote images using Coil**,
- **adding an article details screen**,
- **navigating between screens using Navigation Compose**,
- **opening an URL in an external web browser**,
- **and opening the system share sheet.**

### Final app
<img src="/screenshots/android_final.gif" />