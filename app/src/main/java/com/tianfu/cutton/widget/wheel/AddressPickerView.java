package com.tianfu.cutton.widget.wheel;

import android.content.Context;

import com.bigkoo.pickerview.OptionsPickerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by huangjh on 16/8/22.
 */
public class AddressPickerView extends OptionsPickerView {
    private static List address;
    private List<Map<String, Object>> lists;
    private ArrayList floor_1 = new ArrayList();
    private ArrayList floor_2 = new ArrayList();
    private ArrayList floor_3 = new ArrayList();


    public static final int PROVINCE = 1;
    public static final int CITY = 2;
    public static final int COUNTY = 3;


    //JSON文件
    private static final String FILE_PATH = "addr.json";
    private String title = "选择地址";
    private String different;

    public AddressPickerView(String title, Context context) {
        this(title, context, null);
        this.title = title;
    }

    public AddressPickerView(String title, Context context, String single) {
        super(context);
        this.title = title;
        different = single;
        address = null;
        load(context);
    }

    public AddressPickerView(Context context) {
        this(null, context);
        load(context);
    }


    void load(Context context) {
        if (address == null) {
            address = JsonUtil.loadFromJson(context, FILE_PATH, List.class);
        }
        lists = address;

        fillData(address);
    }

    /**
     * 数据拆包,只能存在三层,所以不需要迭代
     */
    void fillData(List<Map<String, Object>> list) {


        if (list != null) {

            for (Map<String, Object> m : list) {

                List<Map<String, Object>> cities = (List) m.get("sub");

                List oneProvinceCounties = new ArrayList();
                List oneProvinceCities = new ArrayList();
                if (cities != null) {
                    for (Map<String, Object> city : cities) {

                        //最后一层,县区
                        List<Map<String, Object>> counties = (List) city.get("sub");
                        List oneCityCounties = new ArrayList();
                        if (counties != null) {
                            for (Map<String, Object> county : counties) {
                                //一个市里面的所有县区
                                oneCityCounties.add(county.get("name"));
                            }
                        } else {
                            oneCityCounties.add("");
                        }

                        //一个省所有市
                        oneProvinceCities.add(city.get("name"));
                        //按一个省分组所有县区
                        oneProvinceCounties.add(oneCityCounties);
                    }
                }
                floor_1.add(m.get("name"));
                floor_2.add(oneProvinceCities);
                floor_3.add(oneProvinceCounties);

            }
        }
        setPicker(floor_1, floor_2, floor_3, true);

        setTitle(title);
        //设置滚动条是否轮播
        setCyclic(false, false, true);

    }


    public String getText(int... indexes) {
        String province = "";
        String city = "";
        String county = "";
        if (indexes != null && indexes.length > 0) {
            int i = indexes[0];
            province = (String) floor_1.get(i);
            if (indexes.length > 1) {
                int m = indexes[1];
                city = (String) ((List) floor_2.get(i)).get(m);
                if (indexes.length == 3) {
                    county = (String) ((List) (((List) floor_3.get(i)).get(m))).get(indexes[2]);
                }
            }
        }
        return province + " " + city + " " + county;
    }

    @Override
    public void setSelectOptions(int option1, int option2, int option3) {
        super.setSelectOptions(option1, option2, option3);
    }
}
