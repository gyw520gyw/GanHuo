2016.04.14<br>
创建项目Ganhuo

2016.04.15<br>
实现网络请求

参考：   
http://gank.io/api<br>
http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/1109/3662.html
http://jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/1016/3588.html

问题:
String BASE_API = "http://gank.io/api/data"
和
@GET("/{type}/{pageSize}/{pageIndex}")
这样的写法,页面找不到404

解决: 在第二个链接中找到的解决办法
如果有前缀 / 就代表着是一个绝对路径。删除了那个前缀的 /， 你将会得到正确路径的全 URL。


2016.04.25<br/>
实现沉浸式状态栏


参考：
https://github.com/laobie/StatusBarUtil



2016.08.01<br/>
实现首页基本样式,底部tab

2016.09.05<br/>
1.使用FragmentTabHost，创建对应fragment;<br/>
2.使用自定义组合控件Topbar;<br/>


2016.09.06<br/>
1.请求福利类型的接口成功，初步完成福利模块;<br/>
2.使用Glide加载图片;<br/>
最近电脑出了点问题，只要一翻墙，就出现资源管理器不断重启，所以没有升级AndroidStudio..<br/>

参考:
http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0327/2650.html


2016.09.08<br/>
1.使用SwipeRefreshLayout做下拉刷新操作功能;

2016.09.09<br/>
1.添加加载更多功能;


参考:
Glide : http://blog.csdn.net/shangmingchao/article/details/51125554 , 有很多方法的解释;
