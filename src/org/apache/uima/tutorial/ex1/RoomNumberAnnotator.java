/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.uima.tutorial.ex1;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.tutorial.RoomNumber;
import org.apache.uima.util.Level;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Example annotator that detects room numbers using Java 1.4 regular expressions.
 *
 * <pre>
 * [Room Numbering Scheme]
 *
 * Yorktown patterns:
 * 	 20-001, 31-206, 04-123     (Regular Expression Pattern: ##-[0-2]##)
 *
 * Hawthorne patterns:
 * 	 GN-K35, 1S-L07, 4N-B21     (Regular Expression Pattern: [G1-4][NS]-[A-Z]##)
 * </pre>
 *
 * @see {@link org.apache.uima.analysis_component.AnalysisComponent}
 */
public class RoomNumberAnnotator extends JCasAnnotator_ImplBase {

	private Pattern mYorktownPattern = Pattern.compile("\\b[0-4]\\d-[0-2]\\d\\d\\b");

	private Pattern mHawthornePattern = Pattern.compile("\\b[G1-4][NS]-[A-Z]\\d\\d\\b");

	@Override
	public void initialize(UimaContext context) throws ResourceInitializationException {
	}

	@Override
	public void destroy() {
	}

	public void process(JCas aJCas) {
		// 분석할 Documment 획득
		String docText = aJCas.getDocumentText();

		// Yorktown Room Number 검색
		Matcher matcher = mYorktownPattern.matcher(docText);
		while (matcher.find()) {

			// 1개를 찾아서 Annotation을 생성
			RoomNumber annotation = new RoomNumber(aJCas);
			annotation.setBegin(matcher.start());
			annotation.setEnd(matcher.end());
			annotation.setBuilding("Yorktown");

			// CAS에서 관리하는 인덱스에 추가
			annotation.addToIndexes();
		}

		// 1개를 찾아서 Annotation을 생성
		matcher = mHawthornePattern.matcher(docText);
		while (matcher.find()) {
			// found one - create annotation
			RoomNumber annotation = new RoomNumber(aJCas);
			annotation.setBegin(matcher.start());
			annotation.setEnd(matcher.end());
			annotation.setBuilding("Hawthorne");

			// CAS에서 관리하는 인덱스에 추가
			annotation.addToIndexes();
		}
	}

}
