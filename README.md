# Rxjava2_Retrofit2_OkHttp3_DataBinding
使用了Rxjava2_Retrofit2_OkHttp3_DataBinding等一系列库，自己构建的MVPVM架构，有一些细节没有修改，但整体雏形还是可以用于项目开发。<br>

#引用===========================
maven { url "https://jitpack.io" }
  
  dependencies {<br>
	        compile 'com.github.ADL1121:Rxjava2_Retrofit2_OkHttp3_DataBinding:0.0.4'<br>
	}<br>
  
  
#使用==========================
Application初始化时调用InitUtils.init(this, BuildConfig.DEBUG);<br>
  
Activity继承BaseActivity<ActivityMainBinding, MainViewModel, MainPresenter> 指定3个泛型，分别是默认生成的Binding、继承自库的BaseViewModel,继承自库的BasePresenter;<br>
会自动实例化三个变量binding，viewModel，presenter；Fragment类似。<br>
  
具体使用请看Demo，可以极大减少代码量，欢迎star，以后还会继续完善<br>



#Lib中引用到的库===================

compile 'com.android.support:appcompat-v7:25.1.1'<br>
compile 'com.android.support:recyclerview-v7:25.1.1'<br>
<br>
// other   日志<br>
compile 'com.orhanobut:logger:1.15'<br>
//滑动退出activity<br>
compile 'com.jude:swipebackhelper:3.1.2'<br>
//Gson<br>
compile 'com.google.code.gson:gson:2.8.0'<br>
//RxJava2 + RxAndroid<br>
compile 'io.reactivex.rxjava2:rxjava:2.0.0'<br>
compile 'org.reactivestreams:reactive-streams:1.0.0'<br>
compile 'io.reactivex.rxjava2:rxandroid:2.0.0'<br>
//OkHttp3 + Retrofit2<br>
compile 'com.squareup.okhttp3:okhttp:3.4.1'<br>
compile 'com.squareup.okio:okio:1.10.0'<br>
compile 'com.squareup.retrofit2:retrofit:2.1.0'<br>
compile 'com.jakewharton.retrofit:retrofit2-rxjava2-adapter:1.0.0'<br>
// Reservoir   缓存<br>
compile 'com.anupcowkur:reservoir:3.1.0'<br>
//cookie管理<br>
compile 'com.github.franmontiel:PersistentCookieJar:v1.0.0'<br>
//皮肤切换<br>
compile 'com.zhy:changeskin:4.0.2'<br>
compile 'com.wang.avi:library:2.1.3'<br>
