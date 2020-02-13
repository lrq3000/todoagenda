/*
 * Copyright (c) 2019 yvolk (Yuri Volkov), http://yurivolkov.com
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

package org.andstatus.todoagenda.prefs.dateformat;

import android.content.Context;
import android.text.format.DateUtils;

import org.andstatus.todoagenda.R;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Instant;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

public class DateFormatter {
    private static final char NUMBER_OF_DAYS_LETTER = 'b';
    private final Context context;
    private final DateFormatValue dateFormatValue;
    private final DateTime now;
    Locale locale = Locale.getDefault();

    public DateFormatter(Context context, DateFormatValue dateFormatValue, DateTime now) {
        this.context = context;
        this.dateFormatValue = dateFormatValue;
        this.now = now;
    }

    public CharSequence formatMillis(long millis) {
        try {
            if(dateFormatValue.hasPattern()) {
                return formatDateCustom(millis, dateFormatValue.getPattern());
            }

            switch (dateFormatValue.type) {
                case HIDDEN:
                    return "";
                case DEVICE_DEFAULT:
                    return formatDateTime(millis, DateUtils.FORMAT_SHOW_DATE);
                case DEFAULT_WEEKDAY:
                    return formatDateTime(millis, DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_WEEKDAY);
                case ABBREVIATED:
                    return formatDateTime(millis, DateUtils.FORMAT_ABBREV_ALL | DateUtils.FORMAT_SHOW_DATE |
                            DateUtils.FORMAT_SHOW_WEEKDAY);
                case DEFAULT_DAYS:
                    return getDaysFromTodayString(context, 5, getDaysFromToday(millis)) + ", " +
                            formatDateTime(millis, DateUtils.FORMAT_SHOW_DATE);
                case NUMBER_OF_DAYS:
                    return getDaysFromTodayString(context, 5, getDaysFromToday(millis));
                default:
                    return "(not implemented)";
            }
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
    }

    private String formatDateTime(long millis, int flags) {
        return DateUtils.formatDateRange(context,
                new Formatter(new StringBuilder(50), locale),
                millis,
                millis,
                flags,
                now.getZone().getID())
                .toString();
    }

    public static CharSequence getDaysFromTodayString(Context context, int formatLength, int daysFromToday) {
        if (formatLength > 4) {
            switch (daysFromToday) {
                case -1:
                    return context.getText(R.string.yesterday);
                case 0:
                    return context.getText(R.string.today);
                case 1:
                    return context.getText(R.string.tomorrow);
                default:
                    break;
            }
        }
        return Math.abs(daysFromToday) > 9999 ? "..." : Integer.toString(daysFromToday);
    }

    public int getDaysFromToday(long millis) {
        return Days.daysBetween(now.withTimeAtStartOfDay(),
                Instant.ofEpochMilli(millis)).getDays();
    }

    private String formatDateCustom(long millis, String pattern) {
        try {
            String pattern2 = preProcessNumberOfDaysToEvent(millis, pattern);
            SimpleDateFormat format = new SimpleDateFormat(pattern2, locale);
            Date date = new Date(millis);
            return format.format(date);
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
    }

    private String preProcessNumberOfDaysToEvent(long millis, String pattern) {
        int ind1 = pattern.indexOf(NUMBER_OF_DAYS_LETTER);
        if (ind1 < 0) return pattern;
        int ind2 = ind1;
        while (ind2 < pattern.length() && pattern.charAt(ind2) == NUMBER_OF_DAYS_LETTER) {
            ind2++;
        }
        CharSequence result = getDaysFromTodayString(context, ind2 - ind1, getDaysFromToday(millis));
        return (ind1 > 0 ? pattern.substring(0, ind1) : "") +
               "'" + result + "'" +
               (ind2 < pattern.length() ? pattern.substring(ind2) : "");
    }

}