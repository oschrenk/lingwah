package com.googlecode.lingwah.eclipse.ui.editors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.text.rules.ITokenScanner;
import org.eclipse.ui.editors.text.FileDocumentProvider;

import com.googlecode.lingwah.Grammar;

public class LingwahDocumentProvider extends FileDocumentProvider {
	
	private Grammar _grammar;
	public LingwahDocumentProvider(Grammar grammar) {
		_grammar= grammar;
	}

	protected IDocument createDocument(Object element) throws CoreException {
		ITokenScanner kk; 
		IDocument document = super.createDocument(element);
		if (document != null) {
			LingwahPartitionScanner lingwahScanner= 
					new LingwahPartitionScanner(_grammar); 
			IDocumentPartitioner partitioner =
				new FastPartitioner(
						lingwahScanner,
						lingwahScanner.getLegalContentTypes());
			partitioner.connect(document);
			document.setDocumentPartitioner(partitioner);
		}
		return document;
	}
}