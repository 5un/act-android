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

package com.boringapp.boringapp.data;

/**
 * Created by Sun on 6/11/2017 AD.
 */

public class Reward {

    public String name;
    public String brand;
    public int points;
    public String imageUrl;

    public Reward(String name, String brand, int points, String imageUrl) {
        this.name = name;
        this.brand = brand;
        this.points = points;
        this.imageUrl = imageUrl;
    }

}
