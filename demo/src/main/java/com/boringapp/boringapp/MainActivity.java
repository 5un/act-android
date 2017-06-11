/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.boringapp.boringapp;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.boringapp.boringapp.data.RecognitionResult;
import com.bumptech.glide.Glide;
import com.google.android.cameraview.AspectRatio;
import com.google.android.cameraview.CameraView;
import com.ogaclejapan.arclayout.Arc;
import com.ogaclejapan.arclayout.ArcLayout;
import com.sackcentury.shinebuttonlib.ShineButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.sephiroth.android.library.viewrevealanimator.ViewRevealAnimator;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * This demo app saves the taken picture to a constant file.
 * $ adb pull /sdcard/Android/data/com.google.android.cameraview.demo/files/Pictures/picture.jpg
 */
public class MainActivity extends AppCompatActivity implements
        ActivityCompat.OnRequestPermissionsResultCallback,
        AspectRatioFragment.Listener {

    private static final String TAG = "MainActivity";

    private static final int REQUEST_CAMERA_PERMISSION = 1;

    private static final String FRAGMENT_DIALOG = "dialog";

    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private static final MediaType MEDIA_TYPE_JPEG = MediaType.parse("image/jpeg");


    private static final int[] FLASH_OPTIONS = {
            CameraView.FLASH_AUTO,
            CameraView.FLASH_OFF,
            CameraView.FLASH_ON,
    };

    private static final int[] FLASH_ICONS = {
            R.drawable.ic_flash_auto,
            R.drawable.ic_flash_off,
            R.drawable.ic_flash_on,
    };

    private static final int[] FLASH_TITLES = {
            R.string.flash_auto,
            R.string.flash_off,
            R.string.flash_on,
    };

    private int mCurrentFlash;

    private CameraView mCameraView;
    private ViewPager mPager;
    private MainPagerAdapter mPagerAdapter;

    @BindView(R.id.btn_settings)
    ImageButton mBtnSettings;

    @BindView(R.id.btn_redeem)
    ImageButton mBtnRedeem;

    @BindView(R.id.btn_vote)
    ImageButton mBtnVote;

    @BindView(R.id.btn_scan)
    ImageButton mBtnScan;

    @BindView(R.id.btn_history)
    ImageButton mBtnHistory;

    @BindView(R.id.btn_friends)
    ImageButton mBtnFriends;

    @BindView(R.id.shineButton)
    ShineButton mBtnShine;

    private ArcLayout mMenuLayout;
    private ImageButton mBtnTakePhoto;
    private Fragment mCarbonScoreFragment;
    private ViewRevealAnimator mViewRevealAnimator;
    private RelativeLayout mLayoutCarbonScoreOverlay;
    private TextView mTxtProductName;
    private TextView mTxtProductScore;


    private Handler mBackgroundHandler;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.take_picture:

                    if(mMenuLayout.getVisibility() == View.INVISIBLE){
                        showMenu();
                    } else {
                        hideMenu();
                    }
                    break;
                case R.id.btn_scan:
                    if(mPager.getCurrentItem() != 1) {
                        mPager.setCurrentItem(1);
                    }
                    if (mCameraView != null) {
                        mCameraView.takePicture();
                     }
                     hideMenu();
                    break;
                case R.id.btn_redeem:
                    Intent intent = new Intent(MainActivity.this, RewardsMenuActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_friends:
                    mPager.setCurrentItem(2);
                    hideMenu();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mCameraView = (CameraView) findViewById(R.id.camera);
        if (mCameraView != null) {
            mCameraView.addCallback(mCallback);
        }

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                    int positionOffsetPixels) {
                Log.d("ACTAPP", "page:" + mPager.getCurrentItem() +  " position " + position + " positionOffset " + positionOffset + " positionOffsetPixels " + positionOffsetPixels);
                // mPager.getCurrentItem();
                if(position == 0){
                    //TODO the more value, the more transparency
                    mPager.setBackgroundColor(Utils.getColorWithAlpha(getResources().getColor(R.color.black), 1.0f - positionOffset));

                }else if(position == 1){
                    // TODO the less value, the more transparency
                    mPager.setBackgroundColor(Utils.getColorWithAlpha(getResources().getColor(R.color.black), positionOffset));
                }else {
                    // always black
                    mPager.setBackgroundColor(Utils.getColorWithAlpha(getResources().getColor(R.color.black), 1.0f));
                }

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        mPager.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
//
//            }
//        });

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.take_picture);
//        if (fab != null) {
//            fab.setOnClickListener(mOnClickListener);
//        }

        mBtnTakePhoto = (ImageButton) findViewById(R.id.take_picture);
        mBtnTakePhoto.setOnClickListener(mOnClickListener);

        mBtnVote.setOnClickListener(mOnClickListener);
        mBtnScan.setOnClickListener(mOnClickListener);
        mBtnHistory.setOnClickListener(mOnClickListener);
        mBtnFriends.setOnClickListener(mOnClickListener);
        mBtnRedeem.setOnClickListener(mOnClickListener);

        mMenuLayout = (ArcLayout) findViewById(R.id.arc_layout);
        mMenuLayout.setVisibility(View.INVISIBLE);

        mViewRevealAnimator = (ViewRevealAnimator) findViewById(R.id.animator);
        mViewRevealAnimator.setVisibility(View.GONE);

        mLayoutCarbonScoreOverlay = (RelativeLayout) findViewById(R.id.layout_carbon_score_overlay);
        mLayoutCarbonScoreOverlay.setVisibility(View.GONE);

        mTxtProductName = (TextView) findViewById(R.id.txt_product_name);
        mTxtProductScore = (TextView) findViewById(R.id.txt_product_score);


        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }

        mLayoutCarbonScoreOverlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLayoutCarbonScoreOverlay.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            mCameraView.start();
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            ConfirmationDialogFragment
                    .newInstance(R.string.camera_permission_confirmation,
                            new String[]{Manifest.permission.CAMERA},
                            REQUEST_CAMERA_PERMISSION,
                            R.string.camera_permission_not_granted)
                    .show(getSupportFragmentManager(), FRAGMENT_DIALOG);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION);
        }
    }

    @Override
    protected void onPause() {
        mCameraView.stop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBackgroundHandler != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                mBackgroundHandler.getLooper().quitSafely();
            } else {
                mBackgroundHandler.getLooper().quit();
            }
            mBackgroundHandler = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION:
                if (permissions.length != 1 || grantResults.length != 1) {
                    throw new RuntimeException("Error on requesting camera permission.");
                }
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, R.string.camera_permission_not_granted,
                            Toast.LENGTH_SHORT).show();
                }
                // No need to start camera here; it is handled by onResume
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.aspect_ratio:
                FragmentManager fragmentManager = getSupportFragmentManager();
                if (mCameraView != null
                        && fragmentManager.findFragmentByTag(FRAGMENT_DIALOG) == null) {
                    final Set<AspectRatio> ratios = mCameraView.getSupportedAspectRatios();
                    final AspectRatio currentRatio = mCameraView.getAspectRatio();
                    AspectRatioFragment.newInstance(ratios, currentRatio)
                            .show(fragmentManager, FRAGMENT_DIALOG);
                }
                return true;
            case R.id.switch_flash:
                if (mCameraView != null) {
                    mCurrentFlash = (mCurrentFlash + 1) % FLASH_OPTIONS.length;
                    item.setTitle(FLASH_TITLES[mCurrentFlash]);
                    item.setIcon(FLASH_ICONS[mCurrentFlash]);
                    mCameraView.setFlash(FLASH_OPTIONS[mCurrentFlash]);
                }
                return true;
            case R.id.switch_camera:
                if (mCameraView != null) {
                    int facing = mCameraView.getFacing();
                    mCameraView.setFacing(facing == CameraView.FACING_FRONT ?
                            CameraView.FACING_BACK : CameraView.FACING_FRONT);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAspectRatioSelected(@NonNull AspectRatio ratio) {
        if (mCameraView != null) {
            Toast.makeText(this, ratio.toString(), Toast.LENGTH_SHORT).show();
            mCameraView.setAspectRatio(ratio);
        }
    }

    private Handler getBackgroundHandler() {
        if (mBackgroundHandler == null) {
            HandlerThread thread = new HandlerThread("background");
            thread.start();
            mBackgroundHandler = new Handler(thread.getLooper());
        }
        return mBackgroundHandler;
    }

    private CameraView.Callback mCallback
            = new CameraView.Callback() {

        @Override
        public void onCameraOpened(CameraView cameraView) {
            Log.d(TAG, "onCameraOpened");
        }

        @Override
        public void onCameraClosed(CameraView cameraView) {
            Log.d(TAG, "onCameraClosed");
        }

        @Override
        public void onPictureTaken(CameraView cameraView, final byte[] data) {
            Log.d(TAG, "onPictureTaken " + data.length);
            Toast.makeText(cameraView.getContext(), R.string.picture_taken, Toast.LENGTH_SHORT)
                    .show();

            BitmapFactory.Options opts = new BitmapFactory.Options();
            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            final Bitmap bmpScaled = Bitmap.createScaledBitmap(bmp, (int) (bmp.getWidth() * 0.25f),
                                                                (int) (bmp.getHeight() * 0.25f), true);
            //bmp.compress(Bitmap.CompressFormat.JPEG, 70, bos);
            //bmp.compress()

            getBackgroundHandler().post(new Runnable() {
                @Override
                public void run() {
                    File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                            "picture.jpg");

                    OutputStream os = null;
                    try {

                        os = new FileOutputStream(file);
                        //os.write(data);
                        bmpScaled.compress(Bitmap.CompressFormat.JPEG, 70, os);
                        os.close();
                        requestCarbonFootprint(file, "picture.jpg");


                    } catch (IOException e) {
                        Log.w(TAG, "Cannot write to " + file, e);
                    } finally {
                        if (os != null) {
                            try {
                                os.close();
                            } catch (IOException e) {
                                // Ignore
                            }
                        }
                    }
                }
            });
        }

    };

    public static class ConfirmationDialogFragment extends DialogFragment {

        private static final String ARG_MESSAGE = "message";
        private static final String ARG_PERMISSIONS = "permissions";
        private static final String ARG_REQUEST_CODE = "request_code";
        private static final String ARG_NOT_GRANTED_MESSAGE = "not_granted_message";

        public static ConfirmationDialogFragment newInstance(@StringRes int message,
                String[] permissions, int requestCode, @StringRes int notGrantedMessage) {
            ConfirmationDialogFragment fragment = new ConfirmationDialogFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_MESSAGE, message);
            args.putStringArray(ARG_PERMISSIONS, permissions);
            args.putInt(ARG_REQUEST_CODE, requestCode);
            args.putInt(ARG_NOT_GRANTED_MESSAGE, notGrantedMessage);
            fragment.setArguments(args);
            return fragment;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Bundle args = getArguments();
            return new AlertDialog.Builder(getActivity())
                    .setMessage(args.getInt(ARG_MESSAGE))
                    .setPositiveButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String[] permissions = args.getStringArray(ARG_PERMISSIONS);
                                    if (permissions == null) {
                                        throw new IllegalArgumentException();
                                    }
                                    ActivityCompat.requestPermissions(getActivity(),
                                            permissions, args.getInt(ARG_REQUEST_CODE));
                                }
                            })
                    .setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(getActivity(),
                                            args.getInt(ARG_NOT_GRANTED_MESSAGE),
                                            Toast.LENGTH_SHORT).show();
                                }
                            })
                    .create();
        }

    }

    private void requestTest() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                OkHttpClient okHttpClient = new OkHttpClient();

                Request.Builder builder = new Request.Builder();
                Request request = builder.url("http://date.jsontest.com/").build();

                try {
                    Response response = okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()) {
                        return response.body().string();

                    } else {
                        return "Not Success - code : " + response.code();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return "Error - " + e.getMessage();
                }
            }

            @Override
            protected void onPostExecute(String string) {
                super.onPostExecute(string);
                Log.d("ACTAPP", string);
            }
        }.execute();
    }

    private void requestCarbonFootprint(final File file, final String fileName) {
        Log.d("ACTAPP", "send file with path " + file.getAbsolutePath());
        new AsyncTask<Void, Void, RecognitionResult>() {
            @Override
            protected RecognitionResult doInBackground(Void... voids) {
                OkHttpClient okHttpClient = new OkHttpClient();

                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("title", "Square Logo")
                        .addFormDataPart("image", fileName,
                                RequestBody.create(MEDIA_TYPE_JPEG, file))
                        .build();

                Request request = new Request.Builder()
                        // .header("Authorization", "Client-ID " + IMGUR_CLIENT_ID)
                        .url("http://35.185.228.62:8080/api/scan")
                        .post(requestBody)
                        .build();

                try {
                    Response response = okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()) {
                        JSONObject jobject = new JSONObject(response.body().string());
                        RecognitionResult result = new RecognitionResult(jobject);
                        return result;

                    } else {
                        Log.d("ACTAPP", "response not successful");
                        return null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                } catch(JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(RecognitionResult result) {
                if(result != null) {
                    mLayoutCarbonScoreOverlay.setVisibility(View.VISIBLE);
                    mTxtProductName.setText("" + result.description + ", " + result.brand);
                    mTxtProductScore.setText("" + result.carbonScore);
                    mBtnShine.performClick();
                } else {
                    Toast.makeText(MainActivity.this,
                            "Cannot detect product, please try again.",
                            Toast.LENGTH_SHORT).show();
                }



            }
        }.execute();
    }


    @SuppressWarnings("NewApi")
    private void showMenu() {
        mMenuLayout.setVisibility(View.VISIBLE);

        List<Animator> animList = new ArrayList<>();

        for (int i = 0, len = mMenuLayout.getChildCount(); i < len; i++) {
            animList.add(createShowItemAnimator(mMenuLayout.getChildAt(i)));
        }

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(400);
        animSet.setInterpolator(new OvershootInterpolator());
        animSet.playTogether(animList);
        animSet.start();
    }

    @SuppressWarnings("NewApi")
    private void hideMenu() {

        List<Animator> animList = new ArrayList<>();

        for (int i = mMenuLayout.getChildCount() - 1; i >= 0; i--) {
            animList.add(createHideItemAnimator(mMenuLayout.getChildAt(i)));
        }

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(400);
        animSet.setInterpolator(new AnticipateInterpolator());
        animSet.playTogether(animList);
        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mMenuLayout.setVisibility(View.INVISIBLE);
            }
        });
        animSet.start();

    }

    private Animator createShowItemAnimator(View item) {

        float dx = mBtnTakePhoto.getX() - item.getX();
        float dy = mBtnTakePhoto.getY() - item.getY();

        item.setRotation(0f);
        item.setTranslationX(dx);
        item.setTranslationY(dy);

        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                item,
                AnimatorUtils.rotation(0f, 360f),
                AnimatorUtils.translationX(dx, 0f),
                AnimatorUtils.translationY(dy, 0f)
        );

        return anim;
    }

    private Animator createHideItemAnimator(final View item) {
        float dx = mBtnTakePhoto.getX() - item.getX();
        float dy = mBtnTakePhoto.getY() - item.getY();

        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                item,
                AnimatorUtils.rotation(360f, 0f),
                AnimatorUtils.translationX(0f, dx),
                AnimatorUtils.translationY(0f, dy)
        );

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                item.setTranslationX(0f);
                item.setTranslationY(0f);
            }
        });

        return anim;
    }

}
