# Android to WearOS bluetooth communication

![image](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![image](https://img.shields.io/badge/OpenStreetMap-7EBC6F?style=for-the-badge&logo=OpenStreetMap&logoColor=white)
![image](https://img.shields.io/badge/Android_Studio-3DDC84?style=for-the-badge&logo=android-studio&logoColor=white)

Author: [Andrew Gyakobo](https://github.com/Gyakobo)

> This following example is inspired by the following publication(s): [website](https://developer.android.com/training/wearables/data/data-layer#cloud)

This example serves to show how to connect and effectively perpetuate a tight bluetooth link between an Android device and a wearable WearOS device

## Regarding the setup:

1. Open the 'AndroidManifest.xml' file and add the following permissions to both the Android and WearOS devices:

```XML
<uses-permission android:name="android.permission.BLUETOOTH"/>
<uses-permission android:name="android.permission.BLUETOOTH_ADMIN"/>
```

2. Remember to definitely declare the service in the "AndroidManifest.xml" file for the WearOS device

```XML
<service android:name=".DataLayerListenerService">
   <intent-filter>
       <action android:name="com.google.android.gms.wearable.BIND_LISTENER"/>
   </intent-filter>
</service>
```

## Note:

* For both devices you'll have to in order to instantiate end-to-end communication you'll need to know each devices MAC address "00:00:00:00:00:00"

> Note: The following development will soon enough be deprecated in favour of Kotlin. Java just seems to grotesquely large and outdated.
