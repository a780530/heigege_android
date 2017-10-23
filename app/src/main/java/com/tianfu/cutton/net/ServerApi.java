package com.tianfu.cutton.net;

import com.tianfu.cutton.model.AllFactoryBean;
import com.tianfu.cutton.model.AllStoragesBean;
import com.tianfu.cutton.model.AreaLintPriceByLastBean;
import com.tianfu.cutton.model.BagDetailsMessageBean;
import com.tianfu.cutton.model.CalculateOnceBean;
import com.tianfu.cutton.model.CodeValidate;
import com.tianfu.cutton.model.CountBean;
import com.tianfu.cutton.model.EntrepotBean;
import com.tianfu.cutton.model.ForgetPwdBean;
import com.tianfu.cutton.model.GetSms;
import com.tianfu.cutton.model.HomeSortBean;
import com.tianfu.cutton.model.HotBean;
import com.tianfu.cutton.model.IsLogin;
import com.tianfu.cutton.model.KunGetBatch;
import com.tianfu.cutton.model.ListAssociatebean;
import com.tianfu.cutton.model.ListPurchaseOrder;
import com.tianfu.cutton.model.ListSupplierByPurchaseIdBean;
import com.tianfu.cutton.model.ListSupplyOrderBySelfBean;
import com.tianfu.cutton.model.LoginBean;
import com.tianfu.cutton.model.LoginByCodeBean;
import com.tianfu.cutton.model.MyStoreBean;
import com.tianfu.cutton.model.MyfavoritesBean;
import com.tianfu.cutton.model.NewPriceBean;
import com.tianfu.cutton.model.ProvinceBean;
import com.tianfu.cutton.model.PurchaseDynamicsBean;
import com.tianfu.cutton.model.PurchaseOrder;
import com.tianfu.cutton.model.PurchaseOrderBySelfBean;
import com.tianfu.cutton.model.PurchaseorderbySelfDetailsBean;
import com.tianfu.cutton.model.QualityBagBean;
import com.tianfu.cutton.model.QualityBean;
import com.tianfu.cutton.model.QualityCountBean;
import com.tianfu.cutton.model.QualityKunMessageBean;
import com.tianfu.cutton.model.ResourcesBean;
import com.tianfu.cutton.model.SearchTestbean;
import com.tianfu.cutton.model.SellerBean;
import com.tianfu.cutton.model.SetPwdBean;
import com.tianfu.cutton.model.StoreBean;
import com.tianfu.cutton.model.StoreKunMessage;
import com.tianfu.cutton.model.TestBean;
import com.tianfu.cutton.model.VersionBean;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by xiaohei on 2017/3/28.
 */

public interface ServerApi {
    @FormUrlEncoded
    @POST("/userApp/customer/sendMsg.json")
    Call<GetSms> getSms(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/userApp/customer/doValidateCoder.json")
    Call<CodeValidate> smsValidate(
            @FieldMap Map<String, String> paramsCode
    );

    @FormUrlEncoded
    @POST("/userApp/customer/doRegister.json")
    Call<ForgetPwdBean> doRegister(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/userApp/customer/loginByPwd.json")
    Call<LoginBean> loginByPwd(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/userApp/customer/isLogin.json")
    Call<IsLogin> isLogin(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/userApp/customer/forgetPasswd.json")
    Call<ForgetPwdBean> forgetPwd(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/userApp/customer/logout.json")
    Call<CodeValidate> loginOut(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/userApp/customer/doUpdatePwd.json")
    Call<CodeValidate> modfidyPwd(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/userApp/customer/loginByCode.json")
    Call<LoginByCodeBean> loginByCode(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/userApp/customer/setPassword.json")
    Call<SetPwdBean> setPwd(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/orderApp/purchase/listPurchaseOrderBySelf.json")
    Call<PurchaseOrderBySelfBean> getPurchaseOrder(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/orderApp/purchase/getPurchaseOrderBySn.json")
    Call<PurchaseorderbySelfDetailsBean> getPurchaseOrderDetails(
            @FieldMap Map<String, String> params
          /*  @Field("purchaseSn") String purchaseSn,
            @Field("deviceNo") String deviceNo*/
    );

    @FormUrlEncoded
    @POST("/orderApp/supply/listSupplierByPurchaseId.json")
    Call<ListSupplierByPurchaseIdBean> getListSupplierByPurchaseId(
            @FieldMap Map<String, String> params
        /*    @Field("purchaseId") String purchaseId,
            @Field("pageNum") String pageNum*/
    );

    @FormUrlEncoded
    @POST("/orderApp/purchase/completePurchaseOrder.json")
    Call<PurchaseOrder> completePurchaseOrder(
            @FieldMap Map<String, String> params
//            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("/orderApp/purchase/closePurchaseOrder.json")
    Call<PurchaseOrder> closePurchaseOrder(
            @FieldMap Map<String, String> params
//            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("/orderApp/purchase/updatePurchaseOrder2.json")
    Call<PurchaseOrder> updatePurchase(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/orderApp/supply/listSupplyOrderBySelf.json")
    Call<ListSupplyOrderBySelfBean> listSupplyOrderBySelf(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/orderApp/supply/confirmSupplyOrder.json")
    Call<PurchaseOrder> sureSupply(
            @FieldMap Map<String, String> params
//            @Field("supplyId") String id
    );

    @FormUrlEncoded
    @POST("/orderApp/purchase/listPurchaseOrder.json")
    Call<ListPurchaseOrder> listPurchaseOrder(
//            @Header("Authorization")String sign,
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/orderApp/purchase/createPurchaseOrder.json")
    Call<PurchaseOrder> createPurchaseOrder(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/orderApp/supply/createSupplyOrder.json")
    Call<PurchaseOrder> createSupplyOrder(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/cottonApp/search/list.json")
    Call<QualityBean> getQualityList(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/cottonApp/search/getDetailHead.json")
    Call<QualityKunMessageBean> getQualityKunMessage(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/cottonApp/search/getBatchImList.json")
    Call<KunGetBatch> kunGetBatch(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/cottonApp/search/list.json")
    Call<StoreBean> getStoreList(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/userApp/favorites/isFavorites.json")
    Call<CodeValidate> isCollection(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/userApp/favorites/addFavorites.json")
    Call<CodeValidate> addCollection(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/userApp/favorites/cancelFavorites.json")
    Call<CodeValidate> deleteCollection(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/productApp/product/getProductById.json")
    Call<StoreKunMessage> getStoreKunMessage(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/userApp/favorites/listFavorites.json")
    Call<MyfavoritesBean> myCollection(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/userApp/favorites/deleteFavorites.json")
    Call<CodeValidate> deleteCollectionS(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/userApp/shareRecord/listShare.json")
    Call<MyfavoritesBean> getshareRecord(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/userApp/shareRecord/deleteShare.json")
    Call<CodeValidate> deleteshare(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/userApp/browsing/listHistory.json")
    Call<MyfavoritesBean> getListHistory(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/userApp/browsing/deleteHistory.json")
    Call<CodeValidate> deleteListHistory(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/userApp/customer/getCount.json")
    Call<CountBean> getCount(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/userApp/shareRecord/share.json")
    Call<CountBean> addShare(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/productApp/product/listProductCustomer.json")
    Call<MyStoreBean> myCommodity(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/productApp/product/changeState.json")
    Call<MyStoreBean> changeState(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/cottonApp/search/searchIndex.json")
    Call<HotBean> getHot(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/cottonApp/search/listAssociate.json")
    Call<ListAssociatebean> getlistAssociate(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/cottonApp/search/list.json")
    Call<SearchTestbean> getSearch(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("cottonApp/search/getDetail.json")
    Call<QualityCountBean> getQualityCount(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/userApp/browsing/addHistory.json")
    Call<QualityCountBean> addHistory(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/cottonApp/search/getBagList.json")
    Call<QualityBagBean> getBagMessage(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/cottonApp/search/clean.json")
    Call<CodeValidate> deleteSearchHis(
            @FieldMap Map<String, String> params
//            @Field("deviceNo") String id
    );

    @FormUrlEncoded
    @POST("/userApp/customer/ifBindMobile.json")
    Call<LoginBean> ifBindMobile(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/userApp/customer/bindMobile.json")
    Call<LoginBean> bindMobile(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/cottonApp/search/getBag.json")
    Call<BagDetailsMessageBean> getBag(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/userApp/customer/scan.json")
    Call<BagDetailsMessageBean> scan(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/userApp/customer/scanLogin.json")
    Call<BagDetailsMessageBean> scanLogin(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/userApp/customer/cancleScanLogin.json")
    Call<BagDetailsMessageBean> cancleLogin(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/userApp/customer/userBanking.json")
    Call<HomeSortBean> getSort(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/authApp/version/getAppVersion.json")
    Call<VersionBean> getAppVersion(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/productApp/product/calculateOnce.json")
    Call<CalculateOnceBean> calculateOnce(
            @FieldMap Map<String, String> params
    );

    @FormUrlEncoded
    @POST("/cottonApp/storage/getStorageProvinces.json")
    Call<ProvinceBean> getProvince(
            @FieldMap Map<String, String> params
    );
    @FormUrlEncoded
    @POST("/cotton/storage/getStoragesByProvince.json")
    Call<EntrepotBean> getStoragesByProvince(
            @FieldMap Map<String, String> params
    );
    @FormUrlEncoded
    @POST("/productApp/priceIndex/getPriceIndexByType.json")
    Call<NewPriceBean> getPriceIndexByType(
            @FieldMap Map<String, String> params
    );
    @FormUrlEncoded
    @POST("/productApp/product/cheapProduct.json")
    Call<ResourcesBean> cheapProduct(
            @FieldMap Map<String, String> params
    );
    @FormUrlEncoded
    @POST("/userApp/customer/listAllRecommendSeller.json")
    Call<SellerBean> listAllRecommendSeller(
            @FieldMap Map<String, String> params
    );
    @FormUrlEncoded
    @POST("/orderApp/purchase/purchaseDynamics.json")
    Call<PurchaseDynamicsBean> purchaseDynamics(
            @FieldMap Map<String, String> params
    );
    @FormUrlEncoded
    @POST("/productApp/areaLintPrice/getAreaLintPriceByLast.json")
    Call<AreaLintPriceByLastBean> getAreaLintPriceByLast(
            @FieldMap Map<String, String> params
    );
    @FormUrlEncoded
    @POST("/productApp/product/addProductOne.json")
    Call<CodeValidate> addProductOne(
            @FieldMap Map<String, String> params
    );
    @FormUrlEncoded
    @POST("/productApp/product/addProductBatch.json")
    Call<CodeValidate> addProductBatch(
            @FieldMap Map<String, String> params
    );
    @FormUrlEncoded
    @POST("cottonApp/search/getDetail.json")
    Call<TestBean> getTest(
            @FieldMap Map<String, String> params
    );
    @FormUrlEncoded
    @POST("/productApp/product/updateProduct.json")
    Call<CodeValidate> changeBatch(
            @FieldMap Map<String, String> params
    );
    @FormUrlEncoded
    @POST("/cottonApp/storage/getAllStorages.json")
    Call<AllStoragesBean> getAllStorages(
            @FieldMap Map<String, String> params
    );
    @FormUrlEncoded
    @POST("/cotton/factory/getAllFactorys.json")
    Call<AllFactoryBean> getAllFactorys(
            @FieldMap Map<String, String> params
    );
}
