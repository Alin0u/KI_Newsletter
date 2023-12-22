import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TextProcessingService {

  constructor() { }

  /**
   * Processes the provided text to extract a subject specified after a 'subject:' keyword
   * and prepares the remaining text for display by replacing line breaks with HTML <br> tags.
   * It also removes any immediate punctuation following the extracted subject.
   *
   * @param outputText The text to be processed.
   * @returns An object containing the extracted subject and the updated text.
   */
  processOutputTextForSubject(outputText: string): { subject: string, updatedOutput: string } {
    const subjectKeyword = "subject:";
    const subjectIndex = outputText.toLowerCase().indexOf(subjectKeyword);
    let extractedSubject = '';
    let updatedOutput = outputText;

    if (subjectIndex !== -1) {
      const startIndexOfSubject = subjectIndex + subjectKeyword.length;
      const textAfterSubject = outputText.substring(startIndexOfSubject);

      const regex = /^[^,.!?;:\n]+/;
      const match = textAfterSubject.match(regex);

      if (match && match[0]) {
        extractedSubject = match[0].trim();
        let endIndexOfSubject = startIndexOfSubject + match[0].length;

        const nextChar = outputText.charAt(endIndexOfSubject);
        if (nextChar.match(/[,.!?;]/)) {
          endIndexOfSubject += 1;
        }

        const textBeforeSubject = outputText.substring(0, subjectIndex);
        const textAfterSubjectRemoved = outputText.substring(endIndexOfSubject);
        updatedOutput = textBeforeSubject + textAfterSubjectRemoved.replace(/\n/g, '<br>');
      }
    }

    return { subject: extractedSubject, updatedOutput: updatedOutput };
  }
}
