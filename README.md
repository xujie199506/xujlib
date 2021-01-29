# 个人工作中经常使用的工具类

- [![](https://jitpack.io/v/xujie199506/xujlib.svg)](https://jitpack.io/#xujie199506/xujlib)

## 如何接入

以下两个步骤：

**1.在工程根目录的build.gradle添加:**

```groovy
allprojects {
    repositories {
        maven { url 'https://www.jitpack.io' }
    }
}
```

**2.在依赖OkBle模块的build.gradle添加：**

```groovy
dependencies {
    implementation 'com.github.xujie199506:xujlib:1.0.1'
}
```