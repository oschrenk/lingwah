package com.googlecode.lingwah.eclipse.ui.editors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.ui.editors.text.FileDocumentProvider;

public class LingwahDocumentProvider extends FileDocumentProvider {
	
	private EclipseGrammarAdapter _grammar;
	public LingwahDocumentProvider(EclipseGrammarAdapter grammar) {
		_grammar= grammar;
	}

	protected IDocument createDocument(Object element) throws CoreException {
		IDocument document = super.createDocument(element);
		if (document != null) {
			EclipseDocumentAdapter lingwahDocument= new EclipseDocumentAdapter(document, _grammar); 
			LingwahPartitionScanner scanner= new LingwahPartitionScanner(lingwahDocument);
			IDocumentPartitioner partitioner =
				new FastPartitioner(scanner, _grammar.getContentTypes());
			partitioner.connect(document);
			document.setDocumentPartitioner(partitioner);
		}
		return document;
	}
}