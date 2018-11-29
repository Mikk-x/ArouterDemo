package com.mikk.common_base.net;


/**
 * @author Mikk
 * @date 2018/8/24
 */
public interface CommonApi {

//    /**
//     * 用户登录
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("/api/auth/login/")
//    Observable<LoginResp> postLogin(@Body RequestBody requestBody);
//
//
//    /**
//     * 获取短信验证码
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("/api/sms/login")
//    Observable<LoginResp> postLoginVerifiy(@Body RequestBody requestBody);
//
//
//    /**
//     * 获取个人信息
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @GET("/api/account")
//    Observable<AccountDataResp> getAccount();
//
//    /**
//     * 获取 金钱
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @GET("/api/balances")
//    Observable<BalancesDataResp> getBalances();
//
//
//    /**
//     * 获取 oss
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @GET("/api/aliyun/sts")
//    Observable<OssResp> getAliyunSts();
//
//    /**
//     * 更换头像
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @PUT("/api/account/avatar")
//    Observable<BaseResp> putAvatar(@Body RequestBody requestBody);
//
//
//    /**
//     * 创建收款
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("/api/collections")
//    Observable<ReceivablesResp> postCollections(@Body RequestBody requestBody);
//
//
//    /**
//     * 添加银行卡
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("/api/bankcards/")
//    Observable<BaseResp> postAddBank(@Body RequestBody requestBody);
//
//    /**
//     * 查询银行卡
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @GET("/api/bankcards")
//    Observable<BankCardResp> getBankCard();
//
//
//    /**
//     * 二维码 id
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @GET("/api/collections/{collection_id}")
//    Observable<ReceivablesResp> getCollections(@Path("collection_id") String collection_id);
//
//    /**
//     * 转账到银行卡
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("/api/payments")
//    Observable<BaseResp> postPayments(@Body RequestBody requestBody);
//
//    /**
//     * 查询汇率
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @GET("/api/exchange_rates")
//    Observable<ExchangeResp> getExchangeRates();
//
//    /**
//     * 设置支付密码
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("/api/account/pay_password")
//    Observable<BaseResp> postSetPayPassword(@Body RequestBody requestBody);
//
//
//    /**
//     * 重置当前用户支付密码
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @PUT("/api/account/pay_password")
//    Observable<BaseResp> putResetPayPassword(@Body RequestBody requestBody);
//
//    /**
//     * 找回支付密码
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @PUT("/api/account/pay_password/reset")
//    Observable<BaseResp> putForgetPayPassword(@Body RequestBody requestBody);
//
//
//    /**
//     * 更新当前用户昵称
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @PUT("/api/account/name")
//    Observable<BaseResp> putName(@Body RequestBody requestBody);
//
//
//    /**
//     * orc 个人正面
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("/api/imageocrs/person_front")
//    Observable<PositiveResp> postOrcPositive(@Body RequestBody requestBody);
//
//    /**
//     * orc 个人反面
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("/api/imageocrs/person_back")
//    Observable<NegativeResp> postOrcNegative(@Body RequestBody requestBody);
//
//    /**
//     * orc 企业
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("/api/imageocrs/company")
//    Observable<CompanyResp> postOrcCompany(@Body RequestBody requestBody);
//
//    /**
//     * 上传实名认证图片审核
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("/api/legalentity")
//    Observable<LegalEntiryResp> postLegalEntity(@Body RequestBody requestBody);
//
//    /**
//     * 扫码付款
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("/api/transfers")
//    Observable<BaseResp> postTransfers(@Body RequestBody requestBody);
//
//    /**
//     * 账单  交易列表
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @GET("/api/transactions")
//    Observable<RecordResp> getTransactions(@Query("page") int page);
//
//    /**
//     * 充值
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("/api/deposits")
//    Observable<DepositsResp> postDeposits(@Body RequestBody requestBody);
//
//    /**
//     * 提现预览
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("/api/withdrawals/preview")
//    Observable<WithdrawPreviewResp> postWithdrawPreview(@Body RequestBody requestBody);
//
//    /**
//     * 提现
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("/api/withdrawals")
//    Observable<BaseResp> postWithdraw(@Body RequestBody requestBody);
//
//    /**
//     * 查询转账历史银行卡
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @GET("/api/beneficiaries")
//    Observable<TransferSelectResp> getBeneficiaries();
//
//    /**
//     * 转账预览
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("/api/payments/preview")
//    Observable<TransferPreviewResp> postPaymentPreview(@Body RequestBody requestBody);
//
//
//    /**
//     * 转账 短信验证码
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("/api/sms/payment")
//    Observable<BaseResp> postVerify();
//
//
//    /**
//     * 换汇
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("/api/exchanges")
//    Observable<Response<Void>> postExchanges(@Body RequestBody requestBody);
//
//
//    /**
//     * 充值详情
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @GET("/api/deposits/{depositId}")
//    Observable<DepositsDetailResp> getDepositsDetail(@Path("depositId") String depositId);
//
//    /**
//     * 提现详情
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @GET("/api/withdrawal/{withdrawalId}")
//    Observable<WithdrawalDetailResp> getWithdrawalDetail(@Path("withdrawalId") String withdrawalId);
//
//    /**
//     * 换汇详情
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @GET("/api/exchanges/{exchangeId}")
//    Observable<ExchangeDetailResp> getExchangeDetail(@Path("exchangeId") String exchangeId);
//
//    /**
//     * 转账详情
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @GET("/api/payments/{paymentId}")
//    Observable<TransferDetailResp> getPaymentsDetail(@Path("paymentId") String paymentId);
//
//    /**
//     * 收款、付款详情
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @GET("/api/transfers/{transferId}")
//    Observable<PaymentDetailResp> getTransfersDetail(@Path("transferId") String transferId);
//
//    /**
//     * 版本更新
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @GET("/api/appinfo")
//    Observable<AppInfoResp> getAppInfo();


}
