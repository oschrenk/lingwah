package com.googlecode.lingwah.eclipse.ui.editors;

import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

public class LingwahConfiguration extends SourceViewerConfiguration {
	private LingwahDoubleClickStrategy doubleClickStrategy;
	private EclipseGrammarAdapter _grammar;

	public LingwahConfiguration(EclipseGrammarAdapter grammar) {
		_grammar= grammar;
	}
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return _grammar.getContentTypes();
	}
	public ITextDoubleClickStrategy getDoubleClickStrategy(
		ISourceViewer sourceViewer,
		String contentType) {
		if (doubleClickStrategy == null)
			doubleClickStrategy = new LingwahDoubleClickStrategy();
		return doubleClickStrategy;
	}


	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		return _grammar.getPresentationReconciler(sourceViewer);
	}

}