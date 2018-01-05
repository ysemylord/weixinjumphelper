# weixinjumphelper
微信跳一跳游戏助手

## 需要

手机root或者adb shell

注意:shell 模拟长按的命令

```
input swipe 起点横坐标 起点纵坐标 终点横坐标 终点纵坐标 时间（单位ms）
```

## 使用步骤

1. 打开本app,点击“打开辅助界面"按钮，点击"变小""按钮

2. 进入微信跳一跳界面

3. 点击"变大""按钮

4. 分别点击"小人"所在的位置和目标位置，程序计算出需要长按的时间，并显示在界面上

5. 调用input swip命令模拟长按

   ![helper_image1](https://github.com/ysemylord/weixinjumphelper/blob/master/image/helper_image1.png)

