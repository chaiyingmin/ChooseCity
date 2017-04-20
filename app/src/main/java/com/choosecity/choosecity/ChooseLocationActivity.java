package com.choosecity.choosecity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.choosecity.choosecity.base.BaseActivity;
import com.choosecity.choosecity.base.ResultCallback;
import com.choosecity.choosecity.bean.ChooseCity;
import com.choosecity.choosecity.util.OnResultListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/20.
 * 三级选择城市
 */

public class ChooseLocationActivity extends BaseActivity {

    private ListView activityChooseLocationProvince;
    private ListView activityChooseLocationCity;
    private ListView activityChooseLocationArea;
    private ChooseCityAdapter mProvinceAdapter;
    private ChooseCityAdapter mCityAdapter;
    private ChooseCityAdapter mAreaAdapter;
    private ChooseCity mProvinceCity;
    private ChooseCity mCity;
    private ChooseCity mArea;
    private String mChooseCityId;
    private int provinceIndex = 0;
    private int cityIndex = 0;
    private int areaIndex = 0;
    List<ChooseCity> provinces = new ArrayList<>();

    public static Intent getChooseLocationIntent(Context context, String chooseCityId) {
        Intent intent = new Intent(context, ChooseLocationActivity.class);
        intent.putExtra("chooseCityId", chooseCityId);
        return intent;
    }

    public static ChooseCity getIntentResultProvince(Intent data) {
        return data.getParcelableExtra("province");
    }

    public static ChooseCity getIntentResultCity(Intent data) {
        return data.getParcelableExtra("city");
    }

    public static ChooseCity getIntentResultArea(Intent data) {
        return data.getParcelableExtra("area");
    }

    private  void getIntentData(List<ChooseCity> provinces) {
        mChooseCityId = getIntent().getStringExtra("chooseCityId");
        if (!TextUtils.isEmpty(mChooseCityId)) {
            initDefaultListView(provinces);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_location);
        setTitle("全国");
        initView();
        initListView();
        initAdapterResult();
        getCityListFromServer(getContext());
    }
    private void initView() {
        activityChooseLocationProvince = (ListView) findViewById(R.id.activity_choose_location_province);
        activityChooseLocationCity = (ListView) findViewById(R.id.activity_choose_location_city);
        activityChooseLocationArea = (ListView) findViewById(R.id.activity_choose_location_area);
    }
    private void initDefaultListView(List<ChooseCity> provinces) {
        for (int i = 0; i < provinces.size(); i++) {
            ChooseCity province = provinces.get(i);
            if (province.getIdString().equals(mChooseCityId)) {
                provinceIndex = i;
                return;
            }
            for (int j = 0; j < province.getChild().size(); j++) {
                ChooseCity city = province.getChild().get(j);
                if (city.getIdString().equals(mChooseCityId)) {
                    provinceIndex = i;
                    cityIndex = j;
                    return;
                }
                for (int k = 0; k < city.getChild().size(); k++) {
                    ChooseCity area = city.getChild().get(k);
                    if (area.getIdString().equals(mChooseCityId)) {
                        provinceIndex = i;
                        cityIndex = j;
                        areaIndex = k;
                        return;
                    }
                }
            }

        }
    }


    private void initListView() {
        mProvinceAdapter = new ChooseCityAdapter(new ArrayList<ChooseCity>(), getContext());
        mCityAdapter = new ChooseCityAdapter(new ArrayList<ChooseCity>(), getContext());
        mAreaAdapter = new ChooseCityAdapter(new ArrayList<ChooseCity>(), getContext());
        activityChooseLocationProvince.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChooseCity chooseCity = (ChooseCity) parent.getItemAtPosition(position);
                if (null != chooseCity) {
                    mProvinceCity = chooseCity;
                    mCity = null;
                    mArea = null;
                    if (null != chooseCity.getChild() && chooseCity.getChild().size() > 0) {
                        mProvinceAdapter.setCheckProvince(position);
                        mProvinceAdapter.notifyDataSetChanged();
                    } else {
                        setResultIntent();
                    }
                }
            }
        });
        activityChooseLocationCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChooseCity chooseCity = (ChooseCity) parent.getItemAtPosition(position);
                if (null != chooseCity) {
                    mCity = chooseCity;
                    if("全部".equals(chooseCity.getName())){
                        mCity = mProvinceCity;
                    }
                    mArea = null;
                    if (null != chooseCity.getChild() && chooseCity.getChild().size() > 0) {
                        mCityAdapter.setCheckProvince(position);
                        mCityAdapter.notifyDataSetChanged();

                    } else {
                        setResultIntent();
                    }
                }
            }
        });

        activityChooseLocationArea.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChooseCity chooseCity = (ChooseCity) parent.getItemAtPosition(position);
                if (null != chooseCity) {
                    mArea = chooseCity;
                    if("全部".equals(chooseCity.getName())){
                        mArea = mCity;
                    }
                    setResultIntent();
                }
            }
        });
        activityChooseLocationProvince.setAdapter(mProvinceAdapter);
        activityChooseLocationCity.setAdapter(mCityAdapter);
        activityChooseLocationArea.setAdapter(mAreaAdapter);
    }

    private void setResultIntent() {
        Intent intent = new Intent();
        intent.putExtra("province", mProvinceCity);
        intent.putExtra("city", mCity);
        intent.putExtra("area", mArea);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void initAdapterResult() {
        mProvinceAdapter.setCheckResult(new ResultCallback<ChooseCity>() {
            @Override
            public void onResult(ChooseCity result) {
                super.onResult(result);
                if (null != result.getChild() && result.getChild().size() > 0) {
                    mProvinceCity = result;
                    mCityAdapter.setCheckProvince(cityIndex);
                    mCityAdapter.refresh(result.getChild());
                }
            }
        });
        mCityAdapter.setCheckResult(new ResultCallback<ChooseCity>() {
            @Override
            public void onResult(ChooseCity result) {
                super.onResult(result);
                if (null != result.getChild() && result.getChild().size() > 0) {
                    mCity = result;
                    mAreaAdapter.setCheckProvince(areaIndex);
                    mAreaAdapter.refresh(result.getChild());
                } else {
                    mAreaAdapter.refresh(new ArrayList<ChooseCity>());
                }
            }
        });
        mAreaAdapter.setCheckResult(new ResultCallback<ChooseCity>() {
            @Override
            public void onResult(ChooseCity result) {
                super.onResult(result);
                if (null != result.getChild() && result.getChild().size() > 0) {
                    mArea = result;
                }
            }
        });
    }

    private  void refreshProvinceList(List<ChooseCity> provinces) {
        mProvinceAdapter.setCheckProvince(provinceIndex);
        mProvinceAdapter.refresh(provinces);
    }


    private void getCityListFromServer(Context context) {

        HttpShared.getAreas(context, new OnResultListener() {
            @Override
            public void onSuccess(int requestCode, String str) {
                List<ChooseCity> result = JSON.parseObject(str, new TypeReference<List<ChooseCity>>() {
                });

                getIntentData( result);
                refreshProvinceList( result);
            }

            @Override
            public void onAlert(int requestCode, String str) {

            }

            @Override
            public void onFailure(int requestCode, String str) {

            }
        });
    }

}
