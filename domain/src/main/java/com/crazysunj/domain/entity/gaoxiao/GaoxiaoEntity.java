/*
  Copyright 2017 Sun Jian
  <p>
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  <p>
  http://www.apache.org/licenses/LICENSE-2.0
  <p>
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */
package com.crazysunj.domain.entity.gaoxiao;

import android.text.TextUtils;

import com.crazysunj.domain.entity.contact.Contact;

import java.util.List;

/**
 * @author: sunjian
 * created on: 2018/4/13 上午11:20
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class GaoxiaoEntity {

    /**
     * code : 200
     * msg : 成功!
     * data : [{"type":"41","text":"2018十大最强洗脑神曲，太魔性了，你听过几首？","user_id":"18482739","name":"年少滥情不花心","screen_name":"年少滥情不花心","profile_image":"http://wimg.spriteapp.cn/profile/large/2017/07/04/595b4417f130c_mini.jpg","created_at":"2018-04-13 09:38:02","create_time":null,"passtime":"2018-04-13 09:38:02","love":"291","hate":"16","comment":"5","repost":"52","bookmark":"203","bimageuri":"http://wimg.spriteapp.cn/picture/2018/0412/f585d024-3ddb-11e8-aa15-1866daeb0df1_wpd_86.jpg","voiceuri":null,"voicetime":null,"voicelength":null,"status":"4","theme_id":"22871","theme_name":"音乐汇","theme_type":"1","videouri":"http://wvideo.spriteapp.cn/video/2018/0412/f585d024-3ddb-11e8-aa15-1866daeb0df1_wpd.mp4","videotime":159,"original_pid":"0","cache_version":2,"playcount":"9193","playfcount":"494","cai":"16","weixin_url":null,"image1":"http://wimg.spriteapp.cn/picture/2018/0412/f585d024-3ddb-11e8-aa15-1866daeb0df1_wpd_86.jpg","image2":"http://wimg.spriteapp.cn/picture/2018/0412/f585d024-3ddb-11e8-aa15-1866daeb0df1_wpd_86.jpg","is_gif":false,"image0":"http://wimg.spriteapp.cn/picture/2018/0412/f585d024-3ddb-11e8-aa15-1866daeb0df1_wpd_86.jpg","image_small":"http://wimg.spriteapp.cn/picture/2018/0412/f585d024-3ddb-11e8-aa15-1866daeb0df1_wpd_86.jpg","cdn_img":"http://wimg.spriteapp.cn/picture/2018/0412/f585d024-3ddb-11e8-aa15-1866daeb0df1_wpd_86.jpg","width":"852","height":"480","tag":"","t":1523583482,"ding":"291","favourite":"203","top_cmt":null,"themes":null},{"type":"41","text":"现在的小朋友，个个都身怀绝技，将来必成大器！","user_id":"20226826","name":"污姐开车了","screen_name":"污姐开车了","profile_image":"http://wimg.spriteapp.cn/profile/large/2017/10/13/59e0d549b9a60_mini.jpg","created_at":"2018-04-13 09:23:42","create_time":null,"passtime":"2018-04-13 09:23:42","love":"197","hate":"20","comment":"23","repost":"26","bookmark":"22","bimageuri":"http://wimg.spriteapp.cn/picture/2018/0408/27409805_581.jpg","voiceuri":null,"voicetime":null,"voicelength":null,"status":"4","theme_id":"0","theme_name":"","theme_type":"0","videouri":"http://wvideo.spriteapp.cn/video/2018/0408/79478b003b2011e8b4ee842b2b4c75ab_wpd.mp4","videotime":216,"original_pid":"0","cache_version":2,"playcount":"7729","playfcount":"273","cai":"20","weixin_url":null,"image1":"http://wimg.spriteapp.cn/picture/2018/0408/27409805_581.jpg","image2":"http://wimg.spriteapp.cn/picture/2018/0408/27409805_581.jpg","is_gif":false,"image0":"http://wimg.spriteapp.cn/picture/2018/0408/27409805_581.jpg","image_small":"http://wimg.spriteapp.cn/picture/2018/0408/27409805_581.jpg","cdn_img":"http://wimg.spriteapp.cn/picture/2018/0408/27409805_581.jpg","width":"1066","height":"600","tag":"","t":1523582622,"ding":"197","favourite":"22","top_cmt":null,"themes":null},{"type":"41","text":"意外无处不在，哈哈哈哈哈看的我尴尬癌都犯了！","user_id":"15262111","name":"共醉江湖","screen_name":"共醉江湖","profile_image":"http://wimg.spriteapp.cn/profile/large/2016/09/20/57e0cd620e99f_mini.jpg","created_at":"2018-04-13 09:19:02","create_time":null,"passtime":"2018-04-13 09:19:02","love":"178","hate":"10","comment":"0","repost":"32","bookmark":"23","bimageuri":"http://wimg.spriteapp.cn/picture/2018/0412/27510295_436.jpg","voiceuri":null,"voicetime":null,"voicelength":null,"status":"4","theme_id":"25257","theme_name":"尴尬囧事","theme_type":"1","videouri":"http://wvideo.spriteapp.cn/video/2018/0412/5aceca65359f3_wpd.mp4","videotime":85,"original_pid":"0","cache_version":2,"playcount":"10097","playfcount":"1477","cai":"10","weixin_url":null,"image1":"http://wimg.spriteapp.cn/picture/2018/0412/27510295_436.jpg","image2":"http://wimg.spriteapp.cn/picture/2018/0412/27510295_436.jpg","is_gif":false,"image0":"http://wimg.spriteapp.cn/picture/2018/0412/27510295_436.jpg","image_small":"http://wimg.spriteapp.cn/picture/2018/0412/27510295_436.jpg","cdn_img":"http://wimg.spriteapp.cn/picture/2018/0412/27510295_436.jpg","width":"352","height":"640","tag":"","t":1523582342,"ding":"178","favourite":"23","top_cmt":null,"themes":null},{"type":"41","text":"就这样静静地看着土拨鼠吃东西，然后...饿了","user_id":"18482739","name":"年少滥情不花心","screen_name":"年少滥情不花心","profile_image":"http://wimg.spriteapp.cn/profile/large/2017/07/04/595b4417f130c_mini.jpg","created_at":"2018-04-13 09:02:31","create_time":null,"passtime":"2018-04-13 09:02:31","love":"246","hate":"26","comment":"20","repost":"31","bookmark":"18","bimageuri":"http://wimg.spriteapp.cn/picture/2018/0412/27507464_723.jpg","voiceuri":null,"voicetime":null,"voicelength":null,"status":"4","theme_id":"56939","theme_name":"动物成了精","theme_type":"1","videouri":"http://wvideo.spriteapp.cn/video/2018/0412/6f3e3ae2-3de0-11e8-b17c-d4ae5296039d_wpd.mp4","videotime":293,"original_pid":"0","cache_version":2,"playcount":"15678","playfcount":"65","cai":"26","weixin_url":null,"image1":"http://wimg.spriteapp.cn/picture/2018/0412/27507464_723.jpg","image2":"http://wimg.spriteapp.cn/picture/2018/0412/27507464_723.jpg","is_gif":false,"image0":"http://wimg.spriteapp.cn/picture/2018/0412/27507464_723.jpg","image_small":"http://wimg.spriteapp.cn/picture/2018/0412/27507464_723.jpg","cdn_img":"http://wimg.spriteapp.cn/picture/2018/0412/27507464_723.jpg","width":"600","height":"750","tag":"","t":1523581351,"ding":"246","favourite":"18","top_cmt":null,"themes":null},{"type":"41","text":"一辈子遇到真爱的可能性有多大？","user_id":"17642987","name":"我是云飞扬","screen_name":"我是云飞扬","profile_image":"http://wimg.spriteapp.cn/profile/large/2017/02/10/589dd39f8a9ee_mini.jpg","created_at":"2018-04-13 09:00:02","create_time":null,"passtime":"2018-04-13 09:00:02","love":"296","hate":"19","comment":"9","repost":"59","bookmark":"104","bimageuri":"http://wimg.spriteapp.cn/picture/2018/0411/27498149_345.jpg","voiceuri":null,"voicetime":null,"voicelength":null,"status":"4","theme_id":"55163","theme_name":"主版块","theme_type":"1","videouri":"http://wvideo.spriteapp.cn/video/2018/0411/fa5c2b96-3d88-11e8-854d-d4ae5296039d_wpd.mp4","videotime":198,"original_pid":"0","cache_version":2,"playcount":"13705","playfcount":"207","cai":"19","weixin_url":null,"image1":"http://wimg.spriteapp.cn/picture/2018/0411/27498149_345.jpg","image2":"http://wimg.spriteapp.cn/picture/2018/0411/27498149_345.jpg","is_gif":false,"image0":"http://wimg.spriteapp.cn/picture/2018/0411/27498149_345.jpg","image_small":"http://wimg.spriteapp.cn/picture/2018/0411/27498149_345.jpg","cdn_img":"http://wimg.spriteapp.cn/picture/2018/0411/27498149_345.jpg","width":"640","height":"360","tag":"","t":1523581202,"ding":"296","favourite":"104","top_cmt":null,"themes":null},{"type":"41","text":"哈哈哈！小伙子有前途\u2026\u2026","user_id":"18895364","name":"日日曰日","screen_name":"日日曰日","profile_image":"http://wimg.spriteapp.cn/profile/large/2017/07/17/596c4c7c2079f_mini.jpg","created_at":"2018-04-13 08:37:01","create_time":null,"passtime":"2018-04-13 08:37:01","love":"507","hate":"52","comment":"43","repost":"37","bookmark":"29","bimageuri":"http://wimg.spriteapp.cn/picture/2018/0411/27475573_670.jpg","voiceuri":null,"voicetime":null,"voicelength":null,"status":"4","theme_id":"58191","theme_name":"搞笑视频","theme_type":"1","videouri":"http://wvideo.spriteapp.cn/video/2018/0411/67059922-3d3d-11e8-bfaa-1866daeb0df1_wpd.mp4","videotime":31,"original_pid":"0","cache_version":2,"playcount":"32473","playfcount":"5855","cai":"52","weixin_url":null,"image1":"http://wimg.spriteapp.cn/picture/2018/0411/27475573_670.jpg","image2":"http://wimg.spriteapp.cn/picture/2018/0411/27475573_670.jpg","is_gif":false,"image0":"http://wimg.spriteapp.cn/picture/2018/0411/27475573_670.jpg","image_small":"http://wimg.spriteapp.cn/picture/2018/0411/27475573_670.jpg","cdn_img":"http://wimg.spriteapp.cn/picture/2018/0411/27475573_670.jpg","width":"480","height":"852","tag":"","t":1523579821,"ding":"507","favourite":"29","top_cmt":null,"themes":null},{"type":"41","text":"这狗挺自觉的啊！你是一位做好了自己还能吃吗","user_id":"17545511","name":"我是陈苗","screen_name":"我是陈苗","profile_image":"http://wimg.spriteapp.cn/profile/large/2017/08/28/59a3fe4c5e358_mini.jpg","created_at":"2018-04-13 08:32:31","create_time":null,"passtime":"2018-04-13 08:32:31","love":"373","hate":"21","comment":"14","repost":"45","bookmark":"22","bimageuri":"http://wimg.spriteapp.cn/picture/2018/0411/27477049_141.jpg","voiceuri":null,"voicetime":null,"voicelength":null,"status":"4","theme_id":"58191","theme_name":"搞笑视频","theme_type":"1","videouri":"http://wvideo.spriteapp.cn/video/2018/0411/3bb4e40a-3d40-11e8-902b-1866daeb0df1_wpd.mp4","videotime":19,"original_pid":"0","cache_version":2,"playcount":"22581","playfcount":"2216","cai":"21","weixin_url":null,"image1":"http://wimg.spriteapp.cn/picture/2018/0411/27477049_141.jpg","image2":"http://wimg.spriteapp.cn/picture/2018/0411/27477049_141.jpg","is_gif":false,"image0":"http://wimg.spriteapp.cn/picture/2018/0411/27477049_141.jpg","image_small":"http://wimg.spriteapp.cn/picture/2018/0411/27477049_141.jpg","cdn_img":"http://wimg.spriteapp.cn/picture/2018/0411/27477049_141.jpg","width":"600","height":"1066","tag":"","t":1523579551,"ding":"373","favourite":"22","top_cmt":null,"themes":null},{"type":"41","text":"一部特战动画片，兔子成了狙击手、特种兵，场面逼真劲爆不容错过","user_id":"19634336","name":"阿强马强赛马强","screen_name":"阿强马强赛马强","profile_image":"http://wimg.spriteapp.cn/profile/large/2018/01/16/5a5dce3778d6a_mini.jpg","created_at":"2018-04-13 08:11:02","create_time":null,"passtime":"2018-04-13 08:11:02","love":"692","hate":"40","comment":"81","repost":"98","bookmark":"235","bimageuri":"http://wimg.spriteapp.cn/picture/2018/0411/27474604_706.jpg","voiceuri":null,"voicetime":null,"voicelength":null,"status":"4","theme_id":"0","theme_name":"","theme_type":"0","videouri":"http://wvideo.spriteapp.cn/video/2018/0411/9ff2423c3d3b11e89f93842b2b4c75ab_wpd.mp4","videotime":255,"original_pid":"0","cache_version":2,"playcount":"27708","playfcount":"2990","cai":"40","weixin_url":null,"image1":"http://wimg.spriteapp.cn/picture/2018/0411/27474604_706.jpg","image2":"http://wimg.spriteapp.cn/picture/2018/0411/27474604_706.jpg","is_gif":false,"image0":"http://wimg.spriteapp.cn/picture/2018/0411/27474604_706.jpg","image_small":"http://wimg.spriteapp.cn/picture/2018/0411/27474604_706.jpg","cdn_img":"http://wimg.spriteapp.cn/picture/2018/0411/27474604_706.jpg","width":"1066","height":"600","tag":"","t":1523578262,"ding":"692","favourite":"235","top_cmt":null,"themes":null},{"type":"41","text":"哈哈哈萌呆了，这是我见过最激烈的排球比赛，请忽略这拖拉机般笑声！","user_id":"15262111","name":"共醉江湖","screen_name":"共醉江湖","profile_image":"http://wimg.spriteapp.cn/profile/large/2016/09/20/57e0cd620e99f_mini.jpg","created_at":"2018-04-13 07:46:02","create_time":null,"passtime":"2018-04-13 07:46:02","love":"479","hate":"42","comment":"22","repost":"61","bookmark":"67","bimageuri":"http://wimg.spriteapp.cn/picture/2018/0412/5aced55ddcc07__b.jpg","voiceuri":null,"voicetime":null,"voicelength":null,"status":"4","theme_id":"56939","theme_name":"动物成了精","theme_type":"1","videouri":"http://wvideo.spriteapp.cn/video/2018/0412/5aced55deb677_wpd.mp4","videotime":87,"original_pid":"0","cache_version":2,"playcount":"29385","playfcount":"971","cai":"42","weixin_url":null,"image1":"http://wimg.spriteapp.cn/picture/2018/0412/5aced55ddcc07__b.jpg","image2":"http://wimg.spriteapp.cn/picture/2018/0412/5aced55ddcc07__b.jpg","is_gif":false,"image0":"http://wimg.spriteapp.cn/picture/2018/0412/5aced55ddcc07__b.jpg","image_small":"http://wimg.spriteapp.cn/picture/2018/0412/5aced55ddcc07__b.jpg","cdn_img":"http://wimg.spriteapp.cn/picture/2018/0412/5aced55ddcc07__b.jpg","width":"640","height":"360","tag":"","t":1523576762,"ding":"479","favourite":"67","top_cmt":null,"themes":null},{"type":"41","text":"有梦想，有责任！","user_id":"21504912","name":"胡成功导演","screen_name":"胡成功导演","profile_image":"http://wx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEKE3bXR0UdVibEMERmAcmXfr7UicpJPp09Xd8kpibU18lPoF8rK8wC36DL6zggo8Vhqib4QBZibBbepySA/0","created_at":"2018-04-13 06:51:01","create_time":null,"passtime":"2018-04-13 06:51:01","love":"238","hate":"53","comment":"19","repost":"11","bookmark":"17","bimageuri":"http://wimg.spriteapp.cn/picture/2018/0406/5ac749108a488_wpd.jpg","voiceuri":null,"voicetime":null,"voicelength":null,"status":"4","theme_id":"55163","theme_name":"主版块","theme_type":"1","videouri":"http://wvideo.spriteapp.cn/video/2018/0406/5ac749108a488_wpd.mp4","videotime":50,"original_pid":"0","cache_version":2,"playcount":"22274","playfcount":"1435","cai":"53","weixin_url":null,"image1":"http://wimg.spriteapp.cn/picture/2018/0406/5ac749108a488_wpd.jpg","image2":"http://wimg.spriteapp.cn/picture/2018/0406/5ac749108a488_wpd.jpg","is_gif":false,"image0":"http://wimg.spriteapp.cn/picture/2018/0406/5ac749108a488_wpd.jpg","image_small":"http://wimg.spriteapp.cn/picture/2018/0406/5ac749108a488_wpd.jpg","cdn_img":"http://wimg.spriteapp.cn/picture/2018/0406/5ac749108a488_wpd.jpg","width":"356","height":"640","tag":"","t":1523573461,"ding":"238","favourite":"17","top_cmt":null,"themes":null},{"type":"41","text":"短裤穿太高也是病...那干脆不穿了","user_id":"16014355","name":"                     \u2026141","screen_name":"                     \u2026141","profile_image":"http://qzapp.qlogo.cn/qzapp/100336987/A69F52C4A5FC26B80FFF48CF52B592D5/100","created_at":"2018-04-13 06:25:02","create_time":null,"passtime":"2018-04-13 06:25:02","love":"288","hate":"26","comment":"13","repost":"64","bookmark":"25","bimageuri":"http://wimg.spriteapp.cn/picture/2018/0411/27458926_500.jpg","voiceuri":null,"voicetime":null,"voicelength":null,"status":"4","theme_id":"55163","theme_name":"主版块","theme_type":"1","videouri":"http://wvideo.spriteapp.cn/video/2018/0411/5acd632e56d2b_wpd.mp4","videotime":130,"original_pid":"0","cache_version":2,"playcount":"13545","playfcount":"1380","cai":"26","weixin_url":null,"image1":"http://wimg.spriteapp.cn/picture/2018/0411/27458926_500.jpg","image2":"http://wimg.spriteapp.cn/picture/2018/0411/27458926_500.jpg","is_gif":false,"image0":"http://wimg.spriteapp.cn/picture/2018/0411/27458926_500.jpg","image_small":"http://wimg.spriteapp.cn/picture/2018/0411/27458926_500.jpg","cdn_img":"http://wimg.spriteapp.cn/picture/2018/0411/27458926_500.jpg","width":"640","height":"368","tag":"","t":1523571902,"ding":"288","favourite":"25","top_cmt":null,"themes":null},{"type":"41","text":"两口子吵架大概就是这个样子吧\u2026\u2026","user_id":"20701528","name":"百思搞笑运动员","screen_name":"百思搞笑运动员","profile_image":"http://wx.qlogo.cn/mmopen/2EzJggZltBMKQovGd4IuGUTbUxCnE6yCanY15vtR21Cyjoy2mI5aRYicwic52mgwbibzBmQT9Sx8o2XlEeoDb6Go8swPabbictpo/0","created_at":"2018-04-13 04:55:02","create_time":null,"passtime":"2018-04-13 04:55:02","love":"190","hate":"13","comment":"9","repost":"32","bookmark":"19","bimageuri":"http://wimg.spriteapp.cn/picture/2018/0412/27415639_783.jpg","voiceuri":null,"voicetime":null,"voicelength":null,"status":"4","theme_id":"58191","theme_name":"搞笑视频","theme_type":"1","videouri":"http://wvideo.spriteapp.cn/video/2018/0412/45e050263e2211e8aa71842b2b4c75ab_wpd.mp4","videotime":32,"original_pid":"0","cache_version":2,"playcount":"8383","playfcount":"650","cai":"13","weixin_url":null,"image1":"http://wimg.spriteapp.cn/picture/2018/0412/27415639_783.jpg","image2":"http://wimg.spriteapp.cn/picture/2018/0412/27415639_783.jpg","is_gif":false,"image0":"http://wimg.spriteapp.cn/picture/2018/0412/27415639_783.jpg","image_small":"http://wimg.spriteapp.cn/picture/2018/0412/27415639_783.jpg","cdn_img":"http://wimg.spriteapp.cn/picture/2018/0412/27415639_783.jpg","width":"640","height":"352","tag":"","t":1523566502,"ding":"190","favourite":"19","top_cmt":null,"themes":null},{"type":"41","text":"怎样才能装作不经意间让女朋友看到这个视频...","user_id":"20746583","name":"紫尛少年","screen_name":"紫尛少年","profile_image":"http://wimg.spriteapp.cn/profile/20170512102523.jpg","created_at":"2018-04-13 01:04:22","create_time":null,"passtime":"2018-04-13 01:04:22","love":"194","hate":"19","comment":"19","repost":"35","bookmark":"12","bimageuri":"http://wimg.spriteapp.cn/picture/2018/0408/27404921_902.jpg","voiceuri":null,"voicetime":null,"voicelength":null,"status":"4","theme_id":"58191","theme_name":"搞笑视频","theme_type":"1","videouri":"http://wvideo.spriteapp.cn/video/2018/0408/a299415a3ae011e88ec5842b2b4c75ab_wpd.mp4","videotime":114,"original_pid":"0","cache_version":2,"playcount":"10885","playfcount":"1016","cai":"19","weixin_url":null,"image1":"http://wimg.spriteapp.cn/picture/2018/0408/27404921_902.jpg","image2":"http://wimg.spriteapp.cn/picture/2018/0408/27404921_902.jpg","is_gif":false,"image0":"http://wimg.spriteapp.cn/picture/2018/0408/27404921_902.jpg","image_small":"http://wimg.spriteapp.cn/picture/2018/0408/27404921_902.jpg","cdn_img":"http://wimg.spriteapp.cn/picture/2018/0408/27404921_902.jpg","width":"448","height":"310","tag":"","t":1523552662,"ding":"194","favourite":"12","top_cmt":null,"themes":null},{"type":"41","text":"女孩子得这样夸才行！吃鸡游戏硬是被你们玩成了撩妹现场\u2026\u2026","user_id":"17588230","name":"神马情况这是","screen_name":"神马情况这是","profile_image":"http://wimg.spriteapp.cn/profile/large/2018/03/21/5ab21157ebeaa_mini.jpg","created_at":"2018-04-13 00:54:02","create_time":null,"passtime":"2018-04-13 00:54:02","love":"240","hate":"29","comment":"35","repost":"20","bookmark":"29","bimageuri":"http://wimg.spriteapp.cn/picture/2018/0407/5ac861a9007c8__b.jpg","voiceuri":null,"voicetime":null,"voicelength":null,"status":"4","theme_id":"0","theme_name":"","theme_type":"0","videouri":"http://wvideo.spriteapp.cn/video/2018/0407/5ac861a9168ca_wpd.mp4","videotime":48,"original_pid":"0","cache_version":2,"playcount":"20233","playfcount":"3440","cai":"29","weixin_url":null,"image1":"http://wimg.spriteapp.cn/picture/2018/0407/5ac861a9007c8__b.jpg","image2":"http://wimg.spriteapp.cn/picture/2018/0407/5ac861a9007c8__b.jpg","is_gif":false,"image0":"http://wimg.spriteapp.cn/picture/2018/0407/5ac861a9007c8__b.jpg","image_small":"http://wimg.spriteapp.cn/picture/2018/0407/5ac861a9007c8__b.jpg","cdn_img":"http://wimg.spriteapp.cn/picture/2018/0407/5ac861a9007c8__b.jpg","width":"368","height":"352","tag":"","t":1523552042,"ding":"240","favourite":"29","top_cmt":null,"themes":null},{"type":"41","text":"听语/这几种情侣，最容易分手\u2026\u2026","user_id":"20701528","name":"百思搞笑运动员","screen_name":"百思搞笑运动员","profile_image":"http://wx.qlogo.cn/mmopen/2EzJggZltBMKQovGd4IuGUTbUxCnE6yCanY15vtR21Cyjoy2mI5aRYicwic52mgwbibzBmQT9Sx8o2XlEeoDb6Go8swPabbictpo/0","created_at":"2018-04-13 00:17:02","create_time":null,"passtime":"2018-04-13 00:17:02","love":"302","hate":"35","comment":"28","repost":"75","bookmark":"109","bimageuri":"http://wimg.spriteapp.cn/picture/2018/0412/27502702_292.jpg","voiceuri":null,"voicetime":null,"voicelength":null,"status":"4","theme_id":"55163","theme_name":"主版块","theme_type":"1","videouri":"http://wvideo.spriteapp.cn/video/2018/0412/71ce11603e1211e882ac842b2b4c75ab_wpd.mp4","videotime":269,"original_pid":"0","cache_version":2,"playcount":"11086","playfcount":"371","cai":"35","weixin_url":null,"image1":"http://wimg.spriteapp.cn/picture/2018/0412/27502702_292.jpg","image2":"http://wimg.spriteapp.cn/picture/2018/0412/27502702_292.jpg","is_gif":false,"image0":"http://wimg.spriteapp.cn/picture/2018/0412/27502702_292.jpg","image_small":"http://wimg.spriteapp.cn/picture/2018/0412/27502702_292.jpg","cdn_img":"http://wimg.spriteapp.cn/picture/2018/0412/27502702_292.jpg","width":"640","height":"358","tag":"","t":1523549822,"ding":"302","favourite":"109","top_cmt":null,"themes":null},{"type":"41","text":"1分钟教你学画超萌的小狗，画画从未如此简单！","user_id":"21163527","name":"猫咪domi","screen_name":"猫咪domi","profile_image":"http://wimg.spriteapp.cn/profile/large/2017/08/01/5980718fe08d7_mini.jpg","created_at":"2018-04-12 23:59:02","create_time":null,"passtime":"2018-04-12 23:59:02","love":"161","hate":"41","comment":"18","repost":"24","bookmark":"68","bimageuri":"http://wimg.spriteapp.cn/picture/2018/0305/5a9c9d9e13225_wpd.jpg","voiceuri":null,"voicetime":null,"voicelength":null,"status":"4","theme_id":"0","theme_name":"","theme_type":"0","videouri":"http://wvideo.spriteapp.cn/video/2018/0305/5a9c9d9e13225_wpd.mp4","videotime":49,"original_pid":"0","cache_version":2,"playcount":"7035","playfcount":"523","cai":"41","weixin_url":null,"image1":"http://wimg.spriteapp.cn/picture/2018/0305/5a9c9d9e13225_wpd.jpg","image2":"http://wimg.spriteapp.cn/picture/2018/0305/5a9c9d9e13225_wpd.jpg","is_gif":false,"image0":"http://wimg.spriteapp.cn/picture/2018/0305/5a9c9d9e13225_wpd.jpg","image_small":"http://wimg.spriteapp.cn/picture/2018/0305/5a9c9d9e13225_wpd.jpg","cdn_img":"http://wimg.spriteapp.cn/picture/2018/0305/5a9c9d9e13225_wpd.jpg","width":"1066","height":"600","tag":"","t":1523548742,"ding":"161","favourite":"68","top_cmt":null,"themes":null},{"type":"41","text":"发现监控的小偷合集，逗死我了，全是戏精！","user_id":"9045414","name":"步步高升l大哥","screen_name":"步步高升l大哥","profile_image":"http://wimg.spriteapp.cn/profile/large/2017/03/20/58ceaeb8b2d5c_mini.jpg","created_at":"2018-04-12 23:54:23","create_time":null,"passtime":"2018-04-12 23:54:23","love":"608","hate":"59","comment":"53","repost":"88","bookmark":"69","bimageuri":"http://wimg.spriteapp.cn/picture/2018/0407/a2268e883a6a11e8b4d9842b2b4c75ab_wpd.jpg","voiceuri":null,"voicetime":null,"voicelength":null,"status":"4","theme_id":"0","theme_name":"","theme_type":"0","videouri":"http://wvideo.spriteapp.cn/video/2018/0407/a2268e883a6a11e8b4d9842b2b4c75ab_wpd.mp4","videotime":117,"original_pid":"0","cache_version":2,"playcount":"41971","playfcount":"4023","cai":"59","weixin_url":null,"image1":"http://wimg.spriteapp.cn/picture/2018/0407/a2268e883a6a11e8b4d9842b2b4c75ab_wpd.jpg","image2":"http://wimg.spriteapp.cn/picture/2018/0407/a2268e883a6a11e8b4d9842b2b4c75ab_wpd.jpg","is_gif":false,"image0":"http://wimg.spriteapp.cn/picture/2018/0407/a2268e883a6a11e8b4d9842b2b4c75ab_wpd.jpg","image_small":"http://wimg.spriteapp.cn/picture/2018/0407/a2268e883a6a11e8b4d9842b2b4c75ab_wpd.jpg","cdn_img":"http://wimg.spriteapp.cn/picture/2018/0407/a2268e883a6a11e8b4d9842b2b4c75ab_wpd.jpg","width":"352","height":"592","tag":"","t":1523548463,"ding":"608","favourite":"69","top_cmt":null,"themes":null},{"type":"41","text":"兄弟你这脚有点过分了啊😂","user_id":"19211563","name":"我的下一页由你撰写","screen_name":"我的下一页由你撰写","profile_image":"http://wimg.spriteapp.cn/profile/large/2017/10/05/59d644960c99b_mini.jpg","created_at":"2018-04-12 23:39:05","create_time":null,"passtime":"2018-04-12 23:39:05","love":"170","hate":"13","comment":"10","repost":"17","bookmark":"8","bimageuri":"http://wimg.spriteapp.cn/picture/2018/0412/5ace360c33e49_wpd.jpg","voiceuri":null,"voicetime":null,"voicelength":null,"status":"4","theme_id":"50436","theme_name":"搞笑短剧","theme_type":"1","videouri":"http://dvideo.spriteapp.cn/video/2018/0412/5ace360c33e49_wpd.mp4","videotime":14,"original_pid":"0","cache_version":2,"playcount":"23135","playfcount":"2659","cai":"13","weixin_url":null,"image1":"http://wimg.spriteapp.cn/picture/2018/0412/5ace360c33e49_wpd.jpg","image2":"http://wimg.spriteapp.cn/picture/2018/0412/5ace360c33e49_wpd.jpg","is_gif":false,"image0":"http://wimg.spriteapp.cn/picture/2018/0412/5ace360c33e49_wpd.jpg","image_small":"http://wimg.spriteapp.cn/picture/2018/0412/5ace360c33e49_wpd.jpg","cdn_img":"http://wimg.spriteapp.cn/picture/2018/0412/5ace360c33e49_wpd.jpg","width":"540","height":"960","tag":"","t":1523547545,"ding":"170","favourite":"8","top_cmt":null,"themes":null},{"type":"41","text":"爆笑虫子1：贵族老爷们，让你笑到停不下来","user_id":"19634336","name":"阿强马强赛马强","screen_name":"阿强马强赛马强","profile_image":"http://wimg.spriteapp.cn/profile/large/2018/01/16/5a5dce3778d6a_mini.jpg","created_at":"2018-04-12 23:34:02","create_time":null,"passtime":"2018-04-12 23:34:02","love":"165","hate":"7","comment":"13","repost":"12","bookmark":"16","bimageuri":"http://wimg.spriteapp.cn/picture/2018/0411/27439934_550.jpg","voiceuri":null,"voicetime":null,"voicelength":null,"status":"4","theme_id":"0","theme_name":"","theme_type":"0","videouri":"http://dvideo.spriteapp.cn/video/2018/0411/64cfec5c3ced11e8a99b842b2b4c75ab_wpd.mp4","videotime":1026,"original_pid":"0","cache_version":2,"playcount":"3769","playfcount":"253","cai":"7","weixin_url":null,"image1":"http://wimg.spriteapp.cn/picture/2018/0411/27439934_550.jpg","image2":"http://wimg.spriteapp.cn/picture/2018/0411/27439934_550.jpg","is_gif":false,"image0":"http://wimg.spriteapp.cn/picture/2018/0411/27439934_550.jpg","image_small":"http://wimg.spriteapp.cn/picture/2018/0411/27439934_550.jpg","cdn_img":"http://wimg.spriteapp.cn/picture/2018/0411/27439934_550.jpg","width":"854","height":"480","tag":"","t":1523547242,"ding":"165","favourite":"16","top_cmt":null,"themes":null},{"type":"41","text":"神一样的操作，吃鸡的小伙伴赶紧试一试","user_id":"18482739","name":"年少滥情不花心","screen_name":"年少滥情不花心","profile_image":"http://wimg.spriteapp.cn/profile/large/2017/07/04/595b4417f130c_mini.jpg","created_at":"2018-04-12 23:23:02","create_time":null,"passtime":"2018-04-12 23:23:02","love":"217","hate":"20","comment":"10","repost":"43","bookmark":"17","bimageuri":"http://wimg.spriteapp.cn/picture/2018/0412/ec021d5e-3ddc-11e8-acf3-1866daeb0df1_wpd.jpg","voiceuri":null,"voicetime":null,"voicelength":null,"status":"4","theme_id":"0","theme_name":"","theme_type":"0","videouri":"http://dvideo.spriteapp.cn/video/2018/0412/ec021d5e-3ddc-11e8-acf3-1866daeb0df1_wpd.mp4","videotime":14,"original_pid":"0","cache_version":2,"playcount":"12240","playfcount":"3021","cai":"20","weixin_url":null,"image1":"http://wimg.spriteapp.cn/picture/2018/0412/ec021d5e-3ddc-11e8-acf3-1866daeb0df1_wpd.jpg","image2":"http://wimg.spriteapp.cn/picture/2018/0412/ec021d5e-3ddc-11e8-acf3-1866daeb0df1_wpd.jpg","is_gif":false,"image0":"http://wimg.spriteapp.cn/picture/2018/0412/ec021d5e-3ddc-11e8-acf3-1866daeb0df1_wpd.jpg","image_small":"http://wimg.spriteapp.cn/picture/2018/0412/ec021d5e-3ddc-11e8-acf3-1866daeb0df1_wpd.jpg","cdn_img":"http://wimg.spriteapp.cn/picture/2018/0412/ec021d5e-3ddc-11e8-acf3-1866daeb0df1_wpd.jpg","width":"852","height":"480","tag":"","t":1523546582,"ding":"217","favourite":"17","top_cmt":null,"themes":null}]
     */

    private int code;
    private String msg;
    private List<DataEntity> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * type : 41
         * text : 2018十大最强洗脑神曲，太魔性了，你听过几首？
         * user_id : 18482739
         * name : 年少滥情不花心
         * screen_name : 年少滥情不花心
         * profile_image : http://wimg.spriteapp.cn/profile/large/2017/07/04/595b4417f130c_mini.jpg
         * created_at : 2018-04-13 09:38:02
         * create_time : null
         * passtime : 2018-04-13 09:38:02
         * love : 291
         * hate : 16
         * comment : 5
         * repost : 52
         * bookmark : 203
         * bimageuri : http://wimg.spriteapp.cn/picture/2018/0412/f585d024-3ddb-11e8-aa15-1866daeb0df1_wpd_86.jpg
         * voiceuri : null
         * voicetime : null
         * voicelength : null
         * status : 4
         * theme_id : 22871
         * theme_name : 音乐汇
         * theme_type : 1
         * videouri : http://wvideo.spriteapp.cn/video/2018/0412/f585d024-3ddb-11e8-aa15-1866daeb0df1_wpd.mp4
         * videotime : 159
         * original_pid : 0
         * cache_version : 2
         * playcount : 9193
         * playfcount : 494
         * cai : 16
         * weixin_url : null
         * image1 : http://wimg.spriteapp.cn/picture/2018/0412/f585d024-3ddb-11e8-aa15-1866daeb0df1_wpd_86.jpg
         * image2 : http://wimg.spriteapp.cn/picture/2018/0412/f585d024-3ddb-11e8-aa15-1866daeb0df1_wpd_86.jpg
         * is_gif : false
         * image0 : http://wimg.spriteapp.cn/picture/2018/0412/f585d024-3ddb-11e8-aa15-1866daeb0df1_wpd_86.jpg
         * image_small : http://wimg.spriteapp.cn/picture/2018/0412/f585d024-3ddb-11e8-aa15-1866daeb0df1_wpd_86.jpg
         * cdn_img : http://wimg.spriteapp.cn/picture/2018/0412/f585d024-3ddb-11e8-aa15-1866daeb0df1_wpd_86.jpg
         * width : 852
         * height : 480
         * tag :
         * t : 1523583482
         * ding : 291
         * favourite : 203
         * top_cmt : null
         * themes : null
         */

        private String type;
        private String text;
        private String user_id;
        private String name;
        private String screen_name;
        private String profile_image;
        private String created_at;
        private Object create_time;
        private String passtime;
        private String love;
        private String hate;
        private String comment;
        private String repost;
        private String bookmark;
        private String bimageuri;
        private Object voiceuri;
        private Object voicetime;
        private Object voicelength;
        private String status;
        private String theme_id;
        private String theme_name;
        private String theme_type;
        private String videouri;
        private long videotime;
        private String original_pid;
        private int cache_version;
        private String playcount;
        private String playfcount;
        private String cai;
        private Object weixin_url;
        private String image1;
        private String image2;
        private boolean is_gif;
        private String image0;
        private String image_small;
        private String cdn_img;
        private String width;
        private String height;
        private String tag;
        private int t;
        private String ding;
        private String favourite;
        private Object top_cmt;
        private Object themes;

        @Override
        public int hashCode() {
            if (TextUtils.isEmpty(name)) {
                return super.hashCode();
            }
            return name.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }

            Contact contact = (Contact) obj;
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(contact.getName())) {
                return false;
            }
            return name.equals(contact.getName());
        }

        @Override
        public String toString() {
            return "DataEntity{" +
                    "type='" + type + '\'' +
                    ", text='" + text + '\'' +
                    ", user_id='" + user_id + '\'' +
                    ", name='" + name + '\'' +
                    ", screen_name='" + screen_name + '\'' +
                    ", profile_image='" + profile_image + '\'' +
                    ", created_at='" + created_at + '\'' +
                    ", create_time=" + create_time +
                    ", passtime='" + passtime + '\'' +
                    ", love='" + love + '\'' +
                    ", hate='" + hate + '\'' +
                    ", comment='" + comment + '\'' +
                    ", repost='" + repost + '\'' +
                    ", bookmark='" + bookmark + '\'' +
                    ", bimageuri='" + bimageuri + '\'' +
                    ", voiceuri=" + voiceuri +
                    ", voicetime=" + voicetime +
                    ", voicelength=" + voicelength +
                    ", status='" + status + '\'' +
                    ", theme_id='" + theme_id + '\'' +
                    ", theme_name='" + theme_name + '\'' +
                    ", theme_type='" + theme_type + '\'' +
                    ", videouri='" + videouri + '\'' +
                    ", videotime=" + videotime +
                    ", original_pid='" + original_pid + '\'' +
                    ", cache_version=" + cache_version +
                    ", playcount='" + playcount + '\'' +
                    ", playfcount='" + playfcount + '\'' +
                    ", cai='" + cai + '\'' +
                    ", weixin_url=" + weixin_url +
                    ", image1='" + image1 + '\'' +
                    ", image2='" + image2 + '\'' +
                    ", is_gif=" + is_gif +
                    ", image0='" + image0 + '\'' +
                    ", image_small='" + image_small + '\'' +
                    ", cdn_img='" + cdn_img + '\'' +
                    ", width='" + width + '\'' +
                    ", height='" + height + '\'' +
                    ", tag='" + tag + '\'' +
                    ", t=" + t +
                    ", ding='" + ding + '\'' +
                    ", favourite='" + favourite + '\'' +
                    ", top_cmt=" + top_cmt +
                    ", themes=" + themes +
                    '}';
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getScreen_name() {
            return screen_name;
        }

        public void setScreen_name(String screen_name) {
            this.screen_name = screen_name;
        }

        public String getProfile_image() {
            return profile_image;
        }

        public void setProfile_image(String profile_image) {
            this.profile_image = profile_image;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public Object getCreate_time() {
            return create_time;
        }

        public void setCreate_time(Object create_time) {
            this.create_time = create_time;
        }

        public String getPasstime() {
            return passtime;
        }

        public void setPasstime(String passtime) {
            this.passtime = passtime;
        }

        public String getLove() {
            return love;
        }

        public void setLove(String love) {
            this.love = love;
        }

        public String getHate() {
            return hate;
        }

        public void setHate(String hate) {
            this.hate = hate;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getRepost() {
            return repost;
        }

        public void setRepost(String repost) {
            this.repost = repost;
        }

        public String getBookmark() {
            return bookmark;
        }

        public void setBookmark(String bookmark) {
            this.bookmark = bookmark;
        }

        public String getBimageuri() {
            return bimageuri;
        }

        public void setBimageuri(String bimageuri) {
            this.bimageuri = bimageuri;
        }

        public Object getVoiceuri() {
            return voiceuri;
        }

        public void setVoiceuri(Object voiceuri) {
            this.voiceuri = voiceuri;
        }

        public Object getVoicetime() {
            return voicetime;
        }

        public void setVoicetime(Object voicetime) {
            this.voicetime = voicetime;
        }

        public Object getVoicelength() {
            return voicelength;
        }

        public void setVoicelength(Object voicelength) {
            this.voicelength = voicelength;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTheme_id() {
            return theme_id;
        }

        public void setTheme_id(String theme_id) {
            this.theme_id = theme_id;
        }

        public String getTheme_name() {
            return theme_name;
        }

        public void setTheme_name(String theme_name) {
            this.theme_name = theme_name;
        }

        public String getTheme_type() {
            return theme_type;
        }

        public void setTheme_type(String theme_type) {
            this.theme_type = theme_type;
        }

        public String getVideouri() {
            return videouri;
        }

        public void setVideouri(String videouri) {
            this.videouri = videouri;
        }

        public long getVideotime() {
            return videotime;
        }

        public void setVideotime(long videotime) {
            this.videotime = videotime;
        }

        public String getOriginal_pid() {
            return original_pid;
        }

        public void setOriginal_pid(String original_pid) {
            this.original_pid = original_pid;
        }

        public int getCache_version() {
            return cache_version;
        }

        public void setCache_version(int cache_version) {
            this.cache_version = cache_version;
        }

        public String getPlaycount() {
            return playcount;
        }

        public void setPlaycount(String playcount) {
            this.playcount = playcount;
        }

        public String getPlayfcount() {
            return playfcount;
        }

        public void setPlayfcount(String playfcount) {
            this.playfcount = playfcount;
        }

        public String getCai() {
            return cai;
        }

        public void setCai(String cai) {
            this.cai = cai;
        }

        public Object getWeixin_url() {
            return weixin_url;
        }

        public void setWeixin_url(Object weixin_url) {
            this.weixin_url = weixin_url;
        }

        public String getImage1() {
            return image1;
        }

        public void setImage1(String image1) {
            this.image1 = image1;
        }

        public String getImage2() {
            return image2;
        }

        public void setImage2(String image2) {
            this.image2 = image2;
        }

        public boolean isIs_gif() {
            return is_gif;
        }

        public void setIs_gif(boolean is_gif) {
            this.is_gif = is_gif;
        }

        public String getImage0() {
            return image0;
        }

        public void setImage0(String image0) {
            this.image0 = image0;
        }

        public String getImage_small() {
            return image_small;
        }

        public void setImage_small(String image_small) {
            this.image_small = image_small;
        }

        public String getCdn_img() {
            return cdn_img;
        }

        public void setCdn_img(String cdn_img) {
            this.cdn_img = cdn_img;
        }

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public int getT() {
            return t;
        }

        public void setT(int t) {
            this.t = t;
        }

        public String getDing() {
            return ding;
        }

        public void setDing(String ding) {
            this.ding = ding;
        }

        public String getFavourite() {
            return favourite;
        }

        public void setFavourite(String favourite) {
            this.favourite = favourite;
        }

        public Object getTop_cmt() {
            return top_cmt;
        }

        public void setTop_cmt(Object top_cmt) {
            this.top_cmt = top_cmt;
        }

        public Object getThemes() {
            return themes;
        }

        public void setThemes(Object themes) {
            this.themes = themes;
        }
    }
}
