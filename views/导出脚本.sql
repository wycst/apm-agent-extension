/*
Navicat MySQL Data Transfer

Source Server         : 135
Source Server Version : 50721
Source Host           : 10.12.1.135:3306
Source Database       : monitor_jg

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-09-28 18:32:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `monitor_busi_sys`
-- ----------------------------
DROP TABLE IF EXISTS `monitor_busi_sys`;
CREATE TABLE `monitor_busi_sys` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `sys_name` varchar(200) COLLATE utf8_bin NOT NULL,
  `sys_code` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `web_page_name` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `manager` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `manufacturer` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `manufacturer_phone` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `manufacturer_mail` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `region_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of monitor_busi_sys
-- ----------------------------
INSERT INTO `monitor_busi_sys` VALUES ('1', '财务报表管理系统', '', '中国移动通信集团公司财务报表管理系统', '宋国强', '尚雪峰', '13811537415', 'shangxuefeng@jiuqi.com.cn ', '2');
INSERT INTO `monitor_busi_sys` VALUES ('2', '纪检监察管理系统', '', '中国移动纪检组网站/手机端', '宋培', '张超', '15901072363', 'zhangchao03@smartdot.com', '3');
INSERT INTO `monitor_busi_sys` VALUES ('3', '信息安全合规管理平台1', '', '中国移动信息安全合规管理平台', '宋培', '郭望望', '18511076905', 'guoww@smartdot.com', '1');
INSERT INTO `monitor_busi_sys` VALUES ('4', '内控与内审管理平台', null, '中国移动内控与内审管理平台', '徐光炜', '王海涛', '15811495317', 'wanght@smartdot.com', '1');
INSERT INTO `monitor_busi_sys` VALUES ('5', '内部审计系统（新内审系统）', null, '中国移动内部审计系统', '徐光炜', '王海涛', '15811495317', 'wanght@smartdot.com', '1');
INSERT INTO `monitor_busi_sys` VALUES ('6', 'ERP域运维支撑平台', null, 'ERP域建设支撑平台', '叶宗源', '张荣辉', '18601320069', 'zhangrh@smartdot.com', '1');
INSERT INTO `monitor_busi_sys` VALUES ('7', '财务集中管理系统（报账税务）', '', '直属公司报账系统\n集团总部报账系统\n税务系统', '顾荣', '任丽鹏', '15210990653', 'renlp@cn.ibm.com', '4');
INSERT INTO `monitor_busi_sys` VALUES ('8', '总部银企互联系统', null, '中国移动银企互联系统', '顾荣', '丁庆淼', '13661104302', 'ding.qm@neusoft.com', '1');
INSERT INTO `monitor_busi_sys` VALUES ('9', '在线公司电子影像系统', null, '电子影像管理系统', '顾荣', '高星光', '15724715595', 'gaoxg@cn.ibm.com', '1');
INSERT INTO `monitor_busi_sys` VALUES ('10', '合同管理系统（非集中）', null, '总部合同管理系统\n直属合同管理系统', '高冉', '曾海伦', '18516953101', 'zenghl@mochasoft.com.cn', '1');
INSERT INTO `monitor_busi_sys` VALUES ('11', '全面预算管理系统', null, '全面预算管理系统', '韩跃', '李小奭', '13720004590', 'xiaoshi.li@qijie-tech.com', '1');
INSERT INTO `monitor_busi_sys` VALUES ('12', '资产管理系统', null, '资产管理系统', '韩跃', '苏鑫', '15210439429', 'suxin@dhcc.com.cn', '1');
INSERT INTO `monitor_busi_sys` VALUES ('13', '计划建设管理系统', null, '中国移动计划建设管理系统', '胡杨', '邸立红', '15810606351', 'dilihong@boco.com.cn', '1');
INSERT INTO `monitor_busi_sys` VALUES ('14', '信息港基建管理系统', null, '中国移动信息港基建项目管理系统', '胡杨', '胡斌', '18511588677', 'bin.hu2@h3c.com', '1');
INSERT INTO `monitor_busi_sys` VALUES ('15', '供应链管理系统', null, '中国移动集团供应链系统', '潘陈升', '陈贵荣', '13823618615', '13823618615@139.com', '1');
INSERT INTO `monitor_busi_sys` VALUES ('16', '电子采购与招标投标系统', null, '中国移动采购与招标网', '潘陈升', '刘俊臣', '18205420530', 'a18205420530@sina.com', '1');
INSERT INTO `monitor_busi_sys` VALUES ('17', '信息化服务系统管理平台', null, '信息化服务系统管理平台', '高子玉', '温健鹏', '15555585813', 'wenjianpeng@boco.com.cn', '1');
INSERT INTO `monitor_busi_sys` VALUES ('18', 'ERP系统', null, 'ERP系统', '宋国强', '何宏伟', '18827611435', ' Hong-Wei.He@pccw.com', '1');
INSERT INTO `monitor_busi_sys` VALUES ('19', '供应商主数据', null, '供应商主数据平台', '刘珊珊', '冯金华', '18500058663', 'fengjinhua000@163.com', '1');
INSERT INTO `monitor_busi_sys` VALUES ('22', '323', '3', null, '3', '32', '32', '232323', '4');
INSERT INTO `monitor_busi_sys` VALUES ('23', '这是一个测试业务系统', 'testBusi', null, '2', '1', '2', '2', '3');

-- ----------------------------
-- Table structure for `monitor_database_result`
-- ----------------------------
DROP TABLE IF EXISTS `monitor_database_result`;
CREATE TABLE `monitor_database_result` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `database_id` int(11) DEFAULT NULL,
  `database_url` varchar(150) DEFAULT NULL,
  `query_sql_returnval` text,
  `validate_expr` varchar(100) DEFAULT NULL,
  `validate` int(10) DEFAULT NULL,
  `available` int(10) DEFAULT NULL,
  `error_text` varchar(200) DEFAULT NULL,
  `begin_mills` bigint(20) DEFAULT NULL,
  `end_mills` bigint(20) DEFAULT NULL,
  `time_mills` bigint(20) DEFAULT NULL,
  `month` int(11) DEFAULT NULL,
  `day` int(11) DEFAULT NULL,
  `busi_sys_id` int(11) DEFAULT NULL,
  `begin_time` varchar(45) DEFAULT NULL,
  `end_time` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of monitor_database_result
-- ----------------------------

-- ----------------------------
-- Table structure for `monitor_databaseinfo`
-- ----------------------------
DROP TABLE IF EXISTS `monitor_databaseinfo`;
CREATE TABLE `monitor_databaseinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `database_host` varchar(20) DEFAULT NULL,
  `database_port` int(11) DEFAULT NULL,
  `device_name` varchar(100) DEFAULT NULL,
  `database_instance` varchar(100) DEFAULT NULL,
  `database_user` varchar(100) DEFAULT NULL,
  `database_pwd` varchar(100) DEFAULT NULL,
  `database_type` varchar(100) DEFAULT NULL,
  `query_sql` varchar(500) DEFAULT NULL,
  `validate_expr` varchar(100) DEFAULT NULL,
  `busi_sys_id` int(11) DEFAULT NULL,
  `proxy_id` int(11) DEFAULT NULL,
  `descinfo` varchar(200) DEFAULT NULL,
  `cron_expr` varchar(45) DEFAULT NULL,
  `from_hour_of_day` varchar(45) DEFAULT NULL,
  `to_hour_of_day` varchar(45) DEFAULT NULL,
  `interval_minute` varchar(45) DEFAULT NULL,
  `timeout` bigint(20) DEFAULT NULL,
  `connect_count` int(10) DEFAULT NULL,
  `monitor_state` int(10) DEFAULT NULL,
  `database_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of monitor_databaseinfo
-- ----------------------------
INSERT INTO `monitor_databaseinfo` VALUES ('1', null, '0', '22', null, '1', '2', 'mysql', '2', '2', '1', '0', null, '0 */2 2-0 * * ?', '2', '1', '2', '10000', '3', '1', '11');
INSERT INTO `monitor_databaseinfo` VALUES ('2', null, '0', 'eeerhhhhhh', null, 'er', 'rr', 'mysql', 'ere', 're', '23', '0', null, '0 */1 1-0 * * ?', '1', '1', '1', '10000', '3', '1', 'rer');

-- ----------------------------
-- Table structure for `monitor_network_result`
-- ----------------------------
DROP TABLE IF EXISTS `monitor_network_result`;
CREATE TABLE `monitor_network_result` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `network_id` int(10) unsigned DEFAULT NULL,
  `url` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `begin_time` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `end_time` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `begin_mills` bigint(20) unsigned DEFAULT NULL,
  `end_mills` bigint(20) unsigned DEFAULT NULL,
  `time_mills` bigint(20) unsigned DEFAULT NULL,
  `standard_timeout` bigint(20) unsigned DEFAULT NULL,
  `available` int(10) unsigned DEFAULT NULL,
  `res_code` int(10) DEFAULT NULL,
  `error_text` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `month` int(10) unsigned DEFAULT NULL,
  `day` int(10) unsigned DEFAULT NULL,
  `busi_sys_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_monitor_network_result_1` (`network_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3213 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of monitor_network_result
-- ----------------------------
INSERT INTO `monitor_network_result` VALUES ('3074', '23', 'http://b2b.10086.cnes.b2b.10086.cn', '2018-09-27 09:50:00.100', '2018-09-27 09:50:00.114', '1538013000100', '1538013000114', '14', '10000', '0', '-1', '鏃犳硶璁块棶鐨勫煙鍚嶉敊璇�:b2b.10086.cnes.b2b.10086.cn', '9', '27', '16');
INSERT INTO `monitor_network_result` VALUES ('3075', '16', 'http://cmccbpm2.hq.cmcc/cmcchtpt/', '2018-09-27 09:50:00.100', '2018-09-27 09:50:00.339', '1538013000100', '1538013000339', '239', '10000', '0', '-1', '鏃犳硶璁块棶鐨勫煙鍚嶉敊璇�:cmccbpm2.hq.cmcc', '9', '27', '10');
INSERT INTO `monitor_network_result` VALUES ('3076', '15', 'http://cmccbpm3.hq.cmcc/cmccht/', '2018-09-27 09:50:00.101', '2018-09-27 09:50:00.341', '1538013000101', '1538013000341', '240', '10000', '0', '-1', '鏃犳硶璁块棶鐨勫煙鍚嶉敊璇�:cmccbpm3.hq.cmcc', '9', '27', '10');
INSERT INTO `monitor_network_result` VALUES ('3077', '20', 'https://117.136.129.29:8050/EMAPAdmin', '2018-09-27 09:50:00.102', '2018-09-27 09:50:00.555', '1538013000102', '1538013000555', '453', '10000', '0', '404', '瀹㈡埛绔敊璇紒', '9', '27', '14');
INSERT INTO `monitor_network_result` VALUES ('3078', '25', 'http://misfsprd.hq.cmcc:12000', '2018-09-27 09:50:00.380', '2018-09-27 09:50:01.260', '1538013000380', '1538013001260', '880', '10000', '0', '-1', '鏃犳硶璁块棶鐨勫煙鍚嶉敊璇�:misfsprd.hq.cmcc', '9', '27', '18');
INSERT INTO `monitor_network_result` VALUES ('3079', '3', 'https://cmccdi.chinamobile.com/cmcc_dism_webapp/appIndex', '2018-09-27 09:50:01.313', '2018-09-27 09:50:01.392', '1538013001313', '1538013001392', '79', '10000', '1', '200', null, '9', '27', '2');
INSERT INTO `monitor_network_result` VALUES ('3080', '17', 'http://tbms.hq.cmcc:8008/tbm', '2018-09-27 09:50:00.100', '2018-09-27 09:50:02.361', '1538013000100', '1538013002361', '2261', '10000', '0', '-1', '鏃犳硶璁块棶鐨勫煙鍚嶉敊璇�:tbms.hq.cmcc', '9', '27', '11');
INSERT INTO `monitor_network_result` VALUES ('3081', '18', 'http://eam.hq.cmcc:8080/eam/', '2018-09-27 09:50:00.102', '2018-09-27 09:50:02.426', '1538013000102', '1538013002426', '2324', '10000', '0', '-1', '鏃犳硶璁块棶鐨勫煙鍚嶉敊璇�:eam.hq.cmcc', '9', '27', '12');
INSERT INTO `monitor_network_result` VALUES ('3082', '2', 'http://jjjc.hq.cmcc', '2018-09-27 09:50:00.600', '2018-09-27 09:50:02.857', '1538013000600', '1538013002857', '2257', '10000', '0', '-1', '鏃犳硶璁块棶鐨勫煙鍚嶉敊璇�:jjjc.hq.cmcc', '9', '27', '2');
INSERT INTO `monitor_network_result` VALUES ('3083', '7', 'https://audit.chinamobile.com', '2018-09-27 09:50:02.954', '2018-09-27 09:50:03.055', '1538013002954', '1538013003055', '101', '10000', '0', '-1', 'java.lang.RuntimeException: Could not generate DH keypair', '9', '27', '5');
INSERT INTO `monitor_network_result` VALUES ('3084', '8', 'http://itsm.hq.cmccitsm.hq.cmcc', '2018-09-27 09:50:03.115', '2018-09-27 09:50:03.143', '1538013003115', '1538013003143', '28', '10000', '0', '-1', '鏃犳硶璁块棶鐨勫煙鍚嶉敊璇�:itsm.hq.cmccitsm.hq.cmcc', '9', '27', '6');
INSERT INTO `monitor_network_result` VALUES ('3085', '22', 'http://scm.hq.cmcc', '2018-09-27 09:50:00.100', '2018-09-27 09:50:03.227', '1538013000100', '1538013003227', '3127', '10000', '0', '-1', '鏃犳硶璁块棶鐨勫煙鍚嶉敊璇�:scm.hq.cmcc', '9', '27', '15');
INSERT INTO `monitor_network_result` VALUES ('3086', '10', 'http://efinance.hq.cmcc:9080/efinance', '2018-09-27 09:50:03.260', '2018-09-27 09:50:03.287', '1538013003260', '1538013003287', '27', '10000', '0', '-1', '鏃犳硶璁块棶鐨勫煙鍚嶉敊璇�:efinance.hq.cmcc', '9', '27', '7');
INSERT INTO `monitor_network_result` VALUES ('3087', '9', 'http://112.33.19.191:9999/cmcc_itsm_webapp/login', '2018-09-27 09:50:03.190', '2018-09-27 09:50:03.341', '1538013003190', '1538013003341', '151', '10000', '1', '200', null, '9', '27', '6');
INSERT INTO `monitor_network_result` VALUES ('3088', '11', 'http://cmos-efinance.hq.cmcc:7080/efinance', '2018-09-27 09:50:03.323', '2018-09-27 09:50:03.350', '1538013003323', '1538013003350', '27', '10000', '0', '-1', '鏃犳硶璁块棶鐨勫煙鍚嶉敊璇�:cmos-efinance.hq.cmcc', '9', '27', '7');
INSERT INTO `monitor_network_result` VALUES ('3089', '19', 'http://epms.hq.cmcc', '2018-09-27 09:50:00.102', '2018-09-27 09:50:03.385', '1538013000102', '1538013003385', '3283', '10000', '0', '-1', '鏃犳硶璁块棶鐨勫煙鍚嶉敊璇�:epms.hq.cmcc', '9', '27', '13');
INSERT INTO `monitor_network_result` VALUES ('3090', '5', 'http://icia.hq.cmcc', '2018-09-27 09:50:02.432', '2018-09-27 09:50:04.707', '1538013002432', '1538013004707', '2275', '10000', '0', '-1', '鏃犳硶璁块棶鐨勫煙鍚嶉敊璇�:icia.hq.cmcc', '9', '27', '4');
INSERT INTO `monitor_network_result` VALUES ('3091', '6', 'http://audit.hq.cmcc', '2018-09-27 09:50:02.461', '2018-09-27 09:50:04.739', '1538013002461', '1538013004739', '2278', '10000', '0', '-1', '鏃犳硶璁块棶鐨勫煙鍚嶉敊璇�:audit.hq.cmcc', '9', '27', '5');
INSERT INTO `monitor_network_result` VALUES ('3092', '12', 'http://etax.hq.cmcc/etax', '2018-09-27 09:50:03.376', '2018-09-27 09:50:05.658', '1538013003376', '1538013005658', '2282', '10000', '0', '-1', '鏃犳硶璁块棶鐨勫煙鍚嶉敊璇�:etax.hq.cmcc', '9', '27', '7');
INSERT INTO `monitor_network_result` VALUES ('3093', '1', null, '2018-09-28 10:28:00.083', '2018-09-28 10:28:00.086', '1538101680083', '1538101680086', '3', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3094', '1', null, '2018-09-28 10:30:00.006', '2018-09-28 10:30:00.009', '1538101800006', '1538101800009', '3', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3095', '1', null, '2018-09-28 10:32:00.001', '2018-09-28 10:32:00.003', '1538101920001', '1538101920003', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3096', '1', null, '2018-09-28 10:34:00.002', '2018-09-28 10:34:00.004', '1538102040002', '1538102040004', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3097', '1', null, '2018-09-28 10:36:00.005', '2018-09-28 10:36:00.006', '1538102160005', '1538102160006', '1', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3098', '1', null, '2018-09-28 10:46:00.095', '2018-09-28 10:46:00.096', '1538102760095', '1538102760096', '1', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3099', '1', null, '2018-09-28 10:48:00.001', '2018-09-28 10:48:00.002', '1538102880001', '1538102880002', '1', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3100', '1', null, '2018-09-28 10:50:00.002', '2018-09-28 10:50:00.004', '1538103000002', '1538103000004', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3101', '1', null, '2018-09-28 10:52:00.001', '2018-09-28 10:52:00.003', '1538103120001', '1538103120003', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3102', '1', null, '2018-09-28 10:54:00.002', '2018-09-28 10:54:00.004', '1538103240002', '1538103240004', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3103', '1', null, '2018-09-28 10:56:00.002', '2018-09-28 10:56:00.004', '1538103360002', '1538103360004', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3104', '1', null, '2018-09-28 10:58:00.001', '2018-09-28 10:58:00.003', '1538103480001', '1538103480003', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3105', '1', null, '2018-09-28 11:00:00.002', '2018-09-28 11:00:00.020', '1538103600002', '1538103600020', '18', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3106', '1', null, '2018-09-28 11:02:00.002', '2018-09-28 11:02:00.004', '1538103720002', '1538103720004', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3107', '1', null, '2018-09-28 11:04:00.001', '2018-09-28 11:04:00.002', '1538103840001', '1538103840002', '1', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3108', '1', null, '2018-09-28 11:06:00.002', '2018-09-28 11:06:00.003', '1538103960002', '1538103960003', '1', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3109', '1', null, '2018-09-28 11:08:00.002', '2018-09-28 11:08:00.004', '1538104080002', '1538104080004', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3110', '1', null, '2018-09-28 11:10:00.003', '2018-09-28 11:10:00.006', '1538104200003', '1538104200006', '3', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3111', '1', null, '2018-09-28 11:12:00.006', '2018-09-28 11:12:00.009', '1538104320006', '1538104320009', '3', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3112', '1', null, '2018-09-28 11:14:00.001', '2018-09-28 11:14:00.002', '1538104440001', '1538104440002', '1', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3113', '1', null, '2018-09-28 11:16:00.001', '2018-09-28 11:16:00.003', '1538104560001', '1538104560003', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3114', '1', null, '2018-09-28 11:18:00.001', '2018-09-28 11:18:00.003', '1538104680001', '1538104680003', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3115', '1', null, '2018-09-28 11:20:00.001', '2018-09-28 11:20:00.002', '1538104800001', '1538104800002', '1', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3116', '1', null, '2018-09-28 11:22:00.001', '2018-09-28 11:22:00.003', '1538104920001', '1538104920003', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3117', '1', null, '2018-09-28 11:24:00.001', '2018-09-28 11:24:00.013', '1538105040001', '1538105040013', '12', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3118', '1', null, '2018-09-28 11:26:00.002', '2018-09-28 11:26:00.004', '1538105160002', '1538105160004', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3119', '1', null, '2018-09-28 11:28:00.002', '2018-09-28 11:28:00.004', '1538105280002', '1538105280004', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3120', '1', null, '2018-09-28 11:30:00.000', '2018-09-28 11:30:00.001', '1538105400000', '1538105400001', '1', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3121', '1', null, '2018-09-28 11:32:00.001', '2018-09-28 11:32:00.002', '1538105520001', '1538105520002', '1', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3122', '1', null, '2018-09-28 11:34:00.002', '2018-09-28 11:34:00.004', '1538105640002', '1538105640004', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3123', '1', null, '2018-09-28 11:36:00.001', '2018-09-28 11:36:00.003', '1538105760001', '1538105760003', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3124', '1', null, '2018-09-28 11:38:00.001', '2018-09-28 11:38:00.003', '1538105880001', '1538105880003', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3125', '1', null, '2018-09-28 11:40:00.003', '2018-09-28 11:40:00.005', '1538106000003', '1538106000005', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3126', '1', null, '2018-09-28 11:42:00.002', '2018-09-28 11:42:00.003', '1538106120002', '1538106120003', '1', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3127', '1', null, '2018-09-28 11:44:00.000', '2018-09-28 11:44:00.002', '1538106240000', '1538106240002', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3128', '1', null, '2018-09-28 11:46:00.000', '2018-09-28 11:46:00.001', '1538106360000', '1538106360001', '1', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3129', '1', null, '2018-09-28 11:48:00.002', '2018-09-28 11:48:00.004', '1538106480002', '1538106480004', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3130', '1', null, '2018-09-28 11:50:00.002', '2018-09-28 11:50:00.003', '1538106600002', '1538106600003', '1', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3131', '1', null, '2018-09-28 11:52:00.002', '2018-09-28 11:52:00.003', '1538106720002', '1538106720003', '1', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3132', '1', null, '2018-09-28 11:54:00.002', '2018-09-28 11:54:00.005', '1538106840002', '1538106840005', '3', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3133', '1', null, '2018-09-28 11:56:00.001', '2018-09-28 11:56:00.002', '1538106960001', '1538106960002', '1', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3134', '1', null, '2018-09-28 11:58:00.001', '2018-09-28 11:58:00.003', '1538107080001', '1538107080003', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3135', '1', null, '2018-09-28 12:00:00.007', '2018-09-28 12:00:00.012', '1538107200007', '1538107200012', '5', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3136', '1', null, '2018-09-28 12:02:00.001', '2018-09-28 12:02:00.003', '1538107320001', '1538107320003', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3137', '1', null, '2018-09-28 12:04:00.002', '2018-09-28 12:04:00.004', '1538107440002', '1538107440004', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3138', '1', null, '2018-09-28 12:06:00.002', '2018-09-28 12:06:00.020', '1538107560002', '1538107560020', '18', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3139', '1', null, '2018-09-28 12:08:00.002', '2018-09-28 12:08:00.004', '1538107680002', '1538107680004', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3140', '1', null, '2018-09-28 12:10:00.002', '2018-09-28 12:10:00.016', '1538107800002', '1538107800016', '14', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3141', '1', null, '2018-09-28 12:12:00.000', '2018-09-28 12:12:00.001', '1538107920000', '1538107920001', '1', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3142', '1', null, '2018-09-28 12:14:00.001', '2018-09-28 12:14:00.002', '1538108040001', '1538108040002', '1', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3143', '1', null, '2018-09-28 12:16:00.002', '2018-09-28 12:16:00.004', '1538108160002', '1538108160004', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3144', '1', null, '2018-09-28 12:18:00.002', '2018-09-28 12:18:00.030', '1538108280002', '1538108280030', '28', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3145', '1', null, '2018-09-28 12:20:00.001', '2018-09-28 12:20:00.003', '1538108400001', '1538108400003', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3146', '1', null, '2018-09-28 12:22:00.001', '2018-09-28 12:22:00.020', '1538108520001', '1538108520020', '19', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3147', '1', null, '2018-09-28 12:24:00.001', '2018-09-28 12:24:00.002', '1538108640001', '1538108640002', '1', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3148', '1', null, '2018-09-28 12:26:00.002', '2018-09-28 12:26:00.021', '1538108760002', '1538108760021', '19', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3149', '1', null, '2018-09-28 12:28:00.000', '2018-09-28 12:28:00.002', '1538108880000', '1538108880002', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3150', '1', null, '2018-09-28 12:30:00.000', '2018-09-28 12:30:00.002', '1538109000000', '1538109000002', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3151', '1', null, '2018-09-28 12:32:00.000', '2018-09-28 12:32:00.001', '1538109120000', '1538109120001', '1', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3152', '1', null, '2018-09-28 12:34:00.002', '2018-09-28 12:34:00.004', '1538109240002', '1538109240004', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3153', '1', null, '2018-09-28 12:36:00.001', '2018-09-28 12:36:00.003', '1538109360001', '1538109360003', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3154', '1', null, '2018-09-28 12:38:00.002', '2018-09-28 12:38:00.019', '1538109480002', '1538109480019', '17', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3155', '1', null, '2018-09-28 12:40:00.001', '2018-09-28 12:40:00.003', '1538109600001', '1538109600003', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3156', '1', null, '2018-09-28 12:42:00.000', '2018-09-28 12:42:00.002', '1538109720000', '1538109720002', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3157', '1', null, '2018-09-28 12:44:00.001', '2018-09-28 12:44:00.003', '1538109840001', '1538109840003', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3158', '1', null, '2018-09-28 12:46:00.024', '2018-09-28 12:46:00.060', '1538109960024', '1538109960060', '36', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3159', '1', null, '2018-09-28 12:48:00.001', '2018-09-28 12:48:00.003', '1538110080001', '1538110080003', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3160', '1', null, '2018-09-28 12:50:00.008', '2018-09-28 12:50:00.049', '1538110200008', '1538110200049', '41', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3161', '1', null, '2018-09-28 12:52:00.002', '2018-09-28 12:52:00.004', '1538110320002', '1538110320004', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3162', '1', null, '2018-09-28 12:54:00.001', '2018-09-28 12:54:00.003', '1538110440001', '1538110440003', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3163', '1', null, '2018-09-28 12:56:00.001', '2018-09-28 12:56:00.003', '1538110560001', '1538110560003', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3164', '1', null, '2018-09-28 12:58:00.001', '2018-09-28 12:58:00.003', '1538110680001', '1538110680003', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3165', '1', null, '2018-09-28 13:00:00.001', '2018-09-28 13:00:00.002', '1538110800001', '1538110800002', '1', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3166', '1', null, '2018-09-28 13:02:00.001', '2018-09-28 13:02:00.003', '1538110920001', '1538110920003', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3167', '1', null, '2018-09-28 13:04:00.001', '2018-09-28 13:04:00.003', '1538111040001', '1538111040003', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3168', '1', null, '2018-09-28 13:06:00.014', '2018-09-28 13:06:00.033', '1538111160014', '1538111160033', '19', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3169', '1', null, '2018-09-28 13:08:00.002', '2018-09-28 13:08:00.003', '1538111280002', '1538111280003', '1', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3170', '1', null, '2018-09-28 13:10:00.001', '2018-09-28 13:10:00.020', '1538111400001', '1538111400020', '19', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3171', '1', null, '2018-09-28 13:12:00.001', '2018-09-28 13:12:00.002', '1538111520001', '1538111520002', '1', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3172', '1', null, '2018-09-28 13:14:00.001', '2018-09-28 13:14:00.002', '1538111640001', '1538111640002', '1', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3173', '1', null, '2018-09-28 13:16:00.002', '2018-09-28 13:16:00.004', '1538111760002', '1538111760004', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3174', '1', null, '2018-09-28 13:18:00.001', '2018-09-28 13:18:00.002', '1538111880001', '1538111880002', '1', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3175', '1', null, '2018-09-28 13:20:00.002', '2018-09-28 13:20:00.004', '1538112000002', '1538112000004', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3176', '1', null, '2018-09-28 13:22:00.001', '2018-09-28 13:22:00.002', '1538112120001', '1538112120002', '1', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3177', '1', null, '2018-09-28 13:24:00.000', '2018-09-28 13:24:00.003', '1538112240000', '1538112240003', '3', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3178', '1', null, '2018-09-28 13:26:00.001', '2018-09-28 13:26:00.002', '1538112360001', '1538112360002', '1', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3179', '1', null, '2018-09-28 13:28:00.001', '2018-09-28 13:28:00.003', '1538112480001', '1538112480003', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3180', '1', null, '2018-09-28 13:30:00.002', '2018-09-28 13:30:00.004', '1538112600002', '1538112600004', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3181', '1', null, '2018-09-28 13:32:00.001', '2018-09-28 13:32:00.003', '1538112720001', '1538112720003', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3182', '1', null, '2018-09-28 13:34:00.008', '2018-09-28 13:34:00.011', '1538112840008', '1538112840011', '3', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3183', '1', null, '2018-09-28 13:36:00.002', '2018-09-28 13:36:00.004', '1538112960002', '1538112960004', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3184', '1', null, '2018-09-28 13:38:00.002', '2018-09-28 13:38:00.003', '1538113080002', '1538113080003', '1', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3185', '1', null, '2018-09-28 13:40:00.001', '2018-09-28 13:40:00.057', '1538113200001', '1538113200057', '56', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3186', '1', null, '2018-09-28 13:42:00.000', '2018-09-28 13:42:00.002', '1538113320000', '1538113320002', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3187', '1', null, '2018-09-28 13:44:00.002', '2018-09-28 13:44:00.004', '1538113440002', '1538113440004', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3188', '1', null, '2018-09-28 13:46:00.001', '2018-09-28 13:46:00.003', '1538113560001', '1538113560003', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3189', '1', null, '2018-09-28 13:48:00.001', '2018-09-28 13:48:00.003', '1538113680001', '1538113680003', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3190', '1', null, '2018-09-28 13:50:00.002', '2018-09-28 13:50:00.003', '1538113800002', '1538113800003', '1', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3191', '1', null, '2018-09-28 13:52:00.002', '2018-09-28 13:52:00.003', '1538113920002', '1538113920003', '1', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3192', '1', null, '2018-09-28 13:54:00.000', '2018-09-28 13:54:00.003', '1538114040000', '1538114040003', '3', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3193', '1', null, '2018-09-28 13:56:00.002', '2018-09-28 13:56:00.003', '1538114160002', '1538114160003', '1', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3194', '1', null, '2018-09-28 13:58:00.000', '2018-09-28 13:58:00.002', '1538114280000', '1538114280002', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3195', '1', null, '2018-09-28 14:00:00.002', '2018-09-28 14:00:00.003', '1538114400002', '1538114400003', '1', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3196', '1', null, '2018-09-28 14:02:00.001', '2018-09-28 14:02:00.003', '1538114520001', '1538114520003', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3197', '1', null, '2018-09-28 14:04:00.001', '2018-09-28 14:04:00.003', '1538114640001', '1538114640003', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3198', '1', null, '2018-09-28 14:06:00.001', '2018-09-28 14:06:00.002', '1538114760001', '1538114760002', '1', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3199', '1', null, '2018-09-28 14:08:00.000', '2018-09-28 14:08:00.002', '1538114880000', '1538114880002', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3200', '1', null, '2018-09-28 14:10:00.001', '2018-09-28 14:10:00.019', '1538115000001', '1538115000019', '18', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3201', '1', null, '2018-09-28 14:12:00.000', '2018-09-28 14:12:00.002', '1538115120000', '1538115120002', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3202', '1', null, '2018-09-28 14:14:00.001', '2018-09-28 14:14:00.003', '1538115240001', '1538115240003', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3203', '1', null, '2018-09-28 14:16:00.002', '2018-09-28 14:16:00.004', '1538115360002', '1538115360004', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3204', '1', null, '2018-09-28 14:18:00.002', '2018-09-28 14:18:00.006', '1538115480002', '1538115480006', '4', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3205', '1', null, '2018-09-28 14:20:00.001', '2018-09-28 14:20:00.003', '1538115600001', '1538115600003', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3206', '1', null, '2018-09-28 14:22:00.001', '2018-09-28 14:22:00.002', '1538115720001', '1538115720002', '1', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3207', '1', null, '2018-09-28 14:24:00.001', '2018-09-28 14:24:00.037', '1538115840001', '1538115840037', '36', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3208', '1', null, '2018-09-28 14:26:00.000', '2018-09-28 14:26:00.002', '1538115960000', '1538115960002', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3209', '1', null, '2018-09-28 14:28:00.000', '2018-09-28 14:28:00.002', '1538116080000', '1538116080002', '2', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3210', '1', null, '2018-09-28 15:00:00.080', '2018-09-28 15:00:00.081', '1538118000080', '1538118000081', '1', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3211', '1', null, '2018-09-28 15:02:00.111', '2018-09-28 15:02:00.112', '1538118120111', '1538118120112', '1', '2', '0', '-1', '其他错误，请参考响应码[-1]！', '9', '28', '1');
INSERT INTO `monitor_network_result` VALUES ('3212', '29', 'http test', '2018-09-28 16:00:00.088', '2018-09-28 16:00:00.098', '1538121600088', '1538121600098', '10', '1', '0', '-1', 'no protocol: http test', '9', '28', '23');

-- ----------------------------
-- Table structure for `monitor_networkinfo`
-- ----------------------------
DROP TABLE IF EXISTS `monitor_networkinfo`;
CREATE TABLE `monitor_networkinfo` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `busi_sys_id` int(10) unsigned NOT NULL,
  `web_page_name` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `url` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `server_ip` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `priority` int(10) unsigned DEFAULT NULL,
  `cron_expr` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `from_hour_of_day` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `to_hour_of_day` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `interval_minute` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `timeout` bigint(20) unsigned DEFAULT NULL,
  `connect_count` int(10) unsigned DEFAULT NULL,
  `monitor_state` int(10) unsigned DEFAULT NULL,
  `device_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `proxy_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_monitor_networkinfo_1` (`busi_sys_id`),
  CONSTRAINT `FK_monitor_networkinfo_1` FOREIGN KEY (`busi_sys_id`) REFERENCES `monitor_busi_sys` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of monitor_networkinfo
-- ----------------------------
INSERT INTO `monitor_networkinfo` VALUES ('2', '2', '中国移动纪检组网站/手机端', 'http://jjjc.hq.cmcc', '172.16.175.163', '2', '0 */10 8-17 * * ?', '8', '18', '10', '10000', '3', '0', null, '-1');
INSERT INTO `monitor_networkinfo` VALUES ('3', '2', '中国移动纪检组网站/手机端', 'https://cmccdi.chinamobile.com/cmcc_dism_webapp/appIndex', '172.16.175.163', '2', '0 */10 8-17 * * ?', '8', '18', '10', '10000', '3', '0', null, '-1');
INSERT INTO `monitor_networkinfo` VALUES ('4', '3', '中国移动信息安全合规管理平台', 'http://172.16.175.122', '172.16.175.122', '2', '0 */10 8-17 * * ?', '8', '18', '10', '10000', '3', '0', null, '0');
INSERT INTO `monitor_networkinfo` VALUES ('5', '4', '中国移动内控与内审管理平台', 'http://icia.hq.cmcc', '172.17.250.131', '3', '0 */10 8-19 * * ?', '8', '18', '10', '10000', '3', '0', null, null);
INSERT INTO `monitor_networkinfo` VALUES ('6', '5', '中国移动内部审计系统', 'http://audit.hq.cmcc', '172.16.175.64', '2', '0 */10 8-19 * * ?', '8', '18', '10', '10000', '3', '0', null, null);
INSERT INTO `monitor_networkinfo` VALUES ('7', '5', '中国移动内部审计系统', 'https://audit.chinamobile.com', '172.16.175.64', '2', '0 */10 8-19 * * ?', '8', '18', '10', '10000', '3', '0', null, null);
INSERT INTO `monitor_networkinfo` VALUES ('8', '6', 'ERP域建设支撑平台', 'http://itsm.hq.cmccitsm.hq.cmcc', '172.16.175.129', '3', '0 */10 8-19 * * ?', '8', '18', '10', '10000', '3', '0', null, null);
INSERT INTO `monitor_networkinfo` VALUES ('9', '6', 'ERP域建设支撑平台', 'http://112.33.19.191:9999/cmcc_itsm_webapp/login', '172.16.175.129', '3', '0 */10 8-19 * * ?', '8', '18', '10', '10000', '3', '0', null, null);
INSERT INTO `monitor_networkinfo` VALUES ('10', '7', '直属公司报账系统\n集团总部报账系统\n税务系统', 'http://efinance.hq.cmcc:9080/efinance', '172.16.185.76', '3', '0 */10 8-19 * * ?', '8', '18', '10', '10000', '3', '0', null, null);
INSERT INTO `monitor_networkinfo` VALUES ('11', '7', '直属公司报账系统\n集团总部报账系统\n税务系统', 'http://cmos-efinance.hq.cmcc:7080/efinance', '172.16.185.76', '3', '0 */10 8-19 * * ?', '8', '18', '10', '10000', '3', '0', null, null);
INSERT INTO `monitor_networkinfo` VALUES ('12', '7', '直属公司报账系统\n集团总部报账系统\n税务系统', 'http://etax.hq.cmcc/etax', '172.16.185.76', '3', '0 */10 8-19 * * ?', '8', '18', '10', '10000', '3', '0', null, null);
INSERT INTO `monitor_networkinfo` VALUES ('13', '8', '中国移动银企互联系统', 'http://10.2.7.5:8008/webApplication/main.jsp', '10.2.7.5', '3', '0 */10 8-19 * * ?', '8', '18', '10', '10000', '3', '0', null, null);
INSERT INTO `monitor_networkinfo` VALUES ('14', '9', '电子影像管理系统', 'http://172.16.175.141:8080/ElectronicCertificate', '172.16.175.141', '3', '0 */10 8-19 * * ?', '8', '18', '10', '10000', '3', '0', null, null);
INSERT INTO `monitor_networkinfo` VALUES ('15', '10', '总部合同管理系统直属合同管理系统', 'http://cmccbpm3.hq.cmcc/cmccht/', '172.16.175.21', '3', '0 */10 8-19 * * ?', '8', '18', '10', '10000', '3', '0', null, null);
INSERT INTO `monitor_networkinfo` VALUES ('16', '10', '总部合同管理系统\n直属合同管理系统', 'http://cmccbpm2.hq.cmcc/cmcchtpt/', '172.16.175.21', '3', '0 */10 8-19 * * ?', '8', '18', '10', '10000', '3', '0', null, null);
INSERT INTO `monitor_networkinfo` VALUES ('17', '11', '全面预算管理系统', 'http://tbms.hq.cmcc:8008/tbm', '10.2.7.139', '2', '0 */10 8-19 * * ?', '8', '18', '10', '10000', '3', '0', null, null);
INSERT INTO `monitor_networkinfo` VALUES ('18', '12', '资产管理系统', 'http://eam.hq.cmcc:8080/eam/', '172.16.175.139', '2', '0 */10 8-19 * * ?', '8', '18', '10', '10000', '3', '0', null, null);
INSERT INTO `monitor_networkinfo` VALUES ('19', '13', '中国移动计划建设管理系统', 'http://epms.hq.cmcc', '172.17.66.5', '2', '0 */10 8-19 * * ?', '8', '18', '10', '10000', '3', '0', null, null);
INSERT INTO `monitor_networkinfo` VALUES ('20', '14', '中国移动信息港基建项目管理系统', 'https://117.136.129.29:8050/EMAPAdmin', '117.136.129.29', '2', '0 */10 8-19 * * ?', '8', '18', '10', '10000', '3', '0', null, null);
INSERT INTO `monitor_networkinfo` VALUES ('21', '14', '中国移动信息港基建项目管理系统', 'http://172.16.143.6:9081/sso', '117.136.129.29', '2', '0 */10 8-19 * * ?', '8', '18', '10', '10000', '3', '0', null, null);
INSERT INTO `monitor_networkinfo` VALUES ('22', '15', '中国移动集团供应链系统', 'http://scm.hq.cmcc', '172.16.133.200', '1', '0 */10 8-19 * * ?', '8', '18', '10', '10000', '3', '0', null, null);
INSERT INTO `monitor_networkinfo` VALUES ('23', '16', '中国移动采购与招标网', 'http://b2b.10086.cnes.b2b.10086.cn', '117.136.129.32', '1', '0 */10 8-19 * * ?', '8', '18', '10', '10000', '3', '0', null, null);
INSERT INTO `monitor_networkinfo` VALUES ('24', '17', '信息化服务系统管理平台', 'http://172.16.194.242:8080/cmportal/index.jsp', '172.16.194.242', '3', '0 */10 8-19 * * ?', '8', '18', '10', '10000', '3', '0', null, null);
INSERT INTO `monitor_networkinfo` VALUES ('25', '18', 'ERP系统', 'http://misfsprd.hq.cmcc:12000', '172.16.175.103', '1', '0 */10 8-19 * * ?', '8', '18', '10', '10000', '3', '0', null, null);
INSERT INTO `monitor_networkinfo` VALUES ('26', '19', '供应商主数据平台', 'http://172.16.143.35:7787/mdm/login.htm', '172.16.143.35', '3', '0 */10 8-19 * * ?', '8', '18', '10', '10000', '3', '0', null, null);

-- ----------------------------
-- Table structure for `monitor_ping_result`
-- ----------------------------
DROP TABLE IF EXISTS `monitor_ping_result`;
CREATE TABLE `monitor_ping_result` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ping_id` int(11) DEFAULT NULL,
  `ping_ip` varchar(100) DEFAULT NULL,
  `packet_loss` varchar(10) NOT NULL,
  `avg_delay` int(10) DEFAULT NULL,
  `available` int(10) DEFAULT NULL,
  `error_text` varchar(200) DEFAULT NULL,
  `begin_mills` bigint(20) DEFAULT NULL,
  `end_mills` bigint(20) DEFAULT NULL,
  `time_mills` bigint(20) DEFAULT NULL,
  `month` int(11) DEFAULT NULL,
  `day` int(11) DEFAULT NULL,
  `busi_sys_id` int(11) DEFAULT NULL,
  `begin_time` varchar(45) DEFAULT NULL,
  `end_time` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of monitor_ping_result
-- ----------------------------

-- ----------------------------
-- Table structure for `monitor_pinginfo`
-- ----------------------------
DROP TABLE IF EXISTS `monitor_pinginfo`;
CREATE TABLE `monitor_pinginfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ping_ip` varchar(20) NOT NULL,
  `ping_packet_num` int(11) NOT NULL,
  `device_name` varchar(100) NOT NULL,
  `busi_sys_id` int(11) NOT NULL,
  `proxy_id` int(11) DEFAULT NULL,
  `descinfo` varchar(200) DEFAULT NULL,
  `cron_expr` varchar(45) DEFAULT NULL,
  `from_hour_of_day` varchar(45) DEFAULT NULL,
  `to_hour_of_day` varchar(45) DEFAULT NULL,
  `interval_minute` varchar(45) DEFAULT NULL,
  `timeout` bigint(20) DEFAULT NULL,
  `connect_count` int(10) DEFAULT NULL,
  `monitor_state` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of monitor_pinginfo
-- ----------------------------
INSERT INTO `monitor_pinginfo` VALUES ('2', '1111111222', '111111', 'ping 百度22222', '1', '0', null, '0 */1 1-0 * * ?', '1', '1', '1', '10000', '3', '1');
INSERT INTO `monitor_pinginfo` VALUES ('3', '12', '311', 'pppppp', '1', '0', null, '0 */3 1-1 * * ?', '1', '2', '3', '10000', '3', '1');
INSERT INTO `monitor_pinginfo` VALUES ('4', 'ppppaaaaaaaaa', '10', 'pppppppaaa', '23', '0', null, '0 */3 1-1 * * ?', '1', '2', '3', '10000', '3', '1');

-- ----------------------------
-- Table structure for `monitor_proxy`
-- ----------------------------
DROP TABLE IF EXISTS `monitor_proxy`;
CREATE TABLE `monitor_proxy` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `proxy_host` varchar(100) COLLATE utf8_bin NOT NULL,
  `proxy_port` int(10) unsigned NOT NULL,
  `name` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of monitor_proxy
-- ----------------------------
INSERT INTO `monitor_proxy` VALUES ('1', 'proxy.cmcc', '8080', '研究院上网代理服务');

-- ----------------------------
-- Table structure for `monitor_region`
-- ----------------------------
DROP TABLE IF EXISTS `monitor_region`;
CREATE TABLE `monitor_region` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `region_name` varchar(100) COLLATE utf8_bin NOT NULL,
  `region_code` varchar(100) COLLATE utf8_bin NOT NULL,
  `region_desc` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of monitor_region
-- ----------------------------
INSERT INTO `monitor_region` VALUES ('1', '测试1区', 'test_area1', '1');
INSERT INTO `monitor_region` VALUES ('2', '测试2区', 'test_area2', '2');
INSERT INTO `monitor_region` VALUES ('3', '测试3区', 'test_area3', '3');
INSERT INTO `monitor_region` VALUES ('4', '测试4区', 'test_area4', '4');

-- ----------------------------
-- Table structure for `monitor_tcp_result`
-- ----------------------------
DROP TABLE IF EXISTS `monitor_tcp_result`;
CREATE TABLE `monitor_tcp_result` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tcp_id` int(11) DEFAULT NULL,
  `tcp_hostportpair` varchar(100) DEFAULT NULL,
  `available` int(10) DEFAULT NULL,
  `error_text` varchar(200) DEFAULT NULL,
  `begin_mills` bigint(20) DEFAULT NULL,
  `end_mills` bigint(20) DEFAULT NULL,
  `time_mills` bigint(20) DEFAULT NULL,
  `month` int(11) DEFAULT NULL,
  `day` int(11) DEFAULT NULL,
  `busi_sys_id` int(11) DEFAULT NULL,
  `begin_time` varchar(45) DEFAULT NULL,
  `end_time` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of monitor_tcp_result
-- ----------------------------

-- ----------------------------
-- Table structure for `monitor_tcpinfo`
-- ----------------------------
DROP TABLE IF EXISTS `monitor_tcpinfo`;
CREATE TABLE `monitor_tcpinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tcp_host` varchar(20) NOT NULL,
  `tcp_port` int(11) NOT NULL,
  `device_name` varchar(100) NOT NULL,
  `busi_sys_id` int(11) NOT NULL,
  `proxy_id` int(11) DEFAULT NULL,
  `descinfo` varchar(200) DEFAULT NULL,
  `cron_expr` varchar(45) DEFAULT NULL,
  `from_hour_of_day` varchar(45) DEFAULT NULL,
  `to_hour_of_day` varchar(45) DEFAULT NULL,
  `interval_minute` varchar(45) DEFAULT NULL,
  `timeout` bigint(20) DEFAULT NULL,
  `connect_count` int(10) DEFAULT NULL,
  `monitor_state` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of monitor_tcpinfo
-- ----------------------------
INSERT INTO `monitor_tcpinfo` VALUES ('3', 'AA', '11', 'AAABBB', '23', '0', null, '0 */1 1-0 * * ?', '1', '1', '1', '1', '1', '0');
