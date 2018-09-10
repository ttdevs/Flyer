# Flyer

- 测试机型

    - 小米Mi6 7.1.1
    - 一加6
    - Nexus6 7.1.1
    - Samsung SM-G9350 7.0
    - HUAWEI VTR-AL00 8.0.0
    - Meizu MX4 Pro 5.1.1 OK
    - Samsung SM-T805C 4.4.2
    - Samsung SCH-I545 4.4.2
    - OPPO R11 7.1.1

- Publish

`$ ./gradlew clean build bintrayUpload -PbintrayUser=wwuu2008 -PbintrayKey=BINTRAY_KEY -PdryRun=false`
> https://github.com/novoda/bintray-release

- 获取Context

    - 方法一
        
```java
public class Application {
    private static Context CONTEXT;
    private Application() {
    }
    public static Context getApplicationContext() {
        System.out.println(">>>>>>>>>>" + ActivityThread.currentApplication().toString());
        return ActivityThread.currentApplication();
        if (CONTEXT != null) {
            return CONTEXT;
        } else {
            try {
                Class activityThreadClass = Class.forName("android.app.ActivityThread");
                Method method = activityThreadClass.getMethod("currentApplication");
                CONTEXT = (Context) method.invoke(null);
                return CONTEXT;
            } catch (Exception e) {
                return null;
            }
        }
    }
}
```

    - 方法二

```java
package android.app;
/**
 * @author ttdevs
 */
public final class ActivityThread {
    public static Application currentApplication() {
        return null;
    }
}
```