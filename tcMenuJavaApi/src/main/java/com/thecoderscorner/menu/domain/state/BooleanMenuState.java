/*
 * Copyright (c)  2016-2019 https://www.thecoderscorner.com (Dave Cherry).
 * This product is licensed under an Apache license, see the LICENSE file in the top-level directory.
 *
 */

package com.thecoderscorner.menu.domain.state;

import com.thecoderscorner.menu.domain.MenuItem;

/**
 * An implementation of menu state for booleans. This stores the current value in the MenuTree for an item
 */
public class BooleanMenuState extends MenuState<Boolean> {
    public BooleanMenuState(MenuItem item, boolean changed, boolean active, boolean value) {
        super(StateStorageType.BOOLEAN, item, changed, active, value);
    }
}
