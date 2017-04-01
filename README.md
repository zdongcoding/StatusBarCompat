总有5个方法
  1.setStatusBarColor    设置状态栏着色模式  
  2.translucentStatusBar   透明  全屏模式  对应 清除方法 clearTranslucent
  3.TranslucentStatusBarForCollapsingToolbar   顾名思义   全屏模式     对应 清除方法 clearStatusBarForCollapsingToolbar


先定义几个名词:

ContentView: activity.findViewById(Window.ID_ANDROID_CONTENT) 获取的 View , 即 setContentView 方法所设置的 View, 实质为 FrameLayout.
ContentParent: ContentView 的 parent , 实质为 LinearLayout.
ChildView: ContentView 的第一个子 View ,即布局文件中的 root layout .
再介绍一下相关的函数:

fitsSystemWindows, 该属性可以设置是否为系统 View 预留出空间, 当设置为 true 时,会预留出状态栏的空间.
ContentView, 实质为 ContentFrameLayout, 但是重写了 dispatchFitSystemWindows 方法, 所以对其设置 fitsSystemWindows 无效.
ContentParent, 实质为 FitWindowsLinearLayout, 里面第一个 View 是 ViewStubCompat, 如果主题没有设置 title ,它就不会 inflate .第二个 View 就是 ContentView.
requestApplyInsets(), 当窗口(Window)大小改变了,通知 View 去消费窗口的改变.
FLAG_TRANSLUCENT_STATUS, 设置全屏的标志位, 此时界面可以延伸到状态栏.
5.0以上的处理:
自5.0引入 Material Design ,状态栏对开发者更加直接,可以直接调用 setStatusBarColor 来设置状态栏的颜色.

着色模式:

通过查看 setStatusBarColor() 方法的文档,发现在调用该方法时需要设置以下属性:

添加 FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS Flag(绘制系统栏).
清除 FLAG_TRANSLUCENT_STATUS Flag(透明状态栏).
调用 setStatusBarColor() 设置状态栏颜色.
全屏模式:

由于 5.0 以上为状态栏添加了一个阴影, 所以为全屏模式添加了是否隐藏状态栏阴影的方法.

隐藏阴影

像着色模式一样添加 flag ,然后通过 setStatusBarColor() 设置颜色为透明.
通过 setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN) 隐藏状态栏颜色.
显示阴影

设置 FLAG_TRANSLUCENT_STATUS 来隐藏状态栏.
通过 setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE) 来恢复默认状态栏样式.
4.4-5.0的处理:
4.4-5.0因为没有直接的 API 可以调用,需要自己兼容处理.参考了网上的解决方法及结合我自己遇到的坑,最后想出的解决办法如下:

着色模式

向 DecorView 中添加一个 View, 高度为状态栏的高度(反射获取).
将 ChildView 的 marginTop 加上状态栏的高度,以此来模拟 fitsSystemWindows.
设置 ChildView 的 fitsSystemWindow 为 false, 不预留系统栏位置.
为 DecorView 设置一个 tag, 防止重复添加 View.
这里与其他地方不同的是:

向 ContentView 添加 View 在部分机型(华为)上没有效果.
向 ContentParent 上添加 View 会有一条黑线.
使用 marginTop 而不是 fitsSystemWindows 是因为无法在不重启 Activity 的情况下切换 root layout 的fitsSystemWindows属性, 即直接设置不会生效, 所以用 marginTop 来模拟.
全屏模式

设置 ChildView 的 fitsSystemWindow 为 false, 不预留系统栏位置.
如果在 ChildView 的 marginTop 中添加了状态栏的高度, 则移除.
设置 tag, 防止重复移除.
