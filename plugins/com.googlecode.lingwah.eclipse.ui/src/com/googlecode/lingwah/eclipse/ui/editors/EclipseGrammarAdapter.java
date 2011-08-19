package com.googlecode.lingwah.eclipse.ui.editors;

import java.util.List;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;

import com.googlecode.lingwah.Grammar;
import com.googlecode.lingwah.Parser;

public class EclipseGrammarAdapter {
	private Grammar _grammar;
	private ColorManager _colorManager= new ColorManager();
	private String[] _contentTypes;
	
	EclipseGrammarAdapter(Grammar grammar) {
		_grammar= grammar;
		List<Parser> parsers= grammar.getDeclaredParsers();
		_contentTypes= new String[parsers.size()+1];
		int i= 0;
		_contentTypes[i++]= IDocument.DEFAULT_CONTENT_TYPE;
		for (Parser parser:parsers)
			_contentTypes[i++]= parser.getLabel();
	}
	
	public Grammar getGrammar() {
		return _grammar;
	}
	

	public String[] getContentTypes() {
		return _contentTypes;
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
