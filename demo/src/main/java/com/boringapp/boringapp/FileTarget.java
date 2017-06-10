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

/**
 * Created by Sun on 6/10/2017 AD.
 */

import android.graphics.Bitmap;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.FileOutputStream;
import java.io.IOException;

public class FileTarget extends SimpleTarget<Bitmap> {
    public FileTarget(String fileName, int width, int height) {
        this(fileName, width, height, Bitmap.CompressFormat.JPEG, 70);
    }
    public FileTarget(String fileName, int width, int height, Bitmap.CompressFormat format, int quality) {
        super(width, height);
        this.fileName = fileName;
        this.format = format;
        this.quality = quality;
    }
    String fileName;
    Bitmap.CompressFormat format;
    int quality;

    @Override
    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
        try {
            FileOutputStream out = new FileOutputStream(fileName);
            bitmap.compress(format, quality, out);
            out.flush();
            out.close();
            onFileSaved();
        } catch (IOException e) {
            e.printStackTrace();
            onSaveException(e);
        }
    }

    public void onFileSaved() {
        // do nothing, should be overriden (optional)
    }
    public void onSaveException(Exception e) {
        // do nothing, should be overriden (optional)
    }

}