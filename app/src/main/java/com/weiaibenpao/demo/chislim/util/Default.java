package com.weiaibenpao.demo.chislim.util;

import android.Manifest;

import com.weiaibenpao.demo.chislim.bean.MenuBean;
import com.weiaibenpao.demo.chislim.bean.SportDisSpiceBean;
import com.weiaibenpao.demo.chislim.bean.SportDistrictBean;
import com.weiaibenpao.demo.chislim.bean.SportTypeBean;

import java.util.ArrayList;

/**
 * Created by lenovo on 2016/8/13.
 */

public class Default {
//    public static final String url = "http://112.74.28.179:8080";
    public static final String url = "http://112.74.28.179:8080/chislim5/";
    //public static final String url = "http://172.28.106.1:8080";
    public static final String baiduKey = "467fa36385f73a29f31a6be49a221857";
    public static final String qiniu = "http://ofplk6att.bkt.clouddn.com/";
    public static final String getOneByPhone = "getOneByPhone";
    public static final String getAllTeach = "getAll";
    public static final String getOne = "getOne";
    public static final String updateUser = "updateUser";
    public static final String getOneSport = "getOne";
    public static final String updateProject = "updateProject";
    public static final String updateChannelId = "updateChannelId";
    public static final String countDis = "countDis";
    public static final String getOneByQQ = "getOneByQQ";
    public static final String getOneByWxin = "getOneByWxin";
    public static final String updateMark = "updateMark";
    public static final String getOneByWbo = "getOneByWbo";
    public static final String appKey = "e42cce3acb4d25422ca7451cdb6dc5d2";    //天气预报，聚合的数据

    public static final String urlPic = "http://ofplk6att.bkt.clouddn.com/";
    public static boolean  kaifaban = true;           //判断是否为开发版

    public static final int FIND = 0;
    public static final int LINK = 1;
    public static final int START = 2;
    public static final int STOP = 3;
    public static final long TIME_OUT = 20000;                                   // 扫描超时时间

    public static ArrayList initList(){
        ArrayList disList = new ArrayList();
        disList.add(3);
        disList.add(5);
        disList.add(7);
        disList.add(9);
        disList.add(10);
        disList.add(15);
        disList.add(20);
        disList.add(25);
        disList.add(30);
        disList.add(35);
        disList.add(40);
        disList.add(50);
        disList.add(70);
        disList.add(100);
        return disList;
    }


    public static final int WRITE_EXTERNAL_STORAGE_CODE = 6;
    public static final int READ_EXTERNAL_STORAGE_CODE = 7;


    public static ArrayList MenuList(){
        ArrayList menuList = new ArrayList();

        MenuBean menuBean1 = new MenuBean("http://ofplk6att.bkt.clouddn.com/home_music.png","音乐");
        MenuBean menuBean2 = new MenuBean("http://ofplk6att.bkt.clouddn.com/home_movie.png","电影");
        menuList.add(menuBean1);
        menuList.add(menuBean2);
        return menuList;
    }

    public static ArrayList getTeachMenuList(){
        ArrayList<String> teachMenuList = new ArrayList<String>();
        teachMenuList.add("全部教程");
        teachMenuList.add("减脂");
        teachMenuList.add("塑型");
        teachMenuList.add("增肌");
        teachMenuList.add("放松");
        teachMenuList.add("瑜伽");
        teachMenuList.add("跑步");

        return  teachMenuList;
    }


    public static ArrayList getSportTypeHome(){
        ArrayList<SportTypeBean> sportType = new ArrayList<SportTypeBean>();
        SportTypeBean sportTypeBean1 = new SportTypeBean("111111111111111111","沙地模式","25:00");
        SportTypeBean sportTypeBean2 = new SportTypeBean("111111111111111111","高原模式","25:00");
        SportTypeBean sportTypeBean3 = new SportTypeBean("111111111111111111","登山模式","25:00");
        SportTypeBean sportTypeBean4 = new SportTypeBean("111111111111111111","滨海小镇","25:00");
        SportTypeBean sportTypeBean5 = new SportTypeBean("111111111111111111","暴走模式","25:00");
        SportTypeBean sportTypeBean6 = new SportTypeBean("111111111111111111","音乐模式","25:00");

        sportType.add(sportTypeBean1);
        sportType.add(sportTypeBean2);
        sportType.add(sportTypeBean3);
        sportType.add(sportTypeBean4);
        sportType.add(sportTypeBean5);
        sportType.add(sportTypeBean6);


        return  sportType;
    }


    public static ArrayList getHealthList(){
        ArrayList<String> healthList = new ArrayList<String>();
        healthList.add("http://ofplk6att.bkt.clouddn.com/home_boji.png");
        healthList.add("http://ofplk6att.bkt.clouddn.com/home_zengji.png");
        healthList.add("http://ofplk6att.bkt.clouddn.com/home_suxing.png");
        healthList.add("http://ofplk6att.bkt.clouddn.com/home_yujia.png");
        healthList.add("http://ofplk6att.bkt.clouddn.com/home_qixie.png");
        healthList.add("http://ofplk6att.bkt.clouddn.com/home_jianzhi.png");
        return  healthList;
    }
    //healthList.add("http://ofplk6att.bkt.clouddn.com/home_movie.png");120.181728,30.306727
    //healthList.add("http://ofplk6att.bkt.clouddn.com/home_music.png");91.210166,29.636627
    //healthList.add("http://ofplk6att.bkt.clouddn.com/home_import_class.png");102.954294,29.976163

    public static  ArrayList getSportDisSpice(){
        ArrayList arrayList = new ArrayList();
        SportDisSpiceBean sp1 = new SportDisSpiceBean(1,"测试1","测试2",29.988938,102.981349,29.632566,91.139585,"http://img.hb.aicdn.com/453c1cefb19fb73566a6e036dc8109f7883113ba16c5b-JicWZg_fw658","最美318","走在天堂和地狱之间国道318",18000);
        SportDisSpiceBean sp2 = new SportDisSpiceBean(2,"测试1","测试2",30.308135,120.369698,30.262479,120.357252,"http://img.hb.aicdn.com/03fe29e6c986a085949f53acb4bbabf8f91ae9668cba-4iQkKe_fw658","世界之巅我来了","来到这里不为爬山，只为看一眼珠峰，那是一块没有雕琢的原始玉石，有着自己独特的风格与造型，处处散发着自然的芬芳，被称为人类的最后一块净土",18000);
        SportDisSpiceBean sp3 = new SportDisSpiceBean(3,"测试1","测试2",36.583095,101.804877,37.358787,97.312842,"http://img.hb.aicdn.com/7ce77f90105f9c3a56afd52905b22e48097aa53ee2ba-64cqgn_fw658","只为看你一眼女神，青海湖","广阔灿烂的星空，月光下波光粼粼的湖水，以及两个人依偎坐在湖边草地上，寒风中傻傻的拍星空。我忘记了寒风刺骨的冷，记住了一起仰望星空时的浪漫",18000);
        SportDisSpiceBean sp4 = new SportDisSpiceBean(4,"测试1","测试2",36.104044,103.881307,40.142668,94.641805,"http://a3-q.mafengwo.net/s6/M00/20/A1/wKgB4lM9DkmAblWZAARK4eQiT8Y91.jpeg?imageMogr2%2Fthumbnail%2F%211020x540r%2Fgravity%2FCenter%2Fcrop%2F%211020x540%2Fquality%2F100","大漠孤烟，长河落日","悠扬的驼铃声在遥远的丝绸之路上此起彼伏，盛大的敦煌作为丝绸之路的重要关隘，历经沧桑，几度盛衰，步覆蹒跚地走过了几千年漫长曲折的里程。",18000);
        SportDisSpiceBean sp5 = new SportDisSpiceBean(5,"测试1","测试2",30.121388,97.27939,30.076899,97.284733,"http://img.hb.aicdn.com/9b12e4f6ae39de03a01608c747e180e3ff9de8cd356cb-CVC6Z9_fw658","怒江72拐","七十二拐不算是景点，可是她用她的险峻和壮美吸引着一批批的驴友前来，人们只是记住了她的险，却忘却了在此不幸遇难的驴友，以及他们用生命换来的教训",18000);

        arrayList.add(sp1);
        arrayList.add(sp2);
        arrayList.add(sp3);
        arrayList.add(sp4);
        arrayList.add(sp5);
        arrayList.add(sp1);
        arrayList.add(sp2);
        arrayList.add(sp3);
        arrayList.add(sp4);
        arrayList.add(sp5);

        return arrayList;
    }


    public static  ArrayList getSportDistrict(){
        ArrayList arrayList = new ArrayList();
        //    id    地名    区域周长     描述     热度     背景图片地址
        SportDistrictBean sd1 = new SportDistrictBean(1,"浙江","zhejiang","浙江是吴越文化，江南文化的发源地，是中国古代文明的发祥地之一。距今6000年的马家浜文化和距今5000年的良渚文化，是典型的山水江南，被称为“丝绸之府”。",13458,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1484914125513&di=e02e93682715a05c896dfbe7643a5bb1&imgtype=0&src=http%3A%2F%2Fc.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2F8ad4b31c8701a18b1d6e5de29e2f07082938fe92.jpg");
        SportDistrictBean sd2 = new SportDistrictBean(2,"北京","beijing","北京简称京，是中国的首都，全国的政治、文化中心和国际交往的枢纽，也是一座著名的历史文化名城，与西安、洛阳、开封、南京、杭州并列为中国六大古都。",14588,"http://s3.lvjs.com.cn/uploads/pc/place2/09/C_20130709-154629_1280_.jpg");
        SportDistrictBean sd3 = new SportDistrictBean(3,"天津","tianjin","天津，简称津，中华人民共和国直辖市、国家中心城市、环渤海地区经济中心、首批沿海开放城市，全国先进制造研发基地、北方国际航运核心区、改革开放先行区。",16588,"http://img3.imgtn.bdimg.com/it/u=3736193010,1551672721&fm=23&gp=0.jpg");
        SportDistrictBean sd4 = new SportDistrictBean(4,"上海","shanghai","上海，简称“沪”或“申”，是中华人民共和国直辖市，国家中心城市，超大城市，中国的经济、交通、科技、工业、金融、贸易、会展和航运中心，首批沿海开放城市。",18888,"http://img.pconline.com.cn/images/upload/upc/tx/itbbs/1409/19/c3/38756150_1411095713221.jpg");
        SportDistrictBean sd5 = new SportDistrictBean(5,"重庆","chongqing","重庆，简称巴或渝，位于中国西南部，是中华人民共和国中央直辖市、国家重要的中心城市[1]  、长江上游地区经济、金融、商贸物流、科技创新和航运中心。",18888,"http://img0.imgtn.bdimg.com/it/u=1326524462,2137854994&fm=23&gp=0.jpg");
        SportDistrictBean sd6 = new SportDistrictBean(6,"河北","hebei","河北省，简称“冀”，因位于黄河以北而得名。[1]  地处华北平原，东临渤海、内环京津，西为太行山，北为燕山，燕山以北为张北高原。",18888,"http://imglf0.ph.126.net/pZCWU40p4Du20tx2nugPMg==/6630599373745821735.jpg");
        SportDistrictBean sd7 = new SportDistrictBean(7,"河南","henan","河南，古称中原、中州、豫州，简称“豫”，因历史上大部分位于黄河以南，故名河南。河南位于中国中东部、黄河中下游。",18888,"http://img5.imgtn.bdimg.com/it/u=2869146899,2233598050&fm=23&gp=0.jpg");
        SportDistrictBean sd8 = new SportDistrictBean(8,"湖北","hubei","湖北，简称“鄂”，省会武汉，位于中国中部偏南、长江中游，洞庭湖以北，故名湖北。",18888,"http://file25.mafengwo.net/M00/11/4A/wKgB4lMC5MuAf7poAADYa3dQh_I06.jpeg");
        SportDistrictBean sd9 = new SportDistrictBean(9,"湖南","hunan","湖南自古盛植木芙蓉，唐朝谭用之有诗“秋风万里芙蓉国”咏之，毛泽东更是用“芙蓉国里尽朝晖”赞美湖南，因此又有“芙蓉国”之称。",18888,"http://zx.01ny.cn/uploadfile/2015/0404/20150404103325780.jpg");
        SportDistrictBean sd10 = new SportDistrictBean(10,"江苏","jiangsu","江苏，简称“苏”，省会南京，位于中国大陆东部沿海，介于东经116°18′～121°57′，北纬30°45′～35°20′之间。公元1667年因江南省东西分置而建省。",18888,"http://img0.imgtn.bdimg.com/it/u=670295993,406408651&fm=23&gp=0.jpg");
        SportDistrictBean sd11 = new SportDistrictBean(11,"江西","jiangxi","江西地处中国东南部，东邻浙江省、福建省，南连广东省，西接湖南省，北毗湖北省、安徽省而共接长江，属于华东地区。",18888,"http://upload.17u.net/uploadpicbase/image/201604291154454686.jpg");
        SportDistrictBean sd12 = new SportDistrictBean(12,"辽宁","liaoning","辽宁省是中国重要的重工业基地，是全国工业门类最为齐全的省份，中国最早实行对外开放政策的沿海省份之一，是新中国工业崛起的摇篮。",18888,"http://www.hngican.com/UploadFiles/FCK/2014-10/20141020688L4V0P66.jpg");
        SportDistrictBean sd13 = new SportDistrictBean(13,"吉林","jilin","吉林省地处中国东北中部，东北亚地理中心，因清初建吉林乌拉城而得名，简称“吉”，省会长春。地跨东经121°38′～131°19′、北纬40°50′～46°19′之间。",18888,"http://c.hiphotos.baidu.com/lvpics/s=800/sign=0522a74132fae6cd08b4a6613fb30f9e/b21bb051f8198618a473a9f24ded2e738bd4e694.jpg");
        SportDistrictBean sd14 = new SportDistrictBean(14,"黑龙江","heilongjiang","黑龙江东部和北部以乌苏里江、黑龙江为界河与俄罗斯为邻，与俄罗斯的水陆边界长约3045公里；西接内蒙古自治区，南连吉林省。",18888,"http://img4.imgtn.bdimg.com/it/u=1017364534,2323472382&fm=23&gp=0.jpg");
        SportDistrictBean sd15 = new SportDistrictBean(15,"陕西","shanxi","陕西是中国经纬度基准点大地原点和北京时间中国科学院国家授时中心所在地。全省总面积20.58万平方公里。",18888,"http://dimg04.c-ctrip.com/images/fd/vacations/g1/M09/21/98/CghzfVR9jzGAIVCGADNwZzm9794605.jpg");
        SportDistrictBean sd16 = new SportDistrictBean(16,"山西","shanxi","山西，因居太行山之西而得名，简称“晋”，又称“三晋”。山西东依太行山，西、南依吕梁山、黄河，北依长城,内蒙古等省区为界，柳宗元称之为“表里山河”。",18888,"http://n.sinaimg.cn/transform/20150713/tZgd-fxewnie5457812.jpg");
        SportDistrictBean sd17 = new SportDistrictBean(17,"山东","shandong","山东是儒家文化发源地，儒家思想的创立人孔子、孟子，以及墨家思想的创始人墨子、军事家吴起等，均出生于鲁国。",18888,"http://s.sdnews.com.cn/lygl/201605/W020160516387188928675.jpg");
        SportDistrictBean sd18 = new SportDistrictBean(18,"四川","sichuan","四川，简称“川”或“蜀”，省会成都，位于中国大陆西南腹地，东部为川东平行岭谷和川中丘陵，中部为成都平原，重庆诸省市交界，是国宝大熊猫的故乡。",18888,"http://travel.taiwan.cn/list/201501/W020150101245670176644.jpg");
        SportDistrictBean sd19 = new SportDistrictBean(19,"青海","qinghai","青海，位于中国西部，雄踞世界屋脊青藏高原的东北部[1]  。是中国青藏高原上的重要省份之一，简称青，省会为西宁。",18888,"http://img.pconline.com.cn/images/upload/upc/tx/photoblog/1608/10/c10/25388437_1470820945181_mthumb.jpg");
        SportDistrictBean sd20 = new SportDistrictBean(20,"安徽","anhui","安徽，简称“皖”，省会合肥，位于中国大陆东部，介于东经114°54′-119°37′，北纬29°41′-34°38′之间，公元1667年因江南省东西分置而建省。",18888,"http://pic2.ooopic.com/12/19/15/92bOOOPIC41_1024.jpg");
        SportDistrictBean sd21 = new SportDistrictBean(21,"海南","hainan","海南藏族自治州，是青海省下辖的一个自治州，总面积4.6万平方公里，辖共和、贵德、贵南、同德、兴海5县和龙羊峡行委，共有41个乡镇，自治州政府驻共和县。",18888,"http://www.fm1039.com/2010/11/lvyou/20101104102741.jpg");
        SportDistrictBean sd22 = new SportDistrictBean(22,"广东","guangdong","广东是岭南文化的重要传承地，在语言、风俗、生活习惯、历史文化等方面都有着独特风格，通行粤语，而且粤、客两大方言的中心都在广东。",18888,"http://img.taihuwang.com/nvxing/uploads/20151016/bhdurmfxgaa.jpg");
        SportDistrictBean sd23 = new SportDistrictBean(23,"贵州","guizhou","贵州是古人类发祥地之一，远古人类化石和远古文化遗存发现颇多。早在24万年前，就有人类栖息繁衍，已发现石器时代文化遗址80余处。",18888,"http://youimg1.c-ctrip.com/target/tg/589/452/077/253ea5b65da44d938ce90d79d881d083.jpg");
        SportDistrictBean sd24 = new SportDistrictBean(24,"福建","fujian","福建的地理特点是“依山傍海”，九成陆地面积为山地丘陵地带，被称为“八山一水一分田”。福建的森林覆盖率达65.95%，居全国第一。",18888,"http://cyjctrip.qiniudn.com/151361/1408523358101p18vp8h9gf7gb10v11cgo1mdl4384.jpg");
        SportDistrictBean sd25 = new SportDistrictBean(25,"台湾","taiwan","台湾是中国不可分割的一部分。[9-11]  原住民族（高山族）在17世纪汉族移入前即已在此定居；自明末清初始有较显著之福建南部，最终形成以汉族为主体的社会。",18888,"http://pic.baike.soso.com/p/20140120/20140120125107-28721468.jpg");
        SportDistrictBean sd26 = new SportDistrictBean(26,"甘肃","gansu","甘肃历史跨越八千余年，是中华民族和华夏文明的重要发祥地之一，也是中医药学的发祥地之一，中华民族的人文始祖伏羲、女娲和黄帝相传诞生在甘肃。",18888,"http://img.pconline.com.cn/images/upload/upc/tx/itbbs/1405/06/c25/33933932_1399371672791.jpeg");
        SportDistrictBean sd27 = new SportDistrictBean(27,"云南","yunnan","云南，简称云（滇），省会昆明，位于中国西南的边陲，是人类文明重要发祥地之一。生活在距今170万年前的云南元谋人，是截至2013年为止发现的中国和亚洲最早人类。",18888,"http://limg1.qunarzz.com/T1nFbTBCWT1R49Ip6K.jpeg_r_1920x1080x90_0c0cf80d.jpeg");
        SportDistrictBean sd28 = new SportDistrictBean(28,"西藏","xizang","西藏自治区（藏文：བོད་རང་སྐྱོང་ལྗོངས།，藏语拼音：Poi Ranggyong Jong，威利转写：Bod rang skyong ljongs），简称“藏”，通称西藏，位于中国西南边陲，首府拉萨。",18888,"http://img3.imgtn.bdimg.com/it/u=1602286314,1341295587&fm=23&gp=0.jpg");
        SportDistrictBean sd29 = new SportDistrictBean(29,"宁夏","ningxia","宁夏得黄河水灌溉而形成了悠久的黄河文明。[1]  早在三万年前，宁夏就已有了人类生息的痕迹。历史上是“丝绸之路”的要道，素有“塞上江南”之美誉。",18888,"http://www.nmsg.cn/uploadfile/2013/0513/20130513110827877.jpg");
        SportDistrictBean sd30 = new SportDistrictBean(30,"广西","guangxi","广西壮族自治区，通称广西，简称“桂”，首府南宁，是中国唯一沿海的自治区。截至2015年末，全区常住人口4796万人，下辖有14个地级市。",18888,"http://m.tuniucdn.com/filebroker/cdn/olb/c2/8a/c28a0d58df2429a0a03ddd5b84bf7bc0_w560_h350_c1_t0.jpg");
        SportDistrictBean sd31 = new SportDistrictBean(31,"新疆","xingjiang","新疆维吾尔自治区，位于中国西北边陲，是中国五个少数民族自治区之一，也是中国陆地面积最大的省级行政区，面积166万平方公里，占中国国土总面积六分之一。",18888,"http://img.pconline.com.cn/images/upload/upc/tx/photoblog/1310/30/c0/28123172_28123172_1383076179762.jpg");
        SportDistrictBean sd32 = new SportDistrictBean(32,"内蒙古","neimenggu","内蒙古地处欧亚大陆内部，现辖9个地级市和3个盟，面积118.3万平方公里，占全国总面积的12.3%；2014年末全区常住人口为2504.8万人。",18888,"http://upload.mnw.cn/2015/0824/1440385454301.jpg");
        SportDistrictBean sd33 = new SportDistrictBean(33,"香港","xianggang","香港是全球高度繁荣的国际大都会之一，全境由香港岛、九龙半岛、新界等3大区域组成，管辖陆地总面积1104.32平方公里，人口密度居全世界第三。",18888,"http://img0.imgtn.bdimg.com/it/u=3816321559,2385100281&fm=23&gp=0.jpg");
        SportDistrictBean sd34 = new SportDistrictBean(34,"澳门","aomen","澳门，全称为中华人民共和国澳门特别行政区。北邻广东省珠海市，西与珠海市的湾仔和横琴对望，东与香港隔海相望，相距60公里，南临中国南海。",18888,"http://programme.rthk.org.hk/assets/images/rthk/dtt31/hkcc/originals/mfile_858_268965_2_l.jpg");

        arrayList.add(sd1);
        arrayList.add(sd2);
        arrayList.add(sd3);
        arrayList.add(sd4);
        arrayList.add(sd5);
        arrayList.add(sd6);
        arrayList.add(sd7);
        arrayList.add(sd8);
        arrayList.add(sd9);
        arrayList.add(sd10);
        arrayList.add(sd11);
        arrayList.add(sd12);
        arrayList.add(sd13);
        arrayList.add(sd14);
        arrayList.add(sd15);
        arrayList.add(sd16);
        arrayList.add(sd17);
        arrayList.add(sd18);
        arrayList.add(sd19);
        arrayList.add(sd20);
        arrayList.add(sd21);
        arrayList.add(sd22);
        arrayList.add(sd23);
        arrayList.add(sd24);
        arrayList.add(sd25);
        arrayList.add(sd26);
        arrayList.add(sd27);
        arrayList.add(sd28);
        arrayList.add(sd29);
        arrayList.add(sd30);
        arrayList.add(sd31);
        arrayList.add(sd32);
        arrayList.add(sd33);
        arrayList.add(sd34);

        return arrayList;
    }

    public static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
            //Manifest.permission.READ_CELL_BROADCASTS,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_SETTINGS};
}
