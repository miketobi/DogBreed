# DogBreed App

This is a simple app that shows a list of dog breeds and subbreeds uing MVVM Architecture.

## Introduction
The application is written entirely in kotlin and Jetpack Compose.

It app contains two screens;
* The first screen shows the lists of all master breeds and their subbreeds listed as tags. 
  * clicking on a master breed card passes it's name as argument to load all images of the master breed on the second screen
  * clicking on a tag (subbreed), passes the master breed name and the subbreed 
    name as argument to load all images of the subbreed tied to the master breed
* The second screen simply shows the list of images, since both master breeds and subbreeds images are the same, this screen is re-used to show either of them, and the logic for deciding which endpoint is called remains in the viewmodel

## API
It uses [Dog CEO API](https://dog.ceo/dog-api/documentation/) to fetch list of master breeds and subbreeds

## Data flow
The app uses a unidirectional flow of data pattern, here, the viewmodel houses the state of the UI and passes it down the screen, while events goes up

## Libraries used
* [Dagger Hilt](https://developer.android.com/training/dependency-injection/hilt-android): for dependency injection
* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel): for housing UI-related data
* [Retrofit](https://square.github.io/retrofit/): for making network request
* [Coil](https://coil-kt.github.io/coil/compose): for loading images
* [Coroutine](https://developer.android.com/kotlin/coroutines): for making executes asynchronous call

## Clone project
Use Android Studio 4.0 and above

## License
```
MIT License

Copyright (c) 2020 Tobiloba Oyelekan

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software isfurnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
