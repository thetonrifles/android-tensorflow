package com.thetonrifles.tensorflow;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements DownloadFragment.Callback {

    private static final String FILE_URL = "https://dl.dropboxusercontent.com/u/44270891/graph.pb";

    private DownloadFragment mFragment;

    private ProgressBar mLoader;
    private Button mDownloadButton;
    private Button mLoadButton;
    private TextView mTimestampView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Context context = this;

        mLoader = (ProgressBar) findViewById(R.id.progress);
        mTimestampView = (TextView) findViewById(R.id.txt_timestamp);

        mDownloadButton = (Button) findViewById(R.id.btn_download);
        mDownloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFragment != null) {
                    mFragment.executeDownload(FILE_URL);
                }
            }
        });

        mLoadButton = (Button) findViewById(R.id.btn_read);
        mLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filename = FileStorage.getInstance().readLastUpdateFileName(context);
                if (!TextUtils.isEmpty(filename)) {
                    float a = 2.0f;
                    float b = 3.0f;
                    float c = (new TensorFlow()).sum(filename, a, b);
                    String message = String.format(getString(R.string.toast_output_sum), a, b, c);
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, R.string.toast_empty_model, Toast.LENGTH_SHORT).show();
                }
            }
        });
        mLoadButton.setEnabled(FileStorage.getInstance().readLastUpdateTimestamp(this) != null);

        updateTimestampLabel();

        FragmentManager fm = getSupportFragmentManager();
        mFragment = (DownloadFragment) fm.findFragmentByTag("download");
        if (mFragment == null) {
            mFragment = DownloadFragment.newInstance();
            fm.beginTransaction().add(mFragment, "download").commit();
        }
    }

    private void updateTimestampLabel() {
        String template = getString(R.string.last_update_value);
        Date timestamp = FileStorage.getInstance().readLastUpdateTimestamp(this);
        if (timestamp != null) {
            String label = String.format(template, timestamp.toString());
            mTimestampView.setText(label);
            mLoadButton.setEnabled(true);
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
