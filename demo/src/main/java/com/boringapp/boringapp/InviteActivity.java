package com.boringapp.boringapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;

public class InviteActivity extends AppCompatActivity {

    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

        Button button = (Button) findViewById(R.id.button2);

        callbackManager = CallbackManager.Factory.create();

        final InviteActivity _self = this;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                String appLinkUrl, previewImageUrl;

                appLinkUrl = "https://fb.me/1957657991151520";
                previewImageUrl = "https://www.mydomain.com/my_invite_image.jpg";

                if (AppInviteDialog.canShow()) {
                    AppInviteContent content = new AppInviteContent.Builder()
                            .setApplinkUrl(appLinkUrl)
                            .setPreviewImageUrl(previewImageUrl)
                            .build();
                    AppInviteDialog appInviteDialog = new AppInviteDialog(_self);
                    appInviteDialog.registerCallback(callbackManager, new FacebookCallback<AppInviteDialog.Result>(){
                        @Override
                        public void onSuccess(AppInviteDialog.Result result) {
                            Log.i("Facebook Invite", "Success");
                            Intent intent = new Intent(InviteActivity.this, MainActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onCancel() {

                        }

                        @Override
                        public void onError(FacebookException error) {

                        }
                    });
                    appInviteDialog.show(content);
                }

            }
        });

    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
