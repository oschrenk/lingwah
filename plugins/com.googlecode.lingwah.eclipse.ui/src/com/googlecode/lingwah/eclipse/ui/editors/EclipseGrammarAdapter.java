package com.googlecode.lingwah.eclipse.ui.editors;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;

import com.googlecode.lingwah.Grammar;

public class EclipseGrammarAdapter {
	private Grammar _grammar;
	private ColorManager _colorManager= new ColorManager();
	
	EclipseGrammarAdapter(Grammar grammar) {
		_grammar= grammar;
	}
	
	public Grammar getGrammar() {
		return _grammar;
	}
	

	public String[] getContentTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();

		DefaultDamagerRepairer dr =
			new DefaultDamagerRepairer(getXMLTagScanner());
		reconciler.setDamager(dr, LingwahPartitionScanner.XML_TAG);
		reconciler.setRepairer(dr, LingwahPartitionScanner.XML_TAG);

		dr = new DefaultDamagerRepairer(getXMLScanner());
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

		NonRuleBasedDamagerRepairer ndr =
			new NonRuleBasedDamagerRepairer(
				new TextAttribute(
					_colorManager.getColor(LingwahColorConstants.XML_COMMENT)));
		reconciler.setDamager(ndr, LingwahPartitionScanner.XML_COMMENT);
		reconciler.setRepairer(ndr, LingwahPartitionScanner.XML_COMMENT);

		return reconciler;
	}
	
	public void dispose() {
		_colorManager.dispose();
	}

}
