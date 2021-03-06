/*
 * Copyright (C) 2009-2013 Felix Bechstein
 * 
 * This file is part of CallMeter 3G.
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.muntashirakon.callmeter.data;

import androidx.collection.LruCache;

/**
 * {@link LruCache} holding number to name entries.
 *
 * @author flx
 */
public final class NameCache extends LruCache<String, String> {

    /**
     * Maximum cache size.
     */
    private static final int MAX_CACHE_SIZE = 100;

    /**
     * Single instance.
     */
    private static NameCache instance;

    /**
     * Get the cache.
     *
     * @return the single {@link NameCache} instance
     */
    public static NameCache getInstance() {
        if (instance == null) {
            instance = new NameCache();
        }
        return instance;
    }

    /**
     * hide public constructor.
     */
    private NameCache() {
        super(MAX_CACHE_SIZE);
    }

    /**
     * Return get(key) formatted with format.
     *
     * @param key    key
     * @param format format
     * @return formatted {@link String}
     */
    public String get(final String key, final String format) {
        String s = get(key);
        if (s == null) {
            return null;
        } else {
            return String.format(format, s);
        }
    }
}
