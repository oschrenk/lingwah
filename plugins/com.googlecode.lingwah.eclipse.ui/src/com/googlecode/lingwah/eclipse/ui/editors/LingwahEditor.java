package com.googlecode.lingwah.eclipse.ui.editors;

import org.eclipse.ui.editors.text.TextEditor;

import com.googlecode.lingwah.Grammar;

public class LingwahEditor extends TextEditor {

	private EclipseGrammarAdapter _grammar;

	public LingwahEditor(Grammar grammar) {
		super();
		_grammar= new EclipseGrammarAdapter(grammar);
		setSourceViewerConfiguration(new LingwahConfiguration(_grammar));
		setDocumentProvider(new LingwahDocumentProvider(_grammar));
	}
	public void dispose() {
		try {
			_grammar.dispose();
		}
		finally {
			super.dispose();
		}
	}
}
