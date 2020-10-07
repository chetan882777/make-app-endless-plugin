[![GitHub](https://img.shields.io/github/license/chetan882777/make-app-endless-plugin)](https://github.com/chetan882777/make-app-endless-plugin/blob/master/LICENSE)
[![Pub Version](https://img.shields.io/pub/v/make_app_endless_plugin)](https://pub.dev/packages/make_app_endless_plugin)

# Make App Endless

A flutter plugin for running your flutter appplication even if it is removed from background and keeing all of your other plugin alive and active. 

### Features
- [x] Expand life of your application by just single method call.
- [x] Also stop it by just single method call as just this simple.

## Getting Started
This plugin is Android Only.

### Android
For Android, you must do the following before you can use the plugin:

* Go to android directory of your application and instead of extending your MainActivity, luncher activity, or any activity over which your flutter application working with FLutterActivity extend it by EndlessAcivity.
  
  ```java
  public class MainActivity extends EndlessActivity
  ```
  
  This plugin keeps running your application by putting flutter engine in a foreground service aand foreground service requires notifictions so
  
* If you want to change snall icon on notification call

  ```java
  
  public class MainActivity extends EndlessActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // pass icon here you want to set
        setSmallIcon(android.R.drawable.ic_media_play);
    }
  }

  ```
  
  if this is not called so plugin will not set any small icon to notification and then setting content title and text will not reflect changes to shown notification.

## Usage

* Now to make life of you flutter application endless call

 ``` dart
 
 
 MakeAppEndlessPlugin plugin = MakeAppEndlessPlugin();
 await plugin.extendLifeCycle(
    notificationChannelName, 
    notificationChannelDescription, 
    contentTitle, 
    contentText);
    
    
 ```
  all of the four fields can be null and will not be shown to notification. Like if you pass null in last parameter aka contentText so no content text will be shown
  Also for contentTitle and contentText to be shown you have to setSmallIcon.
  
* To stop extending life of your flutter application
  ```dart
  await plugin.stopLifeCycleExtension();
  ```

  this method will not make any effect if called before `extendLifeCycle` method.

### Thankyou and if you liked this plugin please mark a star over it, Happy Fluttering.
