package com.musichive.main.bean;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.StringRes;

import com.musichive.main.R;
import com.musichive.main.utils.PixgramResUtils;


/**
 * @author <a href="mailto:hejunqiu@everimaging.com">hejunqiu</a>
 * @version 1.0
 * @description
 * @create 18-10-23 : 下午3:01
 */
public class ResponseCode {

    /**
     * Request Api success.
     */
    public static final String REQUEST_SUCCESS = "000";

    /**
     * Token expired
     */
    public static final String INVALID_TOKEN = "403";

    /**
     * NetWork error.
     */
    public static final String NETWORK_ERROR = "1000";

    /**
     * UNKNOWN ERROR
     */
    public static final String UNKNOWN_ERROR = "999";

    /**
     * 用户名或密码错误
     */
    public static final String ACCOUNT_PASSWORD_NOT_MATCH = "119";

    /**
     * password error
     */
    public static final String PASSWORD_ERROR_CODE = "108";

    /**
     * 任务被重复提交ErrorCode.
     */
    public static final String TASK_REPEAT_SUBMIT_CODE = "424";

    public static final String WEXIN_ALREADY_BIND_OTHER_ACCOUNT_CODE = "169";

    public static final String ILLEGAL_WORKS_CODE = "161";

    public static final String LIKE_REPEAT_CODE = "3040001";

    public static final String WORK_EXPIRED_CODE = "3040002";

    public static final String WORK_DELETED_CODE = "170";  //作品被删除

    public static final String REPLY_DELETED_CODE = "173";

    /**
     * 未注册/私钥不存在
     */
    public static final String NOT_REGIST_CODE = "420";

    /**
     * 账户未托管
     */
    public static final String ACCOUNT_NOT_KEPT_CODE = "450";

    /**
     * 手机号已存在
     */
    public static final String MOBILE_CHECK_IN_CODE = "425";

    /**
     * 手机号不存在
     */
    public static final String ACCOUNT_NOT_FOUND_NULL  = "421";

    /**
     * 发送验证码频繁
     */
    public static final String VERIFY_SEND_CODE = "994";

    /**
     * 超过当天验证码发送次数
     */
    public static final String VERIFY_TIMES_CODE = "992";

    /**
     * 用户名已存在
     */
    public static final String ACCOUNT_HAS_EXISTS = "102";

    /**
     * 邀请码错误
     */
    public static final String INVITE_CODE_ERROR = "302";

    /**
     * 邀请码已被使用.
     */
    public static final String INVITE_CODE_USED = "303";

    /**
     * 操作频繁code.
     */
    public static final String FREQUENT_OPERATION_CODE = "104";

    /**
     * 次数限制code.
     */
    public static final String LIMIT_COUNT_CODE = "116";

    /**
     * 不合法的手机号
     */
    public static final String INVALID_PHONE_NUMBER_CODE = "117";
    public static final String INVALID_PHONE_NUMBER_CODE_1 = "991";

    /**
     * 手机号已被绑定
     */
    public static final String PHONE_NUMBER_IS_BINDED_CODE = "115";

    /**
     * 验证码错误
     */
    public static final String ERROR_VERIFICATION_CODE = "130";
    public static final String ERROR_VERIFICATION_CODE1 = "995";

    /**
     * 私钥已被使用.
     */
    public static final String KEYSTORE_ALREADY_OCCUPIED = "103";

    /**
     * 用户名被占用.
     */
    public static final String ACCOUNT_NAME_IS_EXIST = "430";

    /**
     * 身份证号已被使用.
     */
    public static final String IDENTITY_CARD_USED = "181";

    /**
     * 认证次数不足.
     */
    public static final String IDENTITY_TIMES_NOT_ENOUGH = "179";

    /**
     * 已关注
     */
    public static final String HAS_FOLLOWED = "141";

    /**
     * 关注自己
     */
    public static final String FOLLOW_SELF = "208";

    public static final String REPEAT_JOIN_GROUPS = "177";

    /**
     * 圈子已经存在
     */
    public static final String GROUPS_EXISTS_CODE = "163";

    /**
     * 用户昵称重复.
     */
    public static final String USER_NICKNAME_REPEAT = "191";

    public static final String WECHAT_USER_NOT_REGISTER = "198";

    public static final String POST_HAS_EXPIRED = "193";

    public static final String REPEAT_FORWARD_CODE = "142";

    public static final String REPEAT_UNFORWARD_CODE = "162";

    public static final String USER_GROUP_HAS_PROCESSING_CODE = "206";

    public static final String ADD_REPOST_ERROR_CODE = "214";//!< 转发官方账号作品异常

    public static final String JOIN_GROUP_APPLY_SUBMIT = "223"; //被踢的人重新加入圈子，会提示申请已提交 。

    public static final String GROUP_LABEL_REPAET = "224"; //圈子标签重复添加。
    /**
     * 关键字被占用
     */
    public static final String KEY_WORD_IS_OCCUPIED = "192";

    /**
     * 创建所需PIXT不足
     */
    public static final String PIXT_NOT_ENOUGH_TO_CREATE = "171";

    public static final String REPEAT_REMOVE_GROUP_MEMBER = "175"; //!< 踢出圈子，不是圈子成员

    public static final String DELETE_POST_NO_PIXT = "228"; //删除结算的作品金币不足

    public static final String CODE_10000 = "10000";

    public static final String CODE_APPLYED = "236";

    public static final String CODE_USER_NOT_ENABLE = "230"; //用户被封禁

    public static final String CODE_PUBLISH_SENSITIVE = "244"; //作品发布违规
    public static final String CONTEST_IS_OVER  = "254"; //作品发布比赛已结束

    /**
     * 该用户是降权用户
     */
    public static final String USER_HAS_BEEN_DOWNGRADED = "255";

    /**
     * 圈子昵称包含违规
     */
    public static final String CONTAINS_ILLEGAL_CONTENT_BY_GROUP = "247";
    /**
     * 昵称存在违规信息
     */
    public static final String CONTAINS_ILLEGAL_CONTENT_BY_NICKNAME = "248";
    /**
     * 圈子主题标签存在违规信息
     */
    public static final String CONTAINS_ILLEGAL_CONTENT_BY_GROUP_LABEL = "249";


    /**
     * 重复转发
     *
     * @param errorCode
     * @return
     */
    public static boolean isRepeatForwardCode(String errorCode) {
        return REPEAT_FORWARD_CODE.equals(errorCode);
    }

    /**
     * 转发官方账号作品异常
     *
     * @param errorCode
     * @return
     */
    public static boolean isAddRepostError(String errorCode) {
        return ADD_REPOST_ERROR_CODE.equals(errorCode);
    }

    /**
     * 重复取消转发
     *
     * @param errorCode
     * @return
     */
    public static boolean isRepeatUnForwardCode(String errorCode) {
        return REPEAT_UNFORWARD_CODE.equals(errorCode);
    }

    public static boolean isTaskRepeatSubmit(String code) {
        return TASK_REPEAT_SUBMIT_CODE.equals(code);
    }

    public static boolean isInValidToken(String code) {
        return INVALID_TOKEN.equals(code);
    }

    public static boolean isRequestSuccess(String code) {
        return REQUEST_SUCCESS.equals(code);
    }

    public static boolean isUnknownError(String code) {
        return UNKNOWN_ERROR.equals(code);
    }

    public static boolean isWeXinAlreadyBindOtherAccount(String code) {
        return WEXIN_ALREADY_BIND_OTHER_ACCOUNT_CODE.equals(code);
    }

    public static boolean isAccountOrPasswordError(String code) {
        return ACCOUNT_PASSWORD_NOT_MATCH.equals(code);
    }

    public static boolean isIllegalWork(String code) {
        return ILLEGAL_WORKS_CODE.equals(code);
    }

    public static boolean isLikeRepeat(String code) {
        return LIKE_REPEAT_CODE.equals(code);
    }

    public static boolean isWorkExpiredCode(String code) {
        return WORK_EXPIRED_CODE.equals(code);
    }

    public static boolean isWorkDeletedCode(String code) {
        return WORK_DELETED_CODE.equals(code);
    }

    public static boolean isPasswordError(String code) {
        return PASSWORD_ERROR_CODE.equals(code);
    }

    public static boolean isAttemptsToMatch(String code) {
        return VERIFY_SEND_CODE.equals(code);
    }

    public static boolean isNetWorkError(String code) {
        return NETWORK_ERROR.equals(code);
    }

    public static boolean isAccountExists(String code) {
        return ACCOUNT_HAS_EXISTS.equals(code);
    }

    public static boolean isVerifyTimes(String code) {
        return VERIFY_TIMES_CODE.equals(code);
    }

    /**
     * 用户未注册code
     *
     * @param errorCode
     * @return
     */
    public static boolean isNotRegistCode(String errorCode) {
        return ACCOUNT_NOT_FOUND_NULL.equals(errorCode);
    }

    /**
     * 用户未托管
     *
     * @param errorCode
     * @return
     */
    public static boolean isAccountNotKeptCode(String errorCode) {
        return ACCOUNT_NOT_KEPT_CODE.equals(errorCode);
    }

    /**
     * 手机号已登记
     */
    public static boolean isMobileCheckInCode(String errorCode) {
        return MOBILE_CHECK_IN_CODE.equals(errorCode);
    }

    /**
     * 邀请码错误
     *
     * @param errorCode
     * @return
     */
    public static boolean isInviteCodeError(String errorCode) {
        return INVITE_CODE_ERROR.equals(errorCode);
    }

    /**
     * 邀请码被使用
     *
     * @param errorCode
     * @return
     */
    public static boolean isInviteCodeUsed(String errorCode) {
        return INVITE_CODE_USED.equals(errorCode);
    }

    /**
     * 操作太频繁
     *
     * @param errorCode
     * @return
     */
    public static boolean isFrequentOperationCode(String errorCode) {
        return FREQUENT_OPERATION_CODE.equals(errorCode);
    }

    /**
     * 次数操作限制
     *
     * @param errorCode
     * @return
     */
    public static boolean isLimitCountCode(String errorCode) {
        return LIMIT_COUNT_CODE.equals(errorCode);
    }

    /**
     * 手机号不合法
     *
     * @param errorCode
     * @return
     */
    public static boolean isInvalidPhoneNumberCode(String errorCode) {
        return INVALID_PHONE_NUMBER_CODE.equals(errorCode) || INVALID_PHONE_NUMBER_CODE_1.equals(errorCode);
    }

    /**
     * 手机号已被绑定
     *
     * @param errorCode
     * @return
     */
    public static boolean phoneNumberIsBindedCode(String errorCode) {
        return PHONE_NUMBER_IS_BINDED_CODE.equals(errorCode);
    }

    /**
     * 手机验证码错误
     *
     * @param errorCode
     * @return
     */
    public static boolean verificationCodeError(String errorCode) {
        return ERROR_VERIFICATION_CODE.equals(errorCode) ||
                ERROR_VERIFICATION_CODE1.equals(errorCode);
    }

    /**
     * 私钥已被使用.
     *
     * @param errorCode
     * @return
     */
    public static boolean isKeystoreAlreadyOccupied(String errorCode) {
        return KEYSTORE_ALREADY_OCCUPIED.equals(errorCode);
    }

    /**
     * 用户名被占用.
     *
     * @param errorCode
     * @return
     */
    public static boolean accountNameIsExist(String errorCode) {
        return ACCOUNT_NAME_IS_EXIST.equals(errorCode);
    }

    /**
     * 身份证号已被使用.
     *
     * @param errorCode
     * @return
     */
    public static boolean isIdentityCardUsed(String errorCode) {
        return IDENTITY_CARD_USED.equals(errorCode);
    }

    /**
     * 身份认证次数不够.
     *
     * @param errorCode
     * @return
     */
    public static boolean isIdentityTimesNotEnough(String errorCode) {
        return IDENTITY_TIMES_NOT_ENOUGH.equals(errorCode);
    }

    /**
     * 重复加入圈子
     *
     * @param errorCode
     * @return
     */
    public static boolean isRepeatJoinGroups(String errorCode) {
        return REPEAT_JOIN_GROUPS.equals(errorCode);
    }

    public static boolean isRepeatRemoveGourpMember(String errorCode) {
        return REPEAT_REMOVE_GROUP_MEMBER.equals(errorCode);
    }

    /**
     * 圈子已经存在.
     *
     * @param errorCode
     * @return
     */
    public static boolean groupIsExist(String errorCode) {
        return GROUPS_EXISTS_CODE.equals(errorCode);
    }

    /**
     * follow self.
     *
     * @param errorCode
     * @return
     */
    public static boolean isFollowSelf(String errorCode) {
        return FOLLOW_SELF.equals(errorCode);
    }

    /**
     * 已关注.
     *
     * @param errorCode
     * @return
     */
    public static boolean isFollowed(String errorCode) {
        return HAS_FOLLOWED.equals(errorCode);
    }

    /**
     * 上传重复.
     *
     * @param errorCode
     * @return
     */
    public static boolean isUploadRepet(String errorCode) {
        return HAS_FOLLOWED.equals(errorCode);
    }

    /**
     * 回复被删除
     *
     * @param errorCode
     * @return
     */
    public static boolean replyDeleted(String errorCode) {
        return REPLY_DELETED_CODE.equals(errorCode);
    }

    /**
     * 微信用户未注册
     *
     * @param errorCode
     * @return
     */
    public static boolean wechatIsNotRegister(String errorCode) {
        return WECHAT_USER_NOT_REGISTER.equals(errorCode);
    }

    /**
     * 作品已经结算.
     *
     * @param errorCode
     * @return
     */
    public static boolean postHasExpired(String errorCode) {
        return POST_HAS_EXPIRED.equals(errorCode);
    }

    public static boolean isDeldeteNoPIXT(String errorCode) {
        return DELETE_POST_NO_PIXT.equals(errorCode);
    }

    public static boolean isApplyed(String errorCode) {

        return TextUtils.equals(errorCode, CODE_APPLYED);
    }

    public static boolean isViolationWork(String errorCode) {
        return isIllegalWork(errorCode);
    }

    public static boolean notEnoughPIXT(String errorCode) {
        return PIXT_NOT_ENOUGH_TO_CREATE.equals(errorCode);
    }

    /**
     * 用户已有正在审核中的圈子
     *
     * @param errorCode
     * @return
     */
    public static boolean userGroupHasProcessing(String errorCode) {
        return USER_GROUP_HAS_PROCESSING_CODE.equals(errorCode);
    }

    public static boolean isJoinGroupApply(String errorCode) {
        return TextUtils.equals(JOIN_GROUP_APPLY_SUBMIT, errorCode);
    }

    public static @StringRes
    int getStringResIdByCode(Context context, String code) {
        @StringRes int resId = PixgramResUtils.getStringId(context,
                "response_error_code_" + code);
        if (resId == 0) {
            resId = R.string.response_error_code_999;
        }
        return resId;
    }

    //    由于微博的错误码与微信一样。所以重写
    public static @StringRes
    int getStringResIdByWeiboCode(Context context, String code) {
        @StringRes int resId = PixgramResUtils.getStringId(context,
                "response_error_code_weibo_" + code);
        if (resId == 0) {
            resId = PixgramResUtils.getStringId(context,
                    "response_error_code_" + code);
            if (resId == 0) {
                resId = R.string.response_error_code_999;
            }
        }

        return resId;
    }

}