package com.jakeapp.gui.swing;

import com.jakeapp.gui.swing.callbacks.ContextViewChangedCallback;

import java.util.List;
import java.util.ArrayList;

@Deprecated
public class ContextViewChangedHolder {
	private List<ContextViewChangedCallback> contextViewChanged = new ArrayList<ContextViewChangedCallback>();

	public ContextViewChangedHolder() {
	}

	public ContextViewChangedHolder(List<ContextViewChangedCallback> contextViewChanged) {
		this.contextViewChanged = contextViewChanged;
	}

	public void addContextViewChangedListener(ContextViewChangedCallback pvc) {
		contextViewChanged.add(pvc);
	}

	public List<ContextViewChangedCallback> getContextViewChanged() {
		return contextViewChanged;
	}
}