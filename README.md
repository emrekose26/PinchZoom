PinchZoom
===============
 
[![](https://jitpack.io/v/emrekose26/PinchZoom.svg)](https://jitpack.io/#emrekose26/PinchZoom)  [![](https://img.shields.io/badge/platform-android-green.svg)]() [![Packagist](https://img.shields.io/packagist/l/doctrine/orm.svg?maxAge=2592000)](https://github.com/emrekose26/PinchZoom/blob/master/LICENSE.md)


PinchZoom is a supported library the pinch gestures for Android

### Gradle

In your ``build.gradle`` file:

Add it in your root build.gradle at the end of repositories
```
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}	
```
Add the dependency
```
dependencies {
    ...
    compile 'com.github.emrekose26:PinchZoom:-SNAPSHOT'
}
```

### Usage


In your Activity class :

```java
public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView)findViewById(R.id.imageView);

        imageView.setOnTouchListener(new Touch());
    }
}
    
```

If you want to change default min and max zoom values

```java
   imageView.setOnTouchListener(new Touch(2f,6f));
```

In your xml file : 
```xml
<ImageView
    android:id="@+id/imageView
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:scaleType="matrix" />
```



### Contributing

* Check out the latest master to make sure the feature hasn't been implemented or the bug hasn't been fixed yet
* Check out the issue tracker to make sure someone already hasn't requested it and/or contributed it
* Fork the project
* Start a feature/bugfix branch
* Commit and push until you are happy with your contribution
* Make sure to add tests for it. This is important so I don't break it in a future version unintentionally.


### Copyright and license

Code and documentation copyright 2016 Emre Köse
Code released under the [MIT license](https://github.com/emrekose26/PinchZoom/blob/master/LICENSE.md)
