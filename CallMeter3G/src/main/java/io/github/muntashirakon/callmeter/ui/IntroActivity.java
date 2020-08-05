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
package io.github.muntashirakon.callmeter.ui;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import java.util.Objects;

import io.github.muntashirakon.callmeter.R;
import io.github.muntashirakon.callmeter.ui.prefs.Preferences;
import de.ub0r.android.lib.Utils;

/**
 * Display Help Activity.
 *
 * @author flx
 */
public final class IntroActivity extends AppCompatActivity implements OnClickListener {

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        setTheme(Preferences.getTheme(this));
        Utils.setLocale(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);
        setTitle(R.string.intro_);
        Objects.requireNonNull(getSupportActionBar()). setDisplayHomeAsUpEnabled(true);
        findViewById(R.id.ok).setOnClickListener(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onClick(@NonNull final View v) {
        if (v.getId() == R.id.ok) {
            finish();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
