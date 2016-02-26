package com.thetonrifles.tensorflow;

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
import com.thetonrifles.detection.ModelStorage;
import com.thetonrifles.detection.events.ModelUpdatedEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements DownloadFragment.Callback {

    private DownloadFragment mFragment;

    @Bind(R.id.progress) ProgressBar mLoader;
    @Bind(R.id.btn_download) Button mDownloadButton;
    @Bind(R.id.btn_process) Button mLoadButton;
    @Bind(R.id.btn_normalize) Button mNormalizeButton;
    @Bind(R.id.txt_timestamp) TextView mTimestampView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mLoadButton.setEnabled(ModelStorage.getInstance().readLastUpdateTimestamp(this) != null);

        updateTimestampLabel();

        FragmentManager fm = getSupportFragmentManager();
        mFragment = (DownloadFragment) fm.findFragmentByTag("download");
        if (mFragment == null) {
            mFragment = DownloadFragment.newInstance();
            fm.beginTransaction().add(mFragment, "download").commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(ModelUpdatedEvent event) {
        updateTimestampLabel();
    }

    @OnClick(R.id.btn_download)
    void onDownloadButtonClick() {
        if (mFragment != null) {
            mFragment.downloadModel();
        }
    }

    @OnClick(R.id.btn_process)
    void onInvokeButtonClick() {
        float a = 2.0f;
        float b = 3.0f;
        float c = (new ContextDetector(this)).sum(a, b);
        String message = String.format(getString(R.string.toast_output_sum), a, b, c);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_normalize)
    void onNormalizeButtonClick() {
        float[] input = new float[88];
        float[] output = (new ContextDetector(this)).normalize(input);
        for (int i = 0; i < output.length; i++) {
            Log.d("Normalization", "[" + i + "] = " + output[i]);
        }
        Toast.makeText(this, R.string.toast_output_normalize, Toast.LENGTH_SHORT).show();
    }

    private void updateTimestampLabel() {
        String template = getString(R.string.last_update_value);
        Date timestamp = ModelStorage.getInstance().readLastUpdateTimestamp(this);
        if (timestamp != null) {
            String label = String.format(template, timestamp.toString());
            mTimestampView.setText(label);
            mLoadButton.setEnabled(true);
            mNormalizeButton.setEnabled(true);
        }
    }

    @Override
    public void onPrepare() {
        updateLayout(true);
    }

    @Override
    public void onProgress(int percentage) {
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
            mDownloadButton.setVisibility(View.GONE);
            mLoader.setVisibility(View.VISIBLE);
        } else {
            mDownloadButton.setVisibility(View.VISIBLE);
            mLoader.setVisibility(View.GONE);
        }
    }

}
