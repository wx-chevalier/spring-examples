/*
 * Copyright (c) 2020-2030, Shuigedeng (981376577@qq.com & https://blog.taotaocloud.top/).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.taotao.cloud.promotion.biz.util; // package com.taotao.cloud.message.biz.util;
//
// import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
// import com.taotao.cloud.common.enums.ClientTypeEnum;
// import com.taotao.cloud.common.enums.ResultEnum;
// import com.taotao.cloud.common.exception.BusinessException;
// import com.taotao.cloud.common.utils.date.DateUtils;
// import com.taotao.cloud.message.api.enums.WechatMessageItemEnums;
// import com.taotao.cloud.message.biz.entity.WechatMPMessage;
// import com.taotao.cloud.message.biz.entity.WechatMessage;
// import com.taotao.cloud.message.biz.service.WechatMPMessageService;
// import com.taotao.cloud.message.biz.service.WechatMessageService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Component;
//
// import java.util.HashMap;
// import java.util.LinkedHashMap;
// import java.util.LinkedList;
// import java.util.List;
// import java.util.Map;
//
// /**
//  * 微信消息
//  */
//
// @Component
// public class WechatMessageUtil {
//
//     @Autowired
//     private ConnectService connectService;
//     @Autowired
//     private OrderService orderService;
//     @Autowired
//     private OrderItemService orderItemService;
//     @Autowired
//     private WechatAccessTokenUtil wechatAccessTokenUtil;
//     @Autowired
//     private WechatMessageService wechatMessageService;
//
//     @Autowired
//     private WechatMPMessageService wechatMPMessageService;
//
//     public void sendWechatMessage(String sn) {
//         try {
//             this.wechatMessage(sn);
//         } catch (Exception e) {
//             log.error("微信公众号消息异常：", e);
//         }
//         try {
//             this.wechatMpMessage(sn);
//         } catch (Exception e) {
//             log.error("小程序消息订阅异常：", e);
//         }
//
//     }
//
//     /**
//      * 发送微信消息
//      *
//      * @param sn
//      */
//     public void wechatMessage(String sn) {
//         Order order = orderService.getBySn(sn);
//         if (order == null) {
//             throw new BusinessException("订单" + sn + "不存在，发送微信公众号消息错误");
//         }
//         //获取微信消息
//         LambdaQueryWrapper<WechatMessage> wechatMessageQueryWrapper = new LambdaQueryWrapper();
//         wechatMessageQueryWrapper.eq(WechatMessage::getOrderStatus, order.getOrderStatus());
//         WechatMessage wechatMessage = wechatMessageService.getOne(wechatMessageQueryWrapper);
//         if (wechatMessage == null) {
//             return;
//         }
//
//         Connect connect = connectService.queryConnect(
//
// ConnectQueryDTO.builder().userId(order.getMemberId()).unionType(ConnectEnum.WECHAT.name()).build()
//         );
//         if (connect == null) {
//             return;
//         }
//
//         log.info("微信消息发送消息：{}", order.getMemberId() + "-" + sn);
//         //获取token
//         String token = wechatAccessTokenUtil.cgiAccessToken(ClientTypeEnum.H5);
//
//         //发送url
//         String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" +
// token;
//
//         Map<String, String> map = new HashMap<>(4);
//         //用户id
//         map.put("touser", connect.getUnionId());
//         //模版id
//         map.put("template_id", wechatMessage.getCode());
//         //模版中所需数据
//         String postParams = createData(order, wechatMessage);
//         map.put("data", postParams);
//
//         log.info("参数内容：" + JSONUtil.toJsonStr(map));
//         String content = HttpUtils.doPostWithJson(url, map);
//         JSONObject json = new JSONObject(content);
//         log.info("微信消息发送结果：" + content);
//         String errorMessage = json.getStr("errmsg");
//         String errcode = json.getStr("errcode");
//         //发送失败
//         if (!"0".equals(errcode)) {
//             log.error("消息发送失败：" + errorMessage);
//             log.error("消息发送请求token：" + token);
//             log.error("消息发送请求：" + postParams);
//         }
//     }
//
//     /**
//      * 发送微信消息
//      *
//      * @param sn
//      */
//     public void wechatMpMessage(String sn) {
//
//         log.info("发送消息订阅");
//         Order order = orderService.getBySn(sn);
//         if (order == null) {
//             throw new BusinessException("订单" + sn + "不存在，发送订阅消息错误");
//         }
//         //获取微信消息
//         LambdaQueryWrapper<WechatMPMessage> wechatMPMessageQueryWrapper = new
// LambdaQueryWrapper();
//         wechatMPMessageQueryWrapper.eq(WechatMPMessage::getOrderStatus, order.getOrderStatus());
//         WechatMPMessage wechatMPMessage =
// wechatMPMessageService.getOne(wechatMPMessageQueryWrapper);
//         if (wechatMPMessage == null) {
//             log.info("未配置微信消息订阅");
//             return;
//         }
//
//         Connect connect = connectService.queryConnect(
//
// ConnectQueryDTO.builder().userId(order.getMemberId()).unionType(ConnectEnum.WECHAT_MP_OPEN_ID.name()).build()
//         );
//         if (connect == null) {
//             return;
//         }
//
//         log.info("微信消息订阅消息发送：{}", order.getMemberId() + "-" + sn);
//         //获取token
//         String token = wechatAccessTokenUtil.cgiAccessToken(ClientTypeEnum.WECHAT_MP);
//
//         //发送url
//         String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" +
// token;
//
//         Map<String, Object> map = new HashMap<>(4);
//         //用户id
//         map.put("touser", connect.getUnionId());
//         //模版id
//         map.put("template_id", wechatMPMessage.getCode());
//         //模版中所需数据
//         Map<String, Map<String, String>> postParams = createData(order, wechatMPMessage);
//         map.put("data", postParams);
//         map.put("page", "pages/order/orderDetail?sn=" + order.getSn());
//         log.info("参数内容：" + JSONUtil.toJsonStr(map));
//         String content = null;
//         try {
//             content = HttpUtil.post(url, JSONUtil.toJsonStr(map));
//         } catch (Exception e) {
//             log.error("微信消息发送错误", e);
//         }
//         JSONObject json = new JSONObject(content);
//         log.info("微信消息发送结果：" + content);
//         String errorMessage = json.getStr("errmsg");
//         String errcode = json.getStr("errcode");
//         //发送失败
//         if (!"0".equals(errcode)) {
//             log.error("消息发送失败：" + errorMessage);
//             log.error("消息发送请求token：" + token);
//             log.error("消息发送请求：" + postParams);
//         }
//     }
//
//     /**
//      * 构造数据中所需的内容
//      *
//      * @param order
//      * @param wechatMessage
//      * @return
//      */
//     private String createData(Order order, WechatMessage wechatMessage) {
//         WechatMessageData wechatMessageData = new WechatMessageData();
//         wechatMessageData.setFirst(wechatMessage.getFirst());
//         wechatMessageData.setRemark(wechatMessage.getRemark());
//         String[] paramArray = wechatMessage.getKeywords().split(",");
//         LinkedList params = new LinkedList();
//
//         for (String param : paramArray) {
//             WechatMessageItemEnums wechatMessageItemEnums =
// WechatMessageItemEnums.valueOf(param);
//             //初始化参数内容
//             String val = getParams(wechatMessageItemEnums, order);
//             params.add(val);
//         }
//         wechatMessageData.setMessageData(params);
//         return wechatMessageData.createData();
//     }
//
//     /**
//      * 构造数据中所需的内容
//      *
//      * @param order
//      * @param wechatMPMessage
//      * @return
//      */
//     private Map<String, Map<String, String>> createData(Order order, WechatMPMessage
// wechatMPMessage) {
//         WechatMessageData wechatMessageData = new WechatMessageData();
//         List<String> paramArray = JSONUtil.toList(wechatMPMessage.getKeywords(), String.class);
//         List<String> texts = JSONUtil.toList(wechatMPMessage.getKeywordsText(), String.class);
//         Map<String, String> params = new LinkedHashMap<>();
//         for (int i = 0; i < paramArray.size(); i++) {
//             WechatMessageItemEnums wechatMessageItemEnums =
// WechatMessageItemEnums.valueOf(paramArray.get(i));
//             //初始化参数内容
//             String val = getParams(wechatMessageItemEnums, order);
//             val = StringUtils.subStringLength(val, 20);
//             params.put(texts.get(i), val);
//         }
//         wechatMessageData.setMpMessageData(params);
//         return wechatMessageData.createMPData();
//     }
//
//     /**
//      * 获取具体参数
//      *
//      * @param itemEnums
//      * @param order
//      * @return
//      */
//     private String getParams(WechatMessageItemEnums itemEnums, Order order) {
//         switch (itemEnums) {
//             case PRICE:
//                 return order.getPriceDetailDTO().getFlowPrice().toString();
//             case ORDER_SN:
//                 return order.getSn();
//             case SHOP_NAME:
//                 return order.getStoreName();
//             case GOODS_INFO:
//                 List<OrderItem> orderItems = orderItemService.getByOrderSn(order.getSn());
//                 StringBuffer stringBuffer = new StringBuffer();
//                 orderItems.forEach(orderItem -> {
//                     stringBuffer.append(orderItem.getGoodsName() + "*" + orderItem.getNum() + "
// ");
//                 });
//                 return stringBuffer.toString();
//             case MEMBER_NAME:
//                 return order.getMemberName();
//             case LOGISTICS_NO:
//                 return order.getLogisticsNo();
//             case LOGISTICS_NAME:
//                 return order.getLogisticsName();
//             case LOGISTICS_TIME:
//                 return DateUtils.toString(order.getLogisticsTime(), DateUtils.STANDARD_FORMAT);
//             default:
//                 return "";
//         }
//     }
//
//     /**
//      * 如果返回信息有错误
//      *
//      * @param jsonObject 返回消息
//      */
//     public static void wechatHandler(JSONObject jsonObject) {
//         if (jsonObject.containsKey("errmsg")) {
//             if (("ok").equals(jsonObject.getStr("errmsg"))) {
//                 return;
//             }
//             log.error("微信接口异常，错误码" + jsonObject.get("errcode") + "，" +
// jsonObject.getStr("errmsg"));
//             throw new BusinessException(ResultEnum.WECHAT_ERROR);
//         }
//     }
//
//     /**
//      * 如果返回信息有错误
//      *
//      * @param string 返回消息
//      */
//     public static String wechatHandler(String string) {
//         JSONObject jsonObject = new JSONObject();
//         wechatHandler(jsonObject);
//         return string;
//     }
//
// }
