package com.googlecode.lingwah.eclipse.ui.editors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.ui.editors.text.FileDocumentProvider;

import com.googlecode.lingwah.Document;
import com.googlecode.lingwah.ParseContext;

public class LingwahDocumentProvider extends FileDocumentProvider {
	
	private EclipseGrammarAdapter _grammar;
	public LingwahDocumentProvider(EclipseGrammarAdapter grammar) {
		_grammar= grammar;
	}

	protected IDocument createDocument(Object element) throws CoreException {
		IDocument document = super.createDocument(element);
		if (document != null) {
			Document lingwahDocument= new EclipseDocumentAdapter(document); 
			ParseContext parseContext= new ParseContext(lingwahDocument);
			LingwahPartitionScanner scanner= new LingwahPartitionScanner(parseContext, _grammar);
			IDocumentPartitioner partitioner =
				new FastPartitioner(scanner, _grammar.getContentTypes());
			partitioner.connect(document);
			document.setDocumentPartitioner(partitioner);
		}
		return document;
	}
}