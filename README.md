# Rxjava2_Retrofit2_OkHttp3_DataBinding
使用了Rxjava2_Retrofit2_OkHttp3_DataBinding等一系列库，自己构建的MVPVM架构，有一些细节没有修改，但整体雏形还是可以用于项目开发。

#引用
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
  dependencies {
	        compile 'com.github.ADL1121:Rxjava2_Retrofit2_OkHttp3_DataBinding:0.0.1'
	}
  
  
  #使用
  Application初始化时调用InitUtils.init(this, BuildConfig.DEBUG);
  
  Activity继承BaseActivity<ActivityMainBinding, MainViewModel, MainPresenter> 指定3个泛型，分别是默认生成的Binding、继承自库的BaseViewModel,继承自库的BasePresenter;
  会自动实例化三个变量binding，viewModel,presenter，Fragment类似。
  
  具体使用请看Demo，可以极大减少代码量



#引用到的库

compile 'com.android.support:appcompat-v7:25.1.1'
compile 'com.android.support:recyclerview-v7:25.1.1'

// other   日志
compile 'com.orhanobut:logger:1.15'
//滑动退出activity
compile 'com.jude:swipebackhelper:3.1.2'
//Gson
compile 'com.google.code.gson:gson:2.8.0'
//RxJava2 + RxAndroid
compile 'io.reactivex.rxjava2:rxjava:2.0.0'
compile 'org.reactivestreams:reactive-streams:1.0.0'
compile 'io.reactivex.rxjava2:rxandroid:2.0.0'
//OkHttp3 + Retrofit2
compile 'com.squareup.okhttp3:okhttp:3.4.1'
compile 'com.squareup.okio:okio:1.10.0'
compile 'com.squareup.retrofit2:retrofit:2.1.0'
compile 'com.jakewharton.retrofit:retrofit2-rxjava2-adapter:1.0.0'
// Reservoir   缓存
compile 'com.anupcowkur:reservoir:3.1.0'
//cookie管理
compile 'com.github.franmontiel:PersistentCookieJar:v1.0.0'
//皮肤切换
compile 'com.zhy:changeskin:4.0.2'
