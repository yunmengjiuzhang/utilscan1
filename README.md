[](https://jitpack.io/v/wangfeixixi/utilscan1.svg)](https://jitpack.io/#wangfeixixi/utilscan1)

利用libzbar.so和libiconv.so核心代码扫码，速度秒射

配置：
1.根build.gradle添加

      allprojects {
          repositories {
              google()
              jcenter()
              maven { url 'https://jitpack.io' } //添加仓库依赖
          }
      }
2.module的build.gradle添加

    compile 'com.github.wangfeixixi:utilscan1:v1.0'

完工开始体验：
public class MainActivity extends CaptureActivity {

}

如果觉得好请给我点赞哈！

如果需要进一步交流，邮件哦：xuanyuanxixi@foxmail.com
