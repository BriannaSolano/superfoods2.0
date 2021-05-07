Run Instructions
1. Download and install the latest version of Android Studio from https://developer.android.com/studio. Note that the version at the time this project was made was 4.1/4.2
2. If you do not have an Android Virtual Device (AVD) with Android API Level 30 set up, follow these steps:
   1. Navigate to AVD Manager
   2. Click “Create Virtual Device…”
   3. Select “Pixel 4XL”, “Next”
   4. Select Release Name “R” with API Level 30, “Next”
   5. “Finish”
3. Clone the project source code using git
   1. In Android Studio: “File”
   2. “New” > “Project From Version Control”
   3. Repository URL: “https://github.com/BriannaSolano/superfoods2.0.git”
   4. “Clone”
   5. Let the project clone and load, this may take a few seconds
4. Build the project (hammer icon), and run it using the Pixel 4XL AVD configured earlier.
5. Set up Firebase Database connection
   1. Use the .json provided to load example data into the database
6. Set up Google Cloud API Account
   1. Connect API key to project in gradle and local.properties file


Signing In as a User on the App:
1. Customer: “justin@gmail.com” “123456” “123456”
2. Manager*: “manager@gmail.com” “123456” “861”
3. Drone Operator*: “drone@gmail.com” “123456” “208”
* for these accounts use employee login page


Unit tests and Integration tests:
None are provided for the application as tests cannot be completed automatically and require user interaction at every step of the way.