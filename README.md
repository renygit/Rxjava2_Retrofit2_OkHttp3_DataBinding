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
