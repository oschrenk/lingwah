package com.googlecode.lingwah.annotations;

import com.googlecode.lingwah.Grammar;


public @interface Processes {
	Class<? extends Grammar> value();
}
