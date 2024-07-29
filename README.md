# TextView 相关例子
## TextView 生成图片截取中间部分显示
## 字体渐变功能
## 垂直显示TextView，滚动效果

## 渐变Gradient 测试在textview_test中 TextView需要修改isAr
## span动画在shader/utils/shaderanim中


# 参考资料
TextView文滚动显示
https://www.jianshu.com/p/d96cc3422f44

Android 实现水平、垂直方向文字跑马灯效果
https://blog.csdn.net/weixin_53324308/article/details/130427159

https://github.com/AndroidMsky/RandomTextView

获取某个字符在TextView中的位置(坐标)
https://www.jianshu.com/p/fb534a09fbe1


需要修改地方
封装一下VIP的功能，使用GradientSpanV2.java
封装彩虹渐变字体的功能，使用GradientAnimSpanV2.java，放到GradientUtils就可以了吧

getVipNameLimit 262 1
getVipName 250 1
getVipName 285 1
getVipName 297 1
getVipName 309 1
getVipNameClick 380 1
getVipNameClick 390
getVipNameClick 401

替换GradientAnimTextViewV2 流程
1. xml 替换 com.xiaofeng.xchat_core.widget.rainbow_view.GradientAnimTextViewV2
2. GradientAnimTextViewV2 添加style style="@style/text_single_line_rtl" 或 style="@style/textview_style_rtl"
3. 代码替换TextView为GradientAnimTextViewV2
4. 使用RainbowUtils的方法设置Content
if(GlobalConfig.isTest()) {
    tvNick.setContent(RainbowUtils.getGradientSpan(true, memberBean.getNick(), memberBean.getNobleId(), R.color.common_black, GradientUtils.getColors()));
}
彩虹使用方法 getGradientSpan()
彩虹动画使用方法 getGradientAnimSpan()

替换com.xiaofeng.xchat_core.widget.rainbow_view.RainbowScrollTextViewV2流程
1. xml替换 com.xiaofeng.xchat_core.widget.rainbow_view.RainbowScrollTextViewV2
2. xml 设置下面属性
   app:rainbow_scroll_text_color="@color/black"
   app:rainbow_scroll_text_size="18sp"
   app:rainbow_scroll_text_styl="bold"
3. 改变代码的引用类型 RainbowScrollTextViewV2

4. 代码调用下面方法kotlin
if(GlobalConfig.isTest()) {
                val rainbow = true
                if (rainbow) {
                    tv_nick.setContent(userInfo.nick, GradientUtils.getColors())
                } else {
                    tv_nick.setContent(
                        RainbowUtils.getGradientSpan(
                            true,
                            userInfo.nick,
                            userInfo.nobleId,
                            R.color.common_black,
                            GradientUtils.getColors()
                        )
                    )
                }
            }

5. 代码设置下面方法 Java
if(GlobalConfig.isTest()) {
    boolean rainbow = true;
    if(rainbow) {
        tvName.setContent(info.getNick(), GradientUtils.getColors());
    } else {
        tvName.setContent(RainbowUtils.getGradientSpan(false, info.getNick(), info.getNobleId(), R.color.ccbcbcb, GradientUtils.getColors()));
    }
}

6. 渐变滚动统一使用这个方法，使用的是系统的滚动，但是是通过动态刷新渐变span的translate实现的。
   sContent.append(GradientUtils.getGradientAnimText(this, "测试滚动和渐使用RainbowScrollTextViewV2时，渐变不滚动支持，测试这种情况", colors, sContent.length(), 0));
   tvTest6.setContent(sContent);


统一调用方法
可点击
if(GlobalConfig.isTest()) {
   sName.append(RainbowUtils.getGradientSpanClick(true, msg.getSendName(), msg.getNobleId(), R.color.ccbcbcb, GradientUtils.getColors(), this, member, R.color.ccbcbcb));  // 彩虹动画
}

不可点击
if(GlobalConfig.isTest()) {
    sName.append(RainbowUtils.getGradientSpan(true, name, nobleId, R.color.ccbcbcb, GradientUtils.getColors()));  // 彩虹动画
}

全局查找MarqueeTextView 看有没有替换漏的，其实也不一定，好看有没有引用VipManagerV2相关的东西，因为如果是单纯滚动，就不需要替换

addItemType(CoreChatRoomMessage.MSG_TYPE_NORMAL, R.layout.item_chatroom_normal_msg); 1
addItemType(CoreChatRoomMessage.MSG_TYPE_OFFICIAL, R.layout.item_chatroom_official_msg); // 头像 + 文字描述 1
addItemType(CoreChatRoomMessage.MSG_TYPE_SEND_TEXT, R.layout.item_chatroom_send_msg); // 头像 名称 徽章 内容 1
addItemType(CoreChatRoomMessage.MSG_TYPE_SYSTEM_MSG, R.layout.item_chatroom_system_msg); // 仅仅是文字 有背景 1
addItemType(CoreChatRoomMessage.MSG_TYPE_NOTICE, R.layout.item_chatroom_msg_notice); // 公告图标 + 文字 （直播间公告背景渐变，关注消息透明灰色）1
addItemType(CoreChatRoomMessage.MSG_TYPE_FACE_MSG, R.layout.item_chatroom_face); // 表情消息 头像 名称 徽章 内容 1
addItemType(CoreChatRoomMessage.MSG_TYPE_LUCKY_GAME_MSG, R.layout.item_chatroom_lucky_game); // 猜拳、骰子、777 1
addItemType(CoreChatRoomMessage.MSG_TYPE_ACTIVE_GAME_MSG, R.layout.item_chatroom_active_game_msg); // 活跃游戏消息, 游戏图标+说明+GO 1
addItemType(CoreChatRoomMessage.MSG_TYPE_GIFT_BACK, R.layout.item_chatroom_send_msg_gift_back); // 回礼 1
addItemType(CoreChatRoomMessage.MSG_TYPE_REPLY_MSG, R.layout.item_chatroom_reply_msg); // 回复消息

后续：
1.添加修改或配置滚动速度的方法















