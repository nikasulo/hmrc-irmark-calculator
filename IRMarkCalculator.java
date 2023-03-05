package uk.gov.hmrc.mark;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * IRMarkCalculator
 * Extends MarkCalculator and provides the algorithm and main method for a GovTalk message
 */
public class IRMarkCalculator extends MarkCalculator {

	
	public String getAlgorithm()
	{
		// Run a transform over the input to extract the fragment to be digested.
		// This is done by setting up a Transforms object from a Template and then 
		// executing against the input document.
		// The transforms to be performed are specified by using the template XML below.
		
	   	String transformStr =
        "<?xml version='1.0'?>\n"
      	+ "<dsig:Transforms xmlns:dsig='http://www.w3.org/2000/09/xmldsig#' xmlns:gt='http://www.govtalk.gov.uk/CM/envelope'>\n"
      	+ "<dsig:Transform Algorithm='http://www.w3.org/TR/1999/REC-xpath-19991116'>\n"
      	+ "<dsig:XPath>\n"
        + "(count(ancestor-or-self::node()|/gt:GovTalkMessage/gt:Body)=count(ancestor-or-self::node()))\n"
        + " and \n"
        + "(count(ancestor-or-self::node()|/gt:GovTalkMessage/gt:Body/*[name()='IRenvelope']/*[name()='IRheader']/*[name()='IRmark'])!=count(ancestor-or-self::node()))\n"
        + "</dsig:XPath>\n"		
      	+ "</dsig:Transform>\n"
      	+ "<dsig:Transform Algorithm='http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments'/>\n"
      	+ "</dsig:Transforms>\n"
      	;

      	return transformStr;
	}

	/**
	 * Generate and print the mark.
	 * 
	 * @param args
	 *            1: filename of the input document
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception {  

		// validate arguments
		if (args.length != 1) {
			System.out.println("Usage: IRMarkCalculator <file>");
			return;
		}

		File newFile = new File(args[0]);

		FileInputStream fis = null;
		try {
			fis = new FileInputStream(newFile);
		} catch (FileNotFoundException e) {
			System.out.println("The file " + newFile + " could not be opened.");
			return;
		}

		final MarkCalculator mc = new IRMarkCalculator();

		
		System.out.println();			
		System.out.println("IRMark for file " + newFile.getName());			
		System.out.println();
		System.out.println("IRMARK_VALUE:{" + mc.createMark(fis) + "}");
		System.out.println();
	}


}
