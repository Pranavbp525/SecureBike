# SecureBike 🚴‍♂️🔒

SecureBike is an Android Application designed to protect motorbikes from theft. It offers features like fuel tracking, navigation, and wireless lock and unlock capabilities. **Please note that this is solely an app; the physical hardware integration is still in progress.** The app fetches all its data from Firebase, and for full functionality, the corresponding hardware needs to send its data to Firebase for the app to retrieve and process it.

## Features 🌟

- **Toggle Parking Mode**: Easily switch your bike's parking mode on or off.
- **Real-time Location Tracking**: View the current location of your bike on a map.
- **Fuel Level Monitoring**: Keep an eye on your bike's fuel level.
- **Instant Notifications**: Receive immediate alerts if your parked bike's status changes.
- **User Authentication**: Secure user login and registration system.

## Java Classes 📚

### [HomeActivity.java](https://github.com/Pranavbp525/SecureBike/blob/master/app/src/main/java/com/example/securebike/HomeActivity.java)
- Main activity of the application.
- Provides a UI for toggling the parking mode.
- Fetches and displays the bike's status and fuel level from Firebase.
- Options to locate the bike and log out.

### [LoginActivity.java](https://github.com/Pranavbp525/SecureBike/blob/master/app/src/main/java/com/example/securebike/LoginActivity.java)
- Handles user login.
- Checks credentials against Firebase authentication.

### [MainActivity.java](https://github.com/Pranavbp525/SecureBike/blob/master/app/src/main/java/com/example/securebike/MainActivity.java)
- Handles user registration.
- New users can sign up with their email and password.

### [Main2Activity.java](https://github.com/Pranavbp525/SecureBike/blob/master/app/src/main/java/com/example/securebike/Main2Activity.java)
- Provides options to either log in or sign up.

### [MapsActivity.java](https://github.com/Pranavbp525/SecureBike/blob/master/app/src/main/java/com/example/securebike/MapsActivity.java)
- Displays the bike's location on a map.
- Fetches the latitude and longitude from Firebase.

### [Member.java](https://github.com/Pranavbp525/SecureBike/blob/master/app/src/main/java/com/example/securebike/Member.java)
- Model class representing a member.

### [MyForegroundService.java](https://github.com/Pranavbp525/SecureBike/blob/master/app/src/main/java/com/example/securebike/MyForegroundService.java)
- Foreground service that checks Firebase for changes.
- Triggers notifications based on bike's status.

## Installation 📥

1. Clone the repository: `git clone https://github.com/Pranavbp525/SecureBike.git`
2. Open the project in Android Studio.
3. Build and run the application on an emulator or physical device.

## Contributing 🤝

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License 📄

This project is open source.

