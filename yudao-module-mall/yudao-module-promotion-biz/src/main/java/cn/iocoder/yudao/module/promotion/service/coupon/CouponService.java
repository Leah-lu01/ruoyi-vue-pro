package cn.iocoder.yudao.module.promotion.service.coupon;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.promotion.controller.admin.coupon.vo.coupon.CouponPageReqVO;
import cn.iocoder.yudao.module.promotion.dal.dataobject.coupon.CouponDO;
import cn.iocoder.yudao.module.promotion.enums.coupon.CouponTakeTypeEnum;

import java.util.List;
import java.util.Set;

/**
 * 优惠劵 Service 接口
 *
 * @author 芋道源码
 */
public interface CouponService {

    /**
     * 校验优惠劵，包括状态、有限期
     *
     * 1. 如果校验通过，则返回优惠劵信息
     * 2. 如果校验不通过，则直接抛出业务异常
     *
     * @param id 优惠劵编号
     * @param userId 用户编号
     * @return 优惠劵信息
     */
    CouponDO validCoupon(Long id, Long userId);

    /**
     * 校验优惠劵，包括状态、有限期
     *
     * @see #validCoupon(Long, Long) 逻辑相同，只是入参不同
     *
     * @param coupon 优惠劵
     */
    void validCoupon(CouponDO coupon);

    /**
     * 获得优惠劵分页
     *
     * @param pageReqVO 分页查询
     * @return 优惠劵分页
     */
    PageResult<CouponDO> getCouponPage(CouponPageReqVO pageReqVO);

    /**
     * 使用优惠劵
     *
     * @param id      优惠劵编号
     * @param userId  用户编号
     * @param orderId 订单编号
     */
    void useCoupon(Long id, Long userId, Long orderId);

    /**
     * 回收优惠劵
     *
     * @param id 优惠劵编号
     */
    void deleteCoupon(Long id);

    /**
     * 获得用户的优惠劵列表
     *
     * @param userId 用户编号
     * @param status 优惠劵状态
     * @return 优惠劵列表
     */
    List<CouponDO> getCouponList(Long userId, Integer status);

    /**
     * 获得未使用的优惠劵数量
     *
     * @param userId 用户编号
     * @return 未使用的优惠劵数量
     */
    Long getUnusedCouponCount(Long userId);

    // TODO @疯狂：可以返回 void；因为都是 true = =
    /**
     * 领取优惠券
     *
     * @param templateId 优惠券模板编号
     * @param userIds    用户编号列表
     * @param takeType    领取方式
     * @return 领取结果
     */
    Boolean takeCoupon(Long templateId, Set<Long> userIds, CouponTakeTypeEnum takeType);

    // TODO @疯狂：感觉 3 个方法的命名，改成 takeCouponByAdmin；takeCouponByUser；takeCouponByRegister 会更容易理解哈；现在两个都叫 sendCoupon ，感觉不太好懂
    /**
     * 【管理员】给用户发送优惠券
     *
     * @param templateId 优惠券模板编号
     * @param userIds    用户编号列表
     * @return 发送结果
     */
    default Boolean sendCoupon(Long templateId, Set<Long> userIds) {
        return takeCoupon(templateId, userIds, CouponTakeTypeEnum.BY_ADMIN);
    }

    /**
     * 【会员】领取优惠券
     *
     * @param templateId 优惠券模板编号
     * @param userId     用户编号
     * @return 发送结果
     */
    default Boolean receiveCoupon(Long templateId, Long userId) {
        return takeCoupon(templateId, CollUtil.newHashSet(userId), CouponTakeTypeEnum.BY_USER);
    }

    /**
     * 【系统】给用户发送新人券
     *
     * @param templateId 优惠券模板编号
     * @param userId     用户编号列表
     * @return 发送结果
     */
    default Boolean sendCoupon(Long templateId, Long userId) {
        return takeCoupon(templateId, CollUtil.newHashSet(userId), CouponTakeTypeEnum.BY_REGISTER);
    }
}
