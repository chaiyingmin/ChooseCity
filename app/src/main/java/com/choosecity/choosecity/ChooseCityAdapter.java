package com.choosecity.choosecity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.choosecity.choosecity.base.ABaseAdapter;
import com.choosecity.choosecity.base.ResultCallback;
import com.choosecity.choosecity.bean.ChooseCity;

import java.util.List;

/**
 * Created by MDC_006 on 2016/7/15.
 * 选择城市adapter
 */
public class ChooseCityAdapter extends ABaseAdapter<ChooseCity> {

    private ResultCallback<ChooseCity> checkResult;
    private int checkPosition = -1;

    public ChooseCityAdapter(List<ChooseCity> data, Context context) {
        super(data, context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = getInflater().inflate(R.layout.adapter_choose_city, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        ((ViewHolder) convertView.getTag()).initializeViews(getItem(position), position);
        return convertView;
    }

    public void setCheckResult(ResultCallback<ChooseCity> checkResult) {
        this.checkResult = checkResult;
    }

    public void setCheckProvince(int checkPosition) {
        this.checkPosition = checkPosition;
    }

    protected class ViewHolder {
        private TextView adapterChooseCityTv;

        public ViewHolder(View view) {
            adapterChooseCityTv = (TextView) view.findViewById(R.id.adapter_choose_city_tv);
        }

        public void initializeViews(ChooseCity object, int position) {
            adapterChooseCityTv.setText(object.getName());
            if (checkPosition == position) {
                if (null != checkResult) {
                    checkResult.onResult(object);
                }
                adapterChooseCityTv.setBackgroundResource(R.color.colorWhite);
            } else {
                adapterChooseCityTv.setBackgroundResource(R.drawable.selector_content_white_click);
            }
        }
    }
}
