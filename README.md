
# react-native-demo-brillat-savarin 🧀🧀

## Getting started

`$ npm install react-native-demo-brillat-savarin --save`

### Installation

- These instructions are for react-native-based projects, maybe the instructions should simply be... only native?

#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-demo-brillat-savarin` and add `RNDemoBrillatSavarin.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNDemoBrillatSavarin.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)

#### Android

1. Open the activity that will use the Manager, e.g. `MainActivity.java`
  - Add `import com.applicaster.ReactNativeLoginPlugin.Manager;` to the imports at the top of the file
  - Usage example:
    ```java
    // Initialization
    Manager loginManager = new LoginManager(this);
    // loginManager.setReactNativeModuleName("some root name other than RNRoot");
    loginManager.setDevelopmentMode(true);
    loginManager.setLoginActionsListener(new LoginManager.LoginActionsListener() {
        @Override
        public void onLoginResult(String result) {
            Log.d(TAG, "login result: " + result);
        }
        @Override
        public void onReady() {
            Log.d(TAG, "Plugin ready to use");
        }
    });
    loginManager.initialize();

    // Usage
    loginManager.show(findViewById(R.id.my_parent_view));
    ```

2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-demo-brillat-savarin'
  	project(':react-native-demo-brillat-savarin').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-demo-brillat-savarin/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-demo-brillat-savarin')
  	```


## Usage
```javascript
import APReactNativeLogin from 'react-native-demo-brillat-savarin';

APReactNativeLogin.onLoginResult("login result from user"); // A string. Yes, just a string.
```
