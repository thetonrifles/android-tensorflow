package com.thetonrifles.tensorflow;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.thetonrifles.detection.ContextDetector;
import com.thetonrifles.detection.ModelInfo;
import com.thetonrifles.detection.UnavailableModelException;

import java.util.Date;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements DownloadFragment.Callback {

    private DownloadFragment mFragment;

    @Bind(R.id.progress) ProgressBar mLoader;
    @Bind(R.id.txt_remote_path) TextView mUrlView;
    @Bind(R.id.btn_download) Button mDownloadButton;
    @Bind(R.id.btn_normalize) Button mNormalizeButton;
    @Bind(R.id.txt_timestamp) TextView mTimestampView;

    private BroadcastReceiver mModelUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateTimestampLabel();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        updateTimestampLabel();

        FragmentManager fm = getSupportFragmentManager();
        mFragment = (DownloadFragment) fm.findFragmentByTag("download");
        if (mFragment == null) {
            mFragment = DownloadFragment.newInstance();
            fm.beginTransaction().add(mFragment, "download").commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mModelUpdateReceiver, new IntentFilter(BuildConfig.APPLICATION_ID + ".action.MODEL_UPDATE"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mModelUpdateReceiver);
    }

    @OnClick(R.id.btn_download)
    void onDownloadButtonClick() {
        if (mFragment != null) {
            String url = mUrlView.getText().toString();
            mFragment.downloadModel(url);
        }
    }

    @OnClick(R.id.btn_normalize)
    void onNormalizeButtonClick() {
        try {
            float[] input = new float[10];
            for (int i = 0; i<input.length; i++) {
                input[i] = (new Random()).nextFloat();
            }
            float[] output = (new ContextDetector(this)).normalize(input);
            if (output != null) {
                StringBuilder sb = new StringBuilder("[ ");
                for (int i = 0; i < output.length; i++) {
                    sb.append(output[i]);
                    if (i < output.length - 1) {
                        sb.append(", ");
                    }
                }
                sb.append(" ]");
                Log.d("Normalization", "output = " + sb.toString());
                Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.toast_output_invalid, Toast.LENGTH_SHORT).show();
            }
        } catch (UnavailableModelException ex) {
            Log.e(LogTags.LOG_DETECTION, ex.getMessage(), ex);
            Toast.makeText(this, R.string.toast_empty_model, Toast.LENGTH_SHORT).show();
        }
    }

    private void updateTimestampLabel() {
        ModelInfo info = ModelInfo.get(this);
        String template = getString(R.string.last_update_value);
        mUrlView.setText(info.getRemoteUrl());
        if (info.getLastUpdate() > 0l) {
            String label = String.format(template, new Date(info.getLastUpdate()));
            mTimestampView.setText(label);
            mNormalizeButton.setEnabled(true);
        }
    }

    @Override
    public void onPrepare() {
        updateLayout(true);
    }

    @Override
    public void onDownloadCompleted() {
        updateLayout(false);
        updateTimestampLabel();
    }

    @Override
    public void onDownloadFailed(Exception ex) {
        updateLayout(false);
    }

    private void updateLayout(boolean loading) {
        if (loading) {
            mDownloadButton.setVisibility(View.INVISIBLE);
            mLoader.setVisibility(View.VISIBLE);
        } else {
            mDownloadButton.setVisibility(View.VISIBLE);
            mLoader.setVisibility(View.INVISIBLE);
        }
    }

}
