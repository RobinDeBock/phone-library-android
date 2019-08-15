
# Phone library Android

This is an Android application for mobile phones and tablets. It gives access to an overview of specifications for a vast variety of mobile devices. The data comes from the free and open [Fono Api](https://fonoapi.freshpixl.com). As of yet there are two languages supported: *English* and *Dutch*.

There is also an [iOS version](https://github.com/RobinDeBock/phone-library) version of this application, go check it out!.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. 

### Prerequisites

To access the code or run the application, you will require

```
Android Studio
```

### Installing

* Clone the repository from GitHub.
```
git clone https://github.com/RobinDeBock/phone-library-android.git
```
* Open in Android Studio
* Connect a device / start an AVD (Android Virtual Device)
* Press the green start arrow in Android Studio
* Select the device

Enjoy!

## Running the tests

Both the Unit Tests and UI tests can be run using Android Studio in the same manner as described above.
Every test has a clear name which describes it's function, but there is additional documentation supplied for each test class.

### Unit Tests

There are two Unit Tests to check the 'Device' class. These tests include property checks and a check on the supplied comparators for sorting.

```
DeviceSortingUnitTest
DeviceUnitTest
```

### UI tests

These use Espresso and test the application by mimicking certain user behaviour. It's important to remove the application from the device or clear the local storage beforehand. These can be run when the application is using a different locale.
```
BackpressNavigationTest
InvalidSearchTermShowsToastTest
NavigationDrawerAndStartScreenTest
SearchFavoriteShowsIconInListTest
...
```

## Built With

* [Fuel](https://github.com/kittinunf/fuel) - Network calls core package
* [Moshi](https://github.com/square/moshi) - JSON deserialization
* [RxAndroid](https://github.com/ReactiveX/RxAndroid) - Advanced observables
* [Dagger](https://github.com/google/dagger) - Dependency injection
* [Room](https://github.com/googlecodelabs/android-room-with-a-view) - Persistence library (local storage)
* [Anko](https://github.com/Kotlin/anko) - Used for async tasks
* [quiph/RecyclerViewFastScroller](https://github.com/quiph/RecyclerView-FastScroller) - Fast scroll a recycler view

## Contributing

Contributions are more than welcome! If you found a bug in our code or wish to suggest a feature, choose one of these options.

-   Fork this repository, make changes, open a pull request
-   Open an issue, give a good title and description

I'll review these as fast as possible. Thanks for your cooperation!

## Versioning

This repository ([GitHub](https://github.com/)) is used as the only versioning system.

## Authors

* **Robin De Bock** - *Sole author* - [RobinDeBock](https://github.com/RobinDeBock)

## License

This product is free of licensing.

## Acknowledgments

* Big thanks to [shakee93](https://github.com/shakee93) who provides this public API.
* Many thanks to [shahsurajk](https://github.com/shahsurajk) for providing fast support for problems with the RecyclerViewFastScroller. It now works like a charm.
