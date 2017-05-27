# ButterdomButton
A fancy material designed button that makes your app alive!

1. Example of usage in xml
2. Add options
3. Setup a listener
4. Automatically close
5. Gradle dependency

### Get started
![Closed state](http://i.imgur.com/KIpct1P.png)![Open state](http://i.imgur.com/frgiSuY.png)


## Example of usage in XML
Define the butterflybutton namespace ...

`xmlns:butterflybutton="http://schemas.android.com/apk/res-auto"`

And you are ready to go!
```
<dom.butterflybutton.view.ButterflyButton
        android:id="@+id/btnDOM"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        butterflybutton:relativeX="0.5"
        butterflybutton:relativeY="0.5"
        butterflybutton:buttonIcon="@drawable/ic_favorite_white_24dp"
        butterflybutton:bubbleColor="@color/colorPrimary"
        butterflybutton:buttonColor="@color/colorPrimaryDark"
        butterflybutton:selectColor="@color/colorPrimaryDark" />
```

## Add up to 8 funky options to choose from!

```
private ButterflyButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.button = (ButterflyButton) findViewById(R.id.btnDOM);
        
        this.button.addOption(R.drawable.happy,     ButterflyButton.NORTH);
        this.button.addOption(R.drawable.neutral,   ButterflyButton.WEST);
        this.button.addOption(R.drawable.sad,       ButterflyButton.SOUTH);
        ...
    }
```

## Setup your listener 
```
private ButterflyButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.button = (ButterflyButton) findViewById(R.id.btnDOM);

        this.button.setOnOptionSelectedListener(new ButterflyButton.OnOptionSelectedListener() {
            @Override
            public void onOptionSelected(int position, Bitmap icon) {
                ...
            }
        });
    }
```

## Automatically close on selecting? Super easy

```
private ButterflyButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.button = (ButterflyButton) findViewById(R.id.btnDOM);

        this.button.setCloseOnSelect(true);
    }
```

## Gradle dependency
Add the following line in your **project** gradle:
`

    repositories {

        ...

        maven { url 'https://jitpack.io' }

    }

Add the following line in your **app** gradle:
`

    dependencies {

        ...

	    compile 'com.github.TheKevin1512:ButterdomButton:1.1.0'
    }
