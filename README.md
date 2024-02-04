## Overview

This Android application delivers comprehensive weather information for a designated location, offering insights into current weather conditions, a 48-hour hourly forecast, and a 15-day daily forecast. The app seamlessly integrates Visual Crossing Weather Data & API for accurate weather data retrieval.

## Features

- Two distinct activities: Home Weather Screen and Daily Forecast Screen.
- Adaptive layout for landscape orientation on the home weather screen.
- Effortless toggling between imperial and metric units (C/F) using the options-menu icon.
- Quick access to the daily forecast via the calendar icon in the options menu.
- Convenient location change facilitated through the location icon in the options menu.
- Display of a 7-day weather forecast in both portrait and landscape views.
- Time conversion, adept handling of no-network scenarios, and conversion of wind direction for user-friendly presentation.
- Seamless swipe refresh functionality for reloading updated weather data.
- Persistent storage of user settings, ensuring the selected location and units are remembered upon app restart.

## API Key

To harness weather data, it is imperative to establish a free account and procure an API key from Visual Crossing Weather Data & API. Register at [Visual Crossing Weather](https://www.visualcrossing.com/sign-up) to create your account and obtain the API key from the "Your Details" section under the "Key" label. Note that the free account allows up to 1000 calls per day.

## Application/Behavior Diagrams

### 1. App Main Screen

- A detailed Daily Weather RecyclerView showcasing current conditions, hourly forecast, and 48-hourly weather entries.
- Essential information such as location name, current date/time, temperature, feels like, humidity, UV index, sunrise, sunset, and more.
- Options menu offering toggling units, displaying daily forecast, and enabling location change.
- Inclusion of weather icons, wind details, visibility, and daily forecast entries.

### 2. 7-Day Weather Portrait & Landscape

- Displaying location, day & date, high/low temperature, description, precipitation probability, UV index, weather icon, and temperature details for the upcoming week.

### 3. Options Menu

- Change Units: Easily toggle between imperial and metric units.
- Daily Forecast: Direct access to the daily forecast activity.
- Change Location: User-friendly AlertDialog for entering a new location.

### 4. Weather Icons

- Integration of weather icons from JSON data mapped to image files in the drawable resource folder.

### 5. Handling No-Network Situations

- Intuitive display of "No internet connection" in the absence of a network.
- Automatic disabling of options-menu selections during no-network situations.

### 6. Time Conversions

- Seamless conversion of Epoch data fields to actual Date objects with customizable formatting.

### 7. Converting Wind Direction

- Clear presentation of wind direction using compass points (N, SE, W, etc.).

### 8. Other Features

- Swipe Refresh: Effortlessly reload weather data for the current location.
- Saving User Settings: Ensure the selected location and units persist for subsequent app restarts.

## Usage

1. Clone the repository.
2. Open the project in Android Studio.
3. Obtain an API key from Visual Crossing Weather Data & API and replace `YOUR_API_KEY` in the code with your key.
4. Build and run the app on an Android emulator or device.

## Permissions

Verify that the `ACCESS_NETWORK_STATE` and `INTERNET` permissions are properly declared in the project manifest.
