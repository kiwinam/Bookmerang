/*
 * Copyright (C) 2015 The Android Open Source Project
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

package rowan.bookmerang;

import java.util.Random;

public class BooksImage {

    private static final Random RANDOM = new Random();

    public static int getRandomBookDrawable() {
        switch (RANDOM.nextInt(11)) {
            default:
            case 0:
                return R.drawable.book1;
            case 1:
                return R.drawable.book2;
            case 2:
                return R.drawable.book3;
            case 3:
                return R.drawable.book4;
            case 4:
                return R.drawable.book5;
            case 5:
                return R.drawable.book6;
            case 6:
                return R.drawable.book7;
            case 7:
                return R.drawable.book8;
            case 8:
                return R.drawable.book9;
            case 9:
                return R.drawable.book10;
            case 10:
                return R.drawable.book11;
            case 11:
                return R.drawable.book12;
        }
    }
}
