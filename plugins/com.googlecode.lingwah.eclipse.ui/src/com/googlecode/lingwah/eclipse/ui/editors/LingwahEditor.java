package com.googlecode.lingwah.eclipse.ui.editors;

import org.eclipse.ui.editors.text.TextEditor;

import com.googlecode.lingwah.Grammar;

public class LingwahEditor extends TextEditor {

	private ColorManager colorManager;

	public LingwahEditor(Grammar grammar) {
		super();
		colorManager = new ColorManager();
		setSourceViewerConfiguration(new XMLConfiguration(colorManager));
		setDocumentProvider(new LingwahDocumentProvider(grammar));
	}
	public void dispose() {
		colorManager.dispose();
		super.dispose();
	}

}
