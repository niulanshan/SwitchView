# SwitchView
可以进行自定义的SwitchView

SwitchView自定义属性说明<br>
sw_circle_width 设置SwitchView中间小球的宽度 因为有一部分的设计里动的小球不一定是圆形的<br>
sw_circle_margin 设置SwitchView中间小球到边框的距离 因为有一部分的设计里动的小球到边距是有距离的<br>
animation_duration 设置小球一次运动的时间 <br>
on_color 小球到on的颜色<br>
off_color 小球到off的颜色<br>
bg_color 背景的颜色<br>

public void turnOn() 代码控制到on <br>
public void turnOff() 代码控制到off <br>
public void turnOnWithAnimation() 代码控制到on带动画<br>
public void turnOffWithAnimation() 代码控制到off带动画<br>
public void setOnTurnListener(OnTurnListener l) 控件是on还是off的监听<br>