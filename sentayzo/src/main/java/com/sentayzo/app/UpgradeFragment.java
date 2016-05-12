package com.sentayzo.app;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpgradeFragment extends Fragment {

   // TextView detailsNoAds;
    WebView webViewBasic, webViewGold, webViewPlatinum;
    View rootView;

    public UpgradeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_upgrade, container, false);

    //    detailsNoAds = (TextView) rootView.findViewById(R.id.tv_details_block_ads);

       webViewBasic = (WebView) rootView.findViewById(R.id.wv_basic);
        webViewGold = (WebView) rootView.findViewById(R.id.wv_gold);
        webViewPlatinum = (WebView) rootView.findViewById(R.id.wv_platinum);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

      //  detailsNoAds.setText(Html.fromHtml(getString(R.string.block_ads_details)));

        webViewBasic.loadDataWithBaseURL(null, getString(R.string.details_basic), "text/html", "utf-8", null);
        webViewGold.loadDataWithBaseURL(null, getString(R.string.details_gold), "text/html", "utf-8", null);
        webViewPlatinum.loadDataWithBaseURL(null, getString(R.string.details_platinum
        ), "text/html", "utf-8", null);
    }
}
