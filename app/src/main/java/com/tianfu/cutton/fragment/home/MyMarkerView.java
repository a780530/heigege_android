
package com.tianfu.cutton.fragment.home;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.tianfu.cutton.R;

/**
 * Custom implementation of the MarkerView.
 * 
 * @author Philipp Jahoda
 */
public class MyMarkerView extends MarkerView {

    private TextView tvContent;

    public MyMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);

        tvContent = (TextView) findViewById(R.id.mapText1);
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        if (e instanceof CandleEntry) {

            CandleEntry ce = (CandleEntry) e;
            String[] split = Utils.formatNumber(ce.getHigh(), 3, false).split(",");
            while (split[1].endsWith("0")){
                split[1] = split[1].substring(0,split[1].length()-1);
            }
            if (split[1].equals("")){
                split[1] = "0";
            }
            tvContent.setText("" + split[0]+"."+split[1]);
        } else {
            String[] split = Utils.formatNumber(e.getY(), 3, false).split(",");
            while (split[1].endsWith("0")){
                split[1] = split[1].substring(0,split[1].length()-1);
            }
            if (split[1].equals("")){
                split[1] = "0";
            }
            tvContent.setText("" + split[0]+"."+split[1]);
        }

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
